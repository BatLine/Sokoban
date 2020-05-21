package cui;

import java.util.ResourceBundle;

import domein.DomeinController;
import exceptions.AccountException;

/**
 * 
 * De applicatie voor het inloggen
 * 
 * @author Gilles De Pessemier, Rune De Bruyne, Aaron Everaert, Chiel Meneve
 *
 */
public class loginApp extends consoleApp {
	private final DomeinController domainController;
	private ResourceBundle rb;
	
	public loginApp(DomeinController dc) {
		domainController = dc;
		rb = domainController.getResourceBundle();
	}
	
	public void login() {
		boolean loginSucces = false;
		
		do {
			try {
				domainController.aanmeldenSpeler(getUserName(), getPassword());
				//inloggen gelukt
				System.out.println(rb.getString("inlogbevestiging"));
				loginSucces = true;
			} catch (AccountException ex) {
				System.out.println(ex.getMessage());
			}
			catch (Exception ex) {
				System.out.println(rb.getString("inlogmislukt"));
			}
		} while (!loginSucces);
	}
}
