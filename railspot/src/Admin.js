import React from 'react';
import Nav from "./Nav";
import { Link } from 'react-router-dom';

export default function Admin(){
    return(
        <div>
            <Nav />
            <ul>
                <Link to="/ModifyRoutes" >
                    <li>Modify Routes</li>
                </Link>
                <Link to="/TicketsPurchased" >
                    <li>Tickets Purchased</li>
                </Link>
            </ul>
            </div>
    )
}