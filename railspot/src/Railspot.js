import React from 'react';
import LogIn from "./LogIn";
import axios from 'axios';
import SignUp from "./SignUp"

export default class Railspot extends React.Component{
        
	render(){
		return (
			<body>
                <SignUp />
			</body>
		);
	}
}