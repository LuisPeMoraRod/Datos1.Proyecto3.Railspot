import React from 'react';
import LogIn from "./LogIn";
import SignUp from "./SignUp";
import HomePage from "./HomePage";
import Profile from "./Profile";
import ModifyRoutes from "./ModifyRoutes";
import BuyTickets from "./BuyTickets";
import axios from 'axios';
import { BrowserRouter as Router,  Switch, Route } from 'react-router-dom';

export default class Railspot extends React.Component{
        
	render(){
		return (
            <Router>
                <body>
                    <Route path="/" exact component={LogIn} />
                    <Route path="/HomePage" component={HomePage}/>
                    <Route path="/SignUp" component={SignUp} />
                    <Route path="/ModifyRoutes" component={ModifyRoutes} />
                    <Route path="/BuyTickets" component={BuyTickets} />
                    <Route path="/Profile" component={Profile} />
			    </body>
            </Router>
		
		);
	}
}