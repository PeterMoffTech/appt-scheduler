package moffett.scheduler.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import moffett.scheduler.helper.HelperFunctions;
import moffett.scheduler.helper.LoginTracker;
import moffett.scheduler.helper.JDBC;

import java.io.IOException;
import java.net.URL;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Class that allows the user to log in to the scheduler app, establishes
 * a database connection, and generates a history of login attempts.
 */
public class LoginController implements Initializable {

    @FXML
    private Button LoginButton;

    @FXML
    private PasswordField PasswordText;

    @FXML
    private TextField UsernameText;

    @FXML
    private Label ZoneIDLabel;

    @FXML
    private Label locationLabel;

    @FXML
    private Label titleLabel;

    @FXML
    private Label usernameLabel;

    @FXML
    private Label passwordLabel;

    ResourceBundle bundle;

    /**
     * KeyEvent that triggers login attempt when user types in required information
     * in password text field.
     * @param event on enter press in password text field.
     */
    @FXML
    void onEnter(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            Locale userLocale = Locale.getDefault();
            String username = UsernameText.getText();
            if (userLocale.getLanguage().equals("fr")) {
                bundle = ResourceBundle.getBundle("fr-bundle");
            } else {
                bundle = ResourceBundle.getBundle("en-bundle");
            }
            try {
                JDBC.openConnection(username, PasswordText.getText());
                HelperFunctions.changeStage("directory-view.fxml", LoginButton);
                HelperFunctions.greeting();
                LoginTracker.trackLogin(username, true);
            } catch (IOException e) {
                LoginTracker.trackLogin(username, false);
                HelperFunctions.sendAlert(Alert.AlertType.ERROR, bundle.getString("error.title"), bundle.getString("error.content"));
            }
        }
    }

    /**
     * ActionEvent tries to open a connection with user's entered information,
     * and sends the login info to a text file, sends alert if login fails.
     * @param event
     * @throws IOException
     */
    @FXML
    void LoginButtonClick(ActionEvent event) throws IOException {
        Locale userLocale = Locale.getDefault();
        String username = UsernameText.getText();
        if (userLocale.getLanguage().equals("fr")) {
            bundle = ResourceBundle.getBundle("fr-bundle");
        } else {
            bundle = ResourceBundle.getBundle("en-bundle");
        }
        try {
            JDBC.openConnection(username, PasswordText.getText());
            HelperFunctions.changeStage("directory-view.fxml", LoginButton);
            HelperFunctions.greeting();
            LoginTracker.trackLogin(username, true);
        } catch (IOException e) {
            HelperFunctions.sendAlert(Alert.AlertType.ERROR, bundle.getString("error.title"), bundle.getString("error.content"));
            LoginTracker.trackLogin(username, false);
        }
    }

    /**
     * Initialize method that checks for user's default system language, setting
     * language resource bundle accordingly
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Locale userLocale = Locale.getDefault();
        if (userLocale.getLanguage().equals("fr")) {
            bundle = ResourceBundle.getBundle("fr-bundle");
        } else {
            bundle = ResourceBundle.getBundle("en-bundle");
        }
        ZoneIDLabel.setText(String.valueOf(ZoneId.systemDefault()));
        titleLabel.setText(bundle.getString("label.title"));
        usernameLabel.setText(bundle.getString("label.username"));
        passwordLabel.setText(bundle.getString("label.password"));
        locationLabel.setText(bundle.getString("label.location"));
        UsernameText.setPromptText(bundle.getString("prompt.username"));
        PasswordText.setPromptText(bundle.getString("prompt.password"));
        LoginButton.setText(bundle.getString("button.login"));
    }
}
