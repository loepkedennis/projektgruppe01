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
		this.SchiEGzTerLabel.setText("590"+" €");
		this.SchiDGzDacLabel.setText("590"+" €");
		this.ErhEinadHauLabel.setText("690"+" €");
		this.VorfeleAntRolEGLabel.setText("190"+" €");
		this.VorfeleAntRolOGLabel.setText("190"+" €");
		this.VorfeleAntRolDGLabel.setText("190"+" €");
		this.EleRolEGLabel.setText("990"+" €");
		this.EleRolOGLabel.setText("990"+" €");
		this.EleRolDGLabel.setText("990"+" €");
	}

	public void leseGrundrissSonderwuensche() {
	}

	public boolean pruefeKonstellationSonderwuensche(int[] ausgewaehlteSw) {
		return true;
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