package org.projektmanagement.controller;

import java.io.FileWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.projektmanagement.model.Kunde;
import org.projektmanagement.service.KundenService;
import org.projektmanagement.service.SonderwunschService;
import org.projektmanagement.utils.CSVExporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * 
 * @author Stanislaus Bosch
 *
 */
public class FensterUndAussentuerenController implements Initializable {

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

	private KundenService kundenService = new KundenService();
	private SonderwunschService sonderwunschService = new SonderwunschService();
	private Kunde kunde;

	private class Holder {
		public Holder(double v) {
			value = v;
		}

		public double value;
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

	public void initialize(URL arg0, ResourceBundle arg1) {
		log.debug("initialize FensterUndAussentuerenController");
		// TODO get the prices from the db

		/*
		 * for(Label l : labelMap.keySet()) for(Sonderwunsch s
		 * :sonderwunschService.getSonderwunschHandler().getAllSonderwunsch())
		 * if(s.getName() == l.getText()) checkBoxMap.get(labelMap.get(l)).value =
		 * s.getPreis();
		 */
		schiEGzTerPreis = new Holder(590);
		schiDGzDacPreis = new Holder(590);
		erhEinadHauPreis = new Holder(690);
		vorfeleAntRolEGPreis = new Holder(190);
		vorfeleAntRolOGPreis = new Holder(190);
		vorfeleAntRolDGPreis = new Holder(190);
		eleRolEGPreis = new Holder(990);
		eleRolOGPreis = new Holder(990);
		eleRolDGPreis = new Holder(990);
		

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

		for (Label l : labelMap.keySet()) {
			l.setText(checkBoxMap.get(labelMap.get(l)).value + "€");
		}

		/*
		 * TODO Get the selected sonderwuensche from the DB!!!
		 * 
		 * for (Sonderwunsch s: this.kunde.getHouses().get(0).getSonderwuensche()) {
		 * for(Label l : labelMap.keySet()) { if(s.getName().equals(l.getText())){
		 * labelMap.get(l).setSelected(true); } } preisBerechnen();
		 */
		for (CheckBox c : checkBoxMap.keySet())
			c.setSelected(false);
		preisBerechnen();
	}

	@FXML
	public void speichern() {
		// TODO save the informations in the DB
		/*
		 * for(Label label : labelMap.keySet()) { if(labelMap.get(label).isSelected())
		 * this.kunde.getHouses().get(0).getSonderwuensche().add(sonderwunschService.
		 * getSonderwunschHandler().getSonderwunsch(...)) }
		 */
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