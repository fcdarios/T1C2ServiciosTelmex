package sample.customers;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.models.City;
import sample.models.Customer;
import sample.models.CustomerView;
import sample.models.dao.CustomerDAO;
import sample.models.dao.MySQL;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class ControllerCustomers implements Initializable {
    @FXML
    JFXButton btnPdf, btnSalir;

    @FXML TableColumn tvIdCustomer, tvFirstName, tvLastName, tvAddress, tvTelephone, tvCp, tvCity;
    @FXML TableView<CustomerView> tableViewCustomers;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tvIdCustomer.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("id_customer"));
        tvFirstName.setCellValueFactory(new PropertyValueFactory<Customer, String>("first_name"));
        tvLastName.setCellValueFactory(new PropertyValueFactory<Customer, String>("last_name"));
        tvAddress.setCellValueFactory(new PropertyValueFactory<Customer, String>("address"));
        tvTelephone.setCellValueFactory(new PropertyValueFactory<Customer, String>("phone_number"));
        tvCp.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("cp"));
        tvCity.setCellValueFactory(new PropertyValueFactory<Customer, String>("name_city"));

        CustomerDAO customerDAO = new CustomerDAO(MySQL.getConnection());
        tableViewCustomers.setItems(customerDAO.findAllV());

    }


}
