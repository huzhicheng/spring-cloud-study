import React from 'react';
import ReactDOM from 'react-dom';
import * as stores from './stores';
import './styles/index.css';
import Root from './route/root';
import * as serviceWorker from './serviceWorker';

ReactDOM.render(<Root stores={stores}/>, document.getElementById('root'));

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
serviceWorker.unregister();
