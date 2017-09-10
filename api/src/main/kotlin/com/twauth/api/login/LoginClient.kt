package com.twauth.api.login

import org.springframework.web.socket.WebSocketSession

data class LoginClient(
        val message: String,
        val session: WebSocketSession
)
