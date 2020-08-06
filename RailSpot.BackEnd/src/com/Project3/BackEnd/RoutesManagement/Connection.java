package com.Project3.BackEnd.RoutesManagement;

public class Connection {
	private Station destiny;
	private Float distance; // in km
	private Float price; // in colones
	
	public Connection(Station destiny,  Float distance) {
		this.destiny = destiny;
		this.distance = distance;
		this.price = distance * 25;
		
	}
	public Station getDestiny() {
		return destiny;
	}
	public void setDestiny(Station destiny) {
		this.destiny = destiny;
	}
	public Float getDistance() {
		return distance;
	}
	public void setDistance(Float distance) {
		this.distance = distance;
	}
	public Float getPrice() {
		return price;
	}
	public void setPrice(Float price) {
		this.price = price;
	}
	
	

}
