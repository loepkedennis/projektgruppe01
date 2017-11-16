package org.projektmanagement.controller;


import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.projektmanagement.utils.CSVExporter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class InnentuerenControl{
	private Stage stage;

	@FXML private Spinner<Integer> spinnerAnzahlKlarglas;
	@FXML private Spinner<Integer> spinnerAnzahlMilchglas;
	@FXML private CheckBox checkBoxHolztuer;
	@FXML private Label preisKlarglas;
	@FXML private Label preisMilchglas;
	@FXML private Label preisHolztuer;
	@FXML private TextField gesPreis;
	
	/**
	 * Initialisiert Layout aus FXML-Datei und öffnet entsprechendes Fenster.
	 */
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
	}
	
	
	
	/**
	 *Listener Methode für den Button "Preis berechnen". Wird aufgerufen, wenn Button "Preis berechnen" angeklickt wird.
	 * @param event Event welches ausgelöst wurde.
	 */
	@FXML protected void onClickPreisBerechnen(ActionEvent event)
	{
		//Logik zur Preisberechnung...
		int preis = 0;
		preis = spinnerAnzahlKlarglas.getValue() * 460;
		preis = preis + spinnerAnzahlMilchglas.getValue() * 560;
		if(checkBoxHolztuer.isSelected()) {
			preis = preis + 660;
		}
		gesPreis.setText(preis+" €");
		
	}
	/**
	 * Listener Methode für den Button "Speichern". Wird aufgerufen, wenn Button "Speichern" angeklickt wird.
	 * @param event Event welches ausgelöst wurde.
	 */
	@FXML protected void onClickSonderwuenscheSpeichern(ActionEvent event)
	{
		//Logik zur Speicherung...
		

	}
	
	
	/**
	 * Listener Methode für den Button "CSV Export". Wird aufgerufen, wenn Button "CSV Export" angeklickt wird.
	 * @param event Event welches ausgelöst wurde.
	 */
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
