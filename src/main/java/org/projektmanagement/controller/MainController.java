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
import org.omg.CORBA.INITIALIZE;
import org.projektmanagement.model.Kunde;
import org.projektmanagement.service.KundenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.PropertyValue;
import javafx.application.Application;
import javafx.scene.control.cell.PropertyValueFactory;

public class MainController {
	private static final Logger log = LoggerFactory.getLogger(MainController.class);

	@FXML
	private TextField firstnameField;
	@FXML
	private TextField lastnameField;
	@FXML
	private TableView<Kunde> tableView;
	@FXML
	private TableColumn<Kunde, Integer> kundeIdCol;
	@FXML
	private TableColumn<Kunde, String> kundeFirstnameCol;
	@FXML
	private TableColumn<Kunde, String> kundeLastnameCol;

	ListProperty<Kunde> listProperty = new SimpleListProperty();
	Set<String> names = new HashSet<String>();
	private KundenService kundenService = new KundenService();

	@FXML
	private void initialize() {
		kundeIdCol.setCellValueFactory(new PropertyValueFactory<Kunde, Integer>("id"));
		kundeFirstnameCol.setCellValueFactory(new PropertyValueFactory<Kunde, String>("firstname"));
		kundeLastnameCol.setCellValueFactory(new PropertyValueFactory<Kunde, String>("lastname"));
	}
	
	@FXML
	public void kundeEinfuegen() {
		String firstname = firstnameField.getText();
		String lastname = lastnameField.getText();

		kundenService.getKundenHandler().createKunde(firstname, lastname);
		log.info("User \"{} {}\" created");

		firstnameField.clear();
		lastnameField.clear();
		reload();
	}

	@FXML
	public void reload() {
		List<Kunde> kunden = kundenService.getKundenHandler().getAllKunden();

		if (kunden != null) {
			tableView.setItems(FXCollections.observableArrayList(kunden));
		}

		log.info("Customer table updated");
	}
}
