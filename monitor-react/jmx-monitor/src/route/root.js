import React from 'react';
import { BrowserRouter as Router, Route } from 'react-router-dom';
import { Provider } from 'mobx-react';
import MainLayout from '../layouts/MainLayout';
import Home from '../components/Home';
import Domain from '../components/beans/Domain';

const Root = ({ stores }) => (
    <Provider {...stores}>
      <Router basename="/jmx">
        <MainLayout>
          <Route exact path="/" component={Home} />
          <Route exact path="/domain" component={Domain} />
        </MainLayout>
      </Router>
    </Provider>
  );

export default Root;