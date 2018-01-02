package org.projektmanagement.controller;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.projektmanagement.model.Kunde;
import org.projektmanagement.utils.CSVExporter;
import java.util.List;
import javafx.stage.Stage;
import javafx.scene.control.CheckBox;
import javafx.fxml.FXML;


public class AussenanlagenController {
	@FXML private CheckBox cbAbstellraum;
	@FXML private CheckBox cbVorbereitungEAntriebMarkiseEG;
	@FXML private CheckBox cbVortbereitungEAntriebMarkiseDG;
	@FXML private CheckBox cbMarkiseEG;
	@FXML private CheckBox cbMarkiseDG;
	@FXML private CheckBox cbEAntriebGaragentor;
	@FXML private CheckBox cbSektionaltor;
	private Stage stage;
	private Kunde kunde;
	
	
	public void init(Stage stage)
	{
		this.stage = stage;
	}
	public void setKunde(Kunde kunde)
	{
		this.kunde = kunde;
	}
	public void exportToCsv() throws IOException
	{
		List<String> values = new ArrayList();
		CSVExporter exporter = new CSVExporter();
		
		
		if(cbAbstellraum.isSelected()){
			values.add("Abstellraum auf der Terrasse des EG: 3.590 Euro");
		}
		if(cbVorbereitungEAntriebMarkiseEG.isSelected()){
			values.add("Vorbereitung für elektrische Antriebe Markise EG: 170 Euro");
		}
		if(cbVortbereitungEAntriebMarkiseDG.isSelected()){
			values.add("Vorbereitung für elektrische Antriebe Markise DG: 170 Euro");
		}
		if(cbMarkiseEG.isSelected()){
			values.add("Elekrische Markise EG : 890 Euro");
		}
		if(cbMarkiseDG.isSelected()){
			values.add("Elekrische Markise DG : 890 Euro");
		}
		if(cbEAntriebGaragentor.isSelected()){
			values.add("Elektrischen Antrieb für das Garagentor: 990 Euro");
		}
		
		if(cbSektionaltor.isSelected()){
			values.add("Sektionaltor anstatt Schwingtor für die Garage: 790 Euro");
		}
		
		FileWriter fileWriter = new FileWriter(exporter.setStrPfad(stage));
		CSVExporter.writeLine(fileWriter, values);
		
		
	}
}
