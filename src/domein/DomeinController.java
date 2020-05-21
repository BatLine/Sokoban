package domein;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

import exceptions.AccountException;
import exceptions.SpelMakenException;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * 
 * De hoofd domain klasse
 * 
 * @author Gilles De Pessemier, Rune De Bruyne, Aaron Everaert, Chiel Meneve
 *
 */
public class DomeinController
{
    private final SpelerRepository spelerRepository;
    private final SpelRepository spelRepository;
    private Speler huidigeSpeler;
    private Spel huidigSpel;
    private ResourceBundle rb = ResourceBundle.getBundle("languages/resource_bundle", Locale.getDefault());
    
    /**
     * Domeincontroller constructor
     */
    public DomeinController() {
        spelerRepository = new SpelerRepository();
        spelRepository = new SpelRepository();
    }
    
    /**
     * Geeft de resourcebundel terug
     * @return Geeft de correcte resourcebundel terug
     */
    public ResourceBundle getResourceBundle() {
    	return rb;
    }
    
    /**
     * Geeft terug of de gebruik adminrights heeft of niet
     * @return Geeft terug of de gebruik adminrights heeft of niet
     */
    public boolean isAdmin() {
    	return huidigeSpeler.isAdmin();
    }
    
    /**
     * Meld een speler aan
     * @param ingegeven gebruikersNaam
     * @param ingegeven wachtwoord
     * 
     * @exception AccountException
     */
	public void aanmeldenSpeler(String gebruikersNaam, String wachtwoord) {
	      Speler gevondenSpeler = spelerRepository.geefSpeler(gebruikersNaam, wachtwoord);
	        if (gevondenSpeler != null) {
	            setSpeler(gevondenSpeler);
	        } else {
	        	throw new AccountException(rb.getString("geengebruikergevonden"));
	        }
	}
	
    /**
     * Registreert een nieuwe speler
     * @param achternaam
     * @param voornaam
     * @param gebruikersNaam
     * @param wachtwoord
     * @param wachtwoordBevestiging
     * 
     * @exception AccountException
     */
    public void registreerSpeler(String naam, String voornaam, String gebruikersNaam, String wachtwoord, String wachtwoord2) {
        if (!wachtwoord.equals(wachtwoord2)) { throw new AccountException(rb.getString("wachtwoordenkomennietovereen")); }

        Speler nieuweSpeler = new Speler(naam, voornaam, gebruikersNaam, wachtwoord, false);
        setSpeler(nieuweSpeler);
        spelerRepository.voegSpelerToe(nieuweSpeler);
    }
    
    /**
     * Geeft de namen van alle spellen terug
     * @return Geeft de namen van alle spellen terug
     */
    public String[] geefSpelnamen() {
    	return spelRepository.geefAlleSpelnamen();
    }
    
    /**
     * Kiest een gekozen spel
     * @param Heeft de spelNaam nodig om te kiezen
     */
    public void kiesSpel(String spelNaam) {
    	huidigSpel = spelRepository.kiesSpel(spelNaam);
    }
    
    /**
     * Geeft true terug als het level compleet is, anders false
     * @param Heeft de levelNaam nodig om te kijken als het level voltooid is
     * 
     * @return Geeft true terug als het level compleet is, anders false
     */
    public boolean voltooiSpelbord(String levelNaam) {
    	return huidigSpel.isLevelCompleet(levelNaam);
    }
    
    /**
     * Verplaatst een speler
     * @param Heeft richting nodig om de speler te kunnen verplaatsen
     */
    public void verplaatsSpeler(String richting) {
    	huidigSpel.verplaatsSpeler(richting);
    }
    
    /**
     * Geeft het aantal verplaatsingen van dat level terug
     * @param spelNaam om het spel te vinden
     * @param levelNaam om het level te vinden
     * 
     * @return Geeft het aantal verplaatsingen van dat level terug
     */
    public int getAantalVerplaatsingen() {
    	return huidigSpel.getAantalVerplaatsingen();
    }
    
    /**
     * Geeft een 3D String array terug met alle vakken hun vakinfo
     * @param Heeft de levelNaam nodig voor het vinden van alle vakken
     * 
     * @return Geeft een 3D String array terug met alle vakken hun vakinfo
     */
    public String[][] getAlleVakken(String levelNaam) {
    	huidigSpel.setHuidigLevel(levelNaam);
    	Vak[][] vakken = huidigSpel.getAlleVakken();
    	String[][] result = new String[vakken.length * vakken[0].length][4];
    	
    	int intTeller = 0;
    	for (Vak[] rij : vakken) {
    		for (Vak v : rij) {
    			if (v != null)
        			result[intTeller] = v.getVakInfo();
        		intTeller++;	
			}
    	}
    	
    	return result;
    }
    
    /**
     * Maakt een nieuw spel aan
     * @param Heeft de spelNaam nodig
     * 
     * @exception SpelMakenException
     */
    public void maakNieuwSpel(String spelNaam) {
    	if (spelNaam.contains(" "))
    		throw new SpelMakenException(rb.getString("geenspatiesinnaam"));
    	for (String s : spelRepository.geefAlleSpelnamen())
    		if (s.equals(spelNaam))
    			throw new SpelMakenException(rb.getString("naambestaatal"));
    	spelRepository.maakNieuwSpel(spelNaam, huidigeSpeler.getGebruikersnaam());
    	huidigSpel = spelRepository.kiesSpel(spelNaam);
    }
    
    /**
     * Geeft de creatiebevestiging terug
     * @return Geeft de creatiebevestiging terug
     */
    public String getCreatieBevestiging() {
    	return String.format(rb.getString("creatiebevestiging"), huidigSpel.getNaam(), huidigSpel.getAantalSpelborden());
    }
    
    /**
     *  Maakt een nieuw spelbord
     */
    public void maakNieuwSpelbord() {
    	spelRepository.maakNieuwSpelbord(huidigSpel);
    }
    
    /**
     * Stelt de huidige speler in
     * @param Heeft de speler nodig om in te stellen
     */
    private void setSpeler(Speler speler) { this.huidigeSpeler = speler; }
    
    /**
     * Geeft het aantal levels van het huidige spelbord terug
     * @return Geeft het aantal levels van het huidige spelbord terug
     */
    public int getAantalLevels() { return huidigSpel.getAantalSpelborden(); }

    /**
     * Geeft de voltooiSpelbordSummary terug
     * @param Heeft de levelNaam nodig voor het spelbord te vinden
     * 
     * @return Geeft de voltooiSpelbordSummary terug
     */
	public String voltooiSpelbordSummary(String levelNaam) {
		return String.format(rb.getString("voltooispelbordsummary"), huidigSpel.getAantalVoltooideSpelborden(), getAantalLevels());
	}
	
    /**
     * Update een vak in de repository en de database
     * @param xcoord voor de x coordinaat
     * @param ycoord voor de y coordinaat
     * @param nieuwType voor het nieuwe type
     * @param isDoel voor te weten als het vak een doel is
     */
	public void updateVak(int xcoord, int ycoord, String nieuwType, boolean isDoel) {
		huidigSpel.updateVak(xcoord, ycoord, nieuwType, isDoel);
		spelRepository.updateVak(xcoord, ycoord, nieuwType, isDoel, huidigSpel);
	}
	
    /**
     * Geeft het aantal spelborden terug in een string array
     * @param Heeft de spelNaam nodig om een spel te kunnen wijzigen
     * @return Geeft het aantal spelborden terug in een string array
     */
	public String[] wijzigSpel(String spelNaam) {
		kiesSpel(spelNaam);
		int aantalSpelborden = huidigSpel.getAantalSpelborden();
		String[] result = new String[aantalSpelborden];
		
		for (int i = 1; i <= aantalSpelborden; i++)
			result[i-1]= String.valueOf(i);
		
		return result;
	}
	
    /**
     * Update een vak in de repository
     * @param xcoord voor de x coordinaat
     * @param ycoord voor de y coordinaat
     * @param nieuwType voor het nieuwe type
     * @param isDoel voor te weten als het vak een doel is
     * @param spelbordNr om te weten over welk spelbord het gaat
     */
	public void vernieuwVak(int xcoord, int ycoord, String nieuwType, boolean isDoel, String spelbordNr) {
		spelRepository.updateVak(xcoord, ycoord, nieuwType, isDoel, huidigSpel);
	}
	
    /**
     * Geeft het voltooispel bericht terug
     * @return Geeft het voltooispel bericht terug
     */
	public String voltooiWijzigSpel() {
		return String.format(rb.getString("voltooiwijzigspelsummary"), huidigSpel.getNaam());	
	}

    /**
     * Geeft het verwijderbevestigingsbericht terug
     * @param Heeft de levelNaam nodig om het level te vinden
     * 
     * @return Geeft het verwijderbevestigingsbericht terug
     */
	public String verwijderLevel(String levelNaam) {
		spelRepository.verwijderLevel(huidigSpel, levelNaam);
		huidigSpel.verwijderLevel(levelNaam);
		
		return String.format(rb.getString("verwijderlevelbevestiging"), levelNaam, huidigSpel.getNaam()); 
	}
	
    /**
     * Verwijderd spel en geeft een melding terug
     * @param Heeft de spelNaam nodig om het spel te vinden
     * 
     * @return Geeft het verwijderbevestigingsbericht terug
     */
	public String verwijderSpel(String spelNaam) {
		spelRepository.verwijderSpel(spelNaam);
		
		return String.format(rb.getString("verwijderspelbevestiging"), spelNaam);
	}
	
	/**
	 * ResourceBundle updaten
	 */
	public void updateLanguage() {
		rb = ResourceBundle.getBundle("languages/resource_bundle", Locale.getDefault());
	}
	
	/**
	 * Geeft terug of de input null, whitespace of empty is
	 * @param String
	 * 
	 * @return Geeft terug of de input null, whitespace of empty is
	 */
	public boolean isNullOrWhitespaceOrEmpty(String s) {
		return s == null || isWhitespace(s) || s.length() == 0;
	}
	
	private boolean isWhitespace(String s) {
	    int length = s.length();
	    if (length > 0) {
	        for (int i = 0; i < length; i++) {
	            if (!Character.isWhitespace(s.charAt(i))) {
	                return false;
	            }
	        }
	        return true;
	    }
	    return false;
	}
	
	/**
	 * Geeft de gebruikersnaam van de huidige speler terug.
	 * @return Geeft de gebruikersnaam van de huidige speler terug.
	 */
	public String geefGebruikersnaam() {
		return huidigeSpeler.getGebruikersnaam();
	}
	
	/**
	 * Toont een nieuwe stage en geeft deze terug.
	 * @param resourceFile in de package gui
	 * @param FX Controller
	 * @param Primary stage
	 * @param Stage width
	 * @param Stage height
	 * 
	 * @return geeft de huidige stage terug
	 */
	public Stage initialize(String resourceFile, Object controller, Stage pstage, double width, double height) {
		try {
		    FXMLLoader fx = new FXMLLoader(getClass().getResource("/gui/" + resourceFile));
			fx.setController(controller);
			
		    Parent root = fx.load();
			Scene scene = new Scene(root, width, height);
			pstage.setScene(scene);
			pstage.resizableProperty().setValue(false);
			pstage.getIcons().add(new Image(getClass().getResource("/icoico.png").toString()));
			pstage.show();
			pstage.setTitle("Sokoban");
			
	        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
	        pstage.setX((primScreenBounds.getWidth() - pstage.getWidth()) / 2);
	        pstage.setY((primScreenBounds.getHeight() - pstage.getHeight()) / 2);
	        
	        return pstage;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Initialiseert de images van het spel
	 * 0 muur
	 * 1 veld
	 * 2 speler
	 * 3 kist
	 * 4 doel
	 * @return Geeft een image arraylist terug met alle images van het spel.
	 */
	public Image[] initImages() {
		Image[] images = new Image[5];
		
		images[0] = new Image("https://www.beautycolorcode.com/666a6d.png", 40, 40, false, false);
		images[1] = new Image("https://sherwin.scene7.com/is/image/sw/color-swatch?_tparam_size=250,250&layer=comp&_tparam_color=C2C0BB", 40, 40, false, false);
		//images[2] = new Image("https://miro.medium.com/max/400/1*XAksToqI3TyMLhcszTCmhg.png", 40, 40, false, false);
		images[2] = new Image(getClass().getResource("/mannetje.png").toString(), 40, 40, false, false);
		images[3] = new Image("http://pixelartmaker.com/art/200ea90c01a0a17.png", 40, 40, false, false);
		images[4] = new Image("https://ak.picdn.net/shutterstock/videos/2913229/thumb/3.jpg", 40, 40, false, false);
		
		return images;
	}
	
	/**
	 * Toont een alert op het scherm met showAndWait()
	 * @param Header text
	 * @param Text
	 * @param alertType
	 */
	public void showAlert(String header, String context, AlertType alertType) {
		Alert a = new Alert(alertType);
		a.setTitle("Sokoban");
		a.setHeaderText(header);
		a.setContentText(context);
		a.showAndWait();
	}
	
	/**
	 * Herstart het opgegeven level
	 * @param levelNaam
	 */
	public void restartLevel(String levelNaam) {
		huidigSpel = spelRepository.restartLevel(huidigSpel.getNaam(), levelNaam);
	}
	
	
	/**
	 * Checkt of het wachtwoord geldig is
	 * @param wachtwoord
	 * 
	 * @return Geeft true terug als het een geldig wachtwoord is
	 */
	public boolean checkPassword(String str) {
	    char ch;
	    boolean hoofdletterFlag = false;
	    boolean kleineletterFlag = false;
	    boolean nummerFlag = false;
	    for(int i=0;i < str.length();i++) {
	        ch = str.charAt(i);
	        if( Character.isDigit(ch)) {
	        	nummerFlag = true;
	        }
	        else if (Character.isUpperCase(ch)) {
	        	hoofdletterFlag = true;
	        } else if (Character.isLowerCase(ch)) {
	        	kleineletterFlag = true;
	        }
	        if(nummerFlag && hoofdletterFlag && kleineletterFlag)
	            return true;
	    }
	    return false;
	}
}