package org.projektmanagement.controller;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.projektmanagement.model.HausSonderwunsch;
import org.projektmanagement.model.Kunde;
import org.projektmanagement.model.Sonderwunsch;
import org.projektmanagement.service.SonderwunschService;
import org.projektmanagement.utils.CSVExporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * 
 * @author Stanislaus Bosch
 *
 */
public class FensterUndAussentuerenController {

	private static final Logger log = LoggerFactory.getLogger(FensterUndAussentuerenController.class);

	@FXML
	GridPane gridPane;
	private Label geskostenLabel;
	private List<Sonderwunsch> sonderwunsch;
	private SonderwunschService sonderwunschService = new SonderwunschService();
	private Kunde kunde;
	private long kategorieID;
	private Stage stage;
	/**
	 * Keys: ID von den Sonderwünschen Values: Labels für die Beschreibung
	 */
	private Map<Long, Label> IDlabelMap;
	/**
	 * Key: CheckBox Value: Price
	 */
	private Map<CheckBox, Label> CheckPriceMap;
	/**
	 * Key: ID von den Sonderwünschen Values: CheckBox
	 */
	private Map<Long, CheckBox> IDCheckBoxMap;

	/**
	 * Erzeugt ein ControlObjekt fuer die Sonderwuensche fuer Fenster und
	 * Aussentueren.
	 * 
	 */
	public FensterUndAussentuerenController() {
		super();
		log.debug("initialisiere den FensterUndAussentuerenController");
	}

	public void init(long id, Stage stage) {
		this.stage = stage;
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

			public void handle(WindowEvent event) {
				boolean isChanged = false;
				List<HausSonderwunsch> hsList = sonderwunschService.getSonderwunschHandler()
						.getSonderwunscheHouse(kunde);

				for (Iterator<HausSonderwunsch> iterator = hsList.iterator(); iterator.hasNext();) {
					HausSonderwunsch hs = iterator.next();
					if (hs.getSonderwunsch().getKategroien().getId() != kategorieID)
						hsList.remove(hs);
				}
				for (HausSonderwunsch hs : hsList)
					if (!IDCheckBoxMap.get(hs.getSonderwunsch().getId()).isSelected())
						isChanged = true;
				int i = 0;
				for (Long l : IDCheckBoxMap.keySet())
					if (IDCheckBoxMap.get(l).isSelected())
						i++;
				if (i != hsList.size())
					isChanged = true;

				if (isChanged) {
					Alert alert = new Alert(AlertType.NONE);
					alert.setTitle("Bestätigung");
					alert.setHeaderText("Wollen Sie speichern bevor Sie das Fenster schließen?");
					alert.getButtonTypes().add(ButtonType.YES);
					alert.getButtonTypes().add(ButtonType.NO);
					Optional<ButtonType> result = alert.showAndWait();
					if (result.get() == ButtonType.NO)
						alert.close();
					else
						speichern();
				}

			}
		});
		kategorieID = id;
		geskostenLabel = new Label();
		geskostenLabel.setScaleX(1.2);
		geskostenLabel.setScaleY(1.2);

		IDlabelMap = new HashMap<Long, Label>();
		CheckPriceMap = new HashMap<CheckBox, Label>();
		IDCheckBoxMap = new HashMap<Long, CheckBox>();
		sonderwunsch = sonderwunschService.getSonderwunschHandler().getSonderwunschByKategorieID(kategorieID);

		// hole die Beschreibung und Preise aus der Datenbank
		for (Sonderwunsch s : sonderwunsch) {
			IDlabelMap.put(s.getId(), new Label(s.getName()));
			CheckBox c = new CheckBox();
			CheckPriceMap.put(c, new Label(s.getPreis() + " €"));
			IDCheckBoxMap.put(s.getId(), c);
		}
		// überprüfe den Haustypen
		if (kunde.getHouses().get(0).getHousetyp().getId() == kategorieID) {
			IDCheckBoxMap.get(8L).setDisable(true);
			IDCheckBoxMap.get(12L).setDisable(true);
			IDCheckBoxMap.get(15L).setDisable(true);
		}
		// aktualisiere die CheckBoxen
		for (HausSonderwunsch s : sonderwunschService.getSonderwunschHandler().getSonderwunscheHouse(kunde)) {
			if (s.getSonderwunsch().getKategroien().getId() == kategorieID)
				IDCheckBoxMap.get(s.getSonderwunsch().getId()).setSelected(true);
		}
		// Füge alle Grafischen Elemente dem GridPane(GUI) hinzu
		int rI = 0;
		for (long j = sonderwunsch.get(0).getId(); j < sonderwunsch.get(0).getId() + sonderwunsch.size(); j++) {
			gridPane.addRow(rI, IDlabelMap.get(j), CheckPriceMap.get(IDCheckBoxMap.get(j)), IDCheckBoxMap.get(j));
			rI++;
		}
		gridPane.add(geskostenLabel, 1, rI + 1);
		preisBerechnen();
	}

	@FXML
	public void speichern() {
		for (Sonderwunsch s : sonderwunsch)
			if (IDCheckBoxMap.get(s.getId()).isSelected())
				sonderwunschService.getSonderwunschHandler().addSonderwunsch(s, kunde.getHouses().get(0));
			else
				sonderwunschService.getSonderwunschHandler().removeSonderwunsch(kunde.getHouses().get(0), s);
		preisBerechnen();
	}

	@FXML
	public void preisBerechnen() {
		int gesamtKosten = 0;
		for (Sonderwunsch s : sonderwunschService.getSonderwunschHandler().getSonderwunschByKategorieID(kategorieID))
			if (IDCheckBoxMap.containsKey(s.getId()) & IDCheckBoxMap.get(s.getId()).isSelected())
				gesamtKosten += s.getPreis();
		geskostenLabel.setText(gesamtKosten + " € ");
	}

	@FXML
	public void csvExport() {
		CSVExporter csvExport = new CSVExporter();
		try {
			FileWriter filePfad = new FileWriter(csvExport.setStrPfad(stage));
			CSVExporter.writeLine(filePfad, getCSVAuswahl());
			filePfad.flush();
			filePfad.close();
		} catch (Exception e) {
		}
	}

	public List<String> getCSVAuswahl() {
		List<String> listStrAuswahl = new ArrayList<String>();
		for (Sonderwunsch s : sonderwunschService.getSonderwunschHandler().getSonderwunschByKategorieID(kategorieID))
			if (IDCheckBoxMap.get(s.getId()).isSelected())
				listStrAuswahl.add(s.getName());
		return listStrAuswahl;
	}

	public void setKunde(Kunde selectedItem) {
		this.kunde = selectedItem;
	}

}