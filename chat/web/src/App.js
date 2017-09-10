import React from 'react'
import './App.css'
import PrivateRoute from './modules/routing/components/PrivateRoute'
import AuthenticationHeader from './modules/authentication/components/AuthenticationHeader'
import LoginPage from './modules/authentication/components/LoginPage'
import ChatPage from './modules/chat/components/ChatPage'
import {BrowserRouter as Router, Route, Redirect, Switch} from 'react-router-dom'

const App = () => (
    <Router>
        <div className="App">
            <AuthenticationHeader/>
            <Switch>
                <Redirect exact from="/" to="/chat"/>
                <Route path="/login" component={LoginPage}/>
                <PrivateRoute path="/chat" component={ChatPage}/>
            </Switch>
        </div>
    </Router>
)

export default App
