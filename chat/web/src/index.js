import React from 'react'
import ReactDOM from 'react-dom'
import './index.css'
import {Provider} from 'react-redux'
import {load, store} from './store'
import App from './App'
import registerServiceWorker from './registerServiceWorker'
import fetchConfiguration from './modules/configuration/fetchConfiguration'

load(store)
    .then(() => {
        return fetchConfiguration().then(configuration => store.dispatch({type: 'CONFIGURATION_SET', data: configuration}))
    })
    .then(() => {
        ReactDOM.render(<Provider store={store}><App/></Provider>, document.getElementById('root'))
        registerServiceWorker()
    })
