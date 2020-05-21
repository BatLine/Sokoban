package domein;

/**
 * 
 * De klasse met alle data van een vak uit een level
 * 
 * @author Gilles De Pessemier, Rune De Bruyne, Aaron Everaert, Chiel Meneve
 *
 */
public class Vak {
	private String type; //muur, veld, speler, kist
	private int xcoord;
	private int ycoord;
	private boolean isDoel;
	
	/**
	 * Constructor vak
	 * @param type van het vak
	 * @param is het een doel of niet
	 * @param xcoord van het vak
	 * @param ycoord van het vak
	 */
	public Vak(String type, boolean isDoel, int xcoord, int ycoord) {
		this.type = type;
		this.isDoel = isDoel;
		this.xcoord = xcoord;
		this.ycoord = ycoord;
	}
	
	/**
	 * Geeft het type terug
	 * @return Geeft het type terug
	 */
	public String getType() { return type; }
	
	/**
	 * Stelt het type in
	 * @param type
	 */
	public void setType(String type) { this.type = type; }
	
	/**
	 * Geeft true terug als het een doel is
	 * @return Geeft true terug als het een doel is
	 */
	public boolean getDoel() { return isDoel; }
	
	/**
	 * Stel isDoel in
	 * @param isDoel
	 */
	public void setDoel(boolean isDoel) { this.isDoel = isDoel; }
	
	/**
	 * Geeft de x coord terug van het vak
	 * @return Geeft de x coord terug van het vak
	 */
	public int getXcoord() { return xcoord; }
	
	/**
	 *  Geeft de y coord terug van het vak
	 * @return Geeft de y coord terug van het vak
	 */
	public int getYcoord() { return ycoord; }
	
	/**
	 * Geeft de vakinfo terug in een String array
	 * 0 type
	 * 1 xcoord
	 * 2 ycoord
	 * 3 isDoel
	 * @return Geeft de vakinfo terug in een String array
	 */
	public String[] getVakInfo() {
		String[] arr = new String[4];
		
		arr[0] = type;
		arr[1] = String.valueOf(xcoord);
		arr[2] = String.valueOf(ycoord);
		arr[3] = String.valueOf(isDoel);
		
		return arr;
	}
}
