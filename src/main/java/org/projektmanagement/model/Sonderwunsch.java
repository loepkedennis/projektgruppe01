package org.projektmanagement.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Sonderwunsch {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "sonderwunsch_id")
	private long id;

	private String name;

	private double preis;

	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
	@JoinColumn(name = "kategorie_id")
	private Kategorie kategroie;

	@OneToMany(mappedBy = "sonderwunsch", fetch = FetchType.EAGER)
	private List<HausSonderwunsch> hausSonderwunsch = new ArrayList<HausSonderwunsch>();

	public List<HausSonderwunsch> getHausSonderwunsch() {
		return hausSonderwunsch;
	}

	public void setHausSonderwunsch(List<HausSonderwunsch> groups) {
		this.hausSonderwunsch = groups;
	}

	public void addHausSonderwunsch(HausSonderwunsch userGroup) {
		this.hausSonderwunsch.add(userGroup);
	}

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

	public Kategorie getKategroien() {
		return kategroie;
	}

	public void setKategroien(Kategorie kategroien) {
		this.kategroie = kategroien;
	}

}
