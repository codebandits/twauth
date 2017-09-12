package com.twauth.api.login

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import twitter4j.FilterQuery
import twitter4j.TwitterStream
import twitter4j.TwitterStreamFactory
import twitter4j.conf.ConfigurationBuilder

@Component
class LoginTweetStreamer(
        @Value("\${twitter.consumer-key}") private val consumerKey: String,
        @Value("\${twitter.consumer-secret}") private val consumerSecret: String,
        @Value("\${twitter.access-token}") private val accessToken: String,
        @Value("\${twitter.access-token-secret}") private val accessTokenSecret: String,
        @Value("\${hashtag}") private val hashtag: String,
        private val loginTweetProcessor: LoginTweetProcessor
) {

    private val logger = LoggerFactory.getLogger(javaClass)

    private val filter: FilterQuery = FilterQuery("#" + hashtag)
    private val configuration: twitter4j.conf.Configuration
    private lateinit var stream: TwitterStream

    init {
        val cb = ConfigurationBuilder()
        cb.setOAuthConsumerKey(consumerKey)
        cb.setOAuthConsumerSecret(consumerSecret)
        cb.setOAuthAccessToken(accessToken)
        cb.setOAuthAccessTokenSecret(accessTokenSecret)
        configuration = cb.build()
    }

    @EventListener(ContextRefreshedEvent::class)
    fun streamLoginTweets() {
        logger.info("creating twitter stream for #{}", hashtag)

        stream = TwitterStreamFactory(configuration).instance
        stream.addListener(loginTweetProcessor)
        stream.filter(filter)
    }
}
