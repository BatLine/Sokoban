package persistentie;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domein.Speler;

/**
 * 
 * De klasse voor het verwerken van speler data van en naar de database
 * 
 * @author Gilles De Pessemier, Rune De Bruyne, Aaron Everaert, Chiel Meneve
 *
 */
public class SpelerMapper {
    private static final String INSERT_SPELER = "INSERT INTO ID222177_g11.speler (naam, voornaam, gebruikersnaam, wachtwoord, admin) VALUES (?, ?, ?, ?, ?)";
        
    public void voegToe(Speler speler) {
        try (
                Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
                PreparedStatement query = conn.prepareStatement(INSERT_SPELER)) {
            query.setString(1, speler.getNaam());
            query.setString(2, speler.getVoornaam());
            query.setString(3, speler.getGebruikersnaam());
            query.setString(4, speler.getWachtwoord());
            query.setBoolean(5, speler.isAdmin());
            query.executeUpdate();
        } catch (SQLException ex) { throw new RuntimeException(ex); }
    }

    public List<Speler> geefSpelers() {
        List<Speler> spelers = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
                PreparedStatement query = conn.prepareStatement("SELECT * FROM ID222177_g11.speler");
                ResultSet rs = query.executeQuery()) {

            while (rs.next()) {
                String naam = rs.getString("naam");
                String voornaam = rs.getString("voornaam");
                String gebruikersnaam = rs.getString("gebruikersnaam");
                String wachtwoord = rs.getString("wachtwoord");
                boolean admin = rs.getBoolean("admin");

                spelers.add(new Speler(naam, voornaam, gebruikersnaam, wachtwoord, admin));
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        return spelers;
    }

    public Speler geefSpeler(String gebruikersnaam) {
        Speler speler = null;

        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
                PreparedStatement query = conn.prepareStatement("SELECT * FROM ID222177_g11.speler WHERE gebruikersnaam = ?")) {
            query.setString(1, gebruikersnaam);
            try (ResultSet rs = query.executeQuery()) {
                if (rs.next()) {
                    String naam = rs.getString("naam");
                    String voornaam = rs.getString("voornaam");
                    String wachtwoord = rs.getString("wachtwoord");
                    boolean admin = rs.getBoolean("admin");

                    speler = new Speler(naam, voornaam, gebruikersnaam, wachtwoord, admin);
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        
        return speler;
    }
}
