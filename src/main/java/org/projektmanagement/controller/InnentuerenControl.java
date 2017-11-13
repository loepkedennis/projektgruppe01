package org.projektmanagement.controller;


import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
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
		//Logik zum Csv-Export
	}
	
	
	

	

	

}
