package sample.models.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.models.City;
import sample.models.Plans;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class CityDAO {

    Connection conn;

    private static ObservableList<City> data = FXCollections.observableArrayList();
    public CityDAO(Connection conn) { this.conn = conn; }
    public static void addTransaction(City city) { data.add(city); }

    public City fetch(int trans_id) {
        ResultSet rs = null;
        City e = null;
        try {
            String query = "SELECT * FROM city where id_city = " + trans_id;
            Statement st = conn.createStatement();
            rs = st.executeQuery(query);
            if(rs.first()) {
                e = new City(
                        rs.getInt("id_city"),
                        rs.getString("name_city")
                );
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error al recuperar información...");
        }
        return e;
    }

}
