import React from 'react';
import './App.css';

export default class App extends React.Component {
	constructor(props) {
		super(props);
		this.state = {
			counter: new Date().toISOString(),
		}
	}
	componentDidMount() {
		setInterval(() => {
			this.setState({
				counter: new Date().toISOString(),
			});
		}, 1000);
	}

	render() {
		return <div className="App" >{this.state.counter}</div>;
	}
}


