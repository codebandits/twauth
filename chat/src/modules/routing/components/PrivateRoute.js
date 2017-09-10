import React from 'react'
import {connect} from 'react-redux'
import {Route, Redirect} from 'react-router-dom'

const mapStateToProps = (state) => ({authenticated: !!state.authentication.token})

const ProtectComponent = connect(mapStateToProps)(({authenticated, component: Component, ...componentProps}) => (
    authenticated
        ? (<Component {...componentProps}/>)
        : (<Redirect to={{pathname: '/login', state: { from: componentProps.location }}}/>)
))

const PrivateRoute = ({component, ...routeProps}) => (
    <Route {...routeProps} render={props => {
        const protectProps = {...props, component}
        return (<ProtectComponent {...protectProps}/>)
    }}/>
)

export default PrivateRoute
