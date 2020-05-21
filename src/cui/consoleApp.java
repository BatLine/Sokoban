package cui;

import java.util.ResourceBundle;
import java.util.Scanner;

import domein.DomeinController;
/**
 * 
 * De hoofd console applicatie
 * 
 * @author Gilles De Pessemier, Rune De Bruyne, Aaron Everaert, Chiel Meneve
 *
 */
public class consoleApp {
	private final DomeinController domainController;
	private ResourceBundle rb;
	
	public consoleApp(DomeinController controller) {
		this.domainController = controller;
		rb = domainController.getResourceBundle();
	}
	
	public consoleApp() {
		this(new DomeinController());
	}
	
	public String getUserName() {
		Scanner s = new Scanner(System.in);
		String username;
		
		do {
			System.out.print(rb.getString("vraaggebruikersnaam"));
			username = s.next();
			if (username.length() < 8) {
				System.out.println(rb.getString("aanmakengebruikersnaamfout"));
				username = "";
			}
		} while ((username == null) || (username.equals("")));
		
		return username;
	}
	public String getPassword() {
		Scanner s = new Scanner(System.in);
		String password;
		
		do {
			System.out.print(rb.getString("vraagwachtwoord"));
			password = s.next(); 
			if (!domainController.checkPassword(password)) {
				System.out.println(rb.getString("aanmakenwachtwoordfout"));
				password = "";
			}
		} while ((password == null) || (password.equals("")));
		
		return password;
	}
	
	public void start() {
		printTitel();
		String keuze = showStartMenu(), keuze2;
		boolean stoppen = false, stoppen2 = false;
		Scanner s = new Scanner(System.in);
		
		switch (keuze) {
		case "1":
			new registreerApp(domainController).registreer();
			break;
		case "2":
			new loginApp(domainController).login();
			break;
		case "0":
			System.exit(0);
			break;
		}
		
		do {
			keuze = showKeuzeMenu();
			switch (keuze) {
			case "1":
				new maakSpelApp(domainController).maakSpel();	
				do {
					new voegLevelToeApp(domainController).voegNieuwLevelToe();
					do {
						System.out.println(rb.getString("bordtoevoegenmenu"));
						keuze2 = s.next();
					} while ((!keuze2.equals("1")) && (!keuze2.equals("2")));
					
					if (keuze2.equals("1")) {
						
					} else if (keuze2.equals("2")) {
						stoppen = true;
					}
				} while (!stoppen);
				System.out.println(domainController.getCreatieBevestiging());
				break;
			case "2":
				new speelSpelApp(domainController).start();
				break;
			case "3": 
				new wijzigSpel(domainController).startWijzigenSpel();
				break;
			case "0":
				stoppen2 = true;
				break;
			}
		} while (!stoppen2);
		
		System.out.println(rb.getString("vaarwel"));
		System.exit(0);
	}

	private void printTitel()
	{
		System.out.println(rb.getString("legende"));
	}
	
	private String showStartMenu() {
		Scanner s = new Scanner(System.in);
		String keuze = "";
		
		System.out.println(rb.getString("startmenu"));
		do {
			System.out.println(rb.getString("vraagkeuze"));
			keuze = s.next();
		} while ((!keuze.equals("1")) && (!keuze.equals("2")) && (!keuze.equals("0")));
		
		return keuze;
	}
	
	private String showKeuzeMenu() {
		Scanner s = new Scanner(System.in);
		String keuze = "";
		
		if (domainController.isAdmin()) {
			do {
				System.out.println(rb.getString("keuzemenuadmin"));
				keuze = s.next();
			} while ((!keuze.equals("1")) && (!keuze.equals("2")) && (!keuze.equals("3")) && (!keuze.equals("0")));
		} else {
			do {
				System.out.println(rb.getString("keuzemenunonadmin"));
				keuze = s.next();
			} while ((!keuze.equals("1")) && (!keuze.equals("0")));
			if (keuze.equals("1"))
				keuze = "2";
		}
		
		return keuze;
	}

	public String[][] vulSpelbord(String[][] vakken) {
		String[][] spelbord = new String[10][10];
		
		for (String[] vak : vakken) {
			String icoontje;
			
			switch (vak[0]) {
			case "muur":
				icoontje = "x";
				break;
			case "veld":
				icoontje = " ";
				break;
			case "kist":
				icoontje = "O";
				break;
			case "speler":
				icoontje = "S";
				break;
			default:
			case "none":
				icoontje = "/";
				break;
			}
			if ((Boolean.valueOf(vak[3])) && (!icoontje.equals("S")) && (!icoontje.equals("O"))) { //speler/kist tonen ipv doel als speler er op staat
				icoontje = "H";
			}
			
			spelbord[Integer.parseInt(vak[1])][Integer.parseInt(vak[2])] = icoontje;
		}
		
		return spelbord;
	}
	
	public void tekenLevel(String[][] spelbord) {
		System.out.print("\n  0 1 2 3 4 5 6 7 8 9  \n#######################\n");
		int intTeller=0;
		
		for (String[] rij : spelbord) {
			System.out.print(intTeller + "#");
			for (String vak : rij) {
				System.out.print(vak + " ");
			}
			System.out.print("#\n");
			intTeller++;
		}
		System.out.println("#######################\n");
	}
	
	public void updatenLevel(String spelNaam, String levelNaam) {
		String keuze, xcoord, ycoord;
		Scanner s = new Scanner(System.in);
		boolean klaar = false;
		if (spelNaam != null)
			domainController.kiesSpel(spelNaam);
		
		do {
			do {
				System.out.println(rb.getString("wijzigveldmenu"));
				keuze = s.next();
			} while ((!keuze.equals("1")) && (!keuze.equals("0")));
			
			switch (keuze) {
			case "1":
				System.out.print(rb.getString("geefxcoord"));
				xcoord = s.next();
				System.out.print(rb.getString("geefycoord"));
				ycoord = s.next();
				String nieuwType;
				do {
					System.out.println(rb.getString("veldaanpassenmenu"));
					nieuwType = s.next();
				} while ((!nieuwType.equals("1")) && (!nieuwType.equals("2")) && (!nieuwType.equals("3")) && (!nieuwType.equals("4")));
				//update veld
				try {
					boolean isDoel = false;
					if (nieuwType.equals("1")) {
						String keuzeDoel;
						System.out.println(rb.getString("vraagdoelmenu"));
						do {
							System.out.println(rb.getString("vraagkeuze"));
							keuzeDoel = s.next();
						} while ((!keuzeDoel.equals("1")) && (!keuzeDoel.equals("2")));
						if (keuzeDoel.equals("1")) {
							isDoel = true;
						} else {
							isDoel = false;
						}
					}
					
					switch (nieuwType) { //muur, veld, speler, kist
					case "1":
						nieuwType = "veld";
						break;
					case "2":
						nieuwType = "muur";
						break;
					case "3":
						nieuwType = "speler";
						break;
					case "4":
						nieuwType = "kist";
						break;
					}
					domainController.updateVak(Integer.parseInt(xcoord), Integer.parseInt(ycoord), nieuwType, isDoel);
					tekenLevel(vulSpelbord(domainController.getAlleVakken(levelNaam)));
				} catch (IllegalArgumentException e) {
					System.out.println(rb.getString("foutupdatenveld"));
				}
				break;
			case "0":
				klaar = true;
				break;
			}
		} while (!klaar);
	}
}