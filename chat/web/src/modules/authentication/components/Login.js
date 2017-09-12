import React from 'react'
import {connect} from 'react-redux'
import {Redirect} from 'react-router-dom'
import LoginSocketHandler from '../utils/LoginSocketHandler'

import {Share} from 'react-twitter-widgets'

class Login extends React.Component {

    state = {}

    componentWillMount() {
        this.socketHandler = new LoginSocketHandler(
            this.props.loginSocket,
            this._tweetRequest.bind(this),
            this._authenticate.bind(this)
        )
    }

    _tweetRequest(request) {
        this.setState({
            text: request.text,
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

        const text = this.state.text
        const hashtags = this.state.hashtags

        return text
            ? (<Share url="''" options={{text: text, hashtags: hashtags, size: 'large'}}/>)
            : (null)
    }
}

const mapStateToProps = (state) => ({authenticated: !!state.authentication.token, loginSocket: state.configuration.twauthLoginSocket})
const mapDispatchToProps = (dispatch) => ({authenticate: (data) => dispatch({type: 'AUTHENTICATION_SET', data: data})})

export default connect(mapStateToProps, mapDispatchToProps)(Login)
