package org.projektmanagement.controller;

import java.io.FileWriter;
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
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class FliesenController implements Initializable {
	private Stage stage;
	//private static final Logger log = LoggerFactory.getLogger(FliesenController.class);
	
	private double dOpt0, dOpt1, dOpt2, dOpt3, dOpt4, dOpt5, dErg;
	@FXML
	private Label txtFieldNoKEG;
	@FXML
	private Label txtFieldNoBOG;
	@FXML
	private Label txtFieldMpKEG;
	@FXML
	private Label txtFieldMpBOG;
	@FXML
	private Label txtFieldBOG;
	@FXML
	private Label txtFieldMpBDG;
	@FXML
	private Label txtFieldGesamtPreis;
	@FXML
	private CheckBox checkBoxNoKuecheEG;
	@FXML
	private CheckBox checkBoxNoBadOG;
	@FXML
	private CheckBox checkBoxMpKuecheEG;
	@FXML
	private CheckBox checkBoxMpBadOG;
	@FXML
	private CheckBox checkBoxBadOG;
	@FXML
	private CheckBox checkBoxMpBadDG;
	private double gesamtPreis;

	public Kunde Kunde;
	private List<Sonderwunsch> sonderwunsch;
    private SonderwunschService sonderwunschService = new SonderwunschService();
    private KundenService kundenService = new KundenService();
	
	public void initialize(URL arg0, ResourceBundle arg1) {
//		dOpt0 = 2890;
//		dOpt1 = 2090;
//		dOpt2 = 2090;
//		dOpt3 = 1790;
//		dOpt4 = 2490;
//		dOpt5 = 1690;
//		
//		txtFieldNoKEG.setText(dOpt0 + " €"); //Wenn ausgewählt ausgabe 
//		txtFieldNoBOG.setText(dOpt1 + " €"); // wenn ausgewählt ausgabe
//		txtFieldMpKEG.setText(dOpt2 + " €");
//		txtFieldMpBOG.setText(dOpt3 + " €");
//		txtFieldBOG.setText(dOpt4 + " €");
//		txtFieldMpBDG.setText(dOpt5 + " €");
//		checkBoxMpBadDG.setDisable(true);
//		
//		// Angaben Prüfung
//	
	}
	
	public void init() {
		//Preise aus der DB holen
        sonderwunsch = sonderwunschService.getSonderwunschHandler().getSonderwunschByKategorieID(6);
        dOpt0 = sonderwunsch.get(0).getPreis(); //660
        dOpt1 = sonderwunsch.get(1).getPreis(); //160
        dOpt2 = sonderwunsch.get(2).getPreis(); //660
        dOpt3 = sonderwunsch.get(3).getPreis(); //8990
        dOpt4 = sonderwunsch.get(4).getPreis(); //9990
        dOpt5 = sonderwunsch.get(5).getPreis(); //9990
        
        
        
        // Die häkchen setzen anhand daten aus datenbank ???
       // if (sonderwunschService.getSonderwunschHandler().getSonderwunsch(28) != null)
         //       checkBoxNoKuecheEG.setSelected(true);

        //sonderwunschService.getSonderwunschHandler().getSonderwunsch(28)
        //checkBoxNoKuecheEG.isSelected()

        txtFieldNoKEG.setText(dOpt0 + " €"); //Wenn ausgewählt ausgabe 
        txtFieldNoBOG.setText(dOpt1 + " €"); // wenn ausgewählt ausgabe
        txtFieldMpKEG.setText(dOpt2 + " €");
        txtFieldMpBOG.setText(dOpt3 + " €");
        txtFieldBOG.setText(dOpt4 + " €");
        txtFieldMpBDG.setText(dOpt5 + " €");
        checkBoxMpBadDG.setDisable(true);

    }
	
	
	
	@FXML
	void onClickCsvExport() {
		// 
		List<String> ueberschrift = new ArrayList<String>();
		ueberschrift.add("Keine Fliesen im Küchenbereich EG");
		ueberschrift.add("Keine Fliesen im Bad des OG");
		ueberschrift.add("Mehrpreis großform. Fliesen Küche EG");
		ueberschrift.add("Mehrpreis großform. Fliesen Bad OG");
		ueberschrift.add("Fliesen Bad OG");
		ueberschrift.add("Mehrpreis großform. Fliesen Bad DG");
		
		List<String> attribute = new ArrayList<String>();
		
		if (checkBoxNoKuecheEG.isSelected()){
			attribute.add("ja");
		}
		else
			attribute.add("nein");
		
		if (checkBoxNoBadOG.isSelected()){
			attribute.add("ja");
		}
		else
			attribute.add("nein");
		
		if (checkBoxMpKuecheEG.isSelected()){
			attribute.add("ja");
		}
		else
			attribute.add("nein");
		
		if (checkBoxMpBadOG.isSelected()){
			attribute.add("ja");
		}
		else
			attribute.add("nein");
		
		if (checkBoxBadOG.isSelected()){
			attribute.add("ja");
		}
		else
			attribute.add("nein");
		
		if (checkBoxMpBadDG.isSelected()){
			attribute.add("ja");
		}
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
	//Ein fall fehlt
    void onClickDbSave() {
        if(checkBoxNoKuecheEG.isSelected())
            sonderwunschService.getSonderwunschHandler().addSonderwunsch(sonderwunschService.getSonderwunschHandler().getSonderwunsch(28), Kunde.getHouses().get(0));
        else
            sonderwunschService.getSonderwunschHandler().removeSonderwunsch(Kunde.getHouses().get(0), sonderwunschService.getSonderwunschHandler().getSonderwunsch(28));
        
        if(checkBoxNoBadOG.isSelected())
            sonderwunschService.getSonderwunschHandler().addSonderwunsch(sonderwunschService.getSonderwunschHandler().getSonderwunsch(29), Kunde.getHouses().get(0));
        else
            sonderwunschService.getSonderwunschHandler().removeSonderwunsch(Kunde.getHouses().get(0), sonderwunschService.getSonderwunschHandler().getSonderwunsch(29));
        if(checkBoxMpKuecheEG.isSelected())
            sonderwunschService.getSonderwunschHandler().addSonderwunsch(sonderwunschService.getSonderwunschHandler().getSonderwunsch(30), Kunde.getHouses().get(0));
        else
            sonderwunschService.getSonderwunschHandler().removeSonderwunsch(Kunde.getHouses().get(0), sonderwunschService.getSonderwunschHandler().getSonderwunsch(30));
        
        if(checkBoxMpBadOG.isSelected())
            sonderwunschService.getSonderwunschHandler().addSonderwunsch(sonderwunschService.getSonderwunschHandler().getSonderwunsch(31), Kunde.getHouses().get(0));
        else
            sonderwunschService.getSonderwunschHandler().removeSonderwunsch(Kunde.getHouses().get(0), sonderwunschService.getSonderwunschHandler().getSonderwunsch(31));
        if(checkBoxMpBadDG.isSelected())
            sonderwunschService.getSonderwunschHandler().addSonderwunsch(sonderwunschService.getSonderwunschHandler().getSonderwunsch(32), Kunde.getHouses().get(0));
        else
            sonderwunschService.getSonderwunschHandler().removeSonderwunsch(Kunde.getHouses().get(0), sonderwunschService.getSonderwunschHandler().getSonderwunsch(32));
	}
	
	@FXML
	protected void OnSelect(ActionEvent event) {
		gesamtPreis = 0;
		checkBoxMpBadDG.setDisable(true);
		
		if(checkBoxNoKuecheEG.isSelected()){//1
			gesamtPreis+=dOpt0;
			//Info ausgabe: Standartmäßig wird er Küchenbereich gefliest
			checkBoxMpKuecheEG.setDisable(true);
		}
		else
			checkBoxMpKuecheEG.setDisable(false);
		
		
		
		
		if(checkBoxNoBadOG.isSelected()){//2
			gesamtPreis+=dOpt1;
			//Info Ausgabe; Standartmäßig wird das Bad im OG gefliest
			checkBoxMpBadOG.setDisable(true);
		}
		else
			checkBoxMpBadOG.setDisable(false);
		
		
		if(checkBoxMpKuecheEG.isSelected()){//3
			gesamtPreis+=dOpt2;
			checkBoxNoKuecheEG.setDisable(true);
		}
		else
			checkBoxNoKuecheEG.setDisable(false);
		
		
		if(checkBoxMpBadOG.isSelected()){ // nur wenn 2 nicht ausgewählt ist 4
			gesamtPreis+=dOpt3;
			
		}
	
		
		
		
		if(checkBoxBadOG.isSelected()){ //wenn DG vorhanden und 2.6 vorhanden 5
			gesamtPreis+=dOpt4;
			checkBoxMpBadDG.setDisable(false);
		}
		else
			checkBoxMpBadDG.setSelected(false);
		if(checkBoxMpBadDG.isSelected()){ // nur wenn 7.5 ausgewählt ist 6
			gesamtPreis+=dOpt5;
		}
		
		txtFieldGesamtPreis.setText(gesamtPreis+ "€");
	}



	
	

}
