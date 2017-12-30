package org.projektmanagement.controller;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.projektmanagement.model.Kunde;
import org.projektmanagement.service.KundenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.application.Platform;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Modality;
import javafx.stage.Stage;

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
	private MenuItem refreshMenuItem;

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
		stage.setTitle("Kunden hinzufügen");
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
				log.info("Customer \"{} {}\" deleted", kunde.getFirstname(), kunde.getLastname());
				refresh();
			} else {
				alert.close();
			}
		}
	}

	@FXML
	public void closeWindow() {
		log.info("Application closed");
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
			log.info("Changes have been adopted");
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
	
	@FXML
	public void innerDoors() throws IOException {
		
		
		log.info("Starting Maske für \"Sonderwuensche fuer Innentueren\"");		
		if(this.tableView == null | this.tableView.getSelectionModel().getSelectedItem() == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Information");
			alert.setHeaderText("Wählen Sie bitte einen Kunden aus!");
			alert.showAndWait();			
		}
		else
		{					
			FXMLLoader loader = new FXMLLoader();
			Parent root = (Parent) loader.load(getClass().getResourceAsStream("/views/InnentuerenView.fxml"));			
			InnentuerenControl fct = loader.<InnentuerenControl>getController();
			fct.setKunde(this.tableView.getSelectionModel().getSelectedItem());
			
			Scene scene = new Scene(root, 560, 410);
			scene.getStylesheets().add(getClass().getResource("/styles/styles.css").toExternalForm());			
			Stage stage = new Stage();	
			stage.setTitle("Innentüren");
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setScene(scene);
			
			fct.init(3, stage);
			stage.showAndWait();			
		}
		
		
	}
	
	@FXML
	public void windowouterdoor() throws IOException {
		log.info("Starting Maske für \"Sonderwuensche fuer Fenster und Aussentueren\"");		
		if(this.tableView == null | this.tableView.getSelectionModel().getSelectedItem() == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Information");
			alert.setHeaderText("Wählen Sie bitte einen Kunden aus!");
			alert.showAndWait();			
		}
		else
		{					
			FXMLLoader loader = new FXMLLoader();
			Parent root = (Parent) loader.load(getClass().getResourceAsStream("/views/FensterAussentuerView.fxml"));			
			FensterUndAussentuerenController fct = loader.<FensterUndAussentuerenController>getController();
			fct.setKunde(this.tableView.getSelectionModel().getSelectedItem());
			
			Scene scene = new Scene(root, 560, 410);
			scene.getStylesheets().add(getClass().getResource("/styles/styles.css").toExternalForm());			
			Stage stage = new Stage();		
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setScene(scene);
			
			fct.init(2, stage);
			stage.showAndWait();			
		}
			
	}
	@FXML
	public void heizungen() throws IOException {
		log.info("Starte Maske für \"Sonderwuensche fuer Heizungen\"");
		Kunde kunde = this.tableView.getSelectionModel().getSelectedItem();
		if(kunde != null) 
		{
			
			FXMLLoader loader = new FXMLLoader();
			Parent root = (Parent) loader.load(getClass().getResourceAsStream("/views/HeizungView.fxml"));
			HeizungController hc = loader.<HeizungController>getController();
			hc.setKunde(kunde);
			hc.init();
			
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/styles/styles.css").toExternalForm());
			Stage stage = new Stage();
			stage.setTitle("Heizungen");
			stage.setScene(scene);
			stage.show();
		}
		else 
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Kunde nicht gefunden");
			Label label = new Label("Es wurde kein Kunde ausgewählt. Bitte wählen Sie einen Kunden aus.");
			label.setWrapText(true);
			alert.getDialogPane().setContent(label);
			alert.showAndWait();
		}
		
	}
	@FXML
	public void grundriss() throws IOException {
		Kunde kunde = tableView.getSelectionModel().getSelectedItem();

		if(kunde != null) {
			FXMLLoader loader = new FXMLLoader();
			Parent rootNode = (Parent) loader.load(getClass().getResource("/views/Grundriss.fxml").openStream());
					
			GrundrissController grundrissController = loader.<GrundrissController>getController();
			grundrissController.setKunde(kunde);
			
			Stage stage = new Stage();
			stage.setTitle("Grundriss");
			stage.setScene(new Scene(rootNode));
			stage.show();
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Kunde nicht gefunden");
			Label label = new Label("Es wurde kein Kunde ausgewählt. Bitte wählen Sie einen Kunden aus.");
			label.setWrapText(true);
			alert.getDialogPane().setContent(label);
			alert.showAndWait();
		}
	}
	@FXML
	public void parkett() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		Parent rootNode = (Parent) loader.load(getClass().getResourceAsStream("/views/ParkettView.fxml"));
		Stage stage = new Stage();
		stage.setTitle("Parkett");
		stage.setScene(new Scene(rootNode));
		stage.show();
	}
	@FXML
	public void fliesen() throws IOException {
		log.info("Starte Maske für \"Sonderwuensche fuer Fliesen\"");
		Kunde kunde = this.tableView.getSelectionModel().getSelectedItem();
		if(kunde != null) 
		{
			FXMLLoader loader = new FXMLLoader();
			Parent rootNode = (Parent) loader.load(getClass().getResourceAsStream("/views/FliesenView.fxml"));
			FliesenController fc = loader.<FliesenController>getController();
			fc.Kunde = kunde;
			fc.init();
			
			Scene scene = new Scene(rootNode);
			scene.getStylesheets().add(getClass().getResource("/styles/styles.css").toExternalForm());
			Stage stage = new Stage();
			stage.setTitle("Fliesen");
			stage.setScene(scene);
			stage.show();
		}
		else 
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Kunde nicht gefunden");
			Label label = new Label("Es wurde kein Kunde ausgewählt. Bitte wählen Sie einen Kunden aus.");
			label.setWrapText(true);
			alert.getDialogPane().setContent(label);
			alert.showAndWait();
		}
	}
	
	@FXML
	public void sani() throws IOException {
		log.info("Starting Maske für \"Sonderwuensche fuer Sanitärinstallationen\"");		
		if(this.tableView == null | this.tableView.getSelectionModel().getSelectedItem() == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Information");
			alert.setHeaderText("Wählen Sie bitte einen Kunden aus!");
			alert.showAndWait();			
		}
		else
		{					
			FXMLLoader loader = new FXMLLoader();
			Parent root = (Parent) loader.load(getClass().getResourceAsStream("/views/SanitaerinstallationView.fxml"));			
			Sanitaerinstallation_swController fct = loader.<Sanitaerinstallation_swController>getController();
			fct.setKunde(this.tableView.getSelectionModel().getSelectedItem());
			
			Scene scene = new Scene(root, 600, 410);
			scene.getStylesheets().add(getClass().getResource("/styles/styles.css").toExternalForm());			
			Stage stage = new Stage();	
			stage.setTitle("Sanitärinstallationen");
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setScene(scene);
			
			fct.init(5, stage);
			stage.showAndWait();			
		}
		
		
	}
}
