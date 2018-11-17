package sample.Invoice;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
import sample.payment.ControllerPayment;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.Optional;
import java.util.ResourceBundle;


public class InvoiceController implements Initializable
{
    @FXML private Label lblName,lblCity,lblCP, lblAddress;
    @FXML private Label lblPayment,lblLimitDate,lblMonth, lblPhone, lblNoInvoice;
    @FXML private Label lblPaymentA, lblPaymentB, lblPaidAmount, lblPaidDate;
    @FXML private Button btnPay;
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
        showPayment();
        showResume();
        showCalls(invoice.getId_customer().getId_customer(), invoice.getId_month().getId_month());

        btnPay.setOnAction(eventBtnPay);
    }

    private void showResume() {
        Invoice invoiceB = new Invoice();
        invoiceB = invoiceDAO.fetchBefore(invoice.getId_customer().getId_customer());

        lblPaymentA.setText(invoiceB.getId_plan().getTotal()+"");
        lblPaidAmount.setText(invoiceB.getPaid_amount()+"");
        lblPaidDate.setText(invoiceB.getPaid_date()+"");
        lblPaymentB.setText(invoiceB.getId_plan().getTotal()+"");
    }

    private void showPayment() {
        lblPayment.setText(invoice.getId_plan().getTotal()+"");
        lblLimitDate.setText(invoice.getLimit_date()+"");
        lblMonth.setText(invoice.getId_month().getName_month());
        lblPhone.setText(invoice.getId_customer().getPhone_number());
        lblNoInvoice.setText(invoice.getNo_invoice()+"");
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
           showPayment(event);
        }
    };

    private void showPayment(ActionEvent event){
        try {
            Stage paymentStage =new Stage();
            paymentStage.setTitle("Pago");
            Parent root = null;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/payment/PaymentFormat.fxml"));

            ControllerPayment controllerPayment = new ControllerPayment();
            controllerPayment.setInvoice(invoice);
            loader.setController(controllerPayment);

            root=loader.load();
            Scene scene=new Scene(root, 500, 300);
            scene.getStylesheets().add("/rsc/DarkTheme2.css");
            paymentStage.setScene(scene);
            paymentStage.show();
        }catch (IOException e ){
            e.printStackTrace();
        }
    }


}
