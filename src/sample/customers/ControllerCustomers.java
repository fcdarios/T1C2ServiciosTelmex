package sample.customers;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.Docs.CustomersPDF;
import sample.Main;
import sample.models.Customer;
import sample.models.CustomerView;
import sample.models.dao.CustomerDAO;
import sample.models.dao.MySQL;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class ControllerCustomers implements Initializable {
    @FXML
    JFXButton btnPdf, btnSalir;

    @FXML TableColumn tvIdCustomer, tvFirstName, tvLastName, tvAddress, tvTelephone, tvCp, tvCity;
    @FXML TableView<CustomerView> tableViewCustomers;

    CustomersPDF customersPDF = new CustomersPDF(MySQL.getConnection());

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

        btnSalir.setOnAction(eventClose);
        btnPdf.setOnAction(eventPDF);
    }

    private EventHandler<ActionEvent> eventClose = event -> {
        Main.primaryStage.show();
        ((Stage)(((Button) event.getSource()).getScene().getWindow())).close();
    };

    private EventHandler<ActionEvent> eventPDF = event -> {
         String DEST4 = "PDFs/Reports/Customers_"+ LocalDate.now()+".pdf";
        try {
            File file = new File(DEST4);
            file.getParentFile().mkdirs();
            customersPDF.createPdf(DEST4);
        }catch (IOException e){
            System.out.println(e);
        }

    };

}
