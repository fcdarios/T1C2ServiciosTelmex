package sample.models.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.models.Invoice;

import java.sql.*;

public class InvoiceDAO
{

    Connection conn;

    private static ObservableList<Invoice> data = FXCollections.observableArrayList();
    public InvoiceDAO(Connection conn) { this.conn = conn; }
    public static void addTransaction(Invoice invoice)
    {
        data.add(invoice);
    }

    public Invoice fetchByPhoneNumber(String phone_number)
    {
        ResultSet rs = null;
        Invoice e = null;
        try {
            String query = "SELECT * FROM Invoice where phone_number = " + "'" + phone_number + "'";
            Statement st = conn.createStatement();
            rs = st.executeQuery(query);

            if (rs.first())
            {
                PlansDAO plansDAO = new PlansDAO(MySQL.getConnection());
                MonthsDAO monthsDAO = new MonthsDAO(MySQL.getConnection());
                CustomerDAO customerDAO = new CustomerDAO(MySQL.getConnection());

                e = new Invoice(
                        rs.getInt("no_invoice"),
                        rs.getString("limit_date"),
                        rs.getString("phone_number"),
                        customerDAO.fetch(rs.getInt("id_customer")),
                        monthsDAO.fetch(rs.getInt("id_month")),
                        plansDAO.fetch(rs.getInt("id_plan"))
                );
                e.setPaid_amount(rs.getDouble("paid_amount"));
                e.setPaid_date(rs.getDate("paid_date"));
            }

            } catch(SQLException ex){
                ex.printStackTrace();
                System.out.println("Error al recuperar información..." + ex.getMessage());
            }

        return e;
    }

    public Boolean updatePayment(double paid_amount, int no_invoice)
    {
        try {
            String query = "update invoice "
                    + " set paid_amount = ?, paid_date = now()"
                    + " where no_invoice=?";
            System.out.println(query + "updating....");

            PreparedStatement st =  conn.prepareStatement(query);
            st.setDouble(1, paid_amount);
            st.setInt(2, no_invoice);
            st.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return false;
    }


}
