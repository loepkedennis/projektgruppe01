package org.projektmanagement.controller;
//////////////////////////////////////////////////
// Author: Andreas Köhler						//
//////////////////////////////////////////////////
import java.io.FileWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.projektmanagement.model.Kunde;
import org.projektmanagement.model.Sonderwunsch;
import org.projektmanagement.service.SonderwunschService;
import org.projektmanagement.utils.CSVExporter;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;


public class HeizungController {
	// CheckBox
	@FXML
	private CheckBox checkBoxGrossesZimmer;
	@FXML
	private CheckBox checkBoxDachgeschoss;
	@FXML
	private CheckBox checkBoxBadDachgeschoss;
	@FXML
	private CheckBox checkBoxFussbodenheizung;
	// Label
	@FXML
	private Label lblGesamtbetrag;
	@FXML
	private Label lblERRZusatzHeizung;
	@FXML
	private Label lblPreisZusatzHeizung;
	@FXML
	private Label lblZusatzHandtuchHeizung;
	@FXML
	private Label lblPreisZusatzHandtuchHeizung;
	@FXML
	private Label lblERRZusatzHandtuchHeizung;
	@FXML
	private Label lblERRGlatteHeizung;
	@FXML
	private Label lblPreisGlatteHeizung;
	@FXML
	private Label lblFussbodenheizung;
	@FXML
	private Label lblZusatzHeizung;
	// TextField
	@FXML
	private TextField txtZusatzHeizung;
	@FXML
	private TextField txtZusatzHandtuchHeizung;
	@FXML
	private TextField txtGlatteHeizung;
	// Button
	@FXML
	private Button btnSpeichern;
	@FXML
	private Button btnAbbrechen;
	@FXML
	private Button btnCSVExport;	
	
	//Kundenvariable
	private Kunde kunde;
	private List<Sonderwunsch> sonderwunsch;
	private SonderwunschService sonderwunschService = new SonderwunschService();
	
	
	// Damit zwei Kommastellen angezeigt werden, wird mit f.format(double wert) gecastet
	DecimalFormat f = new DecimalFormat("#0.00"); 
	
	// Arbeitsvariablen
	int AnzahlHeizungen = 0;
	double preisFussbodenHeizung = 0;
	double gesamtbetrag = 0;
	
	// Variablen für die Anzahl an Heizungen
	int AnzahlZusatzHeizung = 0;
	int AnzahlGlatteHeizung = 0;
	int AnzahlHandtuchHeizung = 0;
	boolean FussbodenHeizung = false;
	
	// Variablen für die einzelnen Preise der Heizungen
	double preisFussbodenHeizungohneDG = 0;
	double preisFussbodenHeizungmitDG = 0;
	double preisZusatzHeizung = 0;
	double preisGlatteHeizung = 0;
	double preisHandtuchHeizung = 0;
	
	// Hauskonfigurationsvariablen
	boolean dachgeschoss = false;
	boolean badimDach = false;
	boolean grossesZimmerimOG = false;
	boolean existiertSonderwunsch = false;
	
	@FXML
	private void initialize()
	{
		// Nur in Entwicklerversion wegen Entwicklercontrolls
		// Anfang Zusatz Entwicklerblock /////////////////
		if (dachgeschoss)								//
		{												//
			checkBoxBadDachgeschoss.setDisable(false);	//
		}												//
		else if (badimDach)								//
		{												//
			checkBoxBadDachgeschoss.setDisable(true);	//
			checkBoxBadDachgeschoss.setSelected(false);	//
			chkBadDach();								//
		}												//
		else											//
		{												//
			checkBoxBadDachgeschoss.setDisable(true);	//
			checkBoxBadDachgeschoss.setSelected(false);	//
		}												//
		AnzahlHeizungen = 0;							//
		AnzahlZusatzHeizung = 0;						//
		AnzahlGlatteHeizung = 0;						//
		AnzahlHandtuchHeizung = 0;				    	//
														//
														//
		// Ende Zusatz Entwicklerblock ///////////////////
		

			// Konfiguration aus der Datenbank ziehen:
			//
			// boolean dachgeschoss auf true setzen wenn ein Dachgeschoss vorhanden ist 
			// boolean badimDach auf true setzen wenn ein Dachgeschoss und ein Bad im Dachgeschoss vorhanden ist
			// boolean grossesZimmerimOG auf true setzen wenn ein grosses Zimmer vorhanden ist
			//
			// Daten aus der Datenbank holen: // noch zu machen
			// Es muss aus der Datenbak geholt werden:
			// Der Haustyp: Ob das Haus ein Dachgeschoss hat oder nicht.
			// Ob das Haus ein Dachgeschoss hat, wenn ja auch ob im Dachgeschoss ein Bad ausgeführt ist
			// Ob das Haus im OG ein grosses Zimmer anstatt zwei kleine hat.
			
			
			// Anzahl Heizungen anhannd der Konfiguration bestimmen:
			// Anforderung:
			//
			// Keller: 	1, falls DG vorhanden
			//			2, falls DG nicht vorhanden
			// 
			// EG:		2
			//
			// OG:		4, falls kein großes Zimmer im OG
			// 			3, falls ein großes Zimmer im OG
			//
			// DG:		3, falls DG vorhanden und Bad im Dach ausgeführt
			//			2, falls DG vorhanden 
			//			0, falls DG nicht vorhanden
			//
			// Maximale mögliche Konfig:							Anzahl Heizung:
			//	DG vorhanden	Bad im DG	Großes Zimmer im OG		Keller	EG	OG	DG	Gesamt		Stimmt Ergebnis
			//	false			false		false					2		2	4	0	8			OK
			//	false			false		true					2		2	3	0	7			OK
			//	true			false		false					1		2	4	2	9			OK
			//	true			false		true					1		2	3	2	8			OK
			//	true			true		false					1		2	4	3	10			OK
			//	true			true		true					1		2	3	3	9			OK
			
			// EG Heizungen ist immer 2
			AnzahlHeizungen += 2;
					
			if (dachgeschoss)
			{
				// Wenn Dachgeschoss vorhanden, da Keller 1 und DG mindestens 2 hat wird mit 3 addiert 
				AnzahlHeizungen += 3;
			}
			else
			{
				AnzahlHeizungen += 2;
			}
			if (grossesZimmerimOG)
			{
				AnzahlHeizungen += 3;
			}
			else
			{
				AnzahlHeizungen += 4;
			}
			if (dachgeschoss && badimDach)
			{
				// Wenn im Dach ein Bad ausgeführt ist ist im DG ein Zusätzlicher Heizkörper
				AnzahlHeizungen += 1;
				
				// Wenn DG vorhanden und Bad im Dach vorhanden Handtuchheizung sichtbar machen
				lblZusatzHandtuchHeizung.setVisible(true);
				lblPreisZusatzHandtuchHeizung.setVisible(true);
				txtZusatzHandtuchHeizung.setVisible(true);
			}
			else 
			{
				// Handtuchheizung unsichtbar machen
				lblZusatzHandtuchHeizung.setVisible(false);
				lblPreisZusatzHandtuchHeizung.setVisible(false);
				txtZusatzHandtuchHeizung.setVisible(false);
			}
			
			// Preise aus der Datenbank holen // noch zu machen
				// Es müssen die Preise von der Zusätzlichen Heizung, sowie
			    // der Preis von den Glatten Heizungen, sowie
				// der Preis von den Handtuchheizungen, sowie
				// der Preis von der Fussbodenheizung ohne DG, sowie
				// der Preis von der Fussbodenheizung mit DG geholt werden
			
			// Statische Preise zwecks Entwicklung
			preisZusatzHeizung = 660;	//Glatte Heizung Preis setzen 160 € je Stück
			preisGlatteHeizung = 160;	//Zusatzheizung Preis setzen: 660 € je Stück	
			preisHandtuchHeizung = 660;	//Handtuchheizung Preis setzen 660 € je Stück
			preisFussbodenHeizungohneDG = 8990; // Fussbodenheizung ohne DG Preis setzen 8990 €
			preisFussbodenHeizungmitDG = 9990; // Fussbodenheizung mit DG Preis setzen 9990 €
			
			// Richtiger Preis für Fussbodenheizung setzen
			if (dachgeschoss)
			{
				// Wenn Dachgeschoss vorhanden
				preisFussbodenHeizung = preisFussbodenHeizungmitDG;
			}
			else
			{
				preisFussbodenHeizung = preisFussbodenHeizungohneDG;
			}
			
			// Preislabels setzen
			lblPreisZusatzHeizung.setText(lblPreisZusatzHeizung.getText().replaceAll("%", ""+f.format(preisZusatzHeizung)));
			lblPreisGlatteHeizung.setText(lblPreisGlatteHeizung.getText().replaceAll("%", ""+f.format(preisGlatteHeizung)));
			lblPreisZusatzHandtuchHeizung.setText(lblPreisZusatzHandtuchHeizung.getText().replaceAll("%", ""+f.format(preisHandtuchHeizung)));
			checkBoxFussbodenheizung.setText(checkBoxFussbodenheizung.getText().replaceAll("%", ""+f.format(preisFussbodenHeizung)));
			
			// Nur in Entwicklerversion wegen Entwicklercontrolls
			// Anfang Zusatz Entwicklerblock ////////////////////////////////////////////////////////////////////////////////////////////////////////////
			checkBoxFussbodenheizung.setText(checkBoxFussbodenheizung.getText().replaceAll("[0-9][0-9][0-9][0-9].[0-9][0-9]", ""+f.format(preisFussbodenHeizung)));   //
			// Ende Zusatz Entwicklerblock //////////////////////////////////////////////////////////////////////////////////////////////////////////////
						
			// Es muss aus der Datenbank mittels der KundeId gelesen werden ob der Sonderwunsch schon existiert, 
			// wenn existiert existiertSonderwunsch auf true setzen und werte der Heizungen setzen:
			
			if (existiertSonderwunsch)
			{
				// Einzelne Anzahl der Zusätlichen Heizungen über DB setzen:
				
				// Statisch zwecks Entwicklung:
					AnzahlZusatzHeizung = 5;
					AnzahlGlatteHeizung = 12;
					AnzahlHandtuchHeizung = 0;
					FussbodenHeizung = true;
					
				// Setzen der einzelnen Textfields und Checkboxen auf den vorgesehenden Wert:
					if (AnzahlZusatzHeizung != 0)
					{
						txtZusatzHeizung.setText(AnzahlZusatzHeizung + "");
					}
					if (AnzahlGlatteHeizung != 0)
					{
						txtGlatteHeizung.setText(AnzahlGlatteHeizung + "");
					}
					if (AnzahlHandtuchHeizung != 0)
					{
						txtZusatzHandtuchHeizung.setText(AnzahlHandtuchHeizung + "");
					}
					checkBoxFussbodenheizung.setSelected(FussbodenHeizung);
			}
			
			// Update Labels etc. zu initialiseren				
			Update();			
}
	@FXML
	private void CSVExport()
	{
		List <String> ueberschrift = new ArrayList<String>();
		ueberschrift.add("Anzahl Zusatz Heizung");
		ueberschrift.add("Anzahl Glatte Heizung");
		ueberschrift.add("Anzahl Handtuchheizung");
		ueberschrift.add("Fussbodenheizung");
		ueberschrift.add("Gesamtpreis");
		List <String> werte = new ArrayList<String>();
		werte.add(txtZusatzHeizung.getText());
		werte.add(txtGlatteHeizung.getText());
		werte.add(txtZusatzHandtuchHeizung.getText());
		if (checkBoxFussbodenheizung.isSelected())
		{
			werte.add("ja");
		}
		else
		{
			werte.add("nein");
		}
		werte.add("" + gesamtbetrag);
		CSVExporter csvExport = new CSVExporter();
		try
		{
			FileWriter filePfad = new FileWriter(csvExport.setStrPfad((Stage) btnCSVExport.getScene().getWindow()));
			CSVExporter.writeLine(filePfad, ueberschrift);
			CSVExporter.writeLine(filePfad, werte);
			
			filePfad.flush();
			filePfad.close();	
		}catch(Exception e)
		{
			
		}
	}
	@FXML
	private void Speichern()
	{
		// Sachen die gemacht werden müssen zum Speichern
		
		// String für den Confirmation Alert aufbauen
		String Ausgabe = "Art der Heizung\tAnzahl\tPreis" + System.lineSeparator();
		if (AnzahlZusatzHeizung != 0)
		{
			Ausgabe += "Zusatzheizung\t\t" + AnzahlZusatzHeizung + "\t\t" + f.format((AnzahlZusatzHeizung*preisZusatzHeizung)) + " €" + System.lineSeparator();
		}
		if (AnzahlGlatteHeizung != 0)
		{
			Ausgabe += "Glatteheizungen\t" + AnzahlGlatteHeizung + "\t\t" + f.format((AnzahlGlatteHeizung*preisGlatteHeizung)) + " €" + System.lineSeparator();
		}
		if (badimDach && AnzahlHandtuchHeizung != 0)
		{
			Ausgabe += "Handtuchheizung\t" + AnzahlHandtuchHeizung + "\t\t" + f.format((AnzahlHandtuchHeizung*preisHandtuchHeizung)) + " €" + System.lineSeparator();
		}
		if (FussbodenHeizung)
		{
			Ausgabe += "Fussbodenheizung\t-\t\t" + f.format((preisFussbodenHeizung)) + " €" + System.lineSeparator();
		}
		Ausgabe += "_____________________________________________" + System.lineSeparator();
		Ausgabe += "Gesamtpreis:\t\t\t\t" + f.format(gesamtbetrag) + " €";
		Alert alert = new Alert(AlertType.NONE);
		alert.setTitle("Speichern");
		alert.setHeaderText("Übersicht der zu Speichernden Daten");
		Label label = new Label(Ausgabe);
		label.setWrapText(true);
		alert.getDialogPane().setContent(label);
		alert.getButtonTypes().add(ButtonType.APPLY);
		alert.getButtonTypes().add(ButtonType.CANCEL);
		// Wenn Ok gedrückt wird gespeichert
		if (alert.showAndWait().get() == ButtonType.APPLY){
		    // In die Datenbank speichern und Form schliessen
			
			// Push in die Datenbank (noch nicht realisiert)
			
			
			
			
			// Form schliessen
			Stage stage = (Stage) btnAbbrechen.getScene().getWindow();
		    stage.close();
		} 
	}
	@FXML
	private void Update()
	{
		// Controls zurücksetzen
		btnSpeichern.setDisable(false);
		btnCSVExport.setDisable(false);
		lblERRZusatzHeizung.setVisible(false);
		lblERRZusatzHandtuchHeizung.setVisible(false);
		lblERRGlatteHeizung.setVisible(false);
		lblERRZusatzHeizung.setText("Nur 1 - 5 Heizungen erlaubt");
		lblERRZusatzHandtuchHeizung.setText("Nur 1 - 2 Heizungen erlaubt");
		
		int anzahlZusatzHeizungen = 0;
		int anzahlGlatteHeizungen = 0;
		int anzahlHandtuchHeizungen = 0;
		double tmpPreisFussbodenHeizung = 0;
		
		// Überprüfung ob die Anzahl der Eingegebenen Anzahl Zusatzheizung stimmt 1 - 5 und die Anzahl der Glatten Heizung aktualisieren
		if (txtZusatzHeizung.getText().matches("[1-5]"))
		{
			anzahlZusatzHeizungen = Integer.parseInt(txtZusatzHeizung.getText());
			txtGlatteHeizung.setPromptText("1 - " + (AnzahlHeizungen + anzahlZusatzHeizungen));
			AnzahlZusatzHeizung = anzahlZusatzHeizungen;
			lblERRGlatteHeizung.setText("Nur 1 - " + (AnzahlHeizungen + anzahlZusatzHeizungen) + " Heizungen erlaubt");
		}
		// Wenn etwas eingegeben wurde aber die Anzahl nicht stimmt Controls sperren und Fehler ausgeben
		else if (txtZusatzHeizung.getText().length() != 0)
		{
			btnSpeichern.setDisable(true);
			btnCSVExport.setDisable(true);
			lblERRZusatzHeizung.setVisible(true);
		}
		// Falls nichts eingegeben wurde die Maximalen Glatten Heizung zurücksetzen 
		else
		{
			txtGlatteHeizung.setPromptText("1 - " + (AnzahlHeizungen + anzahlZusatzHeizungen));
			lblERRGlatteHeizung.setText("Nur 1 - " + (AnzahlHeizungen + anzahlZusatzHeizungen) + " Heizungen erlaubt");
		}
		String regMatch = "";
		// Wenn die Anzahl der Zusatzheizungen geändert wird ändere die Anzahl der zu überprüfenden maximalen Anzhal der Glatten Heizungen
		if ((AnzahlHeizungen + anzahlZusatzHeizungen) < 10)
		{
			regMatch = "[1-"+ (AnzahlHeizungen + anzahlZusatzHeizungen) + "]";
		}
		else if ((AnzahlHeizungen + anzahlZusatzHeizungen) > 9)
		{
			regMatch = "[1-9][0-"+ ((AnzahlHeizungen + anzahlZusatzHeizungen)% 10) + "]?";
		}	
		// Überprüfe ob die Eingegebene Anzahl der eingegebenen Glatten Heizung stimmt
		if (txtGlatteHeizung.getText().matches(regMatch))
		{
			anzahlGlatteHeizungen = Integer.parseInt(txtGlatteHeizung.getText());
			AnzahlGlatteHeizung = anzahlGlatteHeizungen;
		}
		// Wenn etwas eingegeben wurde aber die Anzahl nicht stimmt Controls sperren und Fehler ausgeben
		else if (txtGlatteHeizung.getText().length() != 0)
		{
			btnSpeichern.setDisable(true);
			btnCSVExport.setDisable(true);
			lblERRGlatteHeizung.setVisible(true);
		}
		//Überprüfe ob die Eingegebene Anzahl der eingegebenen Handtuch Heizung stimmt
		if (txtZusatzHandtuchHeizung.getText().matches("[1-2]"))
		{
			anzahlHandtuchHeizungen = Integer.parseInt(txtZusatzHandtuchHeizung.getText());
			AnzahlHandtuchHeizung = anzahlHandtuchHeizungen;
		}
		// Wenn etwas eingegeben wurde aber die Anzahl nicht stimmt Controls sperren und Fehler ausgeben
		else if (txtZusatzHandtuchHeizung.getText().length() != 0)
		{
			btnSpeichern.setDisable(true);
			btnCSVExport.setDisable(true);
			lblERRZusatzHandtuchHeizung.setVisible(true);
		}
		
		// Wenn gar nichts in den Textboxen eingegeben wurden
		if (txtZusatzHeizung.getText().length() == 0 && txtGlatteHeizung.getText().length() == 0 && txtZusatzHandtuchHeizung.getText().length() == 0 && !checkBoxFussbodenheizung.isSelected())
		{
			btnSpeichern.setDisable(true);
			btnCSVExport.setDisable(true);
		}
		// Wenn die Fussbodenheizung ausgewählt wurde Preis setzen
		if (checkBoxFussbodenheizung.isSelected())
		{
			tmpPreisFussbodenHeizung = preisFussbodenHeizung;
			FussbodenHeizung = true;
		}
		else
		{
			FussbodenHeizung = false;
		}
		// Zusammenrechnen
		gesamtbetrag = anzahlZusatzHeizungen*preisZusatzHeizung + anzahlGlatteHeizungen*preisGlatteHeizung + anzahlHandtuchHeizungen*preisHandtuchHeizung + tmpPreisFussbodenHeizung;
		
		// Gesamtbetrag aktualisieren
		// Gesamtbetrag setzen
		lblGesamtbetrag.setText(lblGesamtbetrag.getText().replaceAll("%", ""+f.format(gesamtbetrag)));
		lblGesamtbetrag.setText(lblGesamtbetrag.getText().replaceAll("[0-9]+,[0-9][0-9]", ""+f.format(gesamtbetrag)));
	}
	@FXML
	private void close()
	{
		// Button Abbrechen
		Stage stage = (Stage) btnAbbrechen.getScene().getWindow();
	    stage.close();
	}
	
	@FXML
	private void chkGrossesZimmer()
	{
		if (checkBoxGrossesZimmer.isSelected())
		{
			grossesZimmerimOG = true; 
		}
		else
		{
			grossesZimmerimOG = false;
		}
		initialize();
	}
	@FXML
	private void chkDachgeschoss()
	{
		if (checkBoxDachgeschoss.isSelected())
		{
			dachgeschoss = true;
		}
		else
		{
			dachgeschoss = false;
		}
		initialize();
	}
	@FXML
	private void chkBadDach()
	{
		if (checkBoxBadDachgeschoss.isSelected())
		{
			badimDach = true;
		}
		else
		{
			badimDach = false;
		}
		initialize();
	}
	public void setKunde(Kunde ikunde)
	{
		this.kunde = ikunde;
	}
}
