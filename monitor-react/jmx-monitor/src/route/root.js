import React from 'react';
import { BrowserRouter as Router, Route } from 'react-router-dom';
import { Provider } from 'mobx-react';
import Home from '../components/Home';
import BaseInfoView from '../components/BaseInfo';

const Root = ({ stores }) => (
    <Provider {...stores}>
      <Router>
        <div>
          <Route exact path="/" component={Home} />
          <Route exact path="/baseinfo" component={BaseInfoView} />
        </div>
      </Router>
    </Provider>
  );

export default Root;