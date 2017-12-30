package org.projektmanagement.controller;


import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.projektmanagement.model.HausSonderwunsch;
import org.projektmanagement.model.Kunde;
import org.projektmanagement.model.Sonderwunsch;
import org.projektmanagement.service.SonderwunschService;
import org.projektmanagement.utils.CSVExporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class InnentuerenControl{
	
	private static final Logger log = LoggerFactory.getLogger(InnentuerenControl.class);
	
	private Stage stage;
	private Kunde kunde;
	private List<Sonderwunsch> sonderwunsch;
	private SonderwunschService sonderwunschService = new SonderwunschService();
	private long kategorieID;

	@FXML private Spinner<Integer> spinnerAnzahlKlarglas;
	@FXML private Spinner<Integer> spinnerAnzahlMilchglas;
	@FXML private CheckBox checkBoxHolztuer;
	@FXML private Label labelPreisKlarglas;
	@FXML private Label labelPreisMilchglas;
	@FXML private Label labelPreisHolztuer;
	@FXML private TextField gesPreis;
	
	/**
	 * Initialisiert Layout aus FXML-Datei und öffnet entsprechendes Fenster.
	 */
	/*@SuppressWarnings("restriction")
	public void oeffneInnentuerenView() {
		stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		try {
			AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("InnentuerenView.fxml"));
			Scene scene = new Scene(root,560,400);
			stage.setScene(scene);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		stage.showAndWait();
	}*/
	
	
	@SuppressWarnings("restriction")
	public void init(long id, Stage stage) {
		this.stage = stage;
		kategorieID = id;
		sonderwunsch = sonderwunschService.getSonderwunschHandler().getSonderwunschByKategorieID(kategorieID);

		// hole die Beschreibung und Preise aus der Datenbank		
		labelPreisKlarglas.setText(sonderwunsch.get(0).getPreis()+" €");
		labelPreisMilchglas.setText(sonderwunsch.get(1).getPreis()+" €");
		labelPreisHolztuer.setText(sonderwunsch.get(2).getPreis()+" €");
		
		// Werte setzen aus Tabelle
		List<HausSonderwunsch> sw;
		sw = sonderwunschService.getSonderwunschHandler().getSonderwunscheHouse(kunde);
		for (HausSonderwunsch s : sw) {
			if(s.getSonderwunsch().getName().equals("Mehrpreis für die Ausführung eines Glasausschnitts (Klarglas) in einer Innentür")) {
				spinnerAnzahlKlarglas.getValueFactory().setValue(s.getMenge());
			}
			
			if(s.getSonderwunsch().getName().equals("Mehrpreis für die Ausführung eines Glasausschnitts (Milchglas) in einer Innentür")) {
				spinnerAnzahlMilchglas.getValueFactory().setValue(s.getMenge());
			}
			
			if(s.getSonderwunsch().getName().equals("Innentür zur Garage als Holztür")) {
				checkBoxHolztuer.setSelected(true);
			}
			
		}

	}
	
	
	
	/**
	 *Listener Methode für den Button "Preis berechnen". Wird aufgerufen, wenn Button "Preis berechnen" angeklickt wird.
	 * @param event Event welches ausgelöst wurde.
	 */
	@SuppressWarnings("restriction")
	@FXML protected void onClickPreisBerechnen(ActionEvent event)
	{
		//Logik zur Preisberechnung...
		
		
		double preis = 0;
		preis = spinnerAnzahlKlarglas.getValue() * sonderwunsch.get(0).getPreis() ;
		preis = preis + spinnerAnzahlMilchglas.getValue() * sonderwunsch.get(1).getPreis();
		if(checkBoxHolztuer.isSelected()) {
			preis = preis + sonderwunsch.get(2).getPreis();
		}
		gesPreis.setText(preis+" €");
		
		if(!checkDoors(spinnerAnzahlKlarglas.getValue()+spinnerAnzahlMilchglas.getValue(),checkBoxHolztuer.isSelected())) {
			gesPreis.setText("Auswahl passt nicht zum Grundriss!");
		}
		
	}
	
	/**
	 * Listener Methode für den Button "Speichern". Wird aufgerufen, wenn Button "Speichern" angeklickt wird.
	 * @param event Event welches ausgelöst wurde.
	 */
	@SuppressWarnings("restriction")
	@FXML protected void onClickSonderwuenscheSpeichern(ActionEvent event)
	{
		
		if(!checkDoors(spinnerAnzahlKlarglas.getValue()+spinnerAnzahlMilchglas.getValue(),checkBoxHolztuer.isSelected())) {
			gesPreis.setText("Auswahl passt nicht zum Grundriss!");
		}else {
		
		// Vorher alle Einträge löschen
					for (HausSonderwunsch s : sonderwunschService.getSonderwunschHandler().getSonderwunscheHouse(kunde)) 
					{
						log.debug("KID: "+s.getSonderwunsch().getKategroien().getId());
						if (s.getSonderwunsch().getKategroien().getId() == 3)
						{
							sonderwunschService.getSonderwunschHandler().removeSonderwunsch(kunde.getHouses().get(0), s.getSonderwunsch());
						}
					}
		
		// Speichern /PUSH?
		if(spinnerAnzahlKlarglas.getValue()!=0)
			sonderwunschService.getSonderwunschHandler().addSonderwunsch(sonderwunsch.get(0), kunde.getHouses().get(0),spinnerAnzahlKlarglas.getValue());
		
		if(spinnerAnzahlMilchglas.getValue()!=0)
			sonderwunschService.getSonderwunschHandler().addSonderwunsch(sonderwunsch.get(1), kunde.getHouses().get(0),spinnerAnzahlMilchglas.getValue());
		
		if(checkBoxHolztuer.isSelected())
			sonderwunschService.getSonderwunschHandler().addSonderwunsch(sonderwunsch.get(2), kunde.getHouses().get(0));
		
		}
	}
	
	
	/**
	 * Listener Methode für den Button "CSV Export". Wird aufgerufen, wenn Button "CSV Export" angeklickt wird.
	 * @param event Event welches ausgelöst wurde.
	 */
	@SuppressWarnings("restriction")
	@FXML protected void onClickCsvExport(ActionEvent event)
	{
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
	
	
	//Erstellung der Liste die als CSV exportiert werden soll
	@SuppressWarnings("restriction")
	public List<String> getCSVAuswahl()
	{
		List<String> listStrAuswahl = new ArrayList<String>();
		
		if(this.checkBoxHolztuer.isSelected())
			listStrAuswahl.add("Innentuer zur Garage als Holztuer (660 Euro)");
		
		if(spinnerAnzahlKlarglas.getValue()!=0) {
			listStrAuswahl.add("Ausfuehrung eines Glasausschnitts (Klarglas, 460 Euro), Anzahl:"+spinnerAnzahlKlarglas.getValue());
		}
		
		if(spinnerAnzahlMilchglas.getValue()!=0) {
			listStrAuswahl.add("Ausfuehrung eines Glasausschnitts (Milchglas, 560 Euro), Anzahl:"+spinnerAnzahlMilchglas.getValue());
		}

		return listStrAuswahl;
	}
	
	
	//Prüfen ob Eingabe der Sonderwünsche korrekt ist
    //TODO Es fehlt: Dachgeschoss, Keller
	private boolean checkDoors(int anzSonder, boolean holz) {
		int anz = 0;
		List<HausSonderwunsch> sw;
		boolean dg = true;
		boolean keller = true;		//TODO
		boolean zweizwei = false;	//Tür in der Wand zwischen Küche und Essbereich
		boolean zweidrei = false;	//Großes Zimmer im OG statt zwei kleinen Zimmern
		boolean zweivier = false;	//Abgetrennter Treppenraum im DG
		boolean zweisechs = false;	//Ausführung eines Bades im DG
		long id = kunde.getHouses().get(0).getId();
		
		
		//DG Vorhanden?
		if(id == 1 || id == 6 ||id == 7 ||id == 14 ||id == 15||id == 24) {
			dg = false;
		}
		
		//log.debug("Haus ID: "+id);
		
		sw = sonderwunschService.getSonderwunschHandler().getSonderwunscheHouse(kunde);
		
		for (HausSonderwunsch s : sw) {
			if(s.getSonderwunsch().getName().equals("Tür in der Wand zwischen Küche und Essbereich")) {
				zweizwei = true;
			}
			else if(s.getSonderwunsch().getName().equals("Großes Zimmer im OG statt zwei kleinen Zimmern")) {
				zweidrei = true;
			}
			else if(s.getSonderwunsch().getName().equals("Abgetrennter Treppenraum im DG")) {
				zweivier = true;
			}
			else if(s.getSonderwunsch().getName().equals("Ausführung eines Bades im DG")) {
				zweisechs = true;
			}
			
			log.debug("Gimme Grundriss: "+s.getSonderwunsch().getName());
		}
		
		//Keller
		if(dg)anz=anz+1;
		else if(!dg && keller)anz=anz+2;
		//EG
		if(zweizwei)anz=anz+1;
		//OG
		if(!zweidrei)anz=anz+4;
		if(zweidrei)anz=anz+3;
		//DG
		if(dg && zweivier && zweisechs)anz=anz+2;
		else if(dg && (zweivier||zweisechs))anz=anz+1;
		//Holztür
		if(holz && !dg)return false;
		
		if(anzSonder<=anz)return true;
		else return false;
		
	}
	
	
	/**
	 * @return the stage
	 */
	@SuppressWarnings("restriction")
	public Stage getStage() {
		return stage;
	}

	/**
	 * @param stage the stage to set
	 */
	@SuppressWarnings("restriction")
	public void setStage(Stage stage) {
		this.stage = stage;
	}
	
	public void setKunde(Kunde selectedItem) {
		this.kunde = selectedItem;
	}
	
	
	

	

	

}
