package cui;

import java.util.ResourceBundle;

import domein.DomeinController;
import exceptions.NietGevondenException;

/**
 * 
 * De applicatie voor het toevoegen van een nieuw level
 * 
 * @author Gilles De Pessemier, Rune De Bruyne, Aaron Everaert, Chiel Meneve
 *
 */
public class voegLevelToeApp extends consoleApp {
	private final DomeinController dc;
	private ResourceBundle rb;
	
	public voegLevelToeApp(DomeinController dc) {
		this.dc = dc;
		rb = dc.getResourceBundle();
	}
	
	public void voegNieuwLevelToe() {
		String[][] vakken = new String[0][0];
		try {
			System.out.println(rb.getString("spelbordtoevoegen"));
			dc.maakNieuwSpelbord();
			System.out.println(rb.getString("spelbordtoegevoegd"));
			int aantal = dc.getAantalLevels();
			if (aantal == 0) { aantal = 1; }
			vakken = dc.getAlleVakken(String.valueOf(aantal));
		} catch (NietGevondenException ex) {
			System.out.println(ex.getMessage());
			return;
		} catch (Exception e) {
			System.out.println(rb.getString("spelbordtoevoegenfout"));
			System.out.println(e.getMessage());
			return;
		}
		
		try {
			String[][] spelbord = vulSpelbord(vakken);
			tekenLevel(spelbord);
		} catch (NietGevondenException ex) {
			System.out.println(ex.getMessage());
		} catch (Exception e) {
			System.out.println(rb.getString("spelbordophalenfout"));
		}
		
		updatenLevel(null, null);
	}
}