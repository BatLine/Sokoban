package persistentie;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domein.Spel;
import domein.Vak;
import domein.Level;

/**
 * 
 * De klasse voor het verwerken van spel data van en naar de database
 * 
 * @author Gilles De Pessemier, Rune De Bruyne, Aaron Everaert, Chiel Meneve
 *
 */
public class SpelMapper {
	SpelerMapper spelerMapper;
	
	public SpelMapper() {
		spelerMapper = new SpelerMapper();
	}
	
	public List<Spel> getAlleSpellen() {
        List<Spel> spellen = new ArrayList<Spel>();

        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
                PreparedStatement query = conn.prepareStatement("SELECT * FROM ID222177_g11.spel;");
                ResultSet rs = query.executeQuery()) {

            while (rs.next()) {
                Spel s = new Spel(rs.getString("naam"));
                s.setCreator(spelerMapper.geefSpeler(rs.getString("maker")));
                
                spellen.add(s);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        return spellen;
	}
	
	public Spel getEenSpel(String spelNaam) {
        Spel s = null;

        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
                PreparedStatement query = conn.prepareStatement("SELECT * FROM ID222177_g11.spel where naam='"+spelNaam+"';");
                ResultSet rs = query.executeQuery()) {

            while (rs.next()) {
            	s = new Spel(rs.getString("naam"));
            	s.setCreator(spelerMapper.geefSpeler(rs.getString("maker")));
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        return s;
	}

	public void maakNieuwSpel(String spelNaam, String username) {
        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
            PreparedStatement query = conn.prepareStatement("insert into ID222177_g11.spel (naam, maker) values (?, ?);")) {
        	query.setString(1, spelNaam);
        	query.setString(2, username);
            query.execute();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
	}

	public void maakNieuwSpelbord(int aantalSpelborden, String spelnaam) {
        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
        	PreparedStatement query = conn.prepareStatement("insert into ID222177_g11.spelbord (spelNaam, spelbordNr) values (?, ?);")) {
            query.setString(1, spelnaam);
            query.setInt(2, aantalSpelborden+1);
            query.execute();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
	}

	public void maakNieuwVak(int x, int y, String icoontje, String spelNaam, String levelNaam) {
        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
            	PreparedStatement query = conn.prepareStatement("insert into ID222177_g11.vak (spelbordNr, spelNaam, type, isDoel, xcoord, ycoord) values (?, ?, ?, ?, ?, ?);")) {
                query.setString(1, levelNaam);
                query.setString(2, spelNaam);
                //type
                String type;
                query.setBoolean(4, false); //isDoel
                
                switch (icoontje) {
				case "O": //kist
					type = "kist";
					break;
				case "S":
					type = "speler"; //speler
					break;
				case "H": //doel
					type = "veld";
					query.setBoolean(4, true);
					break;
				case " ":
					type = "veld"; //veld
					break;
				case "x":
					type = "muur"; //muur
					break;
				case "/":
				default:
					type = "none"; //iets fout
					break;
				}
                query.setString(3, type);
                //
                query.setInt(5, x);
                query.setInt(6, y);
                query.execute();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
	}
	
	public List<Vak> getAlleVakken(String spelNaam, String levelNaam) {
        List<Vak> vakken = new ArrayList<Vak>();

        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
                PreparedStatement query = conn.prepareStatement("SELECT * FROM ID222177_g11.vak where spelNaam='"+spelNaam+"' and spelbordNr='"+levelNaam+"';");
                ResultSet rs = query.executeQuery()) {

            while (rs.next()) {
                Vak v = new Vak(rs.getString("type"), rs.getBoolean("isDoel"), rs.getInt("xcoord"), rs.getInt("ycoord"));
                
                vakken.add(v);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        return vakken;
	}
	
	public List<Level> getAlleLevels(String spelNaam) {
        List<Level> levels = new ArrayList<Level>();

        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
                PreparedStatement query = conn.prepareStatement("SELECT * FROM ID222177_g11.spelbord where spelNaam='"+spelNaam+"';");
                ResultSet rs = query.executeQuery()) {

            while (rs.next()) {
            	Level l = new Level(rs.getString("spelbordNr"));
                
                levels.add(l);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        return levels;
	}

	public void updateVak(int xcoord, int ycoord, String nieuwType, boolean isDoel, String spelNaam, String levelNaam) {
        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
            	PreparedStatement query = conn.prepareStatement("UPDATE `ID222177_g11`.`vak` SET `type` = ?, `isDoel` = ? WHERE spelbordNr = ? and spelnaam = ? and xcoord = ? and ycoord = ?;")) {
                query.setString(1, nieuwType);
                query.setBoolean(2, isDoel);
                
                query.setString(3, levelNaam);
                query.setString(4, spelNaam);
                query.setInt(5, xcoord);
                query.setInt(6, ycoord);
                
                query.execute();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
		
	}

	public void verwijderLevel(String naam, String levelNaam) {
        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
            	PreparedStatement query = conn.prepareStatement("DELETE FROM `ID222177_g11`.`spelbord` WHERE (`spelNaam` = ? AND `spelbordNr` = ?);")) {
            query.setString(1, naam);
            query.setInt(2, Integer.parseInt(levelNaam));
            
            query.execute();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
	}

	public void verwijderSpel(String spelNaam) {
        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
            	PreparedStatement query = conn.prepareStatement("DELETE FROM `ID222177_g11`.`spel` WHERE (`naam` = ?);")) {
            query.setString(1, spelNaam);
            
            query.execute();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
	}
}
