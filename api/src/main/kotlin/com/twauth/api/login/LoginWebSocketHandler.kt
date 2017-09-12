package com.twauth.api.login

import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler

class LoginWebSocketHandler(private val loginClientManager: LoginClientManager) : TextWebSocketHandler() {

    private val logger = LoggerFactory.getLogger(javaClass)

    override fun afterConnectionEstablished(session: WebSocketSession) {
        val loginInstructions = loginClientManager.register(session)
        logger.info("login connection #${session.id.toLong(16)} opened with message ${loginInstructions.message}")
        sendLoginInstructions(loginInstructions, session)
        super.afterConnectionEstablished(session)
    }

    override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
        loginClientManager.remove(session)
        logger.info("login connection #${session.id.toLong(16)} closed")
        super.afterConnectionClosed(session, status)
    }

    private fun sendLoginInstructions(loginInstructions: LoginMessage.Payload.TweetRequest, session: WebSocketSession) {
        val message = LoginMessage(messageType = LoginMessage.MessageType.TWEET_REQUEST, payload = loginInstructions)
        val json = ObjectMapper().writeValueAsString(message)
        session.sendMessage(TextMessage(json))
    }
}
