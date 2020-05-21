package gui;

import java.util.ResourceBundle;

import domein.DomeinController;
import exceptions.AccountException;
import exceptions.VerkeerdWachtwoordException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

public class frmLogin {
	private DomeinController dc;
	private Stage pstage;
	private ResourceBundle rb;
	
    @FXML
    private Button btnLogin;
    @FXML
    private TextField txtNaam;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Label lblInloggen;
    
	public frmLogin(DomeinController dc, Stage stage) {
		this.dc = dc;
		rb = dc.getResourceBundle();
		this.pstage = dc.initialize("frmLogin.fxml", this, stage, 237, 250);
		setControlsText();
	}
	
	private void setControlsText() {
		lblInloggen.setText(rb.getString("inloggen"));
		lblInloggen.setLayoutX(pstage.getWidth()/2 - lblInloggen.getWidth());
		txtNaam.setPromptText(rb.getString("gebruikersnaam"));
		txtPassword.setPromptText(rb.getString("wachtwoord"));
		btnLogin.setText(rb.getString("inloggen"));
		
		centerControls();
	}
	
	private void centerControls() {
		lblInloggen.setAlignment(Pos.CENTER);
		txtNaam.setAlignment(Pos.CENTER);
		txtPassword.setAlignment(Pos.CENTER);
		btnLogin.setAlignment(Pos.CENTER);
		lblInloggen.setLayoutX(pstage.getScene().getWidth()/2 - lblInloggen.prefWidth(-1)/2);
	}
	
	@FXML
    void btnLogin_Click(ActionEvent event) {
		if ((dc.isNullOrWhitespaceOrEmpty(txtNaam.getText())) || (dc.isNullOrWhitespaceOrEmpty(txtPassword.getText())))
			dc.showAlert(rb.getString("inlogerror"), "Wachtwoord of gebruikersnaam is leeg", AlertType.ERROR);
		
		try {
			dc.aanmeldenSpeler(txtNaam.getText(), txtPassword.getText());
			
			frmMenu frmMenu = new frmMenu(dc, pstage);
		} catch (VerkeerdWachtwoordException e) {
			dc.showAlert(rb.getString("inlogerror"), rb.getString("wachtwoordkloptniet"), AlertType.ERROR);
		} catch (AccountException e) {
			String message;
			if (e.getMessage().contains(rb.getString("geengebruikergevonden")))
				message = rb.getString("onjuistenaam");
			else 
				message = rb.getString("foutopgetreden");
			
			dc.showAlert(rb.getString("inlogerror"), message, AlertType.ERROR);
		} catch (Exception e) {
			dc.showAlert(rb.getString("inlogerror"), rb.getString("onbekendefoutopgetreden"), AlertType.ERROR);
		}
    }
}
