package com.twauth.api.login

data class LoginMessage(
        val messageType: MessageType,
        val payload: Payload
) {
    enum class MessageType { TWEET_REQUEST, AUTHENTICATION }
    sealed class Payload {
        data class TweetRequest(val message: String, val hashtag: String) : Payload()
        data class Authentication(val token: String, val handle: String) : Payload()
    }
}
