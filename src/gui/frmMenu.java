package gui;
import java.util.ResourceBundle;

import domein.DomeinController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class frmMenu {
	private DomeinController dc;
	private Stage pstage;
	private ResourceBundle rb;
	
    @FXML
    private Button btnSpelSpelen;
    @FXML
    private Button btnSpelMaken;
    @FXML
    private Button btnSpelWijzigen;
    @FXML
    private ImageView ivFoto;
    @FXML
    private Label lblWelkom;
	
	public frmMenu(DomeinController dc, Stage stage) {
		this.dc = dc;
		this.pstage = dc.initialize("frmMenu.fxml", this, stage, 340, 282);
		this.rb = dc.getResourceBundle();
		setControls();
	}
	
	private void setControls() {
        lblWelkom.setText(rb.getString("welkom") + dc.geefGebruikersnaam());
        lblWelkom.setLayoutX(pstage.getScene().getWidth()/2 - lblWelkom.prefWidth(-1)/2);
        
        ivFoto.setImage(new Image(getClass().getResource("/ico.png").toString()));
        ivFoto.toBack();
        
        btnSpelMaken.setText(rb.getString("spelmaken"));
        btnSpelSpelen.setText(rb.getString("spelspelen"));
        btnSpelWijzigen.setText(rb.getString("spelwijzigen"));
        
        if (dc.isAdmin()) {
        	btnSpelMaken.setVisible(true);
        	btnSpelWijzigen.setVisible(true);
        } else {
        	btnSpelMaken.setVisible(false);
        	btnSpelWijzigen.setVisible(false);
        }
	}
	
    @FXML
    void btnSpelSpelen_Click(ActionEvent event) {
    	frmKiesSpel kiesSpel = new frmKiesSpel(dc, pstage, "speelspel");
    }
    
    @FXML
    void btnSpelMaken_Click(ActionEvent event) {
    	frmNieuwSpel frmNieuwSpel = new frmNieuwSpel(dc, pstage);
    }

    @FXML
    void btnSpelWijzigen_Click(ActionEvent event) {
    	frmKiesSpel kiesSpel = new frmKiesSpel(dc, pstage, "wijzigspel");
    }
}
