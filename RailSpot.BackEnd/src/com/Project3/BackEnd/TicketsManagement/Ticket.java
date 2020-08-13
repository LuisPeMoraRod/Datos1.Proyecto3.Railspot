package com.Project3.BackEnd.TicketsManagement;


public class Ticket {
	private String userId;
	private String departureStation;
	private String arrivalStation;
	private Float price;
	private String date;
	private String hour;
	private int amount;
	
	public Ticket (String userId,String departureStation, String arrivalStation, Float price, String date, String hour, int amount) {
		this.userId = userId;
		this.departureStation = departureStation;
		this.arrivalStation=arrivalStation;
		this.price = price;
		this.date = date;
		this.hour = hour;
		this.amount = amount;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getDepartureStation() {
		return departureStation;
	}

	public void setDepartureStation(String departureStation) {
		this.departureStation = departureStation;
	}

	public String getArrivalStation() {
		return arrivalStation;
	}

	public void setArrivalStation(String arrivalStation) {
		this.arrivalStation = arrivalStation;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getHour() {
		return hour;
	}

	public void setHour(String hour) {
		this.hour = hour;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	
	
}
