package org.projektmanagement.controller;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.projektmanagement.model.HausSonderwunsch;
import org.projektmanagement.model.Kunde;
import org.projektmanagement.model.Sonderwunsch;
import org.projektmanagement.service.SonderwunschService;
import org.projektmanagement.utils.CSVExporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * 
 * @author Stanislaus Bosch
 *
 */
public class FensterUndAussentuerenController {

	private static final Logger log = LoggerFactory.getLogger(FensterUndAussentuerenController.class);

	@FXML
	Label SchiEGzTerLabel;
	@FXML
	Label SchiDGzDacLabel;
	@FXML
	Label ErhEinadHauLabel;
	@FXML
	Label VorfeleAntRolLabel;
	@FXML
	Label VorfeleAntRolEGLabel;
	@FXML
	Label VorfeleAntRolOGLabel;
	@FXML
	Label VorfeleAntRolDGLabel;
	@FXML
	Label EleRolEGLabel;
	@FXML
	Label EleRolOGLabel;
	@FXML
	Label EleRolDGLabel;
	@FXML
	CheckBox SchiEGzTerCheckBox;
	@FXML
	CheckBox SchiDGzDacCheckBox;
	@FXML
	CheckBox ErhEinadHauCheckBox;
	@FXML
	CheckBox VorfeleAntRolEGCheckBox;
	@FXML
	CheckBox VorfeleAntRolOGCheckBox;
	@FXML
	CheckBox VorfeleAntRolDGCheckBox;
	@FXML
	CheckBox EleRolEGCheckBox;
	@FXML
	CheckBox EleRolOGCheckBox;
	@FXML
	CheckBox EleRolDGCheckBox;
	@FXML
	Label geskostenLabel;

	private Stage stage;

	private SonderwunschService sonderwunschService = new SonderwunschService();
	private Kunde kunde;

	private class Holder {
		public double value = 0;
	}

	private Holder schiEGzTerPreis, schiDGzDacPreis, erhEinadHauPreis, vorfeleAntRolEGPreis, vorfeleAntRolOGPreis,
			vorfeleAntRolDGPreis, eleRolEGPreis, eleRolOGPreis, eleRolDGPreis;

	/**
	 * Keys: Labels Values: CheckBoxes
	 */
	private Map<Label, CheckBox> labelMap;
	/**
	 * Key: CheckBox Value: Price
	 */
	private Map<CheckBox, Holder> checkBoxMap;

	/**
	 * Erzeugt ein ControlObjekt fuer die Sonderwuensche fuer Fenster und
	 * Aussentueren.
	 * 
	 */
	public FensterUndAussentuerenController() {
		super();

	}

	@FXML
	public void initialize() {
		log.debug("initialize FensterUndAussentuerenController");

	}

	public void init() {
		
		schiEGzTerPreis = new Holder();
		schiDGzDacPreis = new Holder();
		erhEinadHauPreis = new Holder();
		vorfeleAntRolEGPreis = new Holder();
		vorfeleAntRolOGPreis = new Holder();
		vorfeleAntRolDGPreis = new Holder();
		eleRolEGPreis = new Holder();
		eleRolOGPreis = new Holder();
		eleRolDGPreis = new Holder();

		this.labelMap = new HashMap<Label, CheckBox>();

		this.labelMap.put(SchiEGzTerLabel, SchiEGzTerCheckBox);
		this.labelMap.put(SchiDGzDacLabel, SchiDGzDacCheckBox);
		this.labelMap.put(ErhEinadHauLabel, ErhEinadHauCheckBox);
		this.labelMap.put(VorfeleAntRolEGLabel, VorfeleAntRolEGCheckBox);
		this.labelMap.put(VorfeleAntRolOGLabel, VorfeleAntRolOGCheckBox);
		this.labelMap.put(VorfeleAntRolDGLabel, VorfeleAntRolDGCheckBox);
		this.labelMap.put(EleRolEGLabel, EleRolEGCheckBox);
		this.labelMap.put(EleRolOGLabel, EleRolOGCheckBox);
		this.labelMap.put(EleRolDGLabel, EleRolDGCheckBox);

		this.checkBoxMap = new HashMap<CheckBox, Holder>();

		this.checkBoxMap.put(SchiEGzTerCheckBox, schiEGzTerPreis);
		this.checkBoxMap.put(SchiDGzDacCheckBox, schiDGzDacPreis);
		this.checkBoxMap.put(ErhEinadHauCheckBox, erhEinadHauPreis);
		this.checkBoxMap.put(VorfeleAntRolEGCheckBox, vorfeleAntRolEGPreis);
		this.checkBoxMap.put(VorfeleAntRolOGCheckBox, vorfeleAntRolOGPreis);
		this.checkBoxMap.put(VorfeleAntRolDGCheckBox, vorfeleAntRolDGPreis);
		this.checkBoxMap.put(EleRolEGCheckBox, eleRolEGPreis);
		this.checkBoxMap.put(EleRolOGCheckBox, eleRolOGPreis);
		this.checkBoxMap.put(EleRolDGCheckBox, eleRolDGPreis);

		// hole die Preise aus der Datenbank

		for (Sonderwunsch s : sonderwunschService.getSonderwunschHandler().getAllSonderwunsch()) {
			if (s.getName().equals("Schiebetüren im EG zur Terrasse"))
				schiEGzTerPreis.value = s.getPreis();
			if (s.getName().equals("Schiebetüren im DG zur Dachterrasse"))
				schiDGzDacPreis.value = s.getPreis();
			if (s.getName().equals("Erhöhter Einbruchschutz an der Haustür"))
				erhEinadHauPreis.value = s.getPreis();
			if (s.getName().equals("Vorbereitung für elektrische Antriebe Rolläden EG"))
				vorfeleAntRolEGPreis.value = s.getPreis();
			if (s.getName().equals("Vorbereitung für elektrische Antriebe Rolläden OG"))
				vorfeleAntRolOGPreis.value = s.getPreis();
			if (s.getName().equals("Vorbereitung für elektrische Antriebe Rolläden DG"))
				vorfeleAntRolDGPreis.value = s.getPreis();
			if (s.getName().equals("Elektrische Rolläden EG"))
				eleRolEGPreis.value = s.getPreis();
			if (s.getName().equals("Elektrische Rolläden OG"))
				eleRolOGPreis.value = s.getPreis();
			if (s.getName().equals("Elektrische Rolläden DG"))
				eleRolDGPreis.value = s.getPreis();
		}
		for (Label l : labelMap.keySet()) {
			l.setText(checkBoxMap.get(labelMap.get(l)).value + "€");
		}

		for (HausSonderwunsch hs : this.sonderwunschService.getSonderwunschHandler()
				.getSonderwunscheHouse(this.kunde)) {
			if (hs.getSonderwunsch().getName().equals("Schiebetüren im EG zur Terrasse"))
				SchiEGzTerCheckBox.setSelected(true);
			if (hs.getSonderwunsch().getName().equals("Schiebetüren im DG zur Dachterrasse"))
				SchiDGzDacCheckBox.setSelected(true);
			if (hs.getSonderwunsch().getName().equals("Erhöhter Einbruchschutz an der Haustür"))
				ErhEinadHauCheckBox.setSelected(true);
			if (hs.getSonderwunsch().getName().equals("Vorbereitung für elektrische Antriebe Rolläden EG"))
				VorfeleAntRolEGCheckBox.setSelected(true);
			if (hs.getSonderwunsch().getName().equals("Vorbereitung für elektrische Antriebe Rolläden OG"))
				VorfeleAntRolOGCheckBox.setSelected(true);
			if (hs.getSonderwunsch().getName().equals("Vorbereitung für elektrische Antriebe Rolläden DG"))
				VorfeleAntRolDGCheckBox.setSelected(true);
			if (hs.getSonderwunsch().getName().equals("Elektrische Rolläden EG"))
				EleRolEGCheckBox.setSelected(true);
			if (hs.getSonderwunsch().getName().equals("Elektrische Rolläden OG"))
				EleRolOGCheckBox.setSelected(true);
			if (hs.getSonderwunsch().getName().equals("Elektrische Rolläden DG"))
				EleRolDGCheckBox.setSelected(true);
		}
		preisBerechnen();
	}

	@FXML
	public void speichern() {

		for (Sonderwunsch s : sonderwunschService.getSonderwunschHandler().getAllSonderwunsch()) {

			if (s.getName().equals("Schiebetüren im EG zur Terrasse"))
				if (SchiEGzTerCheckBox.isSelected())
					this.sonderwunschService.getSonderwunschHandler().addSonderwunsch(s, kunde.getHouses().get(0));
				else
					sonderwunschService.getSonderwunschHandler().removeSonderwunsch(this.kunde.getHouses().get(0), s);
			if (s.getName().equals("Schiebetüren im DG zur Dachterrasse"))
				if (SchiDGzDacCheckBox.isSelected())
					this.sonderwunschService.getSonderwunschHandler().addSonderwunsch(s, this.kunde.getHouses().get(0));
				else
					sonderwunschService.getSonderwunschHandler().removeSonderwunsch(this.kunde.getHouses().get(0), s);
			if (s.getName().equals("Erhöhter Einbruchschutz an der Haustür"))
				if (ErhEinadHauCheckBox.isSelected())
					this.sonderwunschService.getSonderwunschHandler().addSonderwunsch(s, this.kunde.getHouses().get(0));
				else
					sonderwunschService.getSonderwunschHandler().removeSonderwunsch(this.kunde.getHouses().get(0), s);
			if (s.getName().equals("Vorbereitung für elektrische Antriebe Rolläden EG"))
				if (VorfeleAntRolEGCheckBox.isSelected())
					this.sonderwunschService.getSonderwunschHandler().addSonderwunsch(s, this.kunde.getHouses().get(0));
				else
					sonderwunschService.getSonderwunschHandler().removeSonderwunsch(this.kunde.getHouses().get(0), s);
			if (s.getName().equals("Vorbereitung für elektrische Antriebe Rolläden OG"))
				if (VorfeleAntRolOGCheckBox.isSelected())
					this.sonderwunschService.getSonderwunschHandler().addSonderwunsch(s, this.kunde.getHouses().get(0));
				else
					sonderwunschService.getSonderwunschHandler().removeSonderwunsch(this.kunde.getHouses().get(0), s);
			if (s.getName().equals("Vorbereitung für elektrische Antriebe Rolläden DG"))
				if (VorfeleAntRolDGCheckBox.isSelected())
					this.sonderwunschService.getSonderwunschHandler().addSonderwunsch(s, this.kunde.getHouses().get(0));
				else
					sonderwunschService.getSonderwunschHandler().removeSonderwunsch(this.kunde.getHouses().get(0), s);
			if (s.getName().equals("Elektrische Rolläden EG"))
				if (EleRolEGCheckBox.isSelected())
					this.sonderwunschService.getSonderwunschHandler().addSonderwunsch(s, this.kunde.getHouses().get(0));
				else
					sonderwunschService.getSonderwunschHandler().removeSonderwunsch(this.kunde.getHouses().get(0), s);
			if (s.getName().equals("Elektrische Rolläden OG"))
				if (EleRolOGCheckBox.isSelected())
					this.sonderwunschService.getSonderwunschHandler().addSonderwunsch(s, this.kunde.getHouses().get(0));
				else
					sonderwunschService.getSonderwunschHandler().removeSonderwunsch(this.kunde.getHouses().get(0), s);
			if (s.getName().equals("Elektrische Rolläden DG"))
				if (EleRolDGCheckBox.isSelected())
					this.sonderwunschService.getSonderwunschHandler().addSonderwunsch(s, this.kunde.getHouses().get(0));
				else
					sonderwunschService.getSonderwunschHandler().removeSonderwunsch(this.kunde.getHouses().get(0), s);
		}

	}

	@FXML
	public void preisBerechnen() {
		int gesamtKosten = 0;
		for (CheckBox cBox : checkBoxMap.keySet())
			if (cBox.isSelected())
				gesamtKosten += checkBoxMap.get(cBox).value;

		geskostenLabel.setText(gesamtKosten + " € ");
	}

	@FXML
	public void csvExport() {

		CSVExporter csvExport = new CSVExporter();
		try {
			FileWriter filePfad = new FileWriter(csvExport.setStrPfad(getStage()));
			CSVExporter.writeLine(filePfad, getCSVAuswahl());

			filePfad.flush();
			filePfad.close();
		} catch (Exception e) {

		}
	}

	public List<String> getCSVAuswahl() {
		List<String> listStrAuswahl = new ArrayList<String>();
		if (this.SchiEGzTerCheckBox.isSelected())
			listStrAuswahl.add("Schiebetüren im EG zur Dachterasse");
		if (this.SchiDGzDacCheckBox.isSelected())
			listStrAuswahl.add("Schiebetüren im DG zur Dachterasse");
		if (this.ErhEinadHauCheckBox.isSelected())
			listStrAuswahl.add("Erhöhter Einbruchschutz an der Haustür");
		if (this.VorfeleAntRolEGCheckBox.isSelected())
			listStrAuswahl.add("Vorbereitung für elektrische Antriebe Rolläden EG");
		if (this.VorfeleAntRolOGCheckBox.isSelected())
			listStrAuswahl.add("Vorbereitung für elektrische Antriebe Rolläden OG");
		if (this.VorfeleAntRolDGCheckBox.isSelected())
			listStrAuswahl.add("Vorbereitung für elektrische Antriebe Rolläden DG");
		if (this.EleRolEGCheckBox.isSelected())
			listStrAuswahl.add("Elektrische Rolläden EG");
		if (this.EleRolOGCheckBox.isSelected())
			listStrAuswahl.add("Elektrische Rolläden OG");
		if (this.EleRolDGCheckBox.isSelected())
			listStrAuswahl.add("Elektrische Rolläden DG");

		return listStrAuswahl;
	}

	/**
	 * @return the stage
	 */
	public Stage getStage() {
		return stage;
	}

	/**
	 * @param stage
	 *            the stage to set
	 */
	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public void setKunde(Kunde selectedItem) {
		this.kunde = selectedItem;
	}

}