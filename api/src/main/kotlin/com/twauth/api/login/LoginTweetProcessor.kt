package com.twauth.api.login

import com.fasterxml.jackson.databind.ObjectMapper
import io.github.codebandits.results.Failure
import io.github.codebandits.results.Success
import org.slf4j.LoggerFactory
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

    private val logger = LoggerFactory.getLogger(javaClass)

    override fun onTrackLimitationNotice(numberOfLimitedStatuses: Int) {
        logger.warn("received TrackLimitationNotice: {}", numberOfLimitedStatuses)
    }

    override fun onStallWarning(warning: StallWarning) {
        logger.warn("received StallWarning", warning)
    }

    override fun onException(ex: Exception) {
        logger.error("received Exception", ex)
    }

    override fun onDeletionNotice(statusDeletionNotice: StatusDeletionNotice) {
        logger.info("received DeletionNotice")
    }

    override fun onStatus(status: Status) {
        logger.info("""received Status: "{}"""", status.text)

        val message = status.text.replace("#$hashtag", "").trim()
        val user = loginClientManager.get(message)
        when (user) {
            is Success -> {
                logger.info("""received login tweet "{}" from user {}""", user.content.message, status.user.screenName)

                val authentication = LoginMessage.Payload.Authentication(token = "batman", handle = status.user.screenName)
                val loginMessage = LoginMessage(messageType = LoginMessage.MessageType.AUTHENTICATION, payload = authentication)
                val json = ObjectMapper().writeValueAsString(loginMessage)
                user.content.session.sendMessage(TextMessage(json))
            }
            is Failure -> logger.info("""received unexpected tweet "{}" from user {}""", message, status.user.screenName)
        }
    }

    override fun onScrubGeo(userId: Long, upToStatusId: Long) {
        logger.info("received ScrubGeo: {} {}", userId, upToStatusId)
    }
}
