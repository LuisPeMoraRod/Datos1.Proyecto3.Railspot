import React from 'react';
import { makeStyles } from '@material-ui/core';
import {Link} from 'react-router-dom'

const useStyles = makeStyles((theme) =>({
    nav:{
        display: 'flex',
        justifyContent: 'space-around',
        alignItems: 'center',
        minHeight: '10vh',
        background: 'rgb(37,40,80)',
        color:'white',
    },

    navLinks: {
        width : '50%',
        display: 'flex',
        justifyContent: 'space-around',
        alignItems: 'center',
        listStyle: 'none',
    },
}));

export default function Nav(){
    const classes = useStyles();

    const navStyle = {
        color: 'white'
    };

    return (
        <nav className={classes.nav}>
            <h3>RailSpot</h3>
            <ul className={classes.navLinks}>

                <Link to="/Admin" style={navStyle}> 
                    <li>ADMIN</li>
                </Link>
                <Link to="/BuyTickets" style={navStyle}>
                    <li>Buy Tickets</li>
                </Link>
                <Link to="/Profile" style={navStyle}>
                    <li>Profile</li>
                </Link>
                <Link to="/" style={navStyle}>
                    <li>Log out</li>
                </Link>
                
            </ul>  
        </nav>
    );
}