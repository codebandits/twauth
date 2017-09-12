package com.twauth.api.login

data class LoginMessage(
        val messageType: MessageType,
        val data: Data
) {
    enum class MessageType { TWEET_REQUEST, AUTHENTICATION }
    sealed class Data {
        data class TweetRequest(val text: String, val hashtag: String) : Data()
        data class Authentication(val token: String, val handle: String) : Data()
    }
}
