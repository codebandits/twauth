export default class LoginSocketHandler {

    constructor(loginUrl, onTweetRequest, onAuthenticate) {
        this.onTweetRequest = onTweetRequest
        this.onAuthenticate = onAuthenticate
        this.socket = new WebSocket(loginUrl)
        this._setupWebSocket()
    }

    _setupWebSocket() {
        this.socket.onmessage = (event) => {
            const loginMessage = JSON.parse(event.data)

            switch (loginMessage.messageType) {
                case 'TWEET_REQUEST':
                    this.onTweetRequest({
                        message: loginMessage.payload.message,
                        hashtag: loginMessage.payload.hashtag
                    })
                    break
                case 'AUTHENTICATION':
                    this.onAuthenticate(loginMessage.payload)
                    break
                default:
            }
        }
    }

    close() {
        this.socket.close();
    }
}
