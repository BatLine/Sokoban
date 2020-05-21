package cui;

import java.util.ResourceBundle;
import java.util.Scanner;

import domein.DomeinController;
import exceptions.LevelNietMeerMogelijkException;
import exceptions.NietGevondenException;

/**
 * 
 * De applicatie voor het spelen van een spel
 * 
 * @author Gilles De Pessemier, Rune De Bruyne, Aaron Everaert, Chiel Meneve
 *
 */
public class speelSpelApp extends consoleApp {
	private final DomeinController dc;
	private ResourceBundle rb;
	
	public speelSpelApp(DomeinController dc) {
		this.dc = dc;
		rb = dc.getResourceBundle();
	}

	public void start() {
		int currentLevel = 0;
		String levelNaam;
		dc.kiesSpel(kiesSpel());
		
		do {
			currentLevel++;
			levelNaam = String.valueOf(currentLevel);			
			tekenLevel(vulSpelbord(dc.getAlleVakken(levelNaam)));
			
			try {
				do {
					dc.verplaatsSpeler(zetMenu());
					tekenLevel(vulSpelbord(dc.getAlleVakken(levelNaam)));
					System.out.println(rb.getString("aantalverplaatsingen") + ": " + dc.getAantalVerplaatsingen());
				} while (!dc.voltooiSpelbord(levelNaam));
				System.out.println(dc.voltooiSpelbordSummary(levelNaam));
			} catch (NietGevondenException ex) {
				System.out.println(ex.getMessage());
			} catch (LevelNietMeerMogelijkException ex) {
				System.out.println(rb.getString("onmogelijklevel"));
			} catch (Exception e) {
				System.out.println(rb.getString("foutbijhetspel"));
			}
		
		} while (!levelNaam.equals(String.valueOf(dc.getAantalLevels())));
	}
	
	private String kiesSpel() {
		String keuze;
		Scanner s = new Scanner(System.in);
		
		System.out.println(rb.getString("spellen"));
		for (String spel : dc.geefSpelnamen()) {
			System.out.println(spel);
		}
		
		System.out.print(rb.getString("vraagspelnaam"));
		keuze = s.next();
		return keuze;
	}
	
	private String zetMenu() {
		Scanner s = new Scanner(System.in);
		String keuze;
		
		do {
			System.out.println(rb.getString("beweegmenu"));
			keuze = s.next();
		} while ((!keuze.equals("1")) && (!keuze.equals("2")) && (!keuze.equals("3")) && (!keuze.equals("4")));
		
		String keuzeResult=null;
		switch (keuze) {
		case "1":
			keuzeResult = "omhoog";
			break;
		case "2":
			keuzeResult = "omlaag";
			break;
		case "3":
			keuzeResult = "links";
			break;
		case "4":
			keuzeResult = "rechts";
			break;
		}
		
		return keuzeResult;
	}
}
