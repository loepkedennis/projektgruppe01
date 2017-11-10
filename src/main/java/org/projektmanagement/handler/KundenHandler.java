package org.projektmanagement.handler;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.projektmanagement.model.Kunde;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class KundenHandler {

	
	@PersistenceContext
	private EntityManager em;
	
	public Kunde createKunde(String name){
		Kunde kunde = new Kunde();
		kunde.setName(name);
		em.persist(kunde);
		return kunde;
	}
	
	public Kunde getKunde(int id){
		return em.find(Kunde.class, id);
	}


}
