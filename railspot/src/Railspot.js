import React from 'react';
import LogIn from "./LogIn";
import SignUp from "./SignUp";
import Admin from "./Admin";
import Profile from "./Profile";
import ModifyRoutes from "./ModifyRoutes";
import BuyTickets from "./BuyTickets";
import Nav from "./Nav";
import axios from 'axios';
import { BrowserRouter as Router,  Switch, Route } from 'react-router-dom';
import TicketsPurchased from './TicketsPurchased';

export default class Railspot extends React.Component{
        
	render(){
		return (
            <Router>
                <body>
                    <Route path="/" exact component={LogIn} />
                    <Route path="/SignUp" component={SignUp} />
                    <Route path="/ModifyRoutes" component={ModifyRoutes} />
                    <Route path="/BuyTickets" component={BuyTickets} />
                    <Route path="/Profile" component={Profile} />
                    <Route path="/Admin" component={Admin} />
                    <Route path="/TicketsPurchased" component={TicketsPurchased} />
			    </body>
            </Router>
		
		);
	}
}