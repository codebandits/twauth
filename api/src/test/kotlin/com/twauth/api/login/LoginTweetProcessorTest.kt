package com.twauth.api.login

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.github.codebandits.results.Failure
import io.github.codebandits.results.Success
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import twitter4j.Status
import twitter4j.User

class LoginTweetProcessorTest {

    private val loginClientManager: LoginClientManager = mock()
    private val hashtag = "bats"
    private val subject = LoginTweetProcessor(loginClientManager, hashtag)

    private val session: WebSocketSession = mock()

    private val user: User = mock {
        on { screenName } doReturn "bruce"
    }

    private val message = "login message"

    private val status: Status = mock {
        on { text } doReturn "$message #$hashtag"
        on { user } doReturn user
    }

    @Nested
    inner class `when the Tweet matches a session` {

        @BeforeEach
        fun setUp() {
            val client = LoginClient(message = message, session = session)
            whenever(loginClientManager.get(any())).thenReturn(Success(client))
        }

        @Test
        fun `it should send the authentication message`() {
            subject.onStatus(status)

            verify(loginClientManager).get(message)

            argumentCaptor<TextMessage>().apply {
                verify(session).sendMessage(capture())
                val expectedMessage = """{"messageType":"AUTHENTICATION","payload":{"token":"batman","handle":"bruce"}}"""
                MatcherAssert.assertThat(String(firstValue.asBytes()), Matchers.equalTo(expectedMessage))
            }
        }
    }

    @Nested
    inner class `when the Tweet does not match a session` {

        @BeforeEach
        fun setUp() {
            whenever(loginClientManager.get(any())).thenReturn(Failure(LoginClientManager.LoginClientNotFound))
        }

        @Test
        fun `it should send the authentication message`() {
            subject.onStatus(status)

            verify(session, times(0)).sendMessage(any())
        }
    }
}
