package sample.models.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.models.Plans;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class PlansDAO {

    Connection conn;

    private static ObservableList<Plans> data = FXCollections.observableArrayList();
    public PlansDAO(Connection conn) { this.conn = conn; }
    public static void addTransaction(Plans plans) { data.add(plans); }

    public Plans fetch(int trans_id) {
        ResultSet rs = null;
        Plans e = null;
        try {
            String query = "SELECT * FROM plans where id_plan = " + trans_id;
            Statement st = conn.createStatement();
            rs = st.executeQuery(query);

            if(rs.first()) {
                e = new Plans(
                        rs.getInt("id_plan"),
                        rs.getString("name_plan"),
                        rs.getDouble("total")
                );
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error al recuperar información...");
        }
        return e;
    }

}
