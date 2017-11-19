package org.projektmanagement.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
	private Label lblERRZusatzHandtuchHeizung;
	@FXML
	private Label lblERRGlatteHeizung;
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
	
	int AnzahlHeizungen = 8;
	int fussbodenheizung = 8990;
	int gesamtbetrag = 0;
	
	@FXML
	private void initialize()
	{
		String tmp = "";
		// Fussbodenheizung Preis setzen
		checkBoxFussbodenheizung.setText(checkBoxFussbodenheizung.getText().replaceAll("%", ""+fussbodenheizung));
		// Gesamtbetrag setzen
		lblGesamtbetrag.setText(lblGesamtbetrag.getText().replaceAll("%", ""+gesamtbetrag));
		// Anzeigen der Maximalen Glatten Heizung abhängig von der Anzahl vorhandener Heizungen
		txtGlatteHeizung.setPromptText("1 - " + AnzahlHeizungen);
		
		// Errorlabels unsichtbar machen 
		lblERRZusatzHeizung.setVisible(false);
		lblERRZusatzHandtuchHeizung.setVisible(false);
		lblERRGlatteHeizung.setVisible(false);
		
		// Buttons Disablen
		btnSpeichern.setDisable(true);
		btnCSVExport.setDisable(true);
	}
	@FXML
	private void preisUpdate()
	{
		// Controls zurücksetzen
		btnSpeichern.setDisable(false);
		btnCSVExport.setDisable(false);
		lblERRZusatzHeizung.setVisible(false);
		lblERRZusatzHandtuchHeizung.setVisible(false);
		lblERRGlatteHeizung.setVisible(false);
		
		
		int anzahlZusatzHeizungen = 0;
		int anzahlGlatteHeizungen = 0;
		int anzahlHandtuchHeizungen = 0;
		int preisFussbodenHeizung = 0;
		
		// Überprüfung ob die Anzahl der Eingegebenen Anzahl Zusatzheizung stimmt 1 - 5 und die Anzahl der Glatten Heizung aktualisieren
		if (txtZusatzHeizung.getText().matches("[1-5]"))
		{
			anzahlZusatzHeizungen = Integer.parseInt(txtZusatzHeizung.getText());
			txtGlatteHeizung.setPromptText("1 - " + (AnzahlHeizungen + anzahlZusatzHeizungen));
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
		}
		
		String regMatch = "";
		// Wenn die Anzahl der Zusatzheizungen geändert wird ändere die Anzahl der zu überprüfenden maximalen Anzhal der Glatten Heizungen
		if (AnzahlHeizungen + anzahlZusatzHeizungen < 10)
		{
			regMatch = "[1-"+ AnzahlHeizungen + anzahlZusatzHeizungen + "]";
		}
		else
		{
			regMatch = "[1-9][0-"+ AnzahlHeizungen + anzahlZusatzHeizungen % 10 + "]?";
		}
		
		// Überprüfe ob die Eingegebene Anzahl der eingegebenen Glatten Heizung stimmt
		if (txtGlatteHeizung.getText().matches(regMatch))
		{
			anzahlGlatteHeizungen = Integer.parseInt(txtGlatteHeizung.getText());;
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
			anzahlHandtuchHeizungen = Integer.parseInt(txtZusatzHandtuchHeizung.getText());;
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
			preisFussbodenHeizung = fussbodenheizung;
		}
		// Zusammenrechnen
		gesamtbetrag = anzahlZusatzHeizungen*660 + anzahlGlatteHeizungen*160 + anzahlHandtuchHeizungen*660 + preisFussbodenHeizung;
		
		// Gesamtbetrag aktualisieren
		lblGesamtbetrag.setText(lblGesamtbetrag.getText().replaceAll("[0-9]{1,6}", ""+gesamtbetrag));
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
			AnzahlHeizungen -= 1; 
		}
		else
		{
			AnzahlHeizungen += 1;
		}
		preisUpdate();
	}
	@FXML
	private void chkDachgeschoss()
	{
		if (checkBoxDachgeschoss.isSelected())
		{
			fussbodenheizung = 9990;
			String tmp = "";
			tmp = checkBoxFussbodenheizung.getText();
			tmp = tmp.replaceAll("[0-9]{1,6}", ""+fussbodenheizung);
			checkBoxFussbodenheizung.setText(tmp);
			AnzahlHeizungen +=1;
		}
		else
		{
			fussbodenheizung = 8990;
			String tmp = "";
			tmp = checkBoxFussbodenheizung.getText();
			tmp = tmp.replaceAll("[0-9]{1,6}", ""+fussbodenheizung);
			checkBoxFussbodenheizung.setText(tmp);
			AnzahlHeizungen -=1;
		}
		preisUpdate();
	}
	@FXML
	private void chkBadDach()
	{
		if (checkBoxBadDachgeschoss.isSelected())
		{
			txtZusatzHandtuchHeizung.setDisable(false);
			AnzahlHeizungen +=1;
		}
		else
		{
			AnzahlHeizungen -=1;
			txtZusatzHandtuchHeizung.setDisable(true);
			txtZusatzHandtuchHeizung.setText("");
		}
		preisUpdate();
	}

}
