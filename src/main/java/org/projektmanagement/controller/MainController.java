package org.projektmanagement.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.awt.Desktop.Action;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Observable;
import java.util.Set;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;

import org.apache.commons.lang.StringUtils;
import org.projektmanagement.model.Kunde;
import org.projektmanagement.service.KundenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainController {
	private static final Logger log = LoggerFactory.getLogger(MainController.class);

	@FXML
	private TextField firstNameField;
	@FXML
	private TextField lastNameField;
	@FXML
	ListView listView = new ListView();

	ListProperty<String> listProperty = new SimpleListProperty();
	Set<String> names = new HashSet<String>();
	private KundenService kundenService = new KundenService();

	@FXML
	public void kundeEinfuegen() {

		String firstName = firstNameField.getText();
		String lastName = lastNameField.getText();

		log.info(firstName + " " + lastName + " Test###");

		kundenService.getKundenHandler().createKunde(firstName, lastName);

		firstNameField.clear();
		lastNameField.clear();

	}

	public void reload() {
		log.info("Reload Button geklickt");

		List<Kunde> kunden = kundenService.getKundenHandler().getAllKunden();

		if (kunden != null) {

			for (Kunde k : kunden) {
				names.add(k.getName() + " " + k.getLastname());
			}

			listView.itemsProperty().bind(listProperty);
			listProperty.set(FXCollections.observableArrayList(names));

		}

	}

}
