package main;

import java.util.Locale;
import java.util.Scanner;

import cui.consoleApp;
import domein.DomeinController;
import gui.Home;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * 
 * De klasse voor het starten van een gui of cui applicatie
 * 
 * @author Gilles De Pessemier, Rune De Bruyne, Aaron Everaert, Chiel Meneve
 *
 */
public class StartUp extends Application {
    @Override
    public void start(Stage primaryStage)
    {
		Locale enLocale = new Locale("en","US");
		Locale nlLocale = new Locale("nl", "BE");
		Locale frLocale = new Locale("fr", "FR");
		Locale.setDefault(nlLocale);
		
        DomeinController controller = new DomeinController();

        Scanner s = new Scanner(System.in);
        String keuze;
        do {
        	System.out.println("Menu:\n1) Console\n2) GUI");
        	keuze = s.next();
		} while ((!keuze.equals("1")) && (!keuze.equals("2")));
        
        
        switch (keuze) {
		case "1":
	        do {
	        	System.out.println("\nTaal:\n1) NL\n2) FR\n3) EN");
	        	keuze = s.next();
			} while ((!keuze.equals("1")) && (!keuze.equals("2") && (!keuze.equals("3"))));
	        System.out.println("");
	        
			switch (keuze) {
			case "1":
				Locale.setDefault(nlLocale);
				break;
			case "2":
				Locale.setDefault(frLocale);
				break;
			case "3":
				Locale.setDefault(enLocale);
				break;
			}
			controller.updateLanguage();
			consoleApp ca = new consoleApp(controller);
	        ca.start();
			break;
		case "2":
			Home home = new Home(controller, primaryStage);
			break;
		}
        
    }

    public static void main(String... args)
    {
        Application.launch(StartUp.class, args);
    }
}