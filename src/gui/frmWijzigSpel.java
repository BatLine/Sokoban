package gui;

import java.util.Optional;
import java.util.ResourceBundle;

import domein.DomeinController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class frmWijzigSpel {
	private DomeinController dc;
	private Stage pstage;
	private String huidigLevel = "1";
	private String gekozenSpel;
	private Image imgMuur, imgSpeler, imgVeld, imgKist, imgDoel;
	private ResourceBundle rb;
	
    @FXML
    private GridPane grid;
    @FXML
    private Label lblSpelInfo;
    @FXML
    private Button btnNieuwLevel;
    @FXML
    private Button btnNextLevel;
    @FXML
    private Button btnPreviousLevel;
    @FXML
    private Button btnOpslaan;
    @FXML
    private Label lblRij;
    @FXML
    private Label lblKolom;
    @FXML
    private Label lblType;
    @FXML
    private ComboBox<String> cboRij;
    @FXML
    private ComboBox<String> cboKolom;
    @FXML
    private ComboBox<String> cboType;
    @FXML
    private Button btnBack;
    @FXML
    private Button btnVerwijderLevel;
    @FXML
    private ImageView ivFoto;
	
	public frmWijzigSpel(DomeinController dc, Stage pstage, String gekozenSpel) {
		this.dc = dc;
		this.pstage = dc.initialize("frmWijzigSpel.fxml", this, pstage, 817, 548);
		this.gekozenSpel = gekozenSpel;
		this.rb = dc.getResourceBundle();
		
		dc.kiesSpel(gekozenSpel);
		
		updateSpelInfo();
		initImages();
		initComboBoxes();
		
		createField();
		grid.toBack();
		
		setControls();
	}
	
	private void setControls() {
		lblRij.setText(rb.getString("rij"));
		lblKolom.setText(rb.getString("kolom"));
		lblType.setText(rb.getString("type"));
		btnOpslaan.setText(rb.getString("opslaan"));
		btnNieuwLevel.setText(rb.getString("nieuwlevel"));
		btnBack.setText(rb.getString("terug"));
		btnVerwijderLevel.setText(rb.getString("verwijderlevel"));
		
        ivFoto.setImage(new Image(getClass().getResource("/ico.png").toString()));
        ivFoto.toBack();
		
		btnNieuwLevel.setLayoutX(grid.getLayoutX() + grid.prefWidth(-1)/2 - btnNieuwLevel.prefWidth(-1)/2);
		btnPreviousLevel.setLayoutX(btnNieuwLevel.getLayoutX()-btnPreviousLevel.prefWidth(-1)-6);
		btnNextLevel.setLayoutX(btnNieuwLevel.getLayoutX()+btnNieuwLevel.prefWidth(-1)+6);
		btnVerwijderLevel.setLayoutX(grid.getLayoutX() + grid.prefWidth(-1)/2 - btnVerwijderLevel.prefWidth(-1)/2);
		
		lblSpelInfo.setLayoutX(grid.getLayoutX() + grid.prefWidth(-1) / 2 - lblSpelInfo.prefWidth(-1)/2);
	}
	
	private void updateSpelInfo() {
		lblSpelInfo.setText(gekozenSpel + " - " + huidigLevel + "/" + dc.getAantalLevels());
		
		if (huidigLevel.equals("1")) {
			btnPreviousLevel.setDisable(true);
		} else {
			btnPreviousLevel.setDisable(false);
		}
		
		if (Integer.parseInt(huidigLevel) >= dc.getAantalLevels()) {
			btnNextLevel.setDisable(true);
		} else {
			btnNextLevel.setDisable(false);
		}
	}
	
	private void initImages() {
		Image[] images = dc.initImages();
		
		imgMuur = images[0];
		imgVeld = images[1];
		imgSpeler = images[2];
		imgKist = images[3];
		imgDoel = images[4];
	}
	private void initComboBoxes() {
		for (int j = 1; j <= 10; j++) {
			cboKolom.getItems().add(String.valueOf(j));
			cboRij.getItems().add(String.valueOf(j));
		}
		
		cboType.getItems().add(String.valueOf(rb.getString("veld")));
		cboType.getItems().add(String.valueOf(rb.getString("muur")));
		cboType.getItems().add(String.valueOf(rb.getString("kist")));
		cboType.getItems().add(String.valueOf(rb.getString("speler")));
		cboType.getItems().add(String.valueOf(rb.getString("doel")));
		
		cboKolom.getSelectionModel().select(0);
		cboRij.getSelectionModel().select(0);
		cboType.getSelectionModel().select(0);
	}
	
	private void createField() {
		tekenLeegVeld();
		tekenVeld(dc.getAlleVakken(huidigLevel));
	}
	private void tekenLeegVeld() {
		grid.getChildren().clear();
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				grid.add(new ImageView(imgVeld), j, i);
			}
		}
		grid.setGridLinesVisible(true);
	}
	
	private void tekenVeld(String[][] alleVakken) {
		tekenLeegVeld();
		for (String[] vak : alleVakken) {
			String type = vak[0];
			int xcoord = Integer.parseInt(vak[1]);
			int ycoord = Integer.parseInt(vak[2]);
			Boolean isDoel = Boolean.valueOf(vak[3]);
			
			ImageView foto;
			switch (type) {
			case "muur":
				foto = new ImageView(imgMuur);
				break;
			case "veld":
				foto = new ImageView(imgVeld);
				break;
			case "kist":
				foto = new ImageView(imgKist);
				break;
			case "speler":
				foto = new ImageView(imgSpeler);
				break;
			case "none":
			default:
				foto = new ImageView(imgDoel);
				break;
			}
			
			if (isDoel && (!type.equals("speler")) && (!type.equals("kist"))) {
				foto = new ImageView(imgDoel);
			}
			
			if ((foto.equals(new ImageView(imgSpeler))) || (foto.equals(new ImageView(imgKist)))) { //2* doen door transparante achtergrond
				grid.add(new ImageView(imgVeld), ycoord, xcoord);
				//thread sleep?
			}
			
			int[] coords = new int[2];
			coords[0] = ycoord;
			coords[1] = xcoord;
			foto.setUserData(coords);
			foto.setOnMouseClicked(e -> { fotoClickEvent(e); });
			
			grid.add(foto, ycoord, xcoord);
		}
	}
	
	private void fotoClickEvent(MouseEvent e) {
		ImageView foto = (ImageView) e.getSource();
		int[] coords = (int[]) foto.getUserData();
		cboRij.getSelectionModel().select(coords[1]);
		cboKolom.getSelectionModel().select(coords[0]);
	}
	
    @FXML
    void btnNextLevel_Click(ActionEvent event) {
    	huidigLevel = String.valueOf(Integer.parseInt(huidigLevel) + 1);
    	createField();
    	updateSpelInfo();
    }

    @FXML
    void btnNieuwLevel_Click(ActionEvent event) {
    	huidigLevel = String.valueOf(dc.getAantalLevels() + 1);
    	dc.maakNieuwSpelbord();
    	createField();
    	updateSpelInfo();
    }

    @FXML
    void btnPreviousLevel_Click(ActionEvent event) {
    	huidigLevel = String.valueOf(Integer.parseInt(huidigLevel) - 1);
    	createField();
    	updateSpelInfo();
    }
    
    @FXML
    void btnOpslaan_Click(ActionEvent event) {
    	boolean isDoel = false;
    	String type = "veld";
    	
    	switch (cboType.getSelectionModel().getSelectedIndex()) {
		case 0: //veld
			type = "veld";
			break;
		case 1: //muur
			type = "muur";
			break;
		case 2: //kist
			type = "kist";
			break;
		case 3: //speler
			type = "speler";
			break;
		case 4: //doel
			isDoel = true;
			type = "veld";
			break;
		}
    	
    	dc.updateVak(Integer.parseInt(cboRij.getSelectionModel().getSelectedItem())-1, Integer.parseInt(cboKolom.getSelectionModel().getSelectedItem())-1, type.toLowerCase(), isDoel);
    	tekenVeld(dc.getAlleVakken(huidigLevel));
    }
    
    @FXML
    void btnBack_Click(ActionEvent event) {
    	frmMenu frmMenu = new frmMenu(dc, pstage);
    }
    
    @FXML
    void btnVerwijderLevel_Click(ActionEvent event) {
    	if (dc.getAantalLevels() > 0) {
    		dc.verwijderLevel(huidigLevel);
    		huidigLevel = "1";
    	}
    	if (dc.getAantalLevels() <= 0) {
    		//verwijder spel
    		Alert alert = new Alert(AlertType.CONFIRMATION);
    		alert.setTitle("Sokoban");
    		alert.setHeaderText(rb.getString("wijziglevelerror"));
    		alert.setContentText(rb.getString("geenlevelsmeer"));
    		Optional<ButtonType> result = alert.showAndWait();
    		if ((result.isPresent()) && (result.get() == ButtonType.OK)) {
    			dc.verwijderSpel(gekozenSpel);
    		}
    		
    		frmMenu frmMenu = new frmMenu(dc, pstage);
    		return;
    	}
    	
    	createField();
    	updateSpelInfo();
    }
}
