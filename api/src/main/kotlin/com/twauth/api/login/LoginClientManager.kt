package com.twauth.api.login

import io.github.codebandits.results.Result
import org.springframework.web.socket.WebSocketSession

interface LoginClientManager {
    fun register(session: WebSocketSession): LoginMessage.Data.TweetRequest
    fun get(text: String): Result<LoginClientNotFound, LoginClient>
    fun remove(session: WebSocketSession): Result<LoginClientNotFound, LoginClientRemoved>

    object LoginClientRemoved
    object LoginClientNotFound
}
