import React from 'react';
import Nav from './Nav';
import {BrowserRouter as Router, Switch, Route} from 'react-router-dom';
import Profile from './Profile';
import TrainRoutes from './TrainRoutes'
export default function HomePage(){
    return (
        <Router>
            <div>
                <Nav />
                <Route path="/HomePage/profile" component={Profile}/>
                <Route path="/HomePage/trainroutes" component={TrainRoutes}/>
            </div>
        </Router>   
    );
}