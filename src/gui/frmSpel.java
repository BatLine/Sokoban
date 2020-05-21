package gui;

import java.util.ResourceBundle;

import domein.DomeinController;
import exceptions.LevelNietMeerMogelijkException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class frmSpel extends Pane{
	private DomeinController dc;
	private Stage stage;
	private String huidigLevel = "1";
	private Image imgMuur, imgSpeler, imgVeld, imgKist, imgDoel;
	private String gekozenSpel;
	private ResourceBundle rb;
	private boolean blnPaused = false;
	
    @FXML
    private GridPane grid;
    @FXML
    private Label lblSpelInfo;
    @FXML
    private Label lblAantalVerplaatsingenText;
    @FXML
    private Label lblAantalVerplaatsingenNumber;
    @FXML
    private Button btnBack;
	
	public frmSpel(DomeinController dc, Stage pstage, String gekozenSpel) {		
		this.dc = dc;		
		this.rb = dc.getResourceBundle();
		
		dc.kiesSpel(gekozenSpel);
		
		this.stage = dc.initialize("frmSpel.fxml", this, pstage, 640, 503);
		initImages();
		initMoveEvents();
		
		this.gekozenSpel = gekozenSpel;
		updateSpelInfo();
		
		createField();
		
		lblAantalVerplaatsingenText.setText(rb.getString("aantalverplaatsingen"));
		btnBack.setText(rb.getString("terug"));
	}
	
	private void updateSpelInfo() {
		lblSpelInfo.setText(gekozenSpel + " - " + huidigLevel + "/" + dc.getAantalLevels());
		lblSpelInfo.setLayoutX(stage.getScene().getWidth()/2 - lblSpelInfo.prefWidth(-1)/2);
	}
	
	private void initImages() {
		Image[] images = dc.initImages();
		
		imgMuur = images[0];
		imgVeld = images[1];
		imgSpeler = images[2];
		imgKist = images[3];
		imgDoel = images[4];
	}
	private void initMoveEvents() {
		stage.getScene().setOnKeyPressed(e -> { keyPressEvent(e); });
		btnBack.setOnKeyPressed(e -> { keyPressEvent(e); });
	}

	private void keyPressEvent(javafx.scene.input.KeyEvent e) {
		if (blnPaused)
			return;
	    if (e.getCode() == KeyCode.LEFT) {
	        move("links");
	    } else if (e.getCode() == KeyCode.RIGHT) {
	        move("rechts");
	    } else if (e.getCode() == KeyCode.UP) {
	        move("omhoog");
	    } else if (e.getCode() == KeyCode.DOWN) {
	        move("omlaag");
	    }    
	}
	
	private void move(String richting) {
		try {
			dc.verplaatsSpeler(richting);
			tekenVeld(dc.getAlleVakken(huidigLevel));
			setAantalVerplaatsingen(dc.getAantalVerplaatsingen());
			checkLevelVoltooid(huidigLevel);
			checkSpelVoltooid();
		} catch (LevelNietMeerMogelijkException e) {
			pause(true);
			tekenVeld(dc.getAlleVakken(huidigLevel));
			setAantalVerplaatsingen(dc.getAantalVerplaatsingen());
			dc.showAlert(rb.getString("levelerror"), rb.getString("onmogelijklevel"), AlertType.WARNING);
			dc.restartLevel(huidigLevel);
			createField();
			setAantalVerplaatsingen(dc.getAantalVerplaatsingen());
			pause(false);
		}
	}
	
	private void setAantalVerplaatsingen(int aantal) {
		lblAantalVerplaatsingenNumber.setText(String.valueOf(aantal));
	}
	
	private void checkSpelVoltooid() {
		if (Integer.parseInt(huidigLevel) > dc.getAantalLevels()) {
			dc.showAlert(rb.getString("levelcompleted"), dc.getAantalLevels() + "/" + dc.getAantalLevels() + " " + rb.getString("xvoltooid"), AlertType.INFORMATION);

			frmMenu frmMenu = new frmMenu(dc, stage);
		}
	}
	
	private void checkLevelVoltooid(String level) {
		if (dc.voltooiSpelbord(level)) {
			dc.showAlert(rb.getString("levelcompleted"), level + "/" + dc.getAantalLevels() + " " + rb.getString("xvoltooid"), AlertType.INFORMATION);
			
			huidigLevel = String.valueOf(Integer.parseInt(huidigLevel) + 1);
			tekenLeegVeld();
			tekenVeld(dc.getAlleVakken(huidigLevel));
			updateSpelInfo();
			setAantalVerplaatsingen(0);
		}
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
	}
	
	private void tekenVeld(String[][] alleVakken) {
		tekenLeegVeld(); //doen voor transparante icons
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
			
			grid.add(foto, ycoord, xcoord);
		}
	}
	
    @FXML
    void btnBack_Click(ActionEvent event) {
    	pause(true);
    	dc.restartLevel("1");
    	frmMenu frmMenu = new frmMenu(dc, stage);
    }
    
    private void pause(boolean blnPaused) {
    	this.blnPaused = blnPaused;
    	if (blnPaused) {
    		btnBack.setDisable(true);
    	} else {
    		btnBack.setDisable(false);
    	}
    }
}