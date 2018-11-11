package sample.models.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.models.City;
import sample.models.Customer;
import sample.models.CustomerView;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CustomerDAO {

    Connection conn;

    private static ObservableList<Customer> data = FXCollections.observableArrayList();

    public CustomerDAO(Connection conn) { this.conn = conn; }

    public static void addTransaction(Customer customer) { data.add(customer); }

    /*public ObservableList<Customer> findAll() {
        ObservableList<Customer> customers = FXCollections.observableArrayList();

        try {
            String query = "SELECT * FROM customer";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            Customer p = null;
            if (rs.first()) {
                //CityDAO cityDAO = new CityDAO(MySQL.getConnection());
                CityDAO cityDAO = new CityDAO(MySQL.getConnection());
                while (rs.next()) {
                    City city = new City();
                    city.setId_city(rs.getInt("id_city"));

                    p = new sample.models.Customer(
                            rs.getInt("id_customer"),
                            rs.getString("first_name"),
                            rs.getString("last_name"),
                            rs.getString("address"),
                            rs.getInt("cp"),
                            //cityDAO.fetch(rs.getInt("id_city"))
                            //city
                            rs.getInt("id_city")
                    );
                    customers.add(p);
                }
                rs.close();
                st.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error al recuperar información...");
        }
        return customers;
    }*/

    public ObservableList<CustomerView> findAllV() {
        ObservableList<CustomerView> customerViews = FXCollections.observableArrayList();
        try {
            String query = "SELECT * FROM viewcustomer";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            CustomerView p = null;
            while (rs.next()) {
                p = new CustomerView(
                        rs.getInt("id_customer"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("address"),
                        rs.getString("phone_number"),
                        rs.getInt("cp"),
                        rs.getString("name_city")
                );
                customerViews.add(p);
            }
            rs.close();
            st.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error al recuperar información...");
        }
        return customerViews;
    }

    public Customer fetch(int trans_id) {
        ResultSet rs = null;
        Customer e = null;
        try {
            String query = "SELECT * FROM customer where id_customer = " + trans_id;
            Statement st = conn.createStatement();
            rs = st.executeQuery(query);

            if(rs.first()) {
                City city = new City();
                city.setId_city(rs.getInt("id_city"));

                e = new Customer(
                        rs.getInt("id_customer"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("address"),
                        rs.getInt("cp"),
                        //city
                        rs.getInt("id_city")
                );
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error al recuperar información...");
        }
        return e;
    }
}
