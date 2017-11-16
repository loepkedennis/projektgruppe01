package org.projektmanagement.model;


import java.util.LinkedList;

public class Haus {
	private int hausnr;
	private Kunde besitzer;
	private LinkedList<Sonderwunsch> sonderwunsch;

	public Haus(int hnr, Kunde besitzer){
		this.hausnr = hnr;
		this.besitzer = besitzer;
	}

	
	public int getHausnr() {
		return hausnr;
	}

	public void setHausnr(int hausnr) {
		this.hausnr = hausnr;
	}

	public Kunde getBesitzer() {
		return besitzer;
	}

	public void setBesitzer(Kunde besitzer) {
		this.besitzer = besitzer;
	}
}
