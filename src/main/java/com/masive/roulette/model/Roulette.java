package com.masive.roulette.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Roulette implements Serializable{


	private static final long serialVersionUID = -6452103047524238477L;
	
	private int id;
	private ArrayList<Bet> bets;
	private int wnumber;
	private boolean status;
	
	public Roulette() {
		this.bets = new ArrayList<>();
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public ArrayList<Bet> getBets() {
		return bets;
	}
	public void setBets(ArrayList<Bet> bets) {
		this.bets = bets;
	}
	public int getWnumber() {
		return wnumber;
	}
	public void setWnumber(int wnumber) {
		this.wnumber = wnumber;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	
	public void addBet(Bet bet) {
		this.bets.add(bet);
	}
	
	public void removeBet(Bet bet) {
		this.bets.remove(bet);
	}
	
	@Override
	public String toString() {
		return "Roulette [id=" + id + ", bets=" + bets + ", wnumber=" + wnumber + ", status=" + status + "]";
	}	
	
}
