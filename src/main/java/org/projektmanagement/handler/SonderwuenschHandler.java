package org.projektmanagement.handler;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.projektmanagement.model.Haus;
import org.projektmanagement.model.Kategorie;
import org.projektmanagement.model.Sonderwunsch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class SonderwuenschHandler {
	
	private static final Logger log = LoggerFactory.getLogger(SonderwuenschHandler.class);

	@PersistenceContext
	private EntityManager em;
	
	public Sonderwunsch createSonderwunsch(String sonderwunsch, double preis, String kategorie, Haus haus) {
		
		Kategorie datakategorie = new Kategorie();
		datakategorie.setName(kategorie);
		
		Sonderwunsch datasonderwunsch = new Sonderwunsch();
		datasonderwunsch.setName(sonderwunsch);
		datasonderwunsch.setPreis(preis);
		datasonderwunsch.getKategroien().add(datakategorie);
		datasonderwunsch.setSonderwuensch(em.merge(haus));
		
		datakategorie.setSonderwunsch(datasonderwunsch);

		em.persist(datakategorie);
		em.persist(datasonderwunsch);
		
		
		return datasonderwunsch;
	}
	
	public void editSonderwunsch(Sonderwunsch sonderwunsch) {
		em.persist(em.contains(sonderwunsch) ? sonderwunsch : em.merge(sonderwunsch));
	}

	public void deleteSonderwunsch(Sonderwunsch sonderwunsch) {
		em.remove(em.contains(sonderwunsch) ? sonderwunsch : em.merge(sonderwunsch));
	}

	public Sonderwunsch getSonderwunsch(int id) {
		return em.find(Sonderwunsch.class, id);
	}

	public List<Sonderwunsch> getAllSonderwunsch() {
		TypedQuery<Sonderwunsch> query = em.createQuery("from Sonderwunsch", Sonderwunsch.class);

		List<Sonderwunsch> list = query.getResultList();
		if (list == null || list.isEmpty()) {
			return null;
		}
		return list;
	}

}
