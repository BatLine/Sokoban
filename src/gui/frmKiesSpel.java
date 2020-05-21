package gui;

import java.util.ResourceBundle;

import cui.wijzigSpel;
import domein.DomeinController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class frmKiesSpel {
	private DomeinController dc;
	private Stage pstage;
	private ResourceBundle rb;
	private String purpose;
	
    @FXML
    private Label lblSpellen;
    @FXML
    private Button btnKiesSpel;
    @FXML
    private ListView<String> lvSpellen;
    
    public frmKiesSpel(DomeinController dc, Stage stage, String purpose) {
    	this.purpose = purpose;
		this.dc = dc;
		rb = dc.getResourceBundle();
		this.pstage = dc.initialize("frmKiesSpel.fxml", this, stage, 333, 400);
		setControlsText();
		
		lvSpellen.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		vulListView();
	}
	
	private void setControlsText() {
		lblSpellen.setText(rb.getString("spellen"));
		lblSpellen.setLayoutX(pstage.getScene().getWidth()/2 - lblSpellen.prefWidth(-1)/2);
		
		btnKiesSpel.setText(rb.getString("kiesspel"));
	}
	
	private void vulListView() {
		lvSpellen.getItems().clear();
		String[] spelNamen = dc.geefSpelnamen();
		
		for (String s : spelNamen) {
			lvSpellen.getItems().add(s);
		}
	}

    @FXML
    void btnKiesSpel_Click(ActionEvent event) {
    	String gekozenSpel = lvSpellen.getSelectionModel().getSelectedItem();
    	if (dc.isNullOrWhitespaceOrEmpty(gekozenSpel)) {
    		dc.showAlert(rb.getString("kiesspelerror"), rb.getString("ongeldigspel"), AlertType.ERROR);
    		return;
    	}
    	
    	switch (purpose.toLowerCase()) {
		case "speelspel":
			frmSpel spel = new frmSpel(dc, pstage, gekozenSpel);
			break;
		case "wijzigspel":
			frmWijzigSpel wijzigSpel = new frmWijzigSpel(dc, pstage, gekozenSpel);
			break;
		default:
			frmSpel spelB = new frmSpel(dc, pstage, gekozenSpel);
			break;
		}
    	
    }
}
