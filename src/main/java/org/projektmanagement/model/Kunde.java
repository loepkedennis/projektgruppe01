package org.projektmanagement.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/* Kundenentität mit korrekten Attributen - Sergej
 import java.sql.SQLException;
 import java.util.LinkedList;

public class Kunde {
	// TODO: Attribute in Klasse & DB exakt gleich bennen, alle PKs "id" benennen?
	private int kundennr;
	private String telefonnr;
	private String name;
	private String vorname;
	private String email;
	// Pflichtenheft: Sie können davon ausgehen, 
	// dass ein Kunde genau ein Haus kauft und umgekehrt.
	private Haus haus;
	private LinkedList<Sonderwunsch> sonderwunsch;
	
	public Kunde(){
		
	}

	public Kunde(String name, String vname, String email, String tnr){
		this.telefonnr = tnr;
		this.name = name;
		this.vorname = vname;
		this.email = email;
		
		try {
			DB_Connection db = new DB_Connection();
			this.kundennr = db.createKunde(this);
			db.disconnectDB();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int getKundennr() {
		return kundennr;
	}
	
	// Don't touch! Wird von der DB Klasse aufgerufen nachdem die
	// automatisch erzeugte ID bekannt ist
	public void setKundennr(int kundennr) {
		this.kundennr = kundennr;
	}
		
	public String getTelefonnr() {
		return telefonnr;
	}

	public void setTelefonnr(String telefonnr) {
		System.out.println("telnr hier!");
		this.telefonnr = telefonnr;
		
		try {
			DB_Connection db = new DB_Connection();
			db.updateEntity(kundennr, "kunde", "telefonnr", telefonnr);
			db.disconnectDB();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		
		try {
			DB_Connection db = new DB_Connection();
			db.updateEntity(kundennr, "kunde", "name", name);
			db.disconnectDB();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public String getVorname() {
		return vorname;
	}

	public void setVorname(String vorname) {
		this.vorname = vorname;
		
		try {
			DB_Connection db = new DB_Connection();
			db.updateEntity(kundennr, "kunde", "vorname", vorname);
			db.disconnectDB();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
		
		try {
			DB_Connection db = new DB_Connection();
			db.updateEntity(kundennr, "kunde", "email", email);
			db.disconnectDB();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Haus getHaus() {
		return haus;
	}

	public void setHaus(Haus haus) {
		this.haus = haus;
		
		try {
			DB_Connection db = new DB_Connection();
			db.updateEntity(kundennr, "kunde", "haus", haus.getHausnr());
			db.disconnectDB();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

 */

@Entity
@Table(name = "Kunde")
public class Kunde {

	@Id
	@GeneratedValue
	@Column(name = "kunde_id")
	private int id;

	@Column(name = "firstname")
	private String firstname;

	@Column(name = "lastname")
	private String lastname;

	@Column(name = "email")
	private String email;

	@Column(name = "phone")
	private String phone;

	@Column(name = "street")
	private String street;

	@Column(name = "streetnr")
	private String streetnr;

	@Column(name = "plz")
	private String plz;

	@Column(name = "place")
	private String place;

	@Column(name = "country")
	private String country;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getStreetnr() {
		return streetnr;
	}

	public void setStreetnr(String streetnr) {
		this.streetnr = streetnr;
	}

	public String getPlz() {
		return plz;
	}

	public void setPlz(String plz) {
		this.plz = plz;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
}
