package org.projektmanagement.test;

import org.junit.Assert.*;

import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.projektmanagement.MainApp;
import org.projektmanagement.config.AppConfig;
import org.projektmanagement.config.ApplicationContextProvider;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import org.projektmanagement.model.Kunde;

import javafx.scene.layout.AnchorPane;

public class TestCustomer extends ApplicationTest {

	@Override
	public void start(Stage stage) throws Exception {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class,
				ApplicationContextProvider.class);
		ctx.scan("org.projektmanagement");

		Parent mainNode = FXMLLoader.load(MainApp.class.getResource("/views/customerOverview.fxml"));
		stage.setScene(new Scene(mainNode));
		stage.show();
		stage.toFront();
	}

	@Test
	public void testAddKunde() throws Exception {
		clickOn("#editMenu").clickOn("#addCustomerMenuItem");
		clickOn("#firstnameField");
		write("Max");
		clickOn("#lastnameField");
		write("Mustermann");
		clickOn("#emailField");
		write("max@mustermann.de");
		clickOn("#phoneField");
		write("0123456789");
		clickOn("#streetField");
		write("Musterstraße");
		clickOn("#streetnrField");
		write("1");
		clickOn("#plzField");
		write("12345");
		clickOn("#placeField");
		write("Musterstadt");
		clickOn("#countryComboBox").clickOn("Andorra");
		clickOn("#addCustomerButton");
		clickOn("#fileMenu").clickOn("#refreshMenuItem");
	}

	@Test
	public void testRemoveCustomer() {
		clickOn("#editMenu").clickOn("#addCustomerMenuItem");
		clickOn("#firstnameField");
		write("Max");
		clickOn("#lastnameField");
		write("Mustermann");
		clickOn("#emailField");
		write("max@mustermann.de");
		clickOn("#phoneField");
		write("0123456789");
		clickOn("#streetField");
		write("Musterstraße");
		clickOn("#streetnrField");
		write("1");
		clickOn("#plzField");
		write("12345");
		clickOn("#placeField");
		write("Musterstadt");
		clickOn("#countryComboBox").clickOn("Andorra");
		clickOn("#addCustomerButton");
		clickOn("#fileMenu").clickOn("#refreshMenuItem");
		clickOn("#tableView").clickOn("Max");
		clickOn("#editMenu").clickOn("#removeCustomerMenuItem").clickOn("OK");
		clickOn("#fileMenu").clickOn("#refreshMenuItem");
	}

	@Test
	public void testEditCustomer() {
		clickOn("#editMenu").clickOn("#addCustomerMenuItem");
		clickOn("#firstnameField");
		write("Max");
		clickOn("#lastnameField");
		write("Mustermann");
		clickOn("#emailField");
		write("max@mustermann.de");
		clickOn("#phoneField");
		write("0123456789");
		clickOn("#streetField");
		write("Musterstraße");
		clickOn("#streetnrField");
		write("1");
		clickOn("#plzField");
		write("12345");
		clickOn("#placeField");
		write("Musterstadt");
		clickOn("#countryComboBox").clickOn("Andorra");
		clickOn("#addCustomerButton");
		clickOn("#fileMenu").clickOn("#refreshMenuItem");
		clickOn("#tableView").doubleClickOn("Max").write("Maxi").push(javafx.scene.input.KeyCode.ENTER).clickOn("OK");
		clickOn("#fileMenu").clickOn("#refreshMenuItem");
	}

	@Test
	public void testRefresh() {
		clickOn("#fileMenu").clickOn("#refreshMenuItem");
	}

	@After
	public void tearDown() throws Exception {
		FxToolkit.hideStage();
		release(new KeyCode[] {});
		release(new MouseButton[] {});
	}
}
