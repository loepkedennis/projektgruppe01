package org.projektmanagement.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ParkettController implements Initializable{

	private Stage stage;
	private static final Logger log = LoggerFactory.getLogger(GrundrissController.class);

	private double dOpt0, dOpt1, dOpt2, dOpt3, dOpt4, dOpt5, dOpt6, dOpt7, dOpt8, dOpt9, dErg;
	@FXML private Label lblOpt0;
	@FXML private Label lblOpt1;
	@FXML private Label lblOpt2;
	@FXML private Label lblOpt3;
	@FXML private Label lblOpt4;
	@FXML private Label lblOpt5;
	@FXML private Label lblOpt6;
	@FXML private Label lblOpt7;
	@FXML private Label lblOpt8;
	@FXML private Label lblOpt9;
	@FXML private Label lblGesErg;
	@FXML private RadioButton rbOpt0;
	@FXML private RadioButton rbOpt1;
	@FXML private RadioButton rbOpt2;
	@FXML private RadioButton rbOpt3;
	@FXML private RadioButton rbOpt4;
	@FXML private RadioButton rbOpt5;
	@FXML private RadioButton rbOpt6;
	@FXML private RadioButton rbOpt7;
	@FXML private RadioButton rbOpt8;
	@FXML private RadioButton rbOpt9;
	
	public void oeffneParkettView() {
		stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		try {
			AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("ParkettView.fxml"));
			Scene scene = new Scene(root,560,400);
			stage.setScene(scene);
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		stage.showAndWait();
	}
	
	public void initialize(URL arg0, ResourceBundle arg1) {
		rbOpt0.setText("Landhausdielen massiv im Essbereich des EG:");
		rbOpt1.setText("Landhausdielen massiv im Küchenbereich des EG:");
		rbOpt2.setText("Stäbchenparkett im Essbereich des EG:");
		rbOpt3.setText("Stäbchenparkett im Küchenbereich des EG:");
		rbOpt4.setText("Landhausdielen massiv im OG:");
		rbOpt5.setText("Stäbchenparkett im OG:");
		rbOpt6.setText("Landhausdielen massiv komplett im DG:");
		rbOpt7.setText("Landhausdielen massiv ohne Badbereich im DG:");
		rbOpt8.setText("Stäbchenparkett im DG komplett im DG:");
		rbOpt9.setText("Stäbchenparkett ohne Badbereich im DG:");
		
		dOpt0 = 2890;
		dOpt1 = 2090;
		dOpt2 = 2090;
		dOpt3 = 1790;
		dOpt4 = 2490;
		dOpt5 = 1690;
		dOpt6 = 2490;
		dOpt7 = 2090;
		dOpt8 = 1690;
		dOpt9 = 1690;
		
		lblOpt0.setText(dOpt0+" €");
		lblOpt1.setText(dOpt1+" €");
		lblOpt2.setText(dOpt2+" €");
		lblOpt3.setText(dOpt3+" €");
		lblOpt4.setText(dOpt4+" €");
		lblOpt5.setText(dOpt5+" €");
		lblOpt6.setText(dOpt6+" €");
		lblOpt7.setText(dOpt7+" €");
		lblOpt8.setText(dOpt8+" €");
		lblOpt9.setText(dOpt9+" €");
		
		lblGesErg.setText("0 €");
		
		//TODO: Prüfen der Angaben
	}
	
	@FXML protected void OnClickCSVExport(ActionEvent event)
	{
		//TODO: CSV Export...
		
	}
	@FXML protected void OnClickSave(ActionEvent event)
	{
		//TODO: DB Speichern...
		
	}

}
