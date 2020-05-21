package cui;

import java.util.ResourceBundle;
import java.util.Scanner;

import domein.DomeinController;
import exceptions.AccountException;
import exceptions.GebruikersnaamInGebruikException;

/**
 * 
 * De applicatie voor het registreren van een nieuwe gebruiker
 * 
 * @author Gilles De Pessemier, Rune De Bruyne, Aaron Everaert, Chiel Meneve
 *
 */
public class registreerApp extends consoleApp {
	private final DomeinController domainController;
	private ResourceBundle rb;
	
	public registreerApp(DomeinController dc) {
		domainController = dc;
		rb = dc.getResourceBundle();
	}
	
	public void registreer() {
		Scanner s = new Scanner(System.in);
		String password2, voornaam, achternaam;
		boolean registrerenSucces = false;
		
		do {
			try {
				System.out.print(rb.getString("vraagvoornaam"));
				voornaam = s.next(); 
				System.out.print(rb.getString("vraagachternaam"));
				achternaam = s.next();
				String username = getUserName();
				String password = getPassword();
				do {
					System.out.print(rb.getString("vraagwachtwoordopnieuw"));
					password2 = s.next(); 
				} while ((password2 == null) || (password2.equals("")));
				
				domainController.registreerSpeler(achternaam, voornaam, username, password , password2);
				System.out.println(rb.getString("registratiebevestiging"));
				registrerenSucces = true;
			} catch (GebruikersnaamInGebruikException ex) {
				System.out.println(ex.getMessage());
			} catch (AccountException ex) {
				System.out.println(ex.getMessage());
			} catch (Exception ex) {
				System.out.println(rb.getString("registratiefout"));
			}
		} while (!registrerenSucces);
	}
}
