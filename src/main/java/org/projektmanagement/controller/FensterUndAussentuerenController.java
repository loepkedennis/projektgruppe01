package org.projektmanagement.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.projektmanagement.service.KundenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
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


	private KundenService kundenService;

	/**
	  * 
	  */
	public FensterUndAussentuerenController() {
		super();
	}

	/**
	 * Erzeugt ein ControlObjekt inklusive View-Objekt(FXML) zur Maske fuer die
	 * Sonderwuensche fuer Fenster und Aussentueren.
	 * 
	 * @param kundeService
	 *            KundenService für die Datenbank.
	 */
	public FensterUndAussentuerenController(KundenService kundeService) {
		super();
		log.info("Starting Maske für \"Sonderwuensche fuer Fenster und Aussentueren\"");
		stage = new Stage();
		this.kundenService = kundeService;
		stage.initModality(Modality.APPLICATION_MODAL);
		try {
			AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("/views/FensterAussentuerView.fxml"));
			Scene scene = new Scene(root, 560, 400);
			scene.getStylesheets().add(getClass().getResource("/styles/styles.css").toExternalForm());
			stage.setScene(scene);
			stage.showAndWait();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO get the prices from the db
		this.SchiEGzTerLabel.setText("590"+" € ");
		this.SchiDGzDacLabel.setText("590"+" € ");
		this.ErhEinadHauLabel.setText("690"+" € ");
		this.VorfeleAntRolEGLabel.setText("190"+" € ");
		this.VorfeleAntRolOGLabel.setText("190"+" € ");
		this.VorfeleAntRolDGLabel.setText("190"+" € ");
		this.EleRolEGLabel.setText("990"+" € ");
		this.EleRolOGLabel.setText("990"+" € ");
		this.EleRolDGLabel.setText("990"+" € ");
		
		this.SchiEGzTerCheckBox.setSelected(false);
		this.SchiDGzDacCheckBox.setSelected(false);
		this.ErhEinadHauCheckBox.setSelected(false);		
		this.VorfeleAntRolEGCheckBox.setSelected(false);		
		this.VorfeleAntRolOGCheckBox.setSelected(false);		
		this.VorfeleAntRolDGCheckBox.setSelected(false);		
		this.EleRolEGCheckBox.setSelected(false);		
		this.EleRolOGCheckBox.setSelected(false);	
		this.EleRolDGCheckBox.setSelected(false);			
			
		preisBerechnen();
	}

	/*
	public void leseGrundrissSonderwuensche() {
	}

	public boolean pruefeKonstellationSonderwuensche(int[] ausgewaehlteSw) {
		return true;
	}*/
	@FXML
	public void speichern() {
		// TODO save the informations in the DB
	}
	
	@FXML
	public void preisBerechnen() {
		int gesamtKosten = 0;
		
		if(this.SchiEGzTerCheckBox.isSelected())
			gesamtKosten = gesamtKosten+590;
		if(this.SchiDGzDacCheckBox.isSelected())
			gesamtKosten = gesamtKosten+590;
		if(this.ErhEinadHauCheckBox.isSelected())
			gesamtKosten = gesamtKosten+690;		
		if(this.VorfeleAntRolEGCheckBox.isSelected())	
			gesamtKosten = gesamtKosten+190;	
		if(this.VorfeleAntRolOGCheckBox.isSelected())	
			gesamtKosten = gesamtKosten+190;
		if(this.VorfeleAntRolDGCheckBox.isSelected())	
			gesamtKosten = gesamtKosten+190;
		if(this.EleRolEGCheckBox.isSelected())	
			gesamtKosten = gesamtKosten+990;
		if(this.EleRolOGCheckBox.isSelected())
			gesamtKosten = gesamtKosten+990;
		if(this.EleRolDGCheckBox.isSelected())
			gesamtKosten = gesamtKosten+990;		
		geskostenLabel.setText(gesamtKosten+" € ");
	}
	
	@FXML
	public void csvExport() {
		
		CSVExporter csvExport = new CSVExporter();
		try
		{
			FileWriter filePfad = new FileWriter(csvExport.setStrPfad(getStage()));
			CSVExporter.writeLine(filePfad, getCSVAuswahl());
			
			filePfad.flush();
			filePfad.close();	
		}catch(Exception e)
		{
			
		}
	}
	public List<String> getCSVAuswahl()
	{
		List<String> listStrAuswahl = new ArrayList<String>();
		if(this.SchiEGzTerCheckBox.isSelected())
			listStrAuswahl.add("Schiebetüren im EG zur Dachterasse");
		if(this.SchiDGzDacCheckBox.isSelected())
			listStrAuswahl.add("Schiebetüren im DG zur Dachterasse");
		if(this.ErhEinadHauCheckBox.isSelected())
			listStrAuswahl.add("Erhöhter Einbruchschutz an der Haustür");
		if(this.VorfeleAntRolEGCheckBox.isSelected())	
			listStrAuswahl.add("Vorbereitung für elektrische Antriebe Rolläden EG");
		if(this.VorfeleAntRolOGCheckBox.isSelected())	
			listStrAuswahl.add("Vorbereitung für elektrische Antriebe Rolläden OG");
		if(this.VorfeleAntRolDGCheckBox.isSelected())	
			listStrAuswahl.add("Vorbereitung für elektrische Antriebe Rolläden DG");
		if(this.EleRolEGCheckBox.isSelected())	
			listStrAuswahl.add("Elektrische Rolläden EG");
		if(this.EleRolOGCheckBox.isSelected())
			listStrAuswahl.add("Elektrische Rolläden OG");
		if(this.EleRolDGCheckBox.isSelected())
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
	 * @param stage the stage to set
	 */
	public void setStage(Stage stage) {
		this.stage = stage;
	}

}