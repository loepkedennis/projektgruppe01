package org.projektmanagement.handler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.hibernate.Hibernate;
import org.projektmanagement.model.Haus;
import org.projektmanagement.model.HausSonderwunsch;
import org.projektmanagement.model.Kategorie;
import org.projektmanagement.model.Kunde;
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

	/**
	 * Diese Funktion wird nur einmal in der MainApp aufgerufen! Hier werden alle
	 * Sonderwuensche eingefügt.
	 */
	public void initSonderwunsch() {

		// Sonderwünsche zu Grundriss-Varianten

		Kategorie kategorie = new Kategorie();
		kategorie.setName("Grundriss");

		TypedQuery<Kategorie> query = em.createQuery("from Kategorie", Kategorie.class);
		List<Kategorie> list = query.getResultList();
		boolean exist = false;
		for (Kategorie l : list)
			if (l.getName().equals("Grundriss"))
				exist = true;

		if (!exist) {
			em.persist(kategorie);
			Sonderwunsch sonderw = new Sonderwunsch();
			sonderw.setName("Wand zur Abtrennung der Küche von dem Essbereich");
			sonderw.setPreis(300);
			sonderw.setKategroien(kategorie);
			em.persist(sonderw);
			sonderw = new Sonderwunsch();
			sonderw.setName("Tür in der Wand zwischen Küche und Essbereich");
			sonderw.setPreis(300);
			sonderw.setKategroien(kategorie);
			em.persist(sonderw);
			sonderw = new Sonderwunsch();
			sonderw.setName("Großes Zimmer im OG statt zwei kleinen Zimmern");
			sonderw.setPreis(0);
			sonderw.setKategroien(kategorie);
			em.persist(sonderw);
			sonderw = new Sonderwunsch();
			sonderw.setName("Abgetrennter Treppenraum im DG");
			sonderw.setPreis(890);
			sonderw.setKategroien(kategorie);
			em.persist(sonderw);
			sonderw = new Sonderwunsch();
			sonderw.setName("Vorrichtung eines Bades im DG");
			sonderw.setPreis(990);
			sonderw.setKategroien(kategorie);
			em.persist(sonderw);
			sonderw = new Sonderwunsch();
			sonderw.setName("Ausführung eines Bades im DG");
			sonderw.setPreis(8990);
			sonderw.setKategroien(kategorie);
			em.persist(sonderw);
		}

		// Sonderwünsche zu Fenster und Außentüren

		kategorie = new Kategorie();
		kategorie.setName("FensterUndAussentueren");

		query = em.createQuery("from Kategorie", Kategorie.class);
		list = query.getResultList();
		exist = false;
		for (Kategorie l : list)
			if (l.getName().equals("FensterUndAussentueren"))
				exist = true;

		if (!exist) {
			em.persist(kategorie);
			Sonderwunsch sonderw = new Sonderwunsch();
			sonderw.setName("Schiebetüren im EG zur Terrasse");
			sonderw.setPreis(590);
			sonderw.setKategroien(kategorie);
			em.persist(sonderw);
			sonderw = new Sonderwunsch();
			sonderw.setName("Schiebetüren im DG zur Dachterrasse");
			sonderw.setPreis(590);
			sonderw.setKategroien(kategorie);
			em.persist(sonderw);
			sonderw = new Sonderwunsch();
			sonderw.setName("Erhöhter Einbruchschutz an der Haustür");
			sonderw.setPreis(690);
			sonderw.setKategroien(kategorie);
			em.persist(sonderw);
			sonderw = new Sonderwunsch();
			sonderw.setName("Vorbereitung für elektrische Antriebe Rolläden EG");
			sonderw.setPreis(190);
			sonderw.setKategroien(kategorie);
			em.persist(sonderw);
			sonderw = new Sonderwunsch();
			sonderw.setName("Vorbereitung für elektrische Antriebe Rolläden OG");
			sonderw.setPreis(190);
			sonderw.setKategroien(kategorie);
			em.persist(sonderw);
			sonderw = new Sonderwunsch();
			sonderw.setName("Vorbereitung für elektrische Antriebe Rolläden DG");
			sonderw.setPreis(190);
			sonderw.setKategroien(kategorie);
			em.persist(sonderw);
			sonderw = new Sonderwunsch();
			sonderw.setName("Elektrische Rolläden EG");
			sonderw.setPreis(990);
			sonderw.setKategroien(kategorie);
			em.persist(sonderw);
			sonderw = new Sonderwunsch();
			sonderw.setName("Elektrische Rolläden OG");
			sonderw.setPreis(990);
			sonderw.setKategroien(kategorie);
			em.persist(sonderw);
			sonderw = new Sonderwunsch();
			sonderw.setName("Elektrische Rolläden DG");
			sonderw.setPreis(990);
			sonderw.setKategroien(kategorie);
			em.persist(sonderw);
		}

		// Sonderwünsche zu Innentüren

		kategorie = new Kategorie();
		kategorie.setName("Innentüren");

		query = em.createQuery("from Kategorie", Kategorie.class);
		list = query.getResultList();
		exist = false;
		for (Kategorie l : list)
			if (l.getName().equals("Innentüren"))
				exist = true;

		if (!exist) {
			em.persist(kategorie);
			Sonderwunsch sonderw = new Sonderwunsch();
			sonderw.setName("Mehrpreis für die Ausführung eines Glasausschnitts (Klarglas) in einer Innentür");
			sonderw.setPreis(590);
			sonderw.setKategroien(kategorie);
			em.persist(sonderw);
			sonderw = new Sonderwunsch();
			sonderw.setName("Mehrpreis für die Ausführung eines Glasausschnitts (Milchglas) in einer Innentür");
			sonderw.setPreis(590);
			sonderw.setKategroien(kategorie);
			em.persist(sonderw);
			sonderw = new Sonderwunsch();
			sonderw.setName("Innentür zur Garage als Holztür");
			sonderw.setPreis(690);
			sonderw.setKategroien(kategorie);
			em.persist(sonderw);
		}

		// Sonderwünsche zu Heizungen

		kategorie = new Kategorie();
		kategorie.setName("Heizungen");

		query = em.createQuery("from Kategorie", Kategorie.class);
		list = query.getResultList();
		exist = false;
		for (Kategorie l : list)
			if (l.getName().equals("Heizungen"))
				exist = true;

		if (!exist) {
			em.persist(kategorie);
			Sonderwunsch sonderw = new Sonderwunsch();
			sonderw.setName("Mehrpreis für einen zusätzlichen Standard-Heizkörper");
			sonderw.setPreis(660);
			sonderw.setKategroien(kategorie);
			em.persist(sonderw);
			sonderw = new Sonderwunsch();
			sonderw.setName("Mehrpreis für einen Heizkörper mit glatter Oberfläche");
			sonderw.setPreis(160);
			sonderw.setKategroien(kategorie);
			em.persist(sonderw);
			sonderw = new Sonderwunsch();
			sonderw.setName("Handtuchheizkörper");
			sonderw.setPreis(660);
			sonderw.setKategroien(kategorie);
			em.persist(sonderw);
			sonderw = new Sonderwunsch();
			sonderw.setName("Fußbodenheizung ohne DG");
			sonderw.setPreis(8990);
			sonderw.setKategroien(kategorie);
			em.persist(sonderw);
			sonderw = new Sonderwunsch();
			sonderw.setName("Fußbodenheizung mit DG");
			sonderw.setPreis(9990);
			sonderw.setKategroien(kategorie);
			em.persist(sonderw);
		}

		// Sonderwünsche zur Sanitärinstallation

		kategorie = new Kategorie();
		kategorie.setName("Sanitärinstalltation");

		query = em.createQuery("from Kategorie", Kategorie.class);
		list = query.getResultList();
		exist = false;
		for (Kategorie l : list)
			if (l.getName().equals("Sanitärinstalltation"))
				exist = true;

		if (!exist) {
			em.persist(kategorie);
			Sonderwunsch sonderw = new Sonderwunsch();
			sonderw.setName("Mehrpreis für ein größeres Waschbecken im OG");
			sonderw.setPreis(160);
			sonderw.setKategroien(kategorie);
			em.persist(sonderw);
			sonderw = new Sonderwunsch();
			sonderw.setName("Mehrpreis für ein größeres Waschbecken im DG");
			sonderw.setPreis(160);
			sonderw.setKategroien(kategorie);
			em.persist(sonderw);
			sonderw = new Sonderwunsch();
			sonderw.setName("Mehrpreis für eine bodentiefe Dusche im OG");
			sonderw.setPreis(560);
			sonderw.setKategroien(kategorie);
			em.persist(sonderw);
			sonderw = new Sonderwunsch();
			sonderw.setName("Mehrpreis für eine bodentiefe Dusche im DG");
			sonderw.setPreis(560);
			sonderw.setKategroien(kategorie);
			em.persist(sonderw);
		}

		// Sonderwünsche zu Fliesen

		kategorie = new Kategorie();
		kategorie.setName("Fliesen");

		query = em.createQuery("from Kategorie", Kategorie.class);
		list = query.getResultList();
		exist = false;
		for (Kategorie l : list)
			if (l.getName().equals("Fliesen"))
				exist = true;

		if (!exist) {
			em.persist(kategorie);
			Sonderwunsch sonderw = new Sonderwunsch();
			sonderw.setName("Keine Fliesen im Küchenbereich des EG");
			sonderw.setPreis(590);
			sonderw.setKategroien(kategorie);
			em.persist(sonderw);
			sonderw = new Sonderwunsch();
			sonderw.setName("Keine Fliesen im Bad des OG");
			sonderw.setPreis(1870);
			sonderw.setKategroien(kategorie);
			em.persist(sonderw);
			sonderw = new Sonderwunsch();
			sonderw.setName("Mehrpreis bei großformatige Fliesen im Küchenbereich des EG");
			sonderw.setPreis(170);
			sonderw.setKategroien(kategorie);
			em.persist(sonderw);
			sonderw = new Sonderwunsch();
			sonderw.setName("Mehrpreis bei großformatige Fliesen im Bad des OG");
			sonderw.setPreis(190);
			sonderw.setKategroien(kategorie);
			em.persist(sonderw);
			sonderw = new Sonderwunsch();
			sonderw.setName("Fliesen im Bad des DG");
			sonderw.setPreis(2190);
			sonderw.setKategroien(kategorie);
			em.persist(sonderw);
			sonderw = new Sonderwunsch();
			sonderw.setName("Mehrpreis bei großformatige Fliesen im Bad des DG");
			sonderw.setPreis(190);
			sonderw.setKategroien(kategorie);
			em.persist(sonderw);
		}

		// Sonderwünsche zu Parkett

		kategorie = new Kategorie();
		kategorie.setName("Parkett");

		query = em.createQuery("from Kategorie", Kategorie.class);
		list = query.getResultList();
		exist = false;
		for (Kategorie l : list)
			if (l.getName().equals("Parkett"))
				exist = true;

		if (!exist) {
			em.persist(kategorie);
			Sonderwunsch sonderw = new Sonderwunsch();
			sonderw.setName("Landhausdielen massiv im Essbereich des EG");
			sonderw.setPreis(2890);
			sonderw.setKategroien(kategorie);
			em.persist(sonderw);
			sonderw = new Sonderwunsch();
			sonderw.setName("Landhausdielen massiv im Küchenbereich des EG");
			sonderw.setPreis(2090);
			sonderw.setKategroien(kategorie);
			em.persist(sonderw);
			sonderw = new Sonderwunsch();
			sonderw.setName("Stäbchenparkett im Essbereich des EG");
			sonderw.setPreis(2090);
			sonderw.setKategroien(kategorie);
			em.persist(sonderw);
			sonderw = new Sonderwunsch();
			sonderw.setName("Stäbchenparkett im Küchenbereich des EG");
			sonderw.setPreis(1790);
			sonderw.setKategroien(kategorie);
			em.persist(sonderw);
			sonderw = new Sonderwunsch();
			sonderw.setName("Landhausdielen massiv im OG");
			sonderw.setPreis(2490);
			sonderw.setKategroien(kategorie);
			em.persist(sonderw);
			sonderw = new Sonderwunsch();
			sonderw.setName("Stäbchenparkett im OG");
			sonderw.setPreis(1690);
			sonderw.setKategroien(kategorie);
			em.persist(sonderw);
			sonderw = new Sonderwunsch();
			sonderw.setName("Landhausdielen massiv komplett im DG");
			sonderw.setPreis(2490);
			sonderw.setKategroien(kategorie);
			em.persist(sonderw);
			sonderw = new Sonderwunsch();
			sonderw.setName("Landhausdielen massiv ohne Badbereich im DG");
			sonderw.setPreis(2090);
			sonderw.setKategroien(kategorie);
			em.persist(sonderw);
			sonderw = new Sonderwunsch();
			sonderw.setName("Stäbchenparkett im DG komplett im DG");
			sonderw.setPreis(1690);
			sonderw.setKategroien(kategorie);
			em.persist(sonderw);
			sonderw = new Sonderwunsch();
			sonderw.setName("Stäbchenparkett ohne Badbereich im DG");
			sonderw.setPreis(1690);
			sonderw.setKategroien(kategorie);
			em.persist(sonderw);
		}

		// Sonderwünsche zu Außenanlagen

		kategorie = new Kategorie();
		kategorie.setName("Außenanlagen");

		query = em.createQuery("from Kategorie", Kategorie.class);
		list = query.getResultList();
		exist = false;
		for (Kategorie l : list)
			if (l.getName().equals("Außenanlagen"))
				exist = true;

		if (!exist) {
			em.persist(kategorie);
			Sonderwunsch sonderw = new Sonderwunsch();
			sonderw.setName("Abstellraum auf der Terrasse des EG");
			sonderw.setPreis(3590);
			sonderw.setKategroien(kategorie);
			em.persist(sonderw);
			sonderw = new Sonderwunsch();
			sonderw.setName("Vorbereitung für elektrische Antriebe Markise EG");
			sonderw.setPreis(170);
			sonderw.setKategroien(kategorie);
			em.persist(sonderw);
			sonderw = new Sonderwunsch();
			sonderw.setName("Vorbereitung für elektrische Antriebe Markise DG");
			sonderw.setPreis(170);
			sonderw.setKategroien(kategorie);
			em.persist(sonderw);
			sonderw = new Sonderwunsch();
			sonderw.setName("Elektrische Markise EG");
			sonderw.setPreis(890);
			sonderw.setKategroien(kategorie);
			em.persist(sonderw);
			sonderw = new Sonderwunsch();
			sonderw.setName("Elektrische Markise DG");
			sonderw.setPreis(890);
			sonderw.setKategroien(kategorie);
			em.persist(sonderw);
			sonderw = new Sonderwunsch();
			sonderw.setName("Elektrischen Antrieb für das Garagentor");
			sonderw.setPreis(990);
			sonderw.setKategroien(kategorie);
			em.persist(sonderw);
			sonderw = new Sonderwunsch();
			sonderw.setName("Sektionaltor anstatt Schwingtor für die Garage");
			sonderw.setPreis(790);
			sonderw.setKategroien(kategorie);
			em.persist(sonderw);
		}

	}

	/**
	 * Ruft die Sonderwuensche auf, die in einem Haus, die der Kunde k besitzt
	 * gespeichert sind
	 * 
	 * @param k
	 *            Der Kunde
	 * @return Die Sonderwuensche, die der Kunde bestellt hat
	 */
	public List<HausSonderwunsch> getSonderwunscheHouse(Kunde k) {
		return k.getHouses().get(0).getHausSonderwunsch();
	}

	/**
	 * Fügt einen! Sonderwunsch in ein Haus hinzu. Das Haus muss vom Kunden bezogen
	 * werden.
	 * 
	 * @param sonderwunsch
	 *            Der zu hinzufügende Sonderwunsch
	 * @param haus
	 *            Das Haus, das den Sonderwunsch bekommen soll.
	 */
	public void addSonderwunsch(Sonderwunsch sonderwunsch, Haus haus) {
		HausSonderwunsch hs = new HausSonderwunsch(haus, sonderwunsch, 1);

		// hs.setHaus(haus);
		// hs.setSonderwunsch(sonderwunsch);
		// hs.setMenge(1);

		// haus.getHausSonderwunsch().add(hs);
		// sonderwunsch.getHausSonderwunsch().add(hs);

		em.persist(em.contains(hs) ? hs : em.merge(hs));
	}

	/**
	 * Fügt mehrere Sonderwuensche in ein Haus hinzu. Die Anzahl der Sonderwuensche
	 * betraegt menge. Das Haus muss vom Kunden bezogen werden.
	 * 
	 * 
	 * @param sonderwunsch
	 *            Der zu hinzufügende Sonderwunsch
	 * @param haus
	 *            Das Haus, das den Sonderwunsch bekommen soll.
	 * @param menge
	 *            Die Anzahl der Sonderwuensche
	 */
	public void addSonderwunsch(Sonderwunsch sonderwunsch, Haus haus, int menge) {
		HausSonderwunsch hs = new HausSonderwunsch(haus, sonderwunsch, menge);

		// hs.setHaus(haus);
		// hs.setSonderwunsch(sonderwunsch);
		// hs.setMenge(menge);

		// haus.getHausSonderwunsch().add(hs);
		// sonderwunsch.getHausSonderwunsch().add(hs);

		em.persist(em.contains(hs) ? hs : em.merge(hs));
	}

	/**
	 * Entfernt alle Sonderwuensche, die in einem Haus abgespeichert sind
	 * 
	 * @param haus
	 *            Das Haus, das von Sonderwuenschen geleert werden soll
	 */
	public void removeALLSonderwunsch(Haus haus) {
		for (HausSonderwunsch hs : haus.getHausSonderwunsch())
			em.remove(em.contains(hs) ? hs : em.merge(hs));
		haus.getHausSonderwunsch().clear();
	}

	/**
	 * Entfernt den sonderwunsch, der in haus abgespeichert ist
	 * 
	 * @param haus
	 *            Das Haus, in dem der sonderwunsch entfernt werden soll
	 */
	public void removeSonderwunsch(Haus haus, Sonderwunsch sonderwunsch) {
		List<HausSonderwunsch> sonderwL = new ArrayList<HausSonderwunsch>();
		
		for(HausSonderwunsch s: haus.getHausSonderwunsch())
			if (s.getSonderwunsch().getId() == sonderwunsch.getId()) {
				sonderwL.add(s);
			}
		for(HausSonderwunsch hs:sonderwL) {
			em.remove(em.contains(hs) ? hs : em.merge(hs));
		}
		haus.getHausSonderwunsch().removeAll(sonderwL);	
	}

	public void editSonderwunsch(Sonderwunsch sonderwunsch) {
		em.persist(em.contains(sonderwunsch) ? sonderwunsch : em.merge(sonderwunsch));
	}

	public void deleteSonderwunsch(Sonderwunsch sonderwunsch) {
		em.remove(em.contains(sonderwunsch) ? sonderwunsch : em.merge(sonderwunsch));
	}

	/**
	 * <h1>Kategorien mit den IDs der Sonderwünsche</h1>
	 * 
	 * <p>
	 * Kategorie:Grundriss
	 * <ul>
	 * <li>2 Wand zur Abtrennung der Küche von dem Essbereich
	 * <li>3 Tür in der Wand zwischen Küche und Essbereich
	 * <li>4 Großes Zimmer im OG statt zwei kleinen Zimmern
	 * <li>5 Abgetrennter Treppenraum im DG
	 * <li>6 Vorrichtung eines Bades im DG
	 * <li>7 Ausführung eines Bades im DG
	 * </ul>
	 * <p>
	 * <p>
	 * Kategorie:FensterUndAussentueren
	 * <ul>
	 * <li>9 Schiebetüren im EG zur Terrasse
	 * <li>10 Schiebetüren im DG zur Dachterrasse
	 * <li>11 Erhöhter Einbruchschutz an der Haustür
	 * <li>12 Vorbereitung für elektrische Antriebe Rolläden EG
	 * <li>13 Vorbereitung für elektrische Antriebe Rolläden OG
	 * <li>14 Vorbereitung für elektrische Antriebe Rolläden DG
	 * <li>15 Elektrische Rolläden EG
	 * <li>16 Elektrische Rolläden OG
	 * <li>17 Elektrische Rolläden DG
	 * </ul>
	 * <p>
	 * <p>
	 * Kategorie:Innentüren
	 * <ul>
	 * <li>19 Mehrpreis für die Ausführung eines Glasausschnitts (Klarglas) in einer
	 * Innentür
	 * <li>20 Mehrpreis für die Ausführung eines Glasausschnitts (Milchglas) in
	 * einer Innentür
	 * <li>21 Innentür zur Garage als Holztür
	 * </ul>
	 * <p>
	 * <p>
	 * Kategorie:Heizungen
	 * <ul>
	 * <li>23 Mehrpreis für einen zusätzlichen Standard-Heizkörper
	 * <li>24 Mehrpreis für einen Heizkörper mit glatter Oberfläche
	 * <li>25 Handtuchheizkörper
	 * <li>26 Fußbodenheizung ohne DG
	 * <li>27 Fußbodenheizung mit DG
	 * </ul>
	 * <p>
	 * <p>
	 * Kategorie:Sanitärinstalltation
	 * <ul>
	 * <li>29 Mehrpreis für ein größeres Waschbecken im OG
	 * <li>30 Mehrpreis für ein größeres Waschbecken im DG
	 * <li>31 Mehrpreis für eine bodentiefe Dusche im OG
	 * <li>32 Mehrpreis für eine bodentiefe Dusche im DG
	 * </ul>
	 * <p>
	 * <p>
	 * Kategorie:Fliesen
	 * <ul>
	 * <li>34 Keine Fliesen im Küchenbereich des EG
	 * <li>35 Keine Fliesen im Bad des OG
	 * <li>36 Mehrpreis bei großformatige Fliesen im Küchenbereich des EG
	 * <li>37 Mehrpreis bei großformatige Fliesen im Bad des OG
	 * <li>38 Fliesen im Bad des DG
	 * <li>39 Mehrpreis bei großformatige Fliesen im Bad des DG
	 * </ul>
	 * <p>
	 * <p>
	 * Kategorie:Parkett
	 * <ul>
	 * <li>41 Landhausdielen massiv im Essbereich des EG
	 * <li>42 Landhausdielen massiv im Küchenbereich des EG
	 * <li>43 Stäbchenparkett im Essbereich des EG
	 * <li>44 Stäbchenparkett im Küchenbereich des EG
	 * <li>45 Landhausdielen massiv im OG
	 * <li>46 Stäbchenparkett im OG
	 * <li>47 Landhausdielen massiv komplett im DG
	 * <li>48 Landhausdielen massiv ohne Badbereich im DG
	 * <li>49 Stäbchenparkett im DG komplett im DG
	 * <li>50 Stäbchenparkett ohne Badbereich im DG
	 * </ul>
	 * <p>
	 * <p>
	 * Kategorie:Außenanlagen
	 * <ul>
	 * <li>52 Abstellraum auf der Terrasse des EG
	 * <li>53 Vorbereitung für elektrische Antriebe Markise EG
	 * <li>54 Vorbereitung für elektrische Antriebe Markise DG
	 * <li>55 Elektrische Markise EG
	 * <li>56 Elektrische Markise DG
	 * <li>57 Elektrischen Antrieb für das Garagentor
	 * <li>58 Sektionaltor anstatt Schwingtor für die Garage
	 * </ul>
	 * <p>
	 * 
	 * @param id
	 * @return
	 */
	public Sonderwunsch getSonderwunsch(long id) {
		return em.find(Sonderwunsch.class, id);
	}

	/**
	 * Liefert alle Sonderwuensche, die in der Datenbank gespeichert sind.
	 * 
	 * @return
	 */
	public List<Sonderwunsch> getAllSonderwunsch() {
		TypedQuery<Sonderwunsch> query = em.createQuery("from Sonderwunsch", Sonderwunsch.class);

		List<Sonderwunsch> list = query.getResultList();
		if (list == null || list.isEmpty()) {
			return null;
		}
		return list;
	}
}
