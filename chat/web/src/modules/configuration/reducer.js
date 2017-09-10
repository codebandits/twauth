export default (state = {}, action) => {
    switch (action.type) {
        case 'CONFIGURATION_SET':
            return {...action.data}
        default:
            return state
    }
}
