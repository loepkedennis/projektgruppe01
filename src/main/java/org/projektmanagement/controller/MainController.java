package org.projektmanagement.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.awt.Desktop.Action;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainController
{
    private static final Logger log = LoggerFactory.getLogger(MainController.class);

    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private Label messageLabel;

    @FXML
    public void kundeEinfuegen() {

        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();


    }

}
