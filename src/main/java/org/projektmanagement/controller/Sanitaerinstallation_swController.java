/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.projektmanagement.controller;

import javafx.fxml.Initializable;
import java.io.FileWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import org.projektmanagement.model.HausSonderwunsch;
import org.projektmanagement.model.Kunde;
import org.projektmanagement.model.Sonderwunsch;
import org.projektmanagement.service.KundenService;
import org.projektmanagement.service.SonderwunschService;
import org.projektmanagement.utils.CSVExporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Ejangue Junior
 */
public class Sanitaerinstallation_swController implements Initializable {

    private static final Logger log = (Logger) LoggerFactory.getLogger(Sanitaerinstallation_swController.class);
    @FXML
    Label label;
    @FXML
    Label MehrpreisWBOGLabel;
    @FXML
    Label MehrpreisWBDGLabel;
    @FXML
    Label MehrpreisDSOGLabel;
    @FXML
    Label MehrpreisDSDGLabel;
    @FXML
    Label geskostenLabel;
    @FXML
    CheckBox MehrpreisWBOGCheckBox;
    @FXML
    CheckBox MehrpreisWBDGCheckBox;
    @FXML
    CheckBox MehrpreisDSOGCheckBox;
    @FXML
    CheckBox MehrpreisDSDGCheckBox;

    private Stage stage;
    private KundenService kundenService;
    private Kunde kunde;
    private List<Sonderwunsch> sonderwunsch;
	private SonderwunschService sonderwunschService = new SonderwunschService();
	private long kategorieID;

    public Sanitaerinstallation_swController() {
        super();
    }

    /**
     * Erzeugt ein ControlObjekt inklusive View-Objekt(FXML) zur Maske fuer die
     * Sonderwuensche fuer Fenster und Aussentueren.
     *
     * @param kundeService KundenService f�r die Datenbank.
     */
    public Sanitaerinstallation_swController(KundenService kundenService) {
        super();
        log.info("Starting Maske f�r \"Sonderwuensche zur Sanitaerinstallation\"");
        stage = new Stage();
        this.kundenService = kundenService;
        stage.initModality(Modality.APPLICATION_MODAL);
        try {
            AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("/views/Sanitaerinstallation.fxml"));
            Scene scene = new Scene(root, 560, 400);
            scene.getStylesheets().add(getClass().getResource("/styles/styles.css").toExternalForm());
            stage.setScene(scene);
            stage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    @SuppressWarnings("restriction")
	public void init(long id, Stage stage) {
		this.stage = stage;
		kategorieID = id;
		sonderwunsch = sonderwunschService.getSonderwunschHandler().getSonderwunschByKategorieID(kategorieID);

		// hole die Beschreibung und Preise aus der Datenbank		
		this.MehrpreisWBOGLabel.setText(sonderwunsch.get(0).getPreis() + " € "); 	//160
        this.MehrpreisWBDGLabel.setText(sonderwunsch.get(1).getPreis() + " € ");	//160
        this.MehrpreisDSOGLabel.setText(sonderwunsch.get(2).getPreis() + " €");		//560
        this.MehrpreisDSDGLabel.setText(sonderwunsch.get(3).getPreis() + " €");		//560

        this.MehrpreisWBOGCheckBox.setSelected(false);
        this.MehrpreisWBDGCheckBox.setSelected(false);
        this.MehrpreisDSOGCheckBox.setSelected(false);
        this.MehrpreisDSDGCheckBox.setSelected(false);
		
		// Werte setzen aus Tabelle
        //List<Sonderwunsch> ss;
        //ss=sonderwunschService.getSonderwunschHandler().getAllSonderwunsch();
        //for (Sonderwunsch sss : ss) {
        //		log.debug("Sonderwunsch: "+sss.getName()+";"+sss.getKategroien().getId());
        //}
        
		List<HausSonderwunsch> sw;
		sw = sonderwunschService.getSonderwunschHandler().getSonderwunscheHouse(kunde);
		for (HausSonderwunsch s : sw) {
			log.info("Sonderwunsche: "+s.getSonderwunsch().getName());
			if(s.getSonderwunsch().getName().equals("Mehrpreis für ein größeres Waschbecken im OG")) {
				this.MehrpreisWBOGCheckBox.setSelected(true);
			}
			if(s.getSonderwunsch().getName().equals("Mehrpreis für ein größeres Waschbecken im DG")) {
				this.MehrpreisWBDGCheckBox.setSelected(true);
			}
			if(s.getSonderwunsch().getName().equals("Mehrpreis für eine bodentiefe Dusche im OG")) {
				this.MehrpreisDSOGCheckBox.setSelected(true);
			}
			if(s.getSonderwunsch().getName().equals("Mehrpreis für eine bodentiefe Dusche im DG")) {
				this.MehrpreisDSDGCheckBox.setSelected(true);
			}
			
		}

	}

    public void initialize(URL arg0, ResourceBundle arg1) {
        // get the prices from the DB 
        /*this.MehrpreisWBOGLabel.setText("160" + " € ");
        this.MehrpreisWBDGLabel.setText("160" + " € ");
        this.MehrpreisDSOGLabel.setText("560" + " €");
        this.MehrpreisDSDGLabel.setText("560" + " € ");

        this.MehrpreisWBOGCheckBox.setSelected(false);
        this.MehrpreisWBDGCheckBox.setSelected(false);
        this.MehrpreisDSOGCheckBox.setSelected(false);
        this.MehrpreisDSDGCheckBox.setSelected(false);

        preisBerechnen();
        */
    }

    @FXML
    public void speichern() {
        // TODO save the informations in the DB
    }

    @FXML
    public void preisBerechnen() {
        int gesamtkosten = 0;

        if (this.MehrpreisWBOGCheckBox.isSelected()) {
            gesamtkosten = gesamtkosten + 160;
        }
        if (this.MehrpreisWBDGCheckBox.isSelected()) {
            gesamtkosten = gesamtkosten + 160;
        }
        if (this.MehrpreisDSOGCheckBox.isSelected()) {
            gesamtkosten = gesamtkosten + 560;
        }
        if (this.MehrpreisDSDGCheckBox.isSelected()) {
            gesamtkosten = gesamtkosten + 560;
        }
        geskostenLabel.setText(gesamtkosten + " €");
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

    public List<String> getCSVAuswahl() {
        List<String> listStrAuswahl = new ArrayList<String>();
        if (this.MehrpreisWBOGCheckBox.isSelected()) {
            listStrAuswahl.add(" Gr��eres Waschbecken im OG ");
        }
        if (this.MehrpreisWBDGCheckBox.isSelected()) {
            listStrAuswahl.add(" Gr��eres Waschbecken im DG");
        }
        if (this.MehrpreisDSOGCheckBox.isSelected()) {
            listStrAuswahl.add(" bodentiefe Dusche im OG ");
        }
        if (this.MehrpreisDSDGCheckBox.isSelected()) {
            listStrAuswahl.add(" bodentiefe Dusche im DG ");
        }

        return listStrAuswahl;
    }
    
    //TODO Werte prüfen
    private boolean checkSani() {
    	return false;
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
    
    public void setKunde(Kunde selectedItem) {
		this.kunde = selectedItem;
	}

}
