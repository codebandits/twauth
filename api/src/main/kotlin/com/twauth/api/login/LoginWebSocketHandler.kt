package com.twauth.api.login

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler

class LoginWebSocketHandler(private val loginClientManager: LoginClientManager) : TextWebSocketHandler() {

    override fun afterConnectionEstablished(session: WebSocketSession) {
        val loginInstructions = loginClientManager.register(session)
        sendLoginInstructions(loginInstructions, session)
        super.afterConnectionEstablished(session)
    }

    override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
        loginClientManager.remove(session)
        super.afterConnectionClosed(session, status)
    }

    private fun sendLoginInstructions(loginInstructions: LoginMessage.Payload.TweetRequest, session: WebSocketSession) {
        val message = LoginMessage(messageType = LoginMessage.MessageType.TWEET_REQUEST, payload = loginInstructions)
        val json = ObjectMapper().writeValueAsString(message)
        session.sendMessage(TextMessage(json))
    }
}
