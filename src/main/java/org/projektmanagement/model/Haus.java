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

import org.hibernate.annotations.Cascade;

@Entity
public class Haus {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "haus_id")
	private long id;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "kunde_id")
	private Kunde besitzer;

	@ManyToOne(cascade= {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
	@JoinColumn(name = "haustyp_id")
	private Haustyp housetyp;
	
	@OneToMany(mappedBy="sonderwuensch", fetch=FetchType.EAGER)
	private List<Sonderwunsch>  sonderwuensche = new ArrayList<Sonderwunsch>();
	

	public List<Sonderwunsch> getSonderwuensche() {
		return sonderwuensche;
	}

	public void setSonderwuensche(List<Sonderwunsch> sonderwuensche) {
		this.sonderwuensche = sonderwuensche;
	}

	public Haustyp getHousetyp() {
		return housetyp;
	}

	public void setHousetyp(Haustyp housetyp) {
		this.housetyp = housetyp;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Kunde getBesitzer() {
		return besitzer;
	}

	public void setBesitzer(Kunde besitzer) {
		this.besitzer = besitzer;
	}
	
	
	
}
