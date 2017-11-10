package org.projektmanagement.handler;

import java.util.List;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

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
	
	public Kunde createKunde(String name, String lastname){
		Kunde kunde = new Kunde();
		kunde.setName(name);
		kunde.setLastname(lastname);
		em.persist(kunde);
		return kunde;
	}
	
	public Kunde getKunde(int id){
		return em.find(Kunde.class, id);
	}
	
	public List<Kunde> getAllKunden(){
		TypedQuery<Kunde> query  = em.createQuery("from Kunde", Kunde.class);
		
		List<Kunde> list = query.getResultList();
	    if (list == null || list.isEmpty()) {
	        return null;
	    }
			return list;
	}


}
