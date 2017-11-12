package org.projektmanagement.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.Desktop.Action;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Observable;
import java.util.Optional;
import java.util.Set;

import javax.swing.plaf.basic.BasicTreeUI.SelectionModelPropertyChangeHandler;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;

import org.apache.commons.lang.StringUtils;
import org.omg.CORBA.INITIALIZE;
import org.projektmanagement.model.Kunde;
import org.projektmanagement.service.KundenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.PropertyValue;
import org.springframework.context.ApplicationContext;

import javafx.application.Application;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.application.Platform;

public class CustomerOverviewController {
	private static final Logger log = LoggerFactory.getLogger(CustomerOverviewController.class);
	private ListProperty<Kunde> listProperty = new SimpleListProperty();
	private Set<String> names = new HashSet<String>();
	private KundenService kundenService = new KundenService();

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
	@FXML
	private TableColumn<Kunde, String> kundeEmailCol;
	@FXML
	private TableColumn<Kunde, String> kundePhoneCol;
	@FXML
	private TableColumn<Kunde, String> kundeStreetCol;
	@FXML
	private TableColumn<Kunde, String> kundeStreetnrCol;
	@FXML
	private TableColumn<Kunde, String> kundePlzCol;
	@FXML
	private TableColumn<Kunde, String> kundePlaceCol;
	@FXML
	private TableColumn<Kunde, String> kundeCountryCol;
	@FXML
	private MenuItem closeMenuItem;

	@FXML
	private void initialize() {
		kundeIdCol.setCellValueFactory(new PropertyValueFactory<Kunde, Integer>("id"));
		kundeFirstnameCol.setCellValueFactory(new PropertyValueFactory<Kunde, String>("firstname"));
		kundeLastnameCol.setCellValueFactory(new PropertyValueFactory<Kunde, String>("lastname"));
		kundeEmailCol.setCellValueFactory(new PropertyValueFactory<Kunde, String>("email"));
		kundePhoneCol.setCellValueFactory(new PropertyValueFactory<Kunde, String>("phone"));
		kundeStreetCol.setCellValueFactory(new PropertyValueFactory<Kunde, String>("street"));
		kundeStreetnrCol.setCellValueFactory(new PropertyValueFactory<Kunde, String>("streetnr"));
		kundePlzCol.setCellValueFactory(new PropertyValueFactory<Kunde, String>("plz"));
		kundePlaceCol.setCellValueFactory(new PropertyValueFactory<Kunde, String>("place"));
		kundeCountryCol.setCellValueFactory(new PropertyValueFactory<Kunde, String>("country"));
		tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		kundeFirstnameCol.setCellFactory(TextFieldTableCell.<Kunde>forTableColumn());
		kundeFirstnameCol.setOnEditCommit(new EventHandler<CellEditEvent<Kunde, String>>() {
			public void handle(CellEditEvent<Kunde, String> t) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("Fehler bei: Vorname");
				Label label = new Label("Der Vorname darf nicht leer sein." + t.getNewValue());
				label.setWrapText(true);
				alert.getDialogPane().setContent(label);

				if (t.getNewValue().length() == 0) {
					alert.showAndWait();
				} else {
					if (editCustomer()) {
						((Kunde) t.getTableView().getItems().get(t.getTablePosition().getRow()))
								.setFirstname(t.getNewValue());
						kundenService.getKundenHandler()
								.editCustomer((Kunde) t.getTableView().getItems().get(t.getTablePosition().getRow()));
					} else {
						((Kunde) t.getTableView().getItems().get(t.getTablePosition().getRow()))
								.setFirstname(t.getOldValue());
					}
				}
				refresh();
			}
		});

		kundeLastnameCol.setCellFactory(TextFieldTableCell.<Kunde>forTableColumn());
		kundeLastnameCol.setOnEditCommit(new EventHandler<CellEditEvent<Kunde, String>>() {
			public void handle(CellEditEvent<Kunde, String> t) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("Fehler bei: Nachname");
				Label label = new Label("Der Nachname darf nicht leer sein." + t.getNewValue());
				label.setWrapText(true);
				alert.getDialogPane().setContent(label);

				if (t.getNewValue().length() == 0) {
					alert.showAndWait();
				} else {
					if (editCustomer()) {
						((Kunde) t.getTableView().getItems().get(t.getTablePosition().getRow()))
								.setLastname(t.getNewValue());
						kundenService.getKundenHandler()
								.editCustomer((Kunde) t.getTableView().getItems().get(t.getTablePosition().getRow()));
					} else {
						((Kunde) t.getTableView().getItems().get(t.getTablePosition().getRow()))
								.setLastname(t.getOldValue());
					}
				}
				refresh();
			}
		});

		kundeEmailCol.setCellFactory(TextFieldTableCell.<Kunde>forTableColumn());
		kundeEmailCol.setOnEditCommit(new EventHandler<CellEditEvent<Kunde, String>>() {
			public void handle(CellEditEvent<Kunde, String> t) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("Fehler bei: E-Mail");
				Label label = new Label(
						"Die E-Mail muss ein @-Zeichen enthalten.\n\nIhre Eingabe war: " + t.getNewValue());
				label.setWrapText(true);
				alert.getDialogPane().setContent(label);

				if (t.getNewValue().length() != 0 && !t.getNewValue().contains("@")) {
					alert.showAndWait();
				} else {
					if (editCustomer()) {
						((Kunde) t.getTableView().getItems().get(t.getTablePosition().getRow()))
								.setEmail(t.getNewValue());
						kundenService.getKundenHandler()
								.editCustomer((Kunde) t.getTableView().getItems().get(t.getTablePosition().getRow()));
					} else {
						((Kunde) t.getTableView().getItems().get(t.getTablePosition().getRow()))
								.setEmail(t.getOldValue());
					}
				}
				refresh();
			}
		});

		kundePhoneCol.setCellFactory(TextFieldTableCell.<Kunde>forTableColumn());
		kundePhoneCol.setOnEditCommit(new EventHandler<CellEditEvent<Kunde, String>>() {
			public void handle(CellEditEvent<Kunde, String> t) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("Fehler bei: Telefonnummer");
				Label label = new Label(
						"Die Telefonnummer darf nicht leer sein, nur aus Zahlen bestehen\nund nur zwischen 1 und 24 Ziffern lang sein.\n\nIhre Eingabe war: "
								+ t.getNewValue());
				label.setWrapText(true);
				alert.getDialogPane().setContent(label);

				if (t.getNewValue().length() == 0 || t.getNewValue().length() < 1 && t.getNewValue().length() > 24
						|| !t.getNewValue().matches("[0-9]+")) {
					alert.showAndWait();
				} else {
					if (editCustomer()) {
						((Kunde) t.getTableView().getItems().get(t.getTablePosition().getRow()))
								.setPhone(t.getNewValue());
						kundenService.getKundenHandler()
								.editCustomer((Kunde) t.getTableView().getItems().get(t.getTablePosition().getRow()));
					} else {
						((Kunde) t.getTableView().getItems().get(t.getTablePosition().getRow()))
								.setPhone(t.getOldValue());
					}
				}
				refresh();
			}
		});

		kundeStreetCol.setCellFactory(TextFieldTableCell.<Kunde>forTableColumn());
		kundeStreetCol.setOnEditCommit(new EventHandler<CellEditEvent<Kunde, String>>() {
			public void handle(CellEditEvent<Kunde, String> t) {
				if (editCustomer()) {
					((Kunde) t.getTableView().getItems().get(t.getTablePosition().getRow())).setStreet(t.getNewValue());
					kundenService.getKundenHandler()
							.editCustomer((Kunde) t.getTableView().getItems().get(t.getTablePosition().getRow()));
					refresh();
				} else {
					((Kunde) t.getTableView().getItems().get(t.getTablePosition().getRow())).setStreet(t.getOldValue());
					refresh();
				}
			}
		});

		kundeStreetnrCol.setCellFactory(TextFieldTableCell.<Kunde>forTableColumn());
		kundeStreetnrCol.setOnEditCommit(new EventHandler<CellEditEvent<Kunde, String>>() {
			public void handle(CellEditEvent<Kunde, String> t) {
				if (editCustomer()) {
					((Kunde) t.getTableView().getItems().get(t.getTablePosition().getRow()))
							.setStreetnr(t.getNewValue());
					kundenService.getKundenHandler()
							.editCustomer((Kunde) t.getTableView().getItems().get(t.getTablePosition().getRow()));
				} else {
					((Kunde) t.getTableView().getItems().get(t.getTablePosition().getRow()))
							.setStreetnr(t.getOldValue());
				}
				refresh();
			}
		});

		kundePlzCol.setCellFactory(TextFieldTableCell.<Kunde>forTableColumn());
		kundePlzCol.setOnEditCommit(new EventHandler<CellEditEvent<Kunde, String>>() {
			public void handle(CellEditEvent<Kunde, String> t) {
				if (editCustomer()) {
					((Kunde) t.getTableView().getItems().get(t.getTablePosition().getRow())).setPlz(t.getNewValue());
					kundenService.getKundenHandler()
							.editCustomer((Kunde) t.getTableView().getItems().get(t.getTablePosition().getRow()));
				} else {
					((Kunde) t.getTableView().getItems().get(t.getTablePosition().getRow())).setPlz(t.getOldValue());
				}
				refresh();
			}
		});

		kundePlaceCol.setCellFactory(TextFieldTableCell.<Kunde>forTableColumn());
		kundePlaceCol.setOnEditCommit(new EventHandler<CellEditEvent<Kunde, String>>() {
			public void handle(CellEditEvent<Kunde, String> t) {
				if (editCustomer()) {
					((Kunde) t.getTableView().getItems().get(t.getTablePosition().getRow())).setPlace(t.getNewValue());
					kundenService.getKundenHandler()
							.editCustomer((Kunde) t.getTableView().getItems().get(t.getTablePosition().getRow()));
				} else {
					((Kunde) t.getTableView().getItems().get(t.getTablePosition().getRow())).setPlace(t.getOldValue());
				}
				refresh();
			}
		});

		kundeCountryCol.setCellFactory(TextFieldTableCell.<Kunde>forTableColumn());
		kundeCountryCol.setOnEditCommit(new EventHandler<CellEditEvent<Kunde, String>>() {
			public void handle(CellEditEvent<Kunde, String> t) {
				if (editCustomer()) {
					((Kunde) t.getTableView().getItems().get(t.getTablePosition().getRow()))
							.setCountry(t.getNewValue());
					kundenService.getKundenHandler()
							.editCustomer((Kunde) t.getTableView().getItems().get(t.getTablePosition().getRow()));
				} else {
					((Kunde) t.getTableView().getItems().get(t.getTablePosition().getRow()))
							.setCountry(t.getOldValue());
				}
				refresh();
			}
		});
	}

	@FXML
	public void addKunde() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		Parent rootNode = (Parent) loader.load(getClass().getResourceAsStream("/views/customerAdd.fxml"));
		Stage stage = new Stage();
		stage.setTitle("Benutzer hinzufügen");
		stage.setScene(new Scene(rootNode));
		stage.show();
	}

	@FXML
	public void removeCustomer() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Bestätigung");
		alert.setHeaderText("Kunden löschen");
		ObservableList<Kunde> selectedCustomers = tableView.getSelectionModel().getSelectedItems();
		for (Kunde kunde : selectedCustomers) {
			Label label = new Label("Wollen Sie den Kunden \"" + kunde.getFirstname() + " " + kunde.getLastname()
					+ "\" wirklich löschen?");
			label.setWrapText(true);
			alert.getDialogPane().setContent(label);

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				kundenService.getKundenHandler().deleteCustomer(kunde);
				refresh();
			} else {
				alert.close();
			}
		}
	}

	@FXML
	public void closeWindow() {
		Platform.exit();
	}

	public boolean editCustomer() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Bestätigung");
		alert.setHeaderText("Kunden bearbeiten?");

		Label label = new Label("Wollen Sie den Kunden wirklich bearbeiten?");
		label.setWrapText(true);
		alert.getDialogPane().setContent(label);

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			return true;
		} else {
			alert.close();
			return false;
		}
	}

	@FXML
	public void refresh() {
		tableView.getItems().clear();
		List<Kunde> kunden = kundenService.getKundenHandler().getAllKunden();

		if (kunden != null) {
			tableView.setItems(FXCollections.observableArrayList(kunden));
		}

		log.info("Customer table updated");
	}
}
