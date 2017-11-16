package org.projektmanagement.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Innentueren")
public class Innentueren {

	@Id
	@GeneratedValue
	@Column(name = "innentueren_id")
	private int id;

	@Column(name = "anzKlarglas")
	private int klarglas;

	@Column(name = "anzMilchglas")
	private int milchglas;

	@Column(name = "garageHolz")
	private boolean gHolz;


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAnzKlarglas() {
		return klarglas;
	}

	public void setAnzKlarglas(int anz) {
		this.klarglas = anz;
	}

	public int getAnzMilchglas() {
		return milchglas;
	}

	public void setAnzMilchglas(int anz) {
		this.milchglas = anz;
	}

	public boolean getGHolz() {
		return gHolz;
	}

	public void setGHolz(boolean gHolz) {
		this.gHolz = gHolz;
	}

	
}
