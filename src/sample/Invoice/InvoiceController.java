package sample.Invoice;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.Main;
import sample.models.Calls;
import sample.models.Customer;
import sample.models.Invoice;
import sample.models.dao.CallsDAO;
import sample.models.dao.InvoiceDAO;
import sample.models.dao.MySQL;

import java.net.URL;
import java.sql.Date;
import java.util.Optional;
import java.util.ResourceBundle;


public class InvoiceController implements Initializable
{
    @FXML Label lblName,lblCity,lblCP, lblAddress;
    @FXML Button btnPay;
    @FXML private TableView<Calls> tvCalls;
    @FXML private TableColumn tcNoCalls, tcDate, tcPhoneNumber, tcMinuntes;

    private Invoice invoice = new Invoice();
    private InvoiceDAO invoiceDAO = new InvoiceDAO(MySQL.getConnection());
    private Calls calls = new Calls();
    private CallsDAO callsDAO = new CallsDAO(MySQL.getConnection());

    public void setInvoice(Invoice invoice)
    {
        this.invoice = invoice;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        showClient();
        showCalls(invoice.getId_customer().getId_customer(), invoice.getId_month().getId_month());

        btnPay.setOnAction(eventBtnPay);
    }

    private void showClient() {
        lblName.setText(invoice.getId_customer().getFirst_name()+" "+invoice.getId_customer().getLast_name());
        lblAddress.setText(invoice.getId_customer().getAddress());
        lblCity.setText(invoice.getId_customer().getId_city().getName_city());
        lblCP.setText(invoice.getId_customer().getCp()+"");
    }

    private void showCalls(int id_customer, int month){
        tcNoCalls.setCellValueFactory(new PropertyValueFactory<Calls, Integer>("noCall"));
        tcDate.setCellValueFactory(new PropertyValueFactory<Calls, String>("date_hour"));
        tcPhoneNumber.setCellValueFactory(new PropertyValueFactory<Calls, String>("phone_number"));
        tcMinuntes.setCellValueFactory(new PropertyValueFactory<Calls, Integer>("minutes"));

        tvCalls.setItems(callsDAO.findAllCustomer(id_customer, month));
    }

    EventHandler<ActionEvent> eventBtnPay = new EventHandler<ActionEvent>(){
        @Override
        public void handle(ActionEvent event)
        {
            if(invoiceDAO.updatePayment(invoice.getId_plan().getTotal(), invoice.getNo_invoice()))
            {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Payment Aplied");
                alert.setContentText("Payment successfully aplied");
                Optional<ButtonType> response = alert.showAndWait();
                if(response.get() == ButtonType.OK)
                {
                    //generar ticket
                    ((Stage)(((Button) event.getSource()).getScene().getWindow())).close();
                    Main.primaryStage.show();
                }

            }
        }
    };




}
