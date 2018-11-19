package sample.InvoicesFormat;

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
import sample.Main;
import sample.models.Customer;
import sample.models.CustomerView;
import sample.models.InvoicesView;
import sample.models.dao.CustomerDAO;
import sample.models.dao.InvoiceDAO;
import sample.models.dao.MySQL;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerInvoices implements Initializable {
    @FXML
    JFXButton btnPdf, btnSalir;
    @FXML TableColumn tvName_month, tvNameCustomer, tvTotal, tvPaid_amount, tvLimit_date, tvPaid_date, tvPhone_number, tvName_plan, tvI;
    @FXML TableView<InvoicesView> tableViewInvoices;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tvI.setCellValueFactory(new PropertyValueFactory<InvoicesView,Integer>("no"));
        tvName_month.setCellValueFactory(new PropertyValueFactory<InvoicesView,String>("name_month"));
        tvNameCustomer.setCellValueFactory(new PropertyValueFactory<InvoicesView,String>("nameCustomer"));
        tvTotal.setCellValueFactory(new PropertyValueFactory<InvoicesView,Double>("total"));
        tvPaid_amount.setCellValueFactory(new PropertyValueFactory<InvoicesView,Double>("paid_amount"));
        tvLimit_date.setCellValueFactory(new PropertyValueFactory<InvoicesView,String>("limit_date"));
        tvPaid_date.setCellValueFactory(new PropertyValueFactory<InvoicesView,String>("paid_date"));
        tvPhone_number.setCellValueFactory(new PropertyValueFactory<InvoicesView,String>("phone_number"));
        tvName_plan.setCellValueFactory(new PropertyValueFactory<InvoicesView,String>("name_plan"));

        InvoiceDAO invoiceDAO = new InvoiceDAO(MySQL.getConnection());
        tableViewInvoices.setItems(invoiceDAO.findAllView());

        btnSalir.setOnAction(eventClose);
    }

    private EventHandler<ActionEvent> eventClose = event -> {
        Main.primaryStage.show();
        ((Stage)(((Button) event.getSource()).getScene().getWindow())).close();
    };

}
