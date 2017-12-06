package org.projektmanagement.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Kategorie {

	@Id
	@GeneratedValue
	@Column(name = "kategorie_id")
	private long id;

	@OneToMany(mappedBy = "kategroie", fetch = FetchType.EAGER)
	private List<Sonderwunsch> sonderwuensche = new ArrayList<Sonderwunsch>();

	private String name;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public List<Sonderwunsch> getSonderwunsch() {
		return sonderwuensche;
	}

	public void setSonderwunsch(List<Sonderwunsch> sonderwunsch) {
		this.sonderwuensche = sonderwunsch;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}