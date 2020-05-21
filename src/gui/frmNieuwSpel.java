package gui;

import java.util.ResourceBundle;

import domein.DomeinController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class frmNieuwSpel {
	private DomeinController dc;
	private Stage pstage;
	private ResourceBundle rb;
	
    @FXML
    private Label lblNieuwSpel;
    @FXML
    private Button btnMaken;
    @FXML
    private TextField txtSpelnaam;
    @FXML
    private TextField txtCreatornaam;
	
	public frmNieuwSpel(DomeinController dc, Stage pstage) {
		this.dc = dc;
		this.pstage = dc.initialize("frmNieuwSpel.fxml", this, pstage, 237, 220);
		this.rb = dc.getResourceBundle();
		
		setControls();
	}
	
	private void setControls() {
		lblNieuwSpel.setText(rb.getString("nieuwspel"));
		lblNieuwSpel.setAlignment(Pos.CENTER);
		lblNieuwSpel.setLayoutX(pstage.getScene().getWidth()/2 - lblNieuwSpel.prefWidth(-1)/2);
		
		txtSpelnaam.setPromptText(rb.getString("spelnaam"));
		txtSpelnaam.setAlignment(Pos.CENTER);
		
		txtCreatornaam.setPromptText(dc.geefGebruikersnaam());
		txtCreatornaam.setText(dc.geefGebruikersnaam());
		txtCreatornaam.setEditable(false);
		txtCreatornaam.setAlignment(Pos.CENTER);
		
		btnMaken.setText(rb.getString("maken"));
		btnMaken.setAlignment(Pos.CENTER);
	}

    @FXML
    void btnMaken_Click(ActionEvent event) {
    	if (dc.isNullOrWhitespaceOrEmpty(txtSpelnaam.getText())) {
    		dc.showAlert(rb.getString("nieuwspelerror"), rb.getString("geldigespelnaam"), AlertType.ERROR);
    		return;
    	}
    	
    	for (String s : dc.geefSpelnamen()) {
			if (s.toLowerCase().equals(txtSpelnaam.getText())) {
				dc.showAlert(rb.getString("nieuwspelerror"), rb.getString("uniekespelnaam"), AlertType.ERROR);
				return;
			}
		}
    	
    	dc.maakNieuwSpel(txtSpelnaam.getText());
    	dc.showAlert(rb.getString("nieuwspel"), rb.getString("nieuwspelaangemaakt"), AlertType.INFORMATION);

    	dc.showAlert(rb.getString("nieuwspel"), rb.getString("nieuwlevelwordtaangemaakt"), AlertType.INFORMATION);
    	dc.kiesSpel(txtSpelnaam.getText());
    	dc.maakNieuwSpelbord();
    	
    	frmMenu frmMenu = new frmMenu(dc, pstage);
    }
}
