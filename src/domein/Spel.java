package domein;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import exceptions.NietGevondenException;

/**
 * 
 * De klasse met alle data van een spel
 * 
 * @author Gilles De Pessemier, Rune De Bruyne, Aaron Everaert, Chiel Meneve
 *
 */
public class Spel
{
    private ResourceBundle rb = ResourceBundle.getBundle("languages/resource_bundle", Locale.getDefault());
	private String spelNaam;
	private Speler creator;
	private Level huidigLevel;
	private List<Level> levels;
	
	/**
	 * Lege constructor voor Spel
	 */
	public Spel() { }
	
	/**
	 * Constructor voor Spel met 1 prameter
	 * @param spelNaam
	 */
	public Spel(String spelNaam) {
		this.spelNaam = spelNaam;
		levels = new ArrayList<Level>();
	}
	
	/**
	 * Stelt het huidige level in op basis van de levelNaam
	 * @param levelNaam
	 */
	public void setHuidigLevel(String levelNaam) {
		for (Level level : levels)
			if (level.getLevelNaam().equals(levelNaam))
				huidigLevel = level;
	}
	
	/**
	 * Geeft het huidige spel terug
	 * @return Geeft het huidige spel terug
	 */
	public Spel getSpel() {
		return this;
	}
	
	/**
	 * Geeft de creator terug
	 * @return Geeft de creator terug
	 */
	public Speler getCreator() {
		return creator;
	}
	
	/**
	 * Stelt de creator in
	 * @param Speler
	 */
	public void setCreator(Speler creator) {
		this.creator = creator;
	}
	
	/**
	 * Geeft het huidige level terug
	 * @return Geeft het huidige level terugs
	 */
	public Level getHuidigLevel() {
		return huidigLevel;
	}
	
	/**
	 * Geeft de spelNaam terug
	 * @return Geeft de spelNaam terug
	 */
	public String getNaam() {
		return spelNaam;
	}

	/**
	 * Geef true terug als het level completed is
	 * @param levelNaam
	 * @return Geef true terug als het level completed is
	 */
	public boolean isLevelCompleet(String levelNaam) {
		for (Level l : levels) {
			if (l.getLevelNaam().equals(levelNaam)) {
				return l.isLevelCompleet();
			}
		}
		
		throw new NietGevondenException(rb.getString("spelbordnietgevonden"));
	}

	/**
	 * Verplaatst de speler indien mogelijk
	 * @param richting naar waar de speler moet
	 * 
	 * @exception NietGevondenException
	 */
	public void verplaatsSpeler(String richting) {
		if (huidigLevel != null)
		{
			huidigLevel.verplaatsSpeler(richting);
			return;
		}
		
		throw new NietGevondenException(rb.getString("spelbordnietgevonden"));
	}

	/**
	 * Geeft het aantal verplaatsingen terug
	 * @return Geeft het aantal verplaatsingen terug
	 * 
	 * @exception NietGevondenException
	 */
	public int getAantalVerplaatsingen() {
		if (huidigLevel != null)
			return huidigLevel.getAantalVerplaatsingen();
		
		throw new NietGevondenException(rb.getString("spelbordnietgevonden"));
	}

	/**
	 * Geeft alle vakken terug in een 2D array
	 * @return Geeft alle vakken terug in een 2D array
	 * 
	 * @exception NietGevondenException
	 */
	public Vak[][] getAlleVakken() {
		try {
			return huidigLevel.getAlleVakken();
		} catch (Exception e) {
			throw new NietGevondenException(rb.getString("spelbordnietgevonden"));
		}
	}
	
	/**
	 * Stelt alle vakken in, in het correcte level
	 * @param levelNaam
	 * @param Lijst met alle vakken
	 * 
	 * @exception NietGevondenException
	 */
	public void setAlleVakken(String levelNaam, List<Vak> alleVakken) {
		for (Level l : levels) {
			if (l.getLevelNaam().equals(levelNaam)) {
				l.setAlleVakken(alleVakken);
				return;
			}
		}
		
		if (alleVakken.size() == 0) //aanmaken spel, haalt vakken op maar zijn er eigenlijk geen
			return;

		throw new NietGevondenException(rb.getString("spelbordnietgevonden"));
	}
	
	/**
	 * Stelt alle levels in
	 * @param Lijst met levels
	 */
	public void setLevels(List<Level> levels) {
		this.levels = levels;
		if (levels.size() > 0)
			setHuidigLevel(levels.get(0).getLevelNaam());
	}
	
	/**
	 * Geeft het aantal spelborden terug
	 * @return Geeft het aantal spelborden terug
	 */
	public int getAantalSpelborden() { return levels.size(); }
	
	/**
	 * Geeft het aantal voltooid spelborden terug
	 * @return Geeft het aantal voltooid spelborden terug
	 */
	public int getAantalVoltooideSpelborden() {
		int intTeller = 0;
		
		for (Level l : levels) {
			if (l.isLevelCompleet())
				intTeller++;
		}
		
		return intTeller;
	}

	/**
	 * Update een vak in alleVakken
	 * @param xcoord van het vak
	 * @param ycoord van het vak
	 * @param nieuwType van het vak
	 * @param is doel of niet
	 */
	public void updateVak(int xcoord, int ycoord, String nieuwType, boolean isDoel) {
		huidigLevel.updateVak(xcoord, ycoord, nieuwType, isDoel);
	}

	/**
	 * Verwijderd een level
	 * @param levelNaam
	 */
	public void verwijderLevel(String levelNaam) {
		List<Level> tempList = new ArrayList<Level>();
		
		for (Level l : levels) {
			if (!l.getLevelNaam().equals(levelNaam))
				tempList.add(l);
		}
		
		levels = tempList;
	}
	
	/**
	 * Geeft alle levels terug
	 * @return Geeft een lijst met alle levels terug
	 */
	public List<Level> geefAlleLevels() {
		return levels;
	}
}