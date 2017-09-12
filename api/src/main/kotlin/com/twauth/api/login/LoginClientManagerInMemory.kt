package com.twauth.api.login

import io.github.codebandits.results.Failure
import io.github.codebandits.results.Result
import io.github.codebandits.results.Success
import org.springframework.web.socket.WebSocketSession
import java.util.*

class LoginClientManagerInMemory(private val loginHashtag: String) : LoginClientManager {

    private val clients = mutableListOf<LoginClient>()

    override fun register(session: WebSocketSession): LoginMessage.Data.TweetRequest {

        val tweetText = UUID.randomUUID().toString()

        val client = LoginClient(
                tweetText = tweetText,
                session = session
        )

        clients.add(client)

        return LoginMessage.Data.TweetRequest(
                text = tweetText,
                hashtag = loginHashtag
        )
    }

    override fun get(text: String): Result<LoginClientManager.LoginClientNotFound, LoginClient> {
        return clients
                .find { it.tweetText == text }
                ?.let { Success<LoginClientManager.LoginClientNotFound, LoginClient>(it) }
                ?: Failure(LoginClientManager.LoginClientNotFound)
    }

    override fun remove(session: WebSocketSession): Result<LoginClientManager.LoginClientNotFound, LoginClientManager.LoginClientRemoved> {
        return clients
                .find { it.session == session }
                ?.let {
                    clients.remove(it)
                    Success<LoginClientManager.LoginClientNotFound, LoginClientManager.LoginClientRemoved>(LoginClientManager.LoginClientRemoved)
                }
                ?: Failure(LoginClientManager.LoginClientNotFound)
    }


}
