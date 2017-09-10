import React from 'react'
import Login from './Login'

export default (props) => (
    <div>
        <h3>Tweet to login</h3>
        <Login {...props} loginUrl="ws://localhost:8080/login"/>
    </div>
)
