package com.twauth.api.login

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.same
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession

class LoginWebSocketHandlerTest {

    private val loginClientManager: LoginClientManager = mock()
    private val subject = LoginWebSocketHandler(loginClientManager)

    private val session: WebSocketSession = mock {
        on { id } doReturn "0"
    }

    @Nested
    inner class `when a connection is established` {

        private val loginInstructions = LoginMessage.Data.TweetRequest(
                text = "I want to login",
                hashtag = "please"
        )

        @BeforeEach
        fun setUp() {
            whenever(loginClientManager.register(any())).thenReturn(loginInstructions)
        }

        @Test
        fun `it should register the session`() {
            subject.afterConnectionEstablished(session)

            verify(loginClientManager).register(same(session))
        }

        @Test
        fun `it should return send the login instructions`() {
            subject.afterConnectionEstablished(session)

            argumentCaptor<TextMessage>().apply {
                verify(session).sendMessage(capture())
                val expectedMessage = """{"messageType":"TWEET_REQUEST","data":{"text":"I want to login","hashtag":"please"}}"""
                assertThat(String(firstValue.asBytes()), equalTo(expectedMessage))
            }
        }
    }

    @Nested
    inner class `when a connection is closed` {

        @Test
        fun `it should remove the session`() {
            subject.afterConnectionClosed(session, CloseStatus.NORMAL)

            verify(loginClientManager).remove(same(session))
        }
    }
}
