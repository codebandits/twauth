import React from 'react'
import {connect} from 'react-redux'
import {withRouter} from 'react-router-dom'

const mapStateToProps = (state) => ({
    authenticated: !!state.authentication.token,
    handle: state.authentication.handle || 'friend'
})

const mapDispatchToProps = (dispatch) => ({
    logOut: () => dispatch({type: 'AUTHENTICATION_REPUDIATE'})
})

const AuthenticationHeader = ({authenticated, handle, logOut, history}) => {
    const logOutAndRedirect = () => {
        logOut()
        history.push('/')
    }
    return (
        authenticated
            ? <div>Welcome {handle}! <button onClick={logOutAndRedirect}>Log Out</button></div>
            : <div>You are not logged in.</div>
    )
}

export default connect(mapStateToProps, mapDispatchToProps)(withRouter(AuthenticationHeader))
