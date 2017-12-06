package org.projektmanagement;

import org.projektmanagement.config.AppConfig;
import org.projektmanagement.config.ApplicationContextProvider;
import org.projektmanagement.service.SonderwunschService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

	private AnnotationConfigApplicationContext ctx;
	private static final Logger log = LoggerFactory.getLogger(MainApp.class);

	public static void main(String[] args) throws Exception {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class,
				ApplicationContextProvider.class);
		ctx.scan("org.projektmanagement");
		new SonderwunschService().getSonderwunschHandler().initSonderwunsch();
		launch(args);
	}

	public void start(Stage stage) throws Exception {

		log.info("Starting Bauträgeranwendung application");

		String fxmlFile = "/views/customerOverview.fxml";
		log.debug("Loading FXML for main view from: {}", fxmlFile);
		FXMLLoader loader = new FXMLLoader();
		Parent rootNode = (Parent) loader.load(getClass().getResourceAsStream(fxmlFile));

		log.debug("Showing JFX scene");
		Scene scene = new Scene(rootNode);
		scene.getStylesheets().add("/styles/styles.css");

		stage.setTitle("Bauträgeranwendung");
		stage.setScene(scene);
		stage.show();
	}
}
