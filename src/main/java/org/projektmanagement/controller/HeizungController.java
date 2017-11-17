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
	@FXML
	private CheckBox checkBoxGrossesZimmer;
	@FXML
	private CheckBox checkBoxDachgeschoss;
	@FXML
	private CheckBox checkBoxBadDachgeschoss;
	@FXML
	private CheckBox checkBoxFussbodenheizung;
	@FXML
	private Label lblGesamtbetrag;
	@FXML
	private TextField txtZusatzHeizung;
	@FXML
	private TextField txtZusatzHandtuchHeizung;
	@FXML
	private TextField txtGlatteHeizung;
	@FXML
	private Button btnSpeichern;
	@FXML
	private Button btnAbbrechen;
	@FXML
	private Button btnCSVExport;
	@FXML
	private Label lblERRZusatzHeizung;
	@FXML
	private Label lblERRZusatzHandtuchHeizung;
	@FXML
	private Label lblERRGlatteHeizung;
	
	
	int AnzahlHeizungen = 8;
	int fussbodenheizung = 8990;
	int gesamtbetrag = 0;
	
	@FXML
	private void initialize()
	{
		String tmp = "";
		tmp = checkBoxFussbodenheizung.getText();
		tmp = tmp.replaceAll("%", ""+fussbodenheizung);
		checkBoxFussbodenheizung.setText(tmp);
		tmp = lblGesamtbetrag.getText();
		tmp = tmp.replaceAll("%", ""+gesamtbetrag);
		lblGesamtbetrag.setText(tmp);
		txtGlatteHeizung.setPromptText("1 - " + AnzahlHeizungen);
		lblERRZusatzHeizung.setVisible(false);
		lblERRZusatzHandtuchHeizung.setVisible(false);
		lblERRGlatteHeizung.setVisible(false);
		btnSpeichern.setDisable(true);
		btnCSVExport.setDisable(true);
	}
	@FXML
	private void preisUpdate()
	{
		btnSpeichern.setDisable(false);
		btnCSVExport.setDisable(false);
		lblERRZusatzHeizung.setVisible(false);
		lblERRZusatzHandtuchHeizung.setVisible(false);
		lblERRGlatteHeizung.setVisible(false);
		
		
		int anzahlZusatzHeizungen = 0;
		int anzahlGlatteHeizungen = 0;
		int anzahlHandtuchHeizungen = 0;
		int preisFussbodenHeizung = 0;
		String regMatch = "";
		if (txtZusatzHeizung.getText().matches("[1-5]"))
		{
			anzahlZusatzHeizungen = Integer.parseInt(txtZusatzHeizung.getText());
			txtGlatteHeizung.setPromptText("1 - " + (AnzahlHeizungen + anzahlZusatzHeizungen));
		}
		else if (txtZusatzHeizung.getText().length() != 0)
		{
			btnSpeichern.setDisable(true);
			btnCSVExport.setDisable(true);
			lblERRZusatzHeizung.setVisible(true);
		}
		else
		{
			txtGlatteHeizung.setPromptText("1 - " + (AnzahlHeizungen));
		}
		int tmpAnzahlHeizungen = AnzahlHeizungen + anzahlZusatzHeizungen;
		if (tmpAnzahlHeizungen < 10)
		{
			regMatch = "[1-"+ tmpAnzahlHeizungen + "]";
		}
		else
		{
			regMatch = "[1-9][0-"+ tmpAnzahlHeizungen % 10 + "]?";
		}
		if (txtGlatteHeizung.getText().matches(regMatch))
		{
			anzahlGlatteHeizungen = Integer.parseInt(txtGlatteHeizung.getText());;
		}
		else if (txtGlatteHeizung.getText().length() != 0)
		{
			btnSpeichern.setDisable(true);
			btnCSVExport.setDisable(true);
			lblERRGlatteHeizung.setVisible(true);
		}
		if (txtZusatzHandtuchHeizung.getText().matches("[1-2]"))
		{
			anzahlHandtuchHeizungen = Integer.parseInt(txtZusatzHandtuchHeizung.getText());;
		}
		else if (txtZusatzHandtuchHeizung.getText().length() != 0)
		{
			btnSpeichern.setDisable(true);
			btnCSVExport.setDisable(true);
			lblERRZusatzHandtuchHeizung.setVisible(true);
		}
		if (txtZusatzHeizung.getText().length() == 0 && txtGlatteHeizung.getText().length() == 0 && txtZusatzHandtuchHeizung.getText().length() == 0 && !checkBoxFussbodenheizung.isSelected())
		{
			btnSpeichern.setDisable(true);
			btnCSVExport.setDisable(true);
		}
		if (checkBoxFussbodenheizung.isSelected())
		{
			preisFussbodenHeizung = fussbodenheizung;
		}
		gesamtbetrag = anzahlZusatzHeizungen*660 + anzahlGlatteHeizungen*160 + anzahlHandtuchHeizungen*660 + preisFussbodenHeizung;
		lblGesamtbetrag.setText(lblGesamtbetrag.getText().replaceAll("[0-9]{1,6}", ""+gesamtbetrag));
	}
	@FXML
	private void close()
	{
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
