package org.projektmanagement.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Kategorie {
	
	@Id
	@GeneratedValue
	@Column(name = "kategorie_id")
	private long id;
	
	@ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name = "sonderwunsch_id")
	private Sonderwunsch sonderwunsch;
	
	private String name;

	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Sonderwunsch getSonderwunsch() {
		return sonderwunsch;
	}

	public void setSonderwunsch(Sonderwunsch sonderwunsch) {
		this.sonderwunsch = sonderwunsch;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
}