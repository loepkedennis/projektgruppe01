package org.projektmanagement.controller;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.projektmanagement.model.Haus;
import org.projektmanagement.model.Kunde;
import org.projektmanagement.service.SonderwunschService;
import org.projektmanagement.utils.CSVExporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import javafx.stage.Stage;
import javafx.scene.control.CheckBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class AussenanlagenController {

	@FXML
	private CheckBox cbAbstellraum;
	@FXML
	private CheckBox cbVorbereitungEAntriebMarkiseEG;
	@FXML
	private CheckBox cbVortbereitungEAntriebMarkiseDG;
	@FXML
	private CheckBox cbMarkiseEG;
	@FXML
	private CheckBox cbMarkiseDG;
	@FXML
	private CheckBox cbEAntriebGaragentor;
	@FXML
	private CheckBox cbSektionaltor;
	private Stage stage;
	private Kunde kunde;
	private static final Logger log = LoggerFactory.getLogger(GrundrissController.class);
	private SonderwunschService sonderwunschService = new SonderwunschService();

	public void init(Stage stage) {
		this.stage = stage;
	}

	public void setKunde(Kunde kunde) {
		this.kunde = kunde;
	}

	// TODO: Must be modified if a GUI exists.
	@FXML
	protected void OnClickSave(ActionEvent event) {
		Haus haus = kunde.getHouses().get(0);

		if (this.cbAbstellraum.isSelected()) {
			sonderwunschService.getSonderwunschHandler()
					.addSonderwunsch(sonderwunschService.getSonderwunschHandler().getSonderwunsch(44), haus);
			log.info("Special request {} added", cbAbstellraum.getText().replace(":", ""));
		}

		if (this.cbVorbereitungEAntriebMarkiseEG.isSelected()) {
			sonderwunschService.getSonderwunschHandler()
					.addSonderwunsch(sonderwunschService.getSonderwunschHandler().getSonderwunsch(45), haus);
			log.info("Special request {} added", cbVorbereitungEAntriebMarkiseEG.getText().replace(":", ""));
		}

		if (this.cbVortbereitungEAntriebMarkiseDG.isSelected()) {
			sonderwunschService.getSonderwunschHandler()
					.addSonderwunsch(sonderwunschService.getSonderwunschHandler().getSonderwunsch(46), haus);
			log.info("Special request {} added", cbVortbereitungEAntriebMarkiseDG.getText().replace(":", ""));
		}

		if (this.cbMarkiseEG.isSelected()) {
			sonderwunschService.getSonderwunschHandler()
					.addSonderwunsch(sonderwunschService.getSonderwunschHandler().getSonderwunsch(47), haus);
			log.info("Special request {} added", cbMarkiseEG.getText().replace(":", ""));
		}

		if (this.cbMarkiseDG.isSelected()) {
			sonderwunschService.getSonderwunschHandler()
					.addSonderwunsch(sonderwunschService.getSonderwunschHandler().getSonderwunsch(48), haus);
			log.info("Special request {} added", cbMarkiseDG.getText().replace(":", ""));
		}

		if (this.cbEAntriebGaragentor.isSelected()) {
			sonderwunschService.getSonderwunschHandler()
					.addSonderwunsch(sonderwunschService.getSonderwunschHandler().getSonderwunsch(49), haus);
			log.info("Special request {} added", cbEAntriebGaragentor.getText().replace(":", ""));
		}

		if (this.cbSektionaltor.isSelected()) {
			sonderwunschService.getSonderwunschHandler()
					.addSonderwunsch(sonderwunschService.getSonderwunschHandler().getSonderwunsch(50), haus);
			log.info("Special request {} added", cbSektionaltor.getText().replace(":", ""));
		}
	}

	public void exportToCsv() throws IOException {
		List<String> values = new ArrayList();
		CSVExporter exporter = new CSVExporter();

		if (cbAbstellraum.isSelected()) {
			values.add("Abstellraum auf der Terrasse des EG: 3.590 Euro");
		}
		if (cbVorbereitungEAntriebMarkiseEG.isSelected()) {
			values.add("Vorbereitung für elektrische Antriebe Markise EG: 170 Euro");
		}
		if (cbVortbereitungEAntriebMarkiseDG.isSelected()) {
			values.add("Vorbereitung für elektrische Antriebe Markise DG: 170 Euro");
		}
		if (cbMarkiseEG.isSelected()) {
			values.add("Elekrische Markise EG : 890 Euro");
		}
		if (cbMarkiseDG.isSelected()) {
			values.add("Elekrische Markise DG : 890 Euro");
		}
		if (cbEAntriebGaragentor.isSelected()) {
			values.add("Elektrischen Antrieb für das Garagentor: 990 Euro");
		}

		if (cbSektionaltor.isSelected()) {
			values.add("Sektionaltor anstatt Schwingtor für die Garage: 790 Euro");
		}

		FileWriter fileWriter = new FileWriter(exporter.setStrPfad(stage));
		CSVExporter.writeLine(fileWriter, values);
	}
}
