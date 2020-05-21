package domein;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import exceptions.LevelNietMeerMogelijkException;

/**
 * 
 * De klasse met alle data van een level
 * 
 * @author Gilles De Pessemier, Rune De Bruyne, Aaron Everaert, Chiel Meneve
 *
 */
public class Level {
	private String levelNaam;
	private int aantalVerplaatsingen = 0;
	private int spelerXCoord = 5;
	private int spelerYCoord = 5;
	private Vak[][] alleVakken;
	private boolean isLevelCompleet = false;
	private Media movekist = new Media(getClass().getResource("/movekist.mp3").toString());
	
	/**
	 * Level constructor
	 * levelnaam is eigenluk een soort levelnummer, het is een string aangezien het ook mogelijk is met letters te werken indien later gewenst
	 * @param Naam van het level
	 */
	public Level(String levelNaam) {
		this.levelNaam = levelNaam;
		alleVakken = new Vak[10][10];
	}
	
	/**
	 * Geeft de levelnaam terug
	 * @returnGeeft de levelNaam terug
	 */
	public String getLevelNaam() {
		return levelNaam;
	}
	
	/**
	 * Geeft het aantal verplaatsingen terug
	 * @return Geeft het aantal verplaatsingen terug
	 */
	public int getAantalVerplaatsingen() {
		return aantalVerplaatsingen;
	}
	
	/**
	 * Geeft true terug als het level compleet is
	 * @return Geeft true terug als het level compleet is
	 */
	public boolean isLevelCompleet() {
		if (isLevelCompleet)
			return true;
		
		for (Vak[] vRij : alleVakken) {
			for (Vak v : vRij) {
				if ((v.getType().equals("kist")) && (!v.getDoel())) {
					return false;
				}
			}
		}
		
		isLevelCompleet = true;
		return true;
	}

	/**
	 * Verplaatst de speler indien mogelijk
	 * @param De richting naarwaar de speler zich moet verplaatsen
	 */
	public void verplaatsSpeler(String richting) {
		//eerst nog checken als hij wel naar daar kan
		
		int oudX, oudY, tempNieuwX, tempNieuwY;
		oudX = tempNieuwX = spelerXCoord;
		oudY = tempNieuwY = spelerYCoord;
		
		switch (richting) {
		case "links":
			tempNieuwY = spelerYCoord-1;
			break;
		case "rechts":
			tempNieuwY = spelerYCoord+1;
			break;
		case "omhoog":
			tempNieuwX = spelerXCoord-1;
			break;
		case "omlaag":
			tempNieuwX = spelerXCoord+1;
			break;
		default:
			//exception
			break;
		}
		
		if ((tempNieuwX < 0) || (tempNieuwX > 9))
			return;
		if ((tempNieuwY < 0) || (tempNieuwY > 9))
			return;
		if (alleVakken[tempNieuwX][tempNieuwY].getType().equals("muur"))
			return;
		if (alleVakken[tempNieuwX][tempNieuwY].getType().equals("kist"))
		{
			//kist verplaatsen & checken
			int oudKistXCoord, oudKistYCoord, nieuwKistXCoord, nieuwKistYCoord;
			oudKistXCoord = nieuwKistXCoord = tempNieuwX;
			oudKistYCoord = nieuwKistYCoord = tempNieuwY;
			
			switch (richting) {
			case "links":
				nieuwKistYCoord = oudKistYCoord-1;
				break;
			case "rechts":
				nieuwKistYCoord = oudKistYCoord+1;
				break;
			case "omhoog":
				nieuwKistXCoord = oudKistXCoord-1;
				break;
			case "omlaag":
				nieuwKistXCoord = oudKistXCoord+1;
				break;
			default:
				//exception
				break;
			}
			
			if ((nieuwKistXCoord < 0) || (nieuwKistXCoord > 9))
				return;
			if ((nieuwKistYCoord < 0) || (nieuwKistYCoord > 9))
				return;
			if (alleVakken[nieuwKistXCoord][nieuwKistYCoord].getType().equals("muur"))
				return;
			if (alleVakken[nieuwKistXCoord][nieuwKistYCoord].getType().equals("kist"))
				return;
			
			alleVakken[oudKistXCoord][oudKistYCoord].setType("veld");
			alleVakken[nieuwKistXCoord][nieuwKistYCoord].setType("kist");
			
			if (alleVakken[nieuwKistXCoord][nieuwKistYCoord].getDoel()) {
				MediaPlayer mediaPlayer = new MediaPlayer(movekist);
				mediaPlayer.play();
			}
		}
		
		alleVakken[oudX][oudY].setType("veld");
		alleVakken[tempNieuwX][tempNieuwY].setType("speler");
		spelerXCoord = tempNieuwX;
		spelerYCoord = tempNieuwY;
		voegVerplaatsingToe();
		
		if (!isLevelNogMogelijk())
			throw new LevelNietMeerMogelijkException();
	}
	
	/**
	 * Doet het aantal verplaatsingen + 1
	 */
	private void voegVerplaatsingToe() {
		aantalVerplaatsingen++;
	}
	
	/**
	 * Geeft alle vakken terug in een 2D array
	 * @return Geeft alle vakken terug in een 2D array
	 */
	public Vak[][] getAlleVakken() {		
		return alleVakken;
	}
	
	/**
	 * Stelt alle vakken in
	 * @param List van alle vakken
	 */
	public void setAlleVakken(List<Vak> vakken) {
		for (Vak v : vakken) {
			alleVakken[v.getXcoord()][v.getYcoord()] = v;
		}
	}

	/**
	 * Update een vak in alleVakken
	 * @param xcoord van het vak
	 * @param ycoord van het vak
	 * @param nieuwType van het vak
	 * @param is doel of niet
	 */
	public void updateVak(int xcoord, int ycoord, String nieuwType, boolean isDoel) {
		alleVakken[xcoord][ycoord].setDoel(isDoel);
		alleVakken[xcoord][ycoord].setType(nieuwType);
	}
	
	/**
	 * Checkt als het level nog mogelijk is
	 * @return false als het level niet meer mogelijk is
	 */
	private boolean isLevelNogMogelijk() {
		List<Vak> nietGeplaatsteKisten = new ArrayList<Vak>();
		
		for (Vak[] vrij: alleVakken)
			for (Vak v : vrij)
				if ((v.getType().equals("kist")) && (!v.getDoel()))
					nietGeplaatsteKisten.add(v);
		
		for (Vak v : nietGeplaatsteKisten)
			if (isVakInHoek(v))
				return false;
		
		return true;
	}
	
	/**
	 * Checkt als een vak ingesloten is in een hoek
	 * @param Vak
	 * 
	 * @return Geeft true terug als een vak ingesloten is in een hoek
	 */
	private boolean isVakInHoek(Vak v) {
		Vak vakErboven;
		Vak vakEronder;
		Vak vakLinks;
		Vak vakRechts;
		
		try { vakErboven = alleVakken[v.getXcoord()-1][v.getYcoord()]; } catch (IndexOutOfBoundsException ex) { vakErboven = v; }
		try { vakEronder = alleVakken[v.getXcoord()+1][v.getYcoord()]; } catch (IndexOutOfBoundsException ex) { vakEronder = v; }
		try { vakLinks = alleVakken[v.getXcoord()][v.getYcoord()-1]; } catch (IndexOutOfBoundsException ex) { vakLinks = v; }
		try { vakRechts = alleVakken[v.getXcoord()][v.getYcoord()+1]; } catch (IndexOutOfBoundsException ex) { vakRechts = v; }
		
		if ((vakErboven.getType().equals("muur")) || (vakErboven.getType().equals("kist")) || (vakEronder.getType().equals("muur")) || (vakEronder.getType().equals("kist"))) {
			if ((vakLinks.getType().equals("muur")) || (vakLinks.getType().equals("kist")) || (vakRechts.getType().equals("muur")) || (vakRechts.getType().equals("kist"))) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Stelt in of het level compleet is of niet
	 * @param boolean isLevelCompleet
	 */
	public void setIsLevelCompleet(boolean isLevelCompleet) {
		this.isLevelCompleet = isLevelCompleet;
	}
}