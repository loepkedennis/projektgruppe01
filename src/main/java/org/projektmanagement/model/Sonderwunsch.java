package org.projektmanagement.model;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Sonderwunsch {
	
	@Id
	@GeneratedValue
	@Column(name = "sonderwunsch_id")
	private long id;
	
	private String name;
	
	private double preis;
	
	@ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name = "haus_id")
	private Haus sonderwuensch;
		
	@OneToMany(mappedBy="sonderwunsch",fetch=FetchType.EAGER)
	private List<Kategorie> kategroien = new ArrayList<Kategorie>();

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPreis() {
		return preis;
	}

	public void setPreis(double preis) {
		this.preis = preis;
	}

	public List<Kategorie> getKategroien(){
		return kategroien;
	}

	public void setKategroien(List<Kategorie> kategroien) {
		this.kategroien = kategroien;
	}

	public Haus getSonderwuensch() {
		return sonderwuensch;
	}

	public void setSonderwuensch(Haus sonderwuensch) {
		this.sonderwuensch = sonderwuensch;
	}
	
	


	
	
}
