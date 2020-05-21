package gui;

import java.util.Locale;
import java.util.ResourceBundle;

import domein.DomeinController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


/**
 * 
 * Het GUI startscherm
 * 
 * @author Gilles De Pessemier, Rune De Bruyne, Aaron Everaert, Chiel Meneve
 *
 */
public class Home extends Pane{
	private DomeinController dc;
	private Stage pstage;
	private ResourceBundle rb;
    
    @FXML
    private Button btnInloggen;
    @FXML
    private Button btnRegistreer;
    @FXML
    private ComboBox<String> cboLanguage;
    @FXML
    private ImageView lvFoto;
	
	public Home(DomeinController dc, Stage pstage) {
		this.dc = dc;
		rb = dc.getResourceBundle();
		this.pstage = dc.initialize("frmHome.fxml", this, pstage, 640, 400);
		
		lvFoto.setImage(new Image(getClass().getResource("/ico.png").toString()));
		lvFoto.toBack();
		
		initLanguageBox();
	}
	
	private void initLanguageBox() {
		ObservableList<String> list = FXCollections.observableArrayList();
		
		list.add("Nederlands");
		list.add("Français");
		list.add("English");
		
		cboLanguage.setItems(list);
		cboLanguage.getSelectionModel().select(0);
	}
	
    @FXML
    void btnLogin_Click(ActionEvent event) {
    	frmLogin login = new frmLogin(dc, pstage);
    }

    @FXML
    void btnRegistreer_Click(ActionEvent event) {
    	frmRegistreren registreren = new frmRegistreren(dc, pstage);
    }
    
    @FXML
    void cboLanguage_SelectionChanged(ActionEvent event) {
    	Locale currentLanguage = null;
    	SingleSelectionModel<String> selectedItem = cboLanguage.getSelectionModel();
    	
    	
    	switch (String.valueOf(selectedItem.getSelectedIndex())) {
		case "0": //nederlands
			currentLanguage = new Locale("nl", "BE");
			break;
		case "1": //frans
			currentLanguage = new Locale("fr", "FR");
			break;
		case "2": //engels
			currentLanguage = new Locale("en","US");
			break;
		default:
			currentLanguage = new Locale("nl", "BE");
			break;
		}
		
		Locale.setDefault(currentLanguage);
		dc.updateLanguage();
		rb = dc.getResourceBundle();
		updateControlsText();
    }
    
    private void updateControlsText() {
    	btnInloggen.setText(rb.getString("inloggen"));
    	btnRegistreer.setText(rb.getString("registreren"));
    }
}
