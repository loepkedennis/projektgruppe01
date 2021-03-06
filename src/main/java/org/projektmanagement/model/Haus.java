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
public class Haus {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "haus_id")
	private long id;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "kunde_id")
	private Kunde besitzer;

	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST })
	@JoinColumn(name = "haustyp_id")
	private Haustyp housetyp;

	@OneToMany(mappedBy = "haus", fetch=FetchType.EAGER,cascade = { CascadeType.REMOVE})
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
