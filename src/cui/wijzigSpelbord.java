package cui;

import domein.DomeinController;

/**
 * 
 * De applicatie voor het wijzigen van een bestaand spelbord
 * 
 * @author Gilles De Pessemier, Rune De Bruyne, Aaron Everaert, Chiel Meneve
 *
 */
public class wijzigSpelbord extends consoleApp {
	private final DomeinController domainController;
	
	public wijzigSpelbord(DomeinController dc) {
		domainController = dc;
	}

	public void startWijzigenSpelbord(String spelNaam, String levelNaam) {
		tekenLevel(vulSpelbord(domainController.getAlleVakken(levelNaam)));
		updatenLevel(spelNaam, levelNaam);
	}
}
