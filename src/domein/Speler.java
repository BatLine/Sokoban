package domein;

/**
 * 
 * De klasse met alle data van een speler
 * 
 * @author Gilles De Pessemier, Rune De Bruyne, Aaron Everaert, Chiel Meneve
 *
 */
public class Speler {
	private String gebruikersnaam, wachtwoord, naam, voornaam;
	private boolean admin;
	
	/**
	 * Lege constructor voor Speler
	 */
	public Speler() { }
	
	/**
	 * Constructor voor Speler met 5 parameters
	 * @param Achternaam
	 * @param Voornaam
	 * @param Gebruikersnaam
	 * @param Wachtwoord
	 * @param Is de speler admin of niet
	 */
	public Speler(String naam, String voornaam, String gebruikersnaam, String wachtwoord, boolean admin) {
		this.naam = naam;
		this.voornaam = voornaam;
		this.gebruikersnaam = gebruikersnaam;
		this.admin = admin;
		this.wachtwoord = wachtwoord;
	}

	/**
	 * Geeft de gebruikersnaam terug
	 * @return Geeft de gebruikersnaam terug
	 */
	public String getGebruikersnaam() { return gebruikersnaam; }
	
	/**
	 * Geeft het wachtwoord terug
	 * @return Geeft het wachtwoord terug
	 */
	public String getWachtwoord() { return wachtwoord; }
	
	/**
	 * Geeft de achternaam terug
	 * @return Geeft de achternaam terug
	 */
	public String getNaam() { return naam; }
	
	/**
	 * Geeft de voornaam terug
	 * @return Geeft de voornaam terug
	 */
	public String getVoornaam() { return voornaam; }
	
	/**
	 * Geeft true terug als de speler een admin is
	 * @return Geeft true terug als de speler een admin is
	 */
	public boolean isAdmin() { return admin; }
}