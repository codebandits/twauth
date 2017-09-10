package com.twauth.api.login

import io.github.codebandits.results.Failure
import io.github.codebandits.results.Result
import io.github.codebandits.results.Success
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.socket.WebSocketSession
import java.util.*

@Component
class LoginClientManager(
        @Value("\${hashtag}")
        private val hashtag: String
) {

    private val loginHashtag = hashtag
    private val clients = mutableListOf<LoginClient>()

    fun register(session: WebSocketSession): LoginMessage.Payload.TweetRequest {

        val message = UUID.randomUUID().toString()

        val client = LoginClient(
                message = message,
                session = session
        )

        clients.add(client)

        return LoginMessage.Payload.TweetRequest(
                message = message,
                hashtag = loginHashtag
        )
    }

    fun get(message: String): Result<LoginClientNotFound, LoginClient> {
        return clients
                .find { it.message == message }
                ?.let { Success<LoginClientNotFound, LoginClient>(it) }
                ?: Failure(LoginClientNotFound)
    }

    fun remove(session: WebSocketSession): Result<LoginClientNotFound, LoginClientRemoved> {
        return clients
                .find { it.session == session }
                ?.let {
                    clients.remove(it)
                    Success<LoginClientNotFound, LoginClientRemoved>(LoginClientRemoved)
                }
                ?: Failure(LoginClientNotFound)
    }

    object LoginClientRemoved
    object LoginClientNotFound
}
