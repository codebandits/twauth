import React from 'react'
import ReactDOM from 'react-dom'
import {Provider} from 'react-redux'
import configureMockStore from 'redux-mock-store'
import App from './App'


it('renders without crashing', () => {
    const div = document.createElement('div')
    const state = {authentication: {}, configuration: {}}
    const mockStore = configureMockStore()(state)
    ReactDOM.render(<Provider store={mockStore}><App/></Provider>, div)
})
