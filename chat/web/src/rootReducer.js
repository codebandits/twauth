import {combineReducers} from 'redux'

import authentication from './modules/authentication/reducer'
import configuration from './modules/configuration/reducer'

export default combineReducers({
    authentication,
    configuration
})
