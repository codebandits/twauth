package com.twauth.api.login

import com.fasterxml.jackson.databind.ObjectMapper
import io.github.codebandits.results.Failure
import io.github.codebandits.results.Success
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.socket.TextMessage
import twitter4j.StallWarning
import twitter4j.Status
import twitter4j.StatusDeletionNotice
import twitter4j.StatusListener
import java.lang.Exception

@Component
class LoginTweetProcessor(
        private val loginClientManager: LoginClientManager,
        @Value("\${hashtag}")
        private val hashtag: String
) : StatusListener {

    override fun onTrackLimitationNotice(numberOfLimitedStatuses: Int) {
    }

    override fun onStallWarning(warning: StallWarning) {
    }

    override fun onException(ex: Exception) {
    }

    override fun onDeletionNotice(statusDeletionNotice: StatusDeletionNotice) {
    }

    override fun onStatus(status: Status) {
        val message = status.text.replace("#$hashtag", "").trim()
        val user = loginClientManager.get(message)
        when (user) {
            is Success -> {
                val authentication = LoginMessage.Payload.Authentication(token = "batman", handle = status.user.screenName)
                val loginMessage = LoginMessage(messageType = LoginMessage.MessageType.AUTHENTICATION, payload = authentication)
                val json = ObjectMapper().writeValueAsString(loginMessage)
                user.content.session.sendMessage(TextMessage(json))
            }
        }
    }

    override fun onScrubGeo(userId: Long, upToStatusId: Long) {
    }
}
