package com.twauth.api.login

import com.nhaarman.mockito_kotlin.mock
import com.twauth.api.login.LoginClientManager
import io.github.codebandits.results.failsAnd
import io.github.codebandits.results.succeedsAnd
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.isEmptyString
import org.hamcrest.Matchers.not
import org.hamcrest.Matchers.sameInstance
import org.junit.jupiter.api.Test
import org.springframework.web.socket.WebSocketSession

class LoginClientManagerTest {

    private val hashtag = "auth"
    private val subject = LoginClientManager(hashtag)
    private val session: WebSocketSession = mock()

    @Test
    fun `register should provide LoginInstructions`() {
        val loginInstructions = subject.register(session)

        assertThat(loginInstructions.message, not(isEmptyString()))
        assertThat(loginInstructions.hashtag, not(isEmptyString()))
    }

    @Test
    fun `get should retrieve the WebSocketSession`() {
        val loginInstructions = subject.register(session)

        subject.get(loginInstructions.message) succeedsAnd {
            assertThat(it.session, sameInstance(session))
        }
    }

    @Test
    fun `get should return Failure if client doesn't exist`() {
        subject.get("batman") failsAnd {}
    }

    @Test
    fun `remove should succeed when the session is registered`() {
        subject.register(session)

        subject.remove(session) succeedsAnd {}
    }

    @Test
    fun `remove should remove the client when the session is registered`() {
        val loginInstructions = subject.register(session)
        subject.remove(session)

        subject.get(loginInstructions.message) failsAnd {}
    }

    @Test
    fun `remove should fail when the session is not registered`() {
        subject.remove(session) failsAnd {}
    }
}
