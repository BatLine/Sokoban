package gui;

import java.util.ResourceBundle;

import domein.DomeinController;
import exceptions.AccountException;
import exceptions.GebruikersnaamInGebruikException;
import exceptions.VerkeerdWachtwoordException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class frmRegistreren {
	private DomeinController dc;
	private Stage pstage;
	private ResourceBundle rb;
	
    @FXML
    private Label lblRegistreren;
    @FXML
    private Button btnRegistreren;
    @FXML
    private TextField txtNaam;
    @FXML
    private PasswordField  txtPassword;
    @FXML
    private TextField txtVoornaam;
    @FXML
    private TextField txtAchternaam;
    @FXML
    private PasswordField  txtPassword2;
    
    public frmRegistreren(DomeinController dc, Stage stage) {
		this.dc = dc;
		rb = dc.getResourceBundle();
		this.pstage = dc.initialize("frmRegistreren.fxml", this, stage, 237, 338);
		setControlsText();
	}
	
	private void setControlsText() {
		lblRegistreren.setText(rb.getString("registreren"));
		txtVoornaam.setPromptText(rb.getString("voornaam"));
		txtAchternaam.setPromptText(rb.getString("achternaam"));
		txtNaam.setPromptText(rb.getString("gebruikersnaam"));
		txtPassword.setPromptText(rb.getString("wachtwoord"));
		txtPassword2.setPromptText(rb.getString("wachtwoord2"));
		btnRegistreren.setText(rb.getString("registreren"));
		
		centerControls();
	}
	
	private void centerControls() {
		lblRegistreren.setAlignment(Pos.CENTER);
		txtNaam.setAlignment(Pos.CENTER);
		txtPassword.setAlignment(Pos.CENTER);
		txtPassword2.setAlignment(Pos.CENTER);
		txtVoornaam.setAlignment(Pos.CENTER);
		txtAchternaam.setAlignment(Pos.CENTER);
		btnRegistreren.setAlignment(Pos.CENTER);
		lblRegistreren.setLayoutX(pstage.getScene().getWidth()/2 - lblRegistreren.prefWidth(-1)/2);
	}
    
    @FXML
    void btnRegistreren_Click(ActionEvent event) {
		if ((dc.isNullOrWhitespaceOrEmpty(txtNaam.getText())) || (dc.isNullOrWhitespaceOrEmpty(txtPassword.getText()))) {
			dc.showAlert(rb.getString("registreererror"), rb.getString("wachtwoordofgebruikersnaamleeg"), AlertType.ERROR);
			return;
		} else if (!dc.checkPassword(txtPassword.getText())) {
			dc.showAlert(rb.getString("registreererror"), rb.getString("aanmakenwachtwoordfout"), AlertType.ERROR);
			return;
		} else if (txtNaam.getText().length() < 8) {
			dc.showAlert(rb.getString("registreererror"), rb.getString("gebruikersnaamtekort"), AlertType.ERROR);
			return;
		}
			
		try {
			dc.registreerSpeler(txtAchternaam.getText(), txtVoornaam.getText(), txtNaam.getText(), txtPassword.getText() , txtPassword2.getText());

			frmMenu frmMenu = new frmMenu(dc, pstage);
		} catch (VerkeerdWachtwoordException e) {
			dc.showAlert(rb.getString("inlogerror"), rb.getString("wachtwoordkloptniet"), AlertType.ERROR);
		} catch (AccountException e) {
			dc.showAlert(rb.getString("inlogerror"), rb.getString("foutopgetreden"), AlertType.ERROR);
		} catch (GebruikersnaamInGebruikException e) {
			dc.showAlert(rb.getString("inlogerror"), rb.getString("gebruikersnaambestaatal"), AlertType.ERROR);
		} catch (Exception e) {
			dc.showAlert(rb.getString("inlogerror"), rb.getString("onbekendefoutopgetreden"), AlertType.ERROR);
		}
    }
}