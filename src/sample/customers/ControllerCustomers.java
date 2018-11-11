package sample.customers;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import sample.models.Customer;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerCustomers implements Initializable {
    @FXML
    JFXButton btnPdf, btnSalir;

    @FXML
    private TableColumn<Customer, String> tvIdCustomer;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
