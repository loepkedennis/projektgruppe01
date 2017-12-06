package org.projektmanagement.model;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.OneToMany;



@Entity
public class Haustyp {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "haustyp_id")
	private long id;
	
	private String name;
	
	@OneToMany(mappedBy="housetyp", fetch=FetchType.EAGER)
	private List<Haus> housestyp = new ArrayList<Haus>();

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

	public List<Haus> getHousestyp() {
		return housestyp;
	}

	public void setHousestyp(List<Haus> housestyp) {
		this.housestyp = housestyp;
	}
	
	
	

}