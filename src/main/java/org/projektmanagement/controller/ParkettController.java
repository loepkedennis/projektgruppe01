package org.projektmanagement.controller;

import java.awt.Button;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.projektmanagement.model.Kunde;
import org.projektmanagement.model.Sonderwunsch;
import org.projektmanagement.service.KundenService;
import org.projektmanagement.service.SonderwunschService;
import org.projektmanagement.utils.CSVExporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ParkettController implements Initializable {
	private Stage stage;
	private static final Logger log = LoggerFactory.getLogger(GrundrissController.class);
	public Kunde Kunde;
	private List<Sonderwunsch> sonderwunsch;
    private SonderwunschService sonderwunschService = new SonderwunschService();
    private KundenService kundenService = new KundenService();

	private double dOpt0, dOpt1, dOpt2, dOpt3, dOpt4, dOpt5, dOpt6, dOpt7, dOpt8, dOpt9, dErg;
	@FXML
	private Label lblOpt0;
	@FXML
	private Label lblOpt1;
	@FXML
	private Label lblOpt2;
	@FXML
	private Label lblOpt3;
	@FXML
	private Label lblOpt4;
	@FXML
	private Label lblOpt5;
	@FXML
	private Label lblOpt6;
	@FXML
	private Label lblOpt7;
	@FXML
	private Label lblOpt8;
	@FXML
	private Label lblOpt9;
	@FXML
	private Label lblGesErg;
	@FXML
	private RadioButton rbOpt0;
	@FXML
	private RadioButton rbOpt1;
	@FXML
	private RadioButton rbOpt2;
	@FXML
	private RadioButton rbOpt3;
	@FXML
	private RadioButton rbOpt4;
	@FXML
	private RadioButton rbOpt5;
	@FXML
	private RadioButton rbOpt6;
	@FXML
	private RadioButton rbOpt7;
	@FXML
	private RadioButton rbOpt8;
	@FXML
	private RadioButton rbOpt9;
	
	double gesamtPreis = 0;

	public void oeffneParkettView() {
		stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		try {
			AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("ParkettView.fxml"));
			Scene scene = new Scene(root, 560, 400);
			stage.setScene(scene);
		} catch (IOException e) {
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

		lblOpt0.setText(dOpt0 + " €");
		lblOpt1.setText(dOpt1 + " €");
		lblOpt2.setText(dOpt2 + " €");
		lblOpt3.setText(dOpt3 + " €");
		lblOpt4.setText(dOpt4 + " €");
		lblOpt5.setText(dOpt5 + " €");
		lblOpt6.setText(dOpt6 + " €");
		lblOpt7.setText(dOpt7 + " €");
		lblOpt8.setText(dOpt8 + " €");
		lblOpt9.setText(dOpt9 + " €");

		lblGesErg.setText("0 €");

		// TODO: Prüfen der Angaben
	}

	@FXML
	protected void OnClickCSVExport(ActionEvent event) {

		List<String> ueberschrift = new ArrayList<String>();
		ueberschrift.add("Landhausdielen massiv im Essbereich des EG:");
		ueberschrift.add("Landhausdielen massiv im Küchenbereich des EG:");
		ueberschrift.add("Stäbchenparkett im Essbereich des EG:");
		ueberschrift.add("Stäbchenparkett im Küchenbereich des EG:");
		ueberschrift.add("Landhausdielen massiv im OG:");
		ueberschrift.add("Stäbchenparkett im OG:");
		ueberschrift.add("Landhausdielen massiv komplett im DG:");
		ueberschrift.add("Landhausdielen massiv ohne Badbereich im DG:");
		ueberschrift.add("Stäbchenparkett im DG komplett im DG:");
		ueberschrift.add("Stäbchenparkett ohne Badbereich im DG:");

		List<String> attribute = new ArrayList<String>();

		if (rbOpt0.isSelected())
			attribute.add("ja");
		else
			attribute.add("nein");

		if (rbOpt1.isSelected())
			attribute.add("nein");
		else
			attribute.add("ja");

		if (rbOpt2.isSelected())
			attribute.add("ja");
		else
			attribute.add("nein");
		if (rbOpt3.isSelected())
			attribute.add("ja");
		else
			attribute.add("nein");
		if (rbOpt4.isSelected())
			attribute.add("ja");
		else
			attribute.add("nein");
		if (rbOpt5.isSelected())
			attribute.add("ja");
		else
			attribute.add("nein");
		if (rbOpt6.isSelected())
			attribute.add("ja");
		else
			attribute.add("nein");
		if (rbOpt7.isSelected())
			attribute.add("ja");
		else
			attribute.add("nein");
		if (rbOpt8.isSelected())
			attribute.add("ja");
		else
			attribute.add("nein");
		if (rbOpt9.isSelected())
			attribute.add("ja");
		else
			attribute.add("nein");

		CSVExporter csvExport = new CSVExporter();
		try {
			FileWriter fw = new FileWriter(csvExport.setStrPfad(stage));
			CSVExporter.writeLine(fw, ueberschrift);
			CSVExporter.writeLine(fw, attribute);
			fw.flush();
			fw.close();
			

		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	@FXML
	protected void OnClickSave(ActionEvent event) {
		// TODO: DB Speichern...

		if(rbOpt0.isSelected()) 
			sonderwunschService.getSonderwunschHandler().addSonderwunsch(sonderwunschService.getSonderwunschHandler().getSonderwunsch(34), Kunde.getHouses().get(0));
		else 
			sonderwunschService.getSonderwunschHandler().removeSonderwunsch(Kunde.getHouses().get(0), sonderwunschService.getSonderwunschHandler().getSonderwunsch(34));
		
		if(rbOpt1.isSelected()) 
			sonderwunschService.getSonderwunschHandler().addSonderwunsch(sonderwunschService.getSonderwunschHandler().getSonderwunsch(35), Kunde.getHouses().get(0));
		else 
			sonderwunschService.getSonderwunschHandler().removeSonderwunsch(Kunde.getHouses().get(0), sonderwunschService.getSonderwunschHandler().getSonderwunsch(35));
		
		if(rbOpt2.isSelected()) 
			sonderwunschService.getSonderwunschHandler().addSonderwunsch(sonderwunschService.getSonderwunschHandler().getSonderwunsch(36), Kunde.getHouses().get(0));
		else 
			sonderwunschService.getSonderwunschHandler().removeSonderwunsch(Kunde.getHouses().get(0), sonderwunschService.getSonderwunschHandler().getSonderwunsch(36));
		
		if(rbOpt3.isSelected()) 
			sonderwunschService.getSonderwunschHandler().addSonderwunsch(sonderwunschService.getSonderwunschHandler().getSonderwunsch(37), Kunde.getHouses().get(0));
		else 
			sonderwunschService.getSonderwunschHandler().removeSonderwunsch(Kunde.getHouses().get(0), sonderwunschService.getSonderwunschHandler().getSonderwunsch(37));
		
		if(rbOpt4.isSelected()) 
			sonderwunschService.getSonderwunschHandler().addSonderwunsch(sonderwunschService.getSonderwunschHandler().getSonderwunsch(38), Kunde.getHouses().get(0));
		else 
			sonderwunschService.getSonderwunschHandler().removeSonderwunsch(Kunde.getHouses().get(0), sonderwunschService.getSonderwunschHandler().getSonderwunsch(38));
		
		if(rbOpt5.isSelected()) 
			sonderwunschService.getSonderwunschHandler().addSonderwunsch(sonderwunschService.getSonderwunschHandler().getSonderwunsch(39), Kunde.getHouses().get(0));
		else 
			sonderwunschService.getSonderwunschHandler().removeSonderwunsch(Kunde.getHouses().get(0), sonderwunschService.getSonderwunschHandler().getSonderwunsch(39));
		
		if(rbOpt6.isSelected()) 
			sonderwunschService.getSonderwunschHandler().addSonderwunsch(sonderwunschService.getSonderwunschHandler().getSonderwunsch(40), Kunde.getHouses().get(0));
		else 
			sonderwunschService.getSonderwunschHandler().removeSonderwunsch(Kunde.getHouses().get(0), sonderwunschService.getSonderwunschHandler().getSonderwunsch(40));
		
		if(rbOpt7.isSelected()) 
			sonderwunschService.getSonderwunschHandler().addSonderwunsch(sonderwunschService.getSonderwunschHandler().getSonderwunsch(41), Kunde.getHouses().get(0));
		else 
			sonderwunschService.getSonderwunschHandler().removeSonderwunsch(Kunde.getHouses().get(0), sonderwunschService.getSonderwunschHandler().getSonderwunsch(41));
		
		if(rbOpt8.isSelected()) 
			sonderwunschService.getSonderwunschHandler().addSonderwunsch(sonderwunschService.getSonderwunschHandler().getSonderwunsch(42), Kunde.getHouses().get(0));
		else 
			sonderwunschService.getSonderwunschHandler().removeSonderwunsch(Kunde.getHouses().get(0), sonderwunschService.getSonderwunschHandler().getSonderwunsch(42));
		
		if(rbOpt9.isSelected()) 
			sonderwunschService.getSonderwunschHandler().addSonderwunsch(sonderwunschService.getSonderwunschHandler().getSonderwunsch(43), Kunde.getHouses().get(0));
		else 
			sonderwunschService.getSonderwunschHandler().removeSonderwunsch(Kunde.getHouses().get(0), sonderwunschService.getSonderwunschHandler().getSonderwunsch(43));
		
	}
	
	@FXML
	protected void OnSelect(ActionEvent event) {
		gesamtPreis = 0;
		
		if(rbOpt0.isSelected()){ // geht nur wenn weder 5.4 oder 5.5 ausgesucht worden sind
			gesamtPreis+=dOpt0;
		}
		if(rbOpt1.isSelected()){ // geht nur wenn 7.1 ausgesucht wurde und wenn weder 5.4 noch 5.5 ausgesucht worden sind
			gesamtPreis+=dOpt1;
		}
		if(rbOpt2.isSelected()){ 
			gesamtPreis+=dOpt2;
		}
		if(rbOpt3.isSelected()){ //  geht nur wenn 7.1 ausgesucht wurde
			gesamtPreis+=dOpt3;
		}
		if(rbOpt4.isSelected()){ // geht nur wenn weder 5.4 oder 5.5 ausgesucht worden sind
			gesamtPreis+=dOpt4;
		}
		if(rbOpt5.isSelected()){
			gesamtPreis+=dOpt5;
		}
		if(rbOpt6.isSelected()){ // geht nur wenn DG vorhanden  ist und weder 7.5 noch 5.5 ausgesucht worden sind
			gesamtPreis+=dOpt6;
		}
		if(rbOpt7.isSelected()){ // geht nur wenn DG vorhanden  ist und wenn  nicht 5.5 ausgesucht worden ist
			gesamtPreis+=dOpt7;
		}
		if(rbOpt8.isSelected()){ // geht nur wenn DG vorhanden  ist und wenn  nicht 7.5 ausgesucht worden ist
			gesamtPreis+=dOpt8;
		}
		if(rbOpt9.isSelected()){
			gesamtPreis+=dOpt9;
		}
		lblGesErg.setText(gesamtPreis+ "€");
	}

}
