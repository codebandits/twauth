export default (state = {}, action) => {
    switch (action.type) {
        case 'AUTHENTICATION_SET':
            return {...action.data}
        case 'AUTHENTICATION_REPUDIATE':
            return {}
        default:
            return state
    }
}
