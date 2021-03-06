package sample.Invoice;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
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
    @FXML private StackPane myStackPane;
    @FXML private Label lblName,lblCity,lblCP, lblAddress;
    @FXML private Label lblPayment,lblLimitDate,lblMonth, lblPhone, lblNoInvoice;
    @FXML private Label lblPaymentA, lblPaymentB, lblPaidAmount, lblPaidDate;
    @FXML private JFXButton btnPay, btnSave, btnList;
    @FXML private TableView<Calls> tvCalls;
    @FXML private TableColumn tcNoCalls, tcDate, tcPhoneNumber, tcMinuntes;
    @FXML private MenuBar menu;
    @FXML private MenuItem menuItemReport1, menuItemReport2,menuItemReport3 ,menuItemExit, menuItemAbout;

    private Invoice invoice = new Invoice();
    private InvoiceDAO invoiceDAO = new InvoiceDAO(MySQL.getConnection());
    private Calls calls = new Calls();
    private CallsDAO callsDAO = new CallsDAO(MySQL.getConnection());

    public void setInvoice(Invoice invoice) { this.invoice = invoice; }
    public void setF5(){
            invoice = invoiceDAO.fetchLast(invoice.getId_customer().getId_customer());
            showClient();
            showPayment();
            showResume();
            showCalls(invoice.getId_customer().getId_customer(), invoice.getId_month().getId_month());
            System.out.println("F5");
    }


    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        showClient();
        showPayment();
        showResume();
        showCalls(invoice.getId_customer().getId_customer(), invoice.getId_month().getId_month());
        btnPay.setOnAction(eventBtnPay);
        btnSave.setOnAction(eventBtn);
        btnList.setOnAction(eventBtn);
        menuItemAbout.setOnAction(eventMenuItemAbout);
        menuItemExit.setOnAction(eventMenuItemExit);
        menuItemReport1.setOnAction(eventMenuItemReports);
        menuItemReport3.setOnAction(eventMenuItemReports);
        menuItemReport2.setOnAction(eventMenuItemReports);

    }

    EventHandler<ActionEvent> eventMenuItemExit = event -> {
        Stage stage = (Stage) menu.getScene().getWindow();
        stage.close();
    };
    EventHandler<ActionEvent> eventMenuItemAbout = event -> {
        showDialog("Acerca de:","Autor: Darío Olivares" );
    };
    EventHandler<ActionEvent> eventMenuItemReports = event -> {
        showDialog(":(","En mantenimiento" );
    };
    EventHandler<ActionEvent> eventBtn = event ->{
        showDialog(":(","En mantenimiento" );
    };

    private void showResume() {
        Invoice invoiceB = new Invoice();
        invoiceB = invoiceDAO.fetchBeforeLast(invoice.getId_customer().getId_customer());

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
            Scene scene=new Scene(root, 500, 350);
            scene.getStylesheets().add("/rsc/Theme3.css");
            paymentStage.setScene(scene);
            paymentStage.initStyle(StageStyle.UNDECORATED);
            paymentStage.setResizable(false);
            paymentStage.show();
        }catch (IOException e ){
            e.printStackTrace();
        }
    }

    private void showDialog(String titulo, String text){
        String title = titulo ;
        String content = text;

        JFXDialogLayout dialogContent = new JFXDialogLayout();
        dialogContent.setHeading(new Text(title));
        dialogContent.setBody(new Text(content));
        dialogContent.setAlignment(Pos.CENTER);

        JFXButton close = new JFXButton();
        close.setButtonType(JFXButton.ButtonType.RAISED);
        close.setText("OK");
        close.setStyle("-fx-background-color: #3a97ff;-fx-font-size:18;" +
                "-fx-text-fill: White; -fx-font-family: 'Roboto Bold'; -fx-pref-width: 50");

        dialogContent.setActions(close);
        JFXDialog dialog = new JFXDialog(myStackPane, dialogContent, JFXDialog.DialogTransition.TOP);
        close.setOnAction(eventClose -> {
            dialog.close();
        });
        dialog.show();
    }
}
