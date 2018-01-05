package org.projektmanagement.controller;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import org.projektmanagement.model.Kunde;
import org.projektmanagement.model.Sonderwunsch;
import org.projektmanagement.service.KundenService;
import org.projektmanagement.service.SonderwunschService;

/*import java.io.FileWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;*/

import org.projektmanagement.utils.CSVExporter;
/*import org.slf4j.Logger;
import org.slf4j.LoggerFactory;*/

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class AussenanlagenController /*implements Initializable*/ {
	private Stage stage;
	
	private double dOpt0, dOpt1, dOpt2, dOpt3, dOpt4, dOpt5, dOpt6;
	@FXML
	private Label txtFieldAbsEG;
	@FXML
	private Label txtFieldVorEG;
	@FXML
	private Label txtFieldVorDG;
	@FXML
	private Label txtFieldElkEG;
	@FXML
	private Label txtFieldElkDG;
	@FXML
	private Label txtFieldElkAG;
	@FXML
	private Label txtFieldSekSG;
	@FXML
	private Label txtFieldGesamtPreis;
	@FXML
	private CheckBox checkBoxAbsEG;
	@FXML
	private CheckBox checkBoxVorEG;
	@FXML
	private CheckBox checkBoxVorDG;
	@FXML
	private CheckBox checkBoxElkEG;
	@FXML
	private CheckBox checkBoxElkDG;
	@FXML
	private CheckBox checkBoxElkAG;
	@FXML
	private CheckBox checkBoxSekSG;
	private double gesamtPreis;
	
	public Kunde Kunde;
	private List<Sonderwunsch> sonderwunsch;
    private SonderwunschService sonderwunschService = new SonderwunschService();
    private KundenService kundenService = new KundenService();
    
	public void init() {
		//Preise aus der DB holen
        sonderwunsch = sonderwunschService.getSonderwunschHandler().getSonderwunschByKategorieID(8);
        dOpt0 = sonderwunsch.get(0).getPreis();
        dOpt1 = sonderwunsch.get(1).getPreis();
        dOpt2 = sonderwunsch.get(2).getPreis();
        dOpt3 = sonderwunsch.get(3).getPreis();
        dOpt4 = sonderwunsch.get(4).getPreis();
        dOpt5 = sonderwunsch.get(5).getPreis();
        dOpt6 = sonderwunsch.get(6).getPreis();
        
		txtFieldAbsEG.setText(dOpt0 + " €"); 
		txtFieldVorEG.setText(dOpt1 + " €"); 
		txtFieldVorDG.setText(dOpt2 + " €");
		txtFieldElkEG.setText(dOpt3 + " €");
		txtFieldElkDG.setText(dOpt4 + " €");
		txtFieldElkAG.setText(dOpt5 + " €");
		txtFieldSekSG.setText(dOpt6 + " €");
		
		checkBoxAbsEG.setSelected(false);
		checkBoxVorEG.setSelected(false);
		checkBoxVorDG.setSelected(false);
		checkBoxElkEG.setSelected(false);
		checkBoxElkDG.setSelected(false);
		checkBoxElkAG.setSelected(false);
		checkBoxSekSG.setSelected(false);
		
		checkBoxElkEG.setDisable(true); //abhaengig von VorEG
		checkBoxElkDG.setDisable(true); //abhaengig von VorDG
    }
	
	@FXML
	void onClickCsvExport() {
		// TODO: DB Speichern...
		List<String> ueberschrift = new ArrayList<String>();
		ueberschrift.add("Abstellraum auf der Terrasse des EG");
		ueberschrift.add("Vorbereitung für elektrische Antriebe Markise EG");
		ueberschrift.add("Vorbereitung für elektrische Antriebe Markise DG");
		ueberschrift.add("Elektrische Markise EG");
		ueberschrift.add("Elektrische Markise DG");
		ueberschrift.add("Elektrischen Antrieb für das Garagentor");
		ueberschrift.add("Sektionaltor anstatt Schwingtor für die Garage");
		
		List<String> attribute = new ArrayList<String>();
		
		if (checkBoxAbsEG.isSelected())
			attribute.add("ja");
		else
			attribute.add("nein");
		
		if (checkBoxVorEG.isSelected())
			attribute.add("ja");
		else
			attribute.add("nein");
		
		if (checkBoxVorDG.isSelected())
			attribute.add("ja");
		else
			attribute.add("nein");
		
		if (checkBoxElkEG.isSelected())
			attribute.add("ja");
		else
			attribute.add("nein");
		
		if (checkBoxElkDG.isSelected())
			attribute.add("ja");
		else
			attribute.add("nein");
		
		if (checkBoxElkAG.isSelected())
			attribute.add("ja");
		else
			attribute.add("nein");
		
		if (checkBoxSekSG.isSelected())
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
	void onClickDbSave() {
		//44 Abstellraum auf der Terrasse des EG
		if(checkBoxAbsEG.isSelected())
            sonderwunschService.getSonderwunschHandler().addSonderwunsch(sonderwunschService.getSonderwunschHandler().getSonderwunsch(44), Kunde.getHouses().get(0));
        else
            sonderwunschService.getSonderwunschHandler().removeSonderwunsch(Kunde.getHouses().get(0), sonderwunschService.getSonderwunschHandler().getSonderwunsch(44));

		//45 Vorbereitung für elektrische Antriebe Markise EG
		if(checkBoxVorEG.isSelected())
            sonderwunschService.getSonderwunschHandler().addSonderwunsch(sonderwunschService.getSonderwunschHandler().getSonderwunsch(45), Kunde.getHouses().get(0));
        else
            sonderwunschService.getSonderwunschHandler().removeSonderwunsch(Kunde.getHouses().get(0), sonderwunschService.getSonderwunschHandler().getSonderwunsch(45));
		
		//46 Vorbereitung für elektrische Antriebe Markise DG
		if(checkBoxVorDG.isSelected())
            sonderwunschService.getSonderwunschHandler().addSonderwunsch(sonderwunschService.getSonderwunschHandler().getSonderwunsch(46), Kunde.getHouses().get(0));
        else
            sonderwunschService.getSonderwunschHandler().removeSonderwunsch(Kunde.getHouses().get(0), sonderwunschService.getSonderwunschHandler().getSonderwunsch(46));
		
		//47 Elektrische Markise EG
		if(checkBoxElkEG.isSelected())
            sonderwunschService.getSonderwunschHandler().addSonderwunsch(sonderwunschService.getSonderwunschHandler().getSonderwunsch(47), Kunde.getHouses().get(0));
        else
            sonderwunschService.getSonderwunschHandler().removeSonderwunsch(Kunde.getHouses().get(0), sonderwunschService.getSonderwunschHandler().getSonderwunsch(47));
		
		//48 Elektrische Markise DG
		if(checkBoxElkDG.isSelected())
            sonderwunschService.getSonderwunschHandler().addSonderwunsch(sonderwunschService.getSonderwunschHandler().getSonderwunsch(48), Kunde.getHouses().get(0));
        else
            sonderwunschService.getSonderwunschHandler().removeSonderwunsch(Kunde.getHouses().get(0), sonderwunschService.getSonderwunschHandler().getSonderwunsch(48));
		
		//49 Elektrischen Antrieb für das Garagentor
		if(checkBoxElkAG.isSelected())
            sonderwunschService.getSonderwunschHandler().addSonderwunsch(sonderwunschService.getSonderwunschHandler().getSonderwunsch(49), Kunde.getHouses().get(0));
        else
            sonderwunschService.getSonderwunschHandler().removeSonderwunsch(Kunde.getHouses().get(0), sonderwunschService.getSonderwunschHandler().getSonderwunsch(49));
		
		//50 Sektionaltor anstatt Schwingtor für die Garage
		if(checkBoxSekSG.isSelected())
            sonderwunschService.getSonderwunschHandler().addSonderwunsch(sonderwunschService.getSonderwunschHandler().getSonderwunsch(50), Kunde.getHouses().get(0));
        else
            sonderwunschService.getSonderwunschHandler().removeSonderwunsch(Kunde.getHouses().get(0), sonderwunschService.getSonderwunschHandler().getSonderwunsch(50));
	}
	
	@FXML
	protected void OnSelect(ActionEvent event) {
		gesamtPreis = 0;
		//Abstellraum auf der Terrasse des EG
		if(checkBoxAbsEG.isSelected())
			gesamtPreis+=dOpt0;		
		//Vorbereitung für elektrische Antriebe Markise EG
		if(checkBoxVorEG.isSelected())
			gesamtPreis+=dOpt1;
		else
			checkBoxElkEG.setDisable(true);		
		//Vorbereitung für elektrische Antriebe Markise DG:
		if(checkBoxVorDG.isSelected())
			gesamtPreis+=dOpt2;
		else
			checkBoxElkDG.setDisable(true);		
		//Elektrische Markise EG
		if(checkBoxElkEG.isSelected())
			gesamtPreis+=dOpt3;
		//Elektrische Markise DG
		if(checkBoxElkDG.isSelected())
			gesamtPreis+=dOpt4;
		//Elektrischen Antrieb für das Garagentor
		if(checkBoxElkAG.isSelected())
			gesamtPreis+=dOpt5;
		//Sektionaltor anstatt Schwingtor für die Garage
		if(checkBoxSekSG.isSelected())
			gesamtPreis+=dOpt6;
		txtFieldGesamtPreis.setText(gesamtPreis+ "€");
	}
}
