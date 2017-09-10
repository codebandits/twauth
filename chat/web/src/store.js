import {createStore, applyMiddleware} from 'redux'
import {devToolsEnhancer} from 'redux-devtools-extension'
import * as storage from 'redux-storage'
import createEngine from 'redux-storage-engine-localstorage'
import rootReducer from './rootReducer'

const reducer = storage.reducer(rootReducer)
const engine = createEngine('twauth')
const middleware = storage.createMiddleware(engine)
const createStoreWithMiddleware = applyMiddleware(middleware)(createStore)

export const load = storage.createLoader(engine)
export const store = createStoreWithMiddleware(reducer, devToolsEnhancer())
