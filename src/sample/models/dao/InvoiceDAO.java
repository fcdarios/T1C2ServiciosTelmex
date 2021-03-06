package sample.models.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.models.Invoice;
import sample.models.InvoicesView;

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

    public Invoice fetchLast(int trans_id) {
        ResultSet rs = null;
        Invoice e = null;
        try {
            String query = "SELECT i.* FROM Invoice i inner join plans p on i.id_plan = p.id_plan " +
                    "where id_customer = " + trans_id +" AND i.paid_amount < p.total";
            Statement st = conn.createStatement();
            rs = st.executeQuery(query);

            if(rs.first()) {
                MonthsDAO monthsDAO = new MonthsDAO(MySQL.getConnection());
                PlansDAO plansDAO = new PlansDAO(MySQL.getConnection());
                CustomerDAO customerDAO = new CustomerDAO(MySQL.getConnection());
                e = new Invoice(
                        rs.getInt("no_invoice"),
                        rs.getDate("limit_date"),
                        monthsDAO.fetch(rs.getInt("id_month")),
                        plansDAO.fetch(rs.getInt("id_plan")),
                        customerDAO.fetch(rs.getInt("id_customer")),
                        rs.getDouble("paid_amount"),
                        rs.getDate("paid_date")
                );
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error al recuperar información...");
        }
        return e;
    }

    public Invoice fetchBeforeLast(int trans_id) {
        ResultSet rs = null;
        Invoice e = null;
        try {
            String query = "SELECT i.* FROM Invoice i inner join plans p on i.id_plan = p.id_plan " +
                    "where id_customer = " + trans_id +" AND i.paid_amount = p.total ORDER BY i.no_invoice DESC LIMIT 1, 1";
            Statement st = conn.createStatement();
            rs = st.executeQuery(query);
            if(rs.first()) {
                MonthsDAO monthsDAO = new MonthsDAO(MySQL.getConnection());
                PlansDAO plansDAO = new PlansDAO(MySQL.getConnection());
                CustomerDAO customerDAO = new CustomerDAO(MySQL.getConnection());
                System.out.println();
                e = new Invoice(
                        rs.getInt("no_invoice"),
                        rs.getDate("limit_date"),
                        monthsDAO.fetch(rs.getInt("id_month")),
                        plansDAO.fetch(rs.getInt("id_plan")),
                        customerDAO.fetch(rs.getInt("id_customer")),
                        rs.getDouble("paid_amount"),
                        rs.getDate("paid_date")
                );
            }
            System.out.println(e);
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error al recuperar información...");
        }
        return e;
    }


    // Consulta a vista de base de datos InvoiceView
    public ObservableList<InvoicesView> findAllView() {
        ObservableList<InvoicesView> invoicesViews = FXCollections.observableArrayList();
        int i = 1;
        try {
            String query = "SELECT * FROM invoicesView";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            InvoicesView p = null;
            while (rs.next()) {
                p = new InvoicesView(
                        i,
                        rs.getString("name_month"),
                        rs.getString("nameCustomer"),
                        rs.getDouble("total"),
                        rs.getDouble("paid_amount"),
                        rs.getString("limit_date"),
                        rs.getString("paid_date"),
                        rs.getString("phone_number"),
                        rs.getString("name_plan")
                );
                invoicesViews.add(p);
                i++;
            }
            rs.close();
            st.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error al recuperar información...");
        }
        return invoicesViews;
    }
}
