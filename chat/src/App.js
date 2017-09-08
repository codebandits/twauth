import React, {Component} from 'react';
import './App.css';
import { Share } from 'react-twitter-widgets'

import uuid from 'uuid'

class App extends Component {
    render() {
        const message = uuid()
        return (
            <div className="App">
              <h1>Tweet to login:</h1>
              <Share url="''" options={{text: message, hashtags: 'twauth', size: 'large'}}/>
            </div>
        );
    }
}

export default App;
