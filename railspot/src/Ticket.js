import React from 'react';
import { makeStyles } from '@material-ui/core/styles';


const useStyles = makeStyles ((theme) => ({
    ticket: {
        margin: theme.spacing(2, 25),
        border: '2px solid black',
        borderRadius: '10px',
        //width: '30%',
        background: 'red',
        display: 'flex',
        flexDirection: 'column',
        //justifyContent: 'center',
        //alignIOtems: 'center',
    },
}));

export default function Ticket({from, to, date, cost, hour}){
    const classes = useStyles();
    return (
        <div className={classes.ticket}>
            <h3>From: {from}</h3>
            <h3>To: {to}</h3>
            <h3>Cost: {cost}</h3>
            <h3>Date: {date}</h3>
            <h3>Hour: {hour}</h3>
        </div>
    );
}