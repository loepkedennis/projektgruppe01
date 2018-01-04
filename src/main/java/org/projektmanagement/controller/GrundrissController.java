package org.projektmanagement.controller;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.projektmanagement.model.Haus;
import org.projektmanagement.model.HausSonderwunsch;
import org.projektmanagement.model.Kunde;
import org.projektmanagement.model.Sonderwunsch;
import org.projektmanagement.service.KundenService;
import org.projektmanagement.service.SonderwunschService;
import org.projektmanagement.utils.CSVExporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class GrundrissController implements Initializable {
	private Stage stage;
	private static final Logger log = LoggerFactory.getLogger(GrundrissController.class);

	private KundenService kundenService = new KundenService();
	private SonderwunschService sonderwunschService = new SonderwunschService();

	private Kunde kunde;

	private double dOpt0, dOpt1, dOpt2, dOpt3, dOpt4, dOpt5, dErg;
	@FXML
	private Label lblOpt0;
	@FXML
	private Label lblOpt1;
	@FXML
	private Label lblOpt2;
	@FXML
	private Label lblOpt3;
	@FXML
	private Label lblOpt4;
	@FXML
	private Label lblOpt5;
	@FXML
	private Label lblGesErg;
	@FXML
	private RadioButton rbOpt0;
	@FXML
	private RadioButton rbOpt1;
	@FXML
	private RadioButton rbOpt2;
	@FXML
	private RadioButton rbOpt3;
	@FXML
	private RadioButton rbOpt4;
	@FXML
	private RadioButton rbOpt5;
	@FXML
	private CheckBox cbDachgeschoss;

	public void oeffneGrundrissView() {
		stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		try {
			AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("Grundriss.fxml"));
			Scene scene = new Scene(root, 560, 400);
			stage.setScene(scene);
		} catch (IOException e) {
			e.printStackTrace();
		}
		stage.showAndWait();
	}

	public void initialize(URL arg0, ResourceBundle arg1) {
		// Werte aus der DB beziehen fehlt noch
		rbOpt0.setText("Wand zur Abtrennung der Küche von dem Essbereich:");
		rbOpt1.setText("Tür in der Wand zwischen Küche und Essbereich:");
		rbOpt2.setText("Großes Zimmer im OG statt zwei kleinen Zimmern:");
		cbDachgeschoss.setText("Dachgeschoss vorhanden:");
		rbOpt3.setText("Abgetrennter Treppenraum im DG:");
		rbOpt4.setText("Vorrichtung eines Bades im DG:");
		rbOpt5.setText("Ausführung eines Bades im DG:");

		dOpt0 = 300;
		dOpt1 = 300;
		dOpt2 = 0;
		dOpt3 = 890;
		dOpt4 = 990;
		dOpt5 = 8990;

		lblOpt0.setText(dOpt0 + " €");
		lblOpt1.setText(dOpt1 + " €");
		lblOpt2.setText(dOpt2 + " €");
		lblOpt3.setText(dOpt3 + " €");
		lblOpt4.setText(dOpt4 + " €");
		lblOpt5.setText(((int) dOpt5 / 1000) + "," + dOpt5 % 1000 + " €");

		CheckedPrüfung();// Prüft ob aktuelle Auswahl akzeptiert wird
		initListener();// Initialisiert die Listener
		berechnePreis();// Berechnet den Preis
	}

	private void CheckedPrüfung() {
		if (rbOpt0.isSelected() == false) {
			rbOpt1.setDisable(true);
			lblOpt1.setDisable(true);
			rbOpt1.setSelected(false);

		} else {
			rbOpt1.setDisable(false);
			lblOpt1.setDisable(false);
		}

		if (cbDachgeschoss.isSelected() == false) {
			rbOpt3.setDisable(true);
			lblOpt3.setDisable(true);
			rbOpt3.setSelected(false);

			rbOpt4.setDisable(true);
			lblOpt4.setDisable(true);
			rbOpt4.setSelected(false);

			rbOpt5.setDisable(true);
			lblOpt5.setDisable(true);
			rbOpt5.setSelected(false);
		} else {
			rbOpt3.setDisable(false);
			lblOpt3.setDisable(false);
			rbOpt4.setDisable(false);
			lblOpt4.setDisable(false);
			rbOpt5.setDisable(false);
			lblOpt5.setDisable(false);
		}

		if (rbOpt4.isSelected() == false) {
			rbOpt5.setDisable(true);
			lblOpt5.setDisable(true);
			rbOpt5.setSelected(false);

		} else {
			rbOpt5.setDisable(false);
			lblOpt5.setDisable(false);
		}
	}

	private void initListener() {
		rbOpt0.selectedProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean WasSelected, Boolean isNowSelected) {
				if (isNowSelected == true) {
					rbOpt1.setDisable(false);
					lblOpt1.setDisable(false);
				} else {
					rbOpt1.setDisable(true);
					lblOpt1.setDisable(true);
					rbOpt1.setSelected(false);
				}
				berechnePreis();
			}
		});
		rbOpt1.selectedProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean WasSelected, Boolean isNowSelected) {
				berechnePreis();
			}
		});
		rbOpt2.selectedProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean WasSelected, Boolean isNowSelected) {
				berechnePreis();
			}
		});
		cbDachgeschoss.selectedProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean WasSelected, Boolean isNowSelected) {
				if (isNowSelected == true) {
					rbOpt3.setDisable(false);
					lblOpt3.setDisable(false);
					rbOpt4.setDisable(false);
					lblOpt4.setDisable(false);

				} else {
					rbOpt3.setDisable(true);
					lblOpt3.setDisable(true);
					rbOpt3.setSelected(false);

					rbOpt4.setDisable(true);
					lblOpt4.setDisable(true);
					rbOpt4.setSelected(false);

					rbOpt5.setDisable(true);
					lblOpt5.setDisable(true);
					rbOpt5.setSelected(false);
				}
				berechnePreis();
			}
		});
		rbOpt3.selectedProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean WasSelected, Boolean isNowSelected) {
				berechnePreis();
			}
		});
		rbOpt4.selectedProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean WasSelected, Boolean isNowSelected) {
				if (isNowSelected == true) {
					rbOpt5.setDisable(false);
					lblOpt5.setDisable(false);
				} else {
					rbOpt5.setDisable(true);
					lblOpt5.setDisable(true);
					rbOpt5.setSelected(false);
				}
				berechnePreis();
			}
		});
		rbOpt5.selectedProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean WasSelected, Boolean isNowSelected) {
				berechnePreis();
			}
		});
	}

	public void checkIfExist() {
		for (HausSonderwunsch s : sonderwunschService.getSonderwunschHandler().getSonderwunscheHouse(kunde)) {
			if (s.getSonderwunsch().getId() == 1) {
				rbOpt0.setDisable(false);
				rbOpt0.setSelected(true);
			}

			if (s.getSonderwunsch().getId() == 2) {
				rbOpt1.setDisable(false);
				rbOpt1.setSelected(true);
			}

			if (s.getSonderwunsch().getId() == 3) {
				rbOpt2.setDisable(false);
				rbOpt2.setSelected(true);
			}

			if (s.getSonderwunsch().getId() == 4) {
				rbOpt3.setDisable(false);
				rbOpt3.setSelected(true);
				cbDachgeschoss.setDisable(false);
				cbDachgeschoss.setSelected(true);
			}

			if (s.getSonderwunsch().getId() == 5) {
				rbOpt4.setDisable(false);
				rbOpt4.setSelected(true);
				cbDachgeschoss.setDisable(false);
				cbDachgeschoss.setSelected(true);
			}

			if (s.getSonderwunsch().getId() == 6) {
				rbOpt5.setDisable(false);
				rbOpt5.setSelected(true);
				cbDachgeschoss.setDisable(false);
				cbDachgeschoss.setSelected(true);
			}
		}
	}

	private void berechnePreis() {
		dErg = 0;
		if (rbOpt0.isSelected() == true) {
			dErg = dErg + dOpt0;
			if (rbOpt1.isSelected() == true) {
				dErg = dErg + dOpt1;
			}
		}
		if (cbDachgeschoss.isSelected() == true) {
			if (rbOpt3.isSelected() == true) {
				dErg = dErg + dOpt3;
			}
			if (rbOpt4.isSelected() == true) {
				dErg = dErg + dOpt4;
				if (rbOpt5.isSelected() == true) {
					dErg = dErg + dOpt5;
				}
			}
		}
		// Logik zur Preisberechnung
		if (dErg > 999) {

			lblGesErg.setText(((int) dErg / 1000) + "," + dErg % 1000 + " €");
		} else {
			lblGesErg.setText(dErg + " €");
		}
	}

	@FXML
	protected void OnClickCSVExport(ActionEvent event) {
		CheckedPrüfung();
		CSVExporter csvExport = new CSVExporter();
		try {
			FileWriter filePfad = new FileWriter(csvExport.setStrPfad(getStage()));
			CSVExporter.writeLine(filePfad, getCSVAuswahl());

			filePfad.flush();
			filePfad.close();
		} catch (Exception e) {

		}
	}

	public List<String> getCSVAuswahl() {
		List<String> listStrAuswahl = new ArrayList<String>();
		if (this.rbOpt0.isSelected())
			listStrAuswahl.add(rbOpt0.getText().replace(":", ""));
		if (this.rbOpt1.isSelected())
			listStrAuswahl.add(rbOpt1.getText().replace(":", ""));
		if (this.rbOpt2.isSelected())
			listStrAuswahl.add(rbOpt2.getText().replace(":", ""));
		if (this.rbOpt3.isSelected())
			listStrAuswahl.add(rbOpt3.getText().replace(":", ""));
		if (this.rbOpt4.isSelected())
			listStrAuswahl.add(rbOpt4.getText().replace(":", ""));
		if (this.rbOpt5.isSelected())
			listStrAuswahl.add(rbOpt5.getText().replace(":", ""));
		return listStrAuswahl;
	}

	public Stage getStage() {
		return stage;
	}

	@FXML
	protected void OnClickSave(ActionEvent event) {
		Haus haus = kunde.getHouses().get(0);

		for (HausSonderwunsch s : sonderwunschService.getSonderwunschHandler().getSonderwunscheHouse(kunde)) {
			if (!this.rbOpt0.isSelected() && s.getSonderwunsch().getId() == 1) {
				sonderwunschService.getSonderwunschHandler().removeSonderwunsch(haus,
						sonderwunschService.getSonderwunschHandler().getSonderwunsch(1));
				log.info("Special request {} removed", rbOpt0.getText().replace(":", ""));
			}
			if (!this.rbOpt1.isSelected() && s.getSonderwunsch().getId() == 2) {
				sonderwunschService.getSonderwunschHandler().removeSonderwunsch(haus,
						sonderwunschService.getSonderwunschHandler().getSonderwunsch(2));
				log.info("Special request {} removed", rbOpt1.getText().replace(":", ""));
			}
			if (!this.rbOpt2.isSelected() && s.getSonderwunsch().getId() == 3) {
				sonderwunschService.getSonderwunschHandler().removeSonderwunsch(haus,
						sonderwunschService.getSonderwunschHandler().getSonderwunsch(3));
				log.info("Special request {} removed", rbOpt2.getText().replace(":", ""));
			}
			if (!this.rbOpt3.isSelected() && s.getSonderwunsch().getId() == 4) {
				sonderwunschService.getSonderwunschHandler().removeSonderwunsch(haus,
						sonderwunschService.getSonderwunschHandler().getSonderwunsch(4));
				log.info("Special request {} removed", rbOpt3.getText().replace(":", ""));
			}
			if (!this.rbOpt4.isSelected() && s.getSonderwunsch().getId() == 5) {
				sonderwunschService.getSonderwunschHandler().removeSonderwunsch(haus,
						sonderwunschService.getSonderwunschHandler().getSonderwunsch(5));
				log.info("Special request {} removed", rbOpt4.getText().replace(":", ""));
			}
			if (!this.rbOpt5.isSelected() && s.getSonderwunsch().getId() == 6) {
				sonderwunschService.getSonderwunschHandler().removeSonderwunsch(haus,
						sonderwunschService.getSonderwunschHandler().getSonderwunsch(6));
				log.info("Special request {} removed", rbOpt5.getText().replace(":", ""));
			}
		}

		if (this.rbOpt0.isSelected()) {
			sonderwunschService.getSonderwunschHandler()
					.addSonderwunsch(sonderwunschService.getSonderwunschHandler().getSonderwunsch(1), haus);
			log.info("Special request {} added", rbOpt0.getText().replace(":", ""));
		}
		if (this.rbOpt1.isSelected()) {
			sonderwunschService.getSonderwunschHandler()
					.addSonderwunsch(sonderwunschService.getSonderwunschHandler().getSonderwunsch(2), haus);
			log.info("Special request {} added", rbOpt1.getText().replace(":", ""));
		}
		if (this.rbOpt2.isSelected()) {
			sonderwunschService.getSonderwunschHandler()
					.addSonderwunsch(sonderwunschService.getSonderwunschHandler().getSonderwunsch(3), haus);
			log.info("Special request {} added", rbOpt2.getText().replace(":", ""));
		}
		if (this.rbOpt3.isSelected()) {
			sonderwunschService.getSonderwunschHandler()
					.addSonderwunsch(sonderwunschService.getSonderwunschHandler().getSonderwunsch(4), haus);
			log.info("Special request {} added", rbOpt3.getText().replace(":", ""));
		}
		if (this.rbOpt4.isSelected()) {
			sonderwunschService.getSonderwunschHandler()
					.addSonderwunsch(sonderwunschService.getSonderwunschHandler().getSonderwunsch(5), haus);
			log.info("Special request {} added", rbOpt4.getText().replace(":", ""));
		}
		if (this.rbOpt5.isSelected()) {
			sonderwunschService.getSonderwunschHandler()
					.addSonderwunsch(sonderwunschService.getSonderwunschHandler().getSonderwunsch(6), haus);
			log.info("Special request {} added", rbOpt5.getText().replace(":", ""));
		}

		/*
		 * if(this.rbOpt0.isSelected()) {
		 * sonderwunschService.getSonderwunschHandler().createSonderwunsch(rbOpt0.
		 * getText().replace(":",""), dOpt0, "Grundriss", haus);
		 * log.info("Special request {} added", rbOpt0.getText().replace(":","")); }
		 * if(this.rbOpt1.isSelected()) {
		 * sonderwunschService.getSonderwunschHandler().createSonderwunsch(rbOpt1.
		 * getText().replace(":",""), dOpt1, "Grundriss", haus);
		 * log.info("Special request {} added", rbOpt1.getText().replace(":","")); }
		 * if(this.rbOpt2.isSelected()) {
		 * sonderwunschService.getSonderwunschHandler().createSonderwunsch(rbOpt2.
		 * getText().replace(":",""), dOpt2, "Grundriss", haus);
		 * log.info("Special request {} added", rbOpt2.getText().replace(":","")); }
		 * if(this.rbOpt3.isSelected()) {
		 * sonderwunschService.getSonderwunschHandler().createSonderwunsch(rbOpt3.
		 * getText().replace(":",""), dOpt3, "Grundriss", haus);
		 * log.info("Special request {} added", rbOpt3.getText().replace(":","")); }
		 * if(this.rbOpt4.isSelected()) {
		 * sonderwunschService.getSonderwunschHandler().createSonderwunsch(rbOpt4.
		 * getText().replace(":",""), dOpt4, "Grundriss", haus);
		 * log.info("Special request {} added", rbOpt4.getText().replace(":","")); }
		 * if(this.rbOpt5.isSelected()) {
		 * sonderwunschService.getSonderwunschHandler().createSonderwunsch(rbOpt5.
		 * getText().replace(":",""), dOpt5, "Grundriss", haus);
		 * log.info("Special request {} added", rbOpt5.getText().replace(":","")); }
		 */
		Stage stage = (Stage) lblOpt0.getScene().getWindow();
		stage.close();
	}

	public void setKunde(Kunde kunde) {
		this.kunde = kunde;
	}
}
