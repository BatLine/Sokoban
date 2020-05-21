package cui;

import java.util.ResourceBundle;
import java.util.Scanner;

import domein.DomeinController;
import exceptions.NietGevondenException;
import exceptions.SpelMakenException;

/**
 * 
 * De applicatie voor het maken van een nieuw spel
 * 
 * @author Gilles De Pessemier, Rune De Bruyne, Aaron Everaert, Chiel Meneve
 *
 */
public class maakSpelApp {
	private final DomeinController dc;
	private ResourceBundle rb;
	
	public maakSpelApp(DomeinController dc) {
		this.dc = dc;
		rb = dc.getResourceBundle();
	}
	
	public void maakSpel() {
		Scanner s = new Scanner(System.in);
		String spelNaam;
		
		try {
			do {
				System.out.print(rb.getString("vraagspelnaam"));
				spelNaam = s.next(); 
			} while ((spelNaam == null) || (spelNaam.equals("")));
			
			dc.maakNieuwSpel(spelNaam);
			System.out.printf(rb.getString("nieuwspelisaangemaakt"), spelNaam);
		} catch (SpelMakenException ex) {
			System.out.println(ex.getMessage());
		} catch (NietGevondenException ex) {
			System.out.println(ex.getMessage());
		} catch (Exception ex) {
			System.out.println(rb.getString("spelmakenfout"));
		}
	}
}