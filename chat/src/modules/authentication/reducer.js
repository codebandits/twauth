export default (state = {}, action) => {
    switch (action.type) {
        case 'AUTHENTICATION_SET':
            return {...action.payload}
        case 'AUTHENTICATION_REPUDIATE':
            return {}
        default:
            return state
    }
}
