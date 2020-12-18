package com.masive.roulette.model;

import java.io.Serializable;

public class Bet implements Serializable{
	
	private static final long serialVersionUID = 1755769147844301088L;

	private int idUser;
	private Double amount;
	private int number;
	private String color;
	private Double wMoney;
	
	public int getIdUser() {
		return idUser;
	}
	public void setIdUser(int id) {
		this.idUser = id;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public Double getWMoney() {
		return wMoney;
	}
	public void setWMoney(Double wMoney) {
		this.wMoney = wMoney;
	}

	@Override
	public String toString() {
		return "Bet [idUser=" + idUser + ", amount=" + amount + ", number=" + number + ", color=" + color
				+ "]";
	}
		
}
