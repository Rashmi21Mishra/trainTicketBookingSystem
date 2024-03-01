package com.user.trainticket.entity;

import jakarta.persistence.Entity;

public class ReceiptVO {
	
	private String originStation;
	private String destinationStation;
	private String userName;
	private String userEmailId;
	private String pricePaid;
	
	public String getOriginStation() {
		return originStation;
	}
	public void setOriginStation(String originStation) {
		this.originStation = originStation;
	}
	public String getDestinationStation() {
		return destinationStation;
	}
	public void setDestinationStation(String destinationStation) {
		this.destinationStation = destinationStation;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserEmailId() {
		return userEmailId;
	}
	public void setUserEmailId(String userEmailId) {
		this.userEmailId = userEmailId;
	}
	public String getPricePaid() {
		return pricePaid;
	}
	public void setPricePaid(String pricePaid) {
		this.pricePaid = pricePaid;
	}
	

}
