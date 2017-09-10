package com.twauth.api

import com.twauth.api.login.LoginClientManager
import com.twauth.api.login.LoginWebSocketHandler
import org.springframework.context.annotation.Configuration
import org.springframework.web.socket.config.annotation.EnableWebSocket
import org.springframework.web.socket.config.annotation.WebSocketConfigurer
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry

@Configuration
@EnableWebSocket
class WebSocketConfiguration(private val loginClientManager: LoginClientManager) : WebSocketConfigurer {

    override fun registerWebSocketHandlers(registry: WebSocketHandlerRegistry) {
        registry.addHandler(LoginWebSocketHandler(loginClientManager), "/login").setAllowedOrigins("*")
    }
}
