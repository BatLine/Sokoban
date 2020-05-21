package domein;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import exceptions.NietGevondenException;
import persistentie.SpelMapper;

/**
 * 
 * De klasse met alle data van alle spellen
 * 
 * @author Gilles De Pessemier, Rune De Bruyne, Aaron Everaert, Chiel Meneve
 *
 */
public class SpelRepository {
	private final SpelMapper spelMapper;
	private List<Spel> alleSpellen;
    private ResourceBundle rb = ResourceBundle.getBundle("languages/resource_bundle", Locale.getDefault());

    /**
     * Constructor voor SpelRepository
     */
	public SpelRepository() {
		spelMapper = new SpelMapper();
		updateAlleSpellen();
	}
	
	/**
	 * Geeft alle spelnamen terug in een array
	 * @return Geeft alle spelnamen terug in een array
	 */
	public String[] geefAlleSpelnamen() {
		String[] spelNamen = new String[alleSpellen.size()];
		
		int intTeller = 0;
		for (Spel s : alleSpellen) {
			spelNamen[intTeller] = s.getNaam();
			intTeller++;
		}
		return spelNamen;
	}
	
	/**
	 * Haalt alle spellen uit de database
	 */
	void updateAlleSpellen() {
		alleSpellen = spelMapper.getAlleSpellen();
	}
	
	/**
	 * maakt een nieuw spel aan
	 * @param spelNaam
	 * @param Creatorname
	 */
	public void maakNieuwSpel(String spelNaam, String username) {
		spelMapper.maakNieuwSpel(spelNaam, username);
		updateAlleSpellen();
	}

	/**
	 * Maakt een nieuw spelbord aan
	 * @param Spel
	 */
	public void maakNieuwSpelbord(Spel s) {
		spelMapper.maakNieuwSpelbord(s.getAantalSpelborden(), s.getNaam());
		for (int x = 0; x < 10; x++) {
			for (int y = 0; y < 10; y++) {
				
				//leeg vierkant maken tussen alle muren & speler in midden plaatsen
				String icoontje = "x";
				if ((x == 4) || (x == 5) || (x == 6))
					if ((y == 4) || (y == 5) || (y == 6))
						icoontje = " ";
				if ((x == 5) && (y == 5))
					icoontje = "S";
				//
				
				spelMapper.maakNieuwVak(x, y, icoontje, s.getNaam(), String.valueOf(s.getAantalSpelborden()+1));
			}
		}
		
		s.setLevels(spelMapper.getAlleLevels(s.getNaam()));
		s.setAlleVakken(String.valueOf(s.getAantalSpelborden()), spelMapper.getAlleVakken(s.getNaam(), String.valueOf(s.getAantalSpelborden())));
	}
	
	/**
	 * Stelt het huidige spel in
	 * @param spelnaam
	 * 
	 * @return Geeft het huidige spel terug
	 * 
	 * @exception NietGevondenException
	 */
	public Spel kiesSpel(String spelnaam) {
		Spel huidigSpel;
		
		for (Spel s : alleSpellen) {
			if (s.getNaam().equals(spelnaam)) {
				huidigSpel = s;
				huidigSpel.setLevels(spelMapper.getAlleLevels(huidigSpel.getNaam()));
				for (int i = 1; i <= huidigSpel.getAantalSpelborden(); i++) {
					huidigSpel.setAlleVakken(String.valueOf(i), spelMapper.getAlleVakken(huidigSpel.getNaam(), String.valueOf(i)));
				}
				
				return s;
			}
		}
		throw new NietGevondenException(rb.getString("spelnietgevonden"));
	}

	/**
	 * Update een vak
	 * @param xcoord van het spel
	 * @param ycoord van het spel
	 * @param nieuwType van het spel
	 * @param is het een doel
	 * @param huidigSpel
	 */
	public void updateVak(int xcoord, int ycoord, String nieuwType, boolean isDoel, Spel huidigSpel) {
		spelMapper.updateVak(xcoord, ycoord, nieuwType, isDoel, huidigSpel.getNaam(), huidigSpel.getHuidigLevel().getLevelNaam());
	}

	/**
	 * Verwijder level uit de database
	 * @param huidigSpel
	 * @param levelNaam
	 */
	public void verwijderLevel(Spel huidigSpel, String levelNaam) {
		for (Spel s : alleSpellen) {
			if (s.getNaam().equals(huidigSpel.getNaam()))
				s.verwijderLevel(levelNaam);
		}
		spelMapper.verwijderLevel(huidigSpel.getNaam(), levelNaam);
	}

	/**
	 * Verwijder spel uit de databse
	 * @param spelNaam
	 */
	public void verwijderSpel(String spelNaam) {
		List<Spel> tempList = new ArrayList<Spel>();
		
		for (Spel s : alleSpellen)
			if (!s.getNaam().equals(spelNaam))
				tempList.add(s);
		
		alleSpellen = tempList;
		
		spelMapper.verwijderSpel(spelNaam);
	}

	/**
	 * Herstart het opgegeven level in het opgegeven spel
	 * @param spelNaam
	 * @param levelNaam
	 * 
	 * @return Geeft het aangepaste spel terug
	 */
	public Spel restartLevel(String spelNaam, String levelNaam) {
		Spel s = spelMapper.getEenSpel(spelNaam);
		s = kiesSpel(spelNaam);
		
		for (Spel _s : alleSpellen)
			if (_s.getNaam().equals(spelNaam))
				_s = s;
			
		List<Level> levels = s.geefAlleLevels();
		int intTeller = 0;
		
		for (Level l : levels)
			if (intTeller < (Integer.valueOf(levelNaam) - 1))
				l.setIsLevelCompleet(true);
		
		s.setLevels(levels);
		return s;
	}
}