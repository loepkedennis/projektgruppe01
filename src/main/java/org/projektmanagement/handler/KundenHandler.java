package org.projektmanagement.handler;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.projektmanagement.model.Haus;
import org.projektmanagement.model.Kunde;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class KundenHandler {

	private static final Logger log = LoggerFactory.getLogger(KundenHandler.class);

	@PersistenceContext
	private EntityManager em;

	public Kunde createKunde(String firstname, String lastname, String email, String phone, String street,
			String streetnr, String plz, String place, String country) {

		
		Kunde kunde = new Kunde();
		kunde.setFirstname(firstname);
		kunde.setLastname(lastname);
		kunde.setEmail(email);
		kunde.setPhone(phone);
		kunde.setStreet(street);
		kunde.setStreetnr(streetnr);
		kunde.setPlz(plz);
		kunde.setPlace(place);
		kunde.setCountry(country);
		
		em.persist(kunde);
		
		addHouse(kunde);
		
		return kunde;
	}
	
	public Kunde addHouse(Kunde kunde) {
		Haus haus = new Haus();
		
		Kunde dataKunde = getKunde(kunde.getId());
		dataKunde.getHouses().add(haus);
		haus.setBesitzer(dataKunde);
		
		em.persist(haus);
		em.persist(dataKunde);
		log.info("### Ein Haus wurde dem Kunden hinzugefügt!");
		return dataKunde;
	}

	public void editCustomer(Kunde kunde) {
		em.persist(em.contains(kunde) ? kunde : em.merge(kunde));
	}

	public void deleteCustomer(Kunde kunde) {
		em.remove(em.contains(kunde) ? kunde : em.merge(kunde));
	}

	public Kunde getKunde(int id) {
		return em.find(Kunde.class, id);
	}

	public List<Kunde> getAllKunden() {
		TypedQuery<Kunde> query = em.createQuery("from Kunde", Kunde.class);

		List<Kunde> list = query.getResultList();
		if (list == null || list.isEmpty()) {
			return null;
		}
		return list;
	}
}
