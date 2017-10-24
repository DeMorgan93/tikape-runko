package tikape.runko.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tikape.runko.domain.RaakaAine;
import tikape.runko.domain.Smoothie;
import tikape.runko.domain.SmoothieRaakaAine;

public class SmoothieRaakaAineDao {

    private Database database;

    public SmoothieRaakaAineDao(Database database) {
        this.database = database;
    }

    public List<SmoothieRaakaAine> findAll(Integer id) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM SmoothieRaakaAine WHERE smoothie_id = ?");
        stmt.setInt(1, id);

        ResultSet rs = stmt.executeQuery();
        List<SmoothieRaakaAine> juomanTiedot = new ArrayList<>();
        while (rs.next()) {
            Integer raakaAineId = rs.getInt("raaka_aine_id");
            Integer smoothieId = rs.getInt("smoothie_id");
            String jarjestys = rs.getString("jarjestys");
            String maara = rs.getString("maara");
            String ohje = rs.getString("ohje");

            juomanTiedot.add(new SmoothieRaakaAine(raakaAineId, smoothieId, jarjestys, maara, ohje));
        }

        rs.close();
        stmt.close();
        connection.close();

        return juomanTiedot;
    }

    public void deleteSmoothie(Integer juoma) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM SmoothieRaakaAine WHERE smoothie_id = ?");

        stmt.setInt(1, juoma);
        stmt.executeUpdate();

        stmt.close();
        conn.close();
    }

    public void deleteRaakaAine(Integer raakis) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM SmoothieRaakaAine WHERE raaka_aine_id = ?");

        stmt.setInt(1, raakis);
        stmt.executeUpdate();

        stmt.close();
        conn.close();
    }

    public SmoothieRaakaAine save(SmoothieRaakaAine tallennettava) throws SQLException {

        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO SmoothieRaakaAine (raaka_aine_id, smoothie_id, jarjestys, maara, ohje) VALUES (?, ?, ?, ?, ?)");
        stmt.setInt(1, tallennettava.getRaakaAineId());
        stmt.setInt(2, tallennettava.getSmoothieId());
        stmt.setString(3, tallennettava.getJarjestys());
        stmt.setString(4, tallennettava.getMaara());
        stmt.setString(5, tallennettava.getOhje());

        stmt.executeUpdate();
        stmt.close();

        conn.close();

        return tallennettava;

    }
}
