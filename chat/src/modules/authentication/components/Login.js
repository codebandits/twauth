import React from 'react'
import {connect} from 'react-redux'
import {Redirect} from 'react-router-dom'
import LoginSocketHandler from '../utils/LoginSocketHandler'

import {Share} from 'react-twitter-widgets'

class Login extends React.Component {

    state = {}

    componentWillMount() {
        this.socketHandler = new LoginSocketHandler(
            this.props.loginUrl,
            this._tweetRequest.bind(this),
            this._authenticate.bind(this)
        )
    }

    _tweetRequest(request) {
        this.setState({
            message: request.message,
            hashtags: request.hashtag
        })
    }

    _authenticate(authentication) {
        this.props.authenticate(authentication)
    }

    componentWillUnmount() {
        this.socketHandler.close();
    }

    render() {
        const {authenticated} = this.props
        const {from} = this.props.location.state || {from: {pathname: '/'}}

        if (authenticated) {
            return (
                <Redirect to={from}/>
            )
        }

        const message = this.state.message
        const hashtags = this.state.hashtags

        return message
            ? (<Share url="''" options={{text: message, hashtags: hashtags, size: 'large'}}/>)
            : (null)
    }
}

const mapStateToProps = (state) => ({authenticated: !!state.authentication.token})
const mapDispatchToProps = (dispatch) => ({authenticate: (payload) => dispatch({type: 'AUTHENTICATION_SET', payload: payload})})

export default connect(mapStateToProps, mapDispatchToProps)(Login)
