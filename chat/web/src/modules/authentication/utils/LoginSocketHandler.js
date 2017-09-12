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
                        text: loginMessage.data.text,
                        hashtag: loginMessage.data.hashtag
                    })
                    break
                case 'AUTHENTICATION':
                    this.onAuthenticate(loginMessage.data)
                    break
                default:
            }
        }
    }

    close() {
        this.socket.close();
    }
}
