package cui;


import java.util.ResourceBundle;
import java.util.Scanner;

import domein.DomeinController;

/**
 * 
 * De applicatie voor het wijzigen van een bestaand spel
 * 
 * @author Gilles De Pessemier, Rune De Bruyne, Aaron Everaert, Chiel Meneve
 *
 */
public class wijzigSpel extends consoleApp{
	private final DomeinController domainController;
	private ResourceBundle rb;
	
	public wijzigSpel(DomeinController dc) {
		domainController = dc;
		rb = dc.getResourceBundle();
	}
	
	public void startWijzigenSpel() {
		Boolean doorgaan, stoppen;
		Scanner scanner = new Scanner(System.in);
		String spelNaam, levelNaam;
		String[] spelNamen = domainController.geefSpelnamen(); 
		
		for( String s:spelNamen)
			System.out.println(s);
		
		do {
			doorgaan = false;
			System.out.print(rb.getString("vraagkeuze"));
			spelNaam = scanner.next();
			for(String _spelNaam : spelNamen)
				if (_spelNaam.equals(spelNaam))
					doorgaan = true;
			
		} while (!doorgaan);
		
		domainController.kiesSpel(spelNaam);
		String[] levelNamen = domainController.wijzigSpel(spelNaam);
		
		do {
			stoppen = false;
			String keuze;
			System.out.println("\n" + rb.getString("wijzigspelmenu"));
			do {
				System.out.print(rb.getString("vraagkeuze"));
				keuze = scanner.next();
			} while ((!keuze.equals("1")) && (!keuze.equals("0")) && (!keuze.equals("2")));
			
			if (keuze.equals("0")) {
				stoppen = true;
			} else if (keuze.equals("2")) {
				stoppen = true;
				System.out.println(domainController.verwijderSpel(spelNaam));
				//en nog bijhorende levels
			} else {
				System.out.println(rb.getString("levels"));
				for(String s:levelNamen )
					System.out.println(s);
				
				do {
					doorgaan = false;
					System.out.print(rb.getString("vraagkeuze"));
					levelNaam = scanner.next();
					for(String _levelNaam:levelNamen)
						if (_levelNaam.equals(levelNaam))
							doorgaan = true;
					
				} while (!doorgaan);
				
				System.out.println("\n" + rb.getString("wijzigspelbordmenu"));
				do {
					System.out.print(rb.getString("vraagkeuze"));
					keuze = scanner.next();
				} while ((!keuze.equals("1")) && (!keuze.equals("2")) && (!keuze.equals("3")));
				
				switch (keuze) {
				case "1":
					if (domainController.getAantalLevels() == 0) {
						System.out.println(rb.getString("geenspelborden"));
					} else {
						domainController.kiesSpel(spelNaam);
						new wijzigSpelbord(domainController).startWijzigenSpelbord(spelNaam, levelNaam);
					}
					break;
				case "2":
					System.out.println(domainController.verwijderLevel(levelNaam));
					break;
				case "3":
					new voegLevelToeApp(domainController).voegNieuwLevelToe();
					break;
				}
				
			}
		} while (!stoppen);
		
		System.out.println(domainController.voltooiWijzigSpel());
	}
}
