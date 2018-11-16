package sample.models.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.models.Calls;
import sample.models.City;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class CallsDAO {

    Connection conn;

    private static ObservableList<Calls> data = FXCollections.observableArrayList();
    public CallsDAO(Connection conn) { this.conn = conn; }
    public static void addTransaction(Calls calls) { data.add(calls); }

    public ObservableList<Calls> findAllCustomer(int id_customer, int month) {
        ObservableList<Calls> calls = FXCollections.observableArrayList();
        try {
            String query = "SELECT date_hour, phone_number, minutes FROM calls " +
                    "where id_customer in (select id_customer from customer " +
                                            "where id_customer in (select id_customer from invoice " +
                    "                                               where DATE_FORMAT(date_hour, '%c') = "+month+")) " +
                    "and id_customer = "+id_customer;
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            int i = 1;
            CustomerDAO customerDAO = new CustomerDAO(MySQL.getConnection());
            Calls p = null;
            while (rs.next()) {
                p = new Calls(
                        i,
                        rs.getString("date_hour"),
                        rs.getString("phone_number"),
                        rs.getInt("minutes")
                );
                calls.add(p);
                i++;
            }
            rs.close();
            st.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error al recuperar información...");
        }
        return calls;
    }

}
