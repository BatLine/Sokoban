package domein;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import exceptions.GebruikersnaamInGebruikException;
import exceptions.VerkeerdWachtwoordException;
import persistentie.SpelerMapper;

/**
 * 
 * De klasse met alle data van alle spelers
 * 
 * @author Gilles De Pessemier, Rune De Bruyne, Aaron Everaert, Chiel Meneve
 *
 */
public class SpelerRepository {
	private final SpelerMapper mapper;
	private List<Speler> spelers;
    private ResourceBundle rb = ResourceBundle.getBundle("languages/resource_bundle", Locale.getDefault());

    /**
     * Constructor voor SpelerRepository
     */
    public SpelerRepository() {
        mapper = new SpelerMapper();
        vulSpelers();
    }
    
    /**
     * Haalt de spelers uit de databse
     */
    private void vulSpelers() {
    	spelers = mapper.geefSpelers();
    }
    
    /**
     * Voegt een speler toe aan de database
     * @param nieuwe Speler
     * 
     * @exception GebruikersnaamInGebruikException
     */
    public void voegSpelerToe(Speler s) {
       if (bestaatSpeler(s.getGebruikersnaam()))
            throw new GebruikersnaamInGebruikException(rb.getString("gebruikersnaambestaatal"));
       
       mapper.voegToe(s);
       vulSpelers();
    }

    /**
     * Checkt of de speler al bestaat
     * @param gebruikersNaam
     * 
     * @return Geeft true terug als de speler al bestaat
     */
    private boolean bestaatSpeler(String gebruikersNaam){
    	for (Speler s : spelers) {
    		if (s.getNaam().equals(gebruikersNaam))
    			return true;
    	}
        return false;
    }
    
    /**
     * Geeft de speler terug
     * @param gebruikersNaam
     * @param wachtwoord
     * 
     * @return Geeft de speler terug
     * 
     * @exception VerkeerdWachtwoordException
     */
    public Speler geefSpeler(String gebruikersNaam, String wachtwoord) {
    	for (Speler s : spelers) {
    		if (s.getNaam().equals(gebruikersNaam)) {
    			if (s.getWachtwoord().equals(wachtwoord))
    				return s;
    			throw new VerkeerdWachtwoordException(String.format(rb.getString("wachtwoordkloptniet"), gebruikersNaam));
    		}
    	}
    	
        return null;
    }
}