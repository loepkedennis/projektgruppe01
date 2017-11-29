package org.projektmanagement.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

import java.awt.MenuItem;
import java.awt.Desktop.Action;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Observable;
import java.util.Set;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;

import org.apache.commons.lang.StringUtils;
import org.omg.CORBA.INITIALIZE;
import org.projektmanagement.model.Haus;
import org.projektmanagement.model.Kunde;
import org.projektmanagement.service.KundenService;
import org.projektmanagement.service.SonderwunschService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.PropertyValue;
import javafx.application.Application;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class CustomerAddController {
	private static final Logger log = LoggerFactory.getLogger(CustomerAddController.class);
	private KundenService kundenService = new KundenService();

	private SonderwunschService sonderwunschService = new SonderwunschService();
	
	@FXML
	private TextField firstnameField;
	@FXML
	private TextField lastnameField;
	@FXML
	private TextField emailField;
	@FXML
	private TextField phoneField;
	@FXML
	private TextField streetField;
	@FXML
	private TextField streetnrField;
	@FXML
	private TextField plzField;
	@FXML
	private TextField placeField;
	@FXML
	private ComboBox countryComboBox;
	@FXML
	private Button addCustomerButton;
	@FXML
	private Button cancelButton;
	
	

	@FXML
	private void initialize() {
		ObservableList<String> cities = FXCollections.observableArrayList();
		String[] locales1 = Locale.getISOCountries();
		for (String countryList : locales1) {
			Locale obj = new Locale("", countryList);
			String[] city = { obj.getDisplayCountry() };
			for (int x = 0; x < city.length; x++) {
				cities.add(obj.getDisplayCountry());
			}
		}
		countryComboBox.setItems(cities);
	}

	@FXML
	public void addCustomer() {
		String firstname = firstnameField.getText().trim();
		String lastname = lastnameField.getText().trim();
		String email = emailField.getText().trim();
		String phone = phoneField.getText().trim();
		String street = streetField.getText();
		String streetnr = streetnrField.getText();
		String plz = plzField.getText();
		String place = placeField.getText();
		String country = (String) countryComboBox.getValue();

		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");

		if (phone.length() == 0 || phone.length() < 1 && phone.length() > 24 || !phone.matches("[0-9]+")) {
			alert.setHeaderText("Fehler bei: Telefonnummer");
			Label label = new Label(
					"Die Telefonnummer darf nicht leer sein, nur aus Zahlen bestehen\nund nur zwischen 1 und 24 Ziffern lang sein.\n\nIhre Eingabe war: "
							+ phone);
			label.setWrapText(true);
			alert.getDialogPane().setContent(label);
			alert.showAndWait();
		} else if (email.length() != 0 && !email.contains("@")) {
			alert.setHeaderText("Fehler bei: E-Mail");
			Label label = new Label("Die E-Mail muss ein @-Zeichen enthalten.\n\nIhre Eingabe war: " + email);
			label.setWrapText(true);
			alert.getDialogPane().setContent(label);
			alert.showAndWait();
		} else if (firstname.length() == 0) {
			alert.setHeaderText("Fehler bei: Vorname");
			Label label = new Label("Der Vorname darf nicht leer sein.");
			label.setWrapText(true);
			alert.getDialogPane().setContent(label);
			alert.showAndWait();
		} else if (lastname.length() == 0) {
			alert.setHeaderText("Fehler bei: Nachname");
			Label label = new Label("Der Nachname darf nicht leer sein.");
			label.setWrapText(true);
			alert.getDialogPane().setContent(label);
			alert.showAndWait();
		} else {
			kundenService.getKundenHandler().createKunde(firstname, lastname, email, phone, street, streetnr, plz,
					place, country);
			log.info("User \"{} {}\" created", firstname, lastname);
			
			
			
			/* Dieser Bereich dient als Beispiel und sollte später gelöscht werden */
			log.info("##### Testdaten eingefügt!");
			
			
			Kunde k = kundenService.getKundenHandler().getKunde(1);
			kundenService.getKundenHandler().addHouse(k);
			Haus haus = kundenService.getKundenHandler().getKunde(1).getHouses().get(0);
			sonderwunschService.getSonderwunschHandler().createSonderwunsch("Sonderwunsch Name", 10.00, "Kategorie Name", haus);
			
			// Ende Beispiel
			
			Stage stage = (Stage) addCustomerButton.getScene().getWindow();
			stage.close();
		}
	}

	@FXML
	public void closeWindow() {
		Stage stage = (Stage) cancelButton.getScene().getWindow();
		stage.close();
		log.info("Window \"Kunden hinzufügen\" closed");
	}
}
