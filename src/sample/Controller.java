package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sample.Invoice.InvoiceController;
import sample.InvoicesFormat.ControllerInvoices;
import sample.customers.ControllerCustomers;
import sample.models.Customer;
import sample.models.Invoice;
import sample.models.Plans;
import sample.models.dao.CustomerDAO;
import sample.models.dao.InvoiceDAO;
import sample.models.dao.MySQL;
import sample.models.dao.PlansDAO;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    JFXButton btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn10, btnBorrar, btnConsultar, btnCancelar, btnAyuda;
    @FXML
    JFXTextField tfNumber;
    @FXML private MenuBar menuBar;
    @FXML private MenuItem menuItemCustomers, menuItemInvoices;
    @FXML StackPane myStackPane;


    Invoice invoice = new Invoice();
    InvoiceDAO invoiceDAO = new InvoiceDAO(MySQL.getConnection());
    Customer customer = new Customer();
    CustomerDAO customerDAO = new CustomerDAO(MySQL.getConnection());
    Plans plans = new Plans();
    PlansDAO plansDAO = new PlansDAO(MySQL.getConnection());

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btn1.setOnAction(eventBtn);
        btn2.setOnAction(eventBtn);
        btn3.setOnAction(eventBtn);
        btn4.setOnAction(eventBtn);
        btn5.setOnAction(eventBtn);
        btn6.setOnAction(eventBtn);
        btn7.setOnAction(eventBtn);
        btn8.setOnAction(eventBtn);
        btn9.setOnAction(eventBtn);
        btn10.setOnAction(eventBtn);
        btnBorrar.setOnAction(eventBtn);
        btnConsultar.setOnAction(eventConsultar);
        menuItemCustomers.setOnAction(eventMenuItemCustomers);
        menuItemInvoices.setOnAction(eventMenuItemInvoices);

    }

    EventHandler<ActionEvent> eventMenuItemCustomers = event -> {
        showCustomers();
        Stage stage = (Stage) menuBar.getScene().getWindow();
        stage.close();
    };
    EventHandler<ActionEvent> eventMenuItemInvoices = event -> {
        showInvoicesFormat();
        Stage stage = (Stage) menuBar.getScene().getWindow();
        stage.close();
    };


    EventHandler<ActionEvent> eventConsultar = event -> {
        if(validatePhoneNumber()) {
            invoice = invoiceDAO.fetchLast(customer.getId_customer());
            if (invoice != null) {
                Stage invoiceStage = new Stage();
                showInvoice(invoiceStage, false);
                ((Stage)(((Button) event.getSource()).getScene().getWindow())).close();
                tfNumber.setText("");
            }
            else showDialog("Alerta","El pago ya fue realizado\nDesea imprimir el recibo" ,true);
        }
    };


    //------------------------
    public static Stage invoiceStage;
    public static FXMLLoader loaderInvoice;

    private void showInvoice(Stage _invoiceStage, boolean before){
        try {
            invoiceStage= _invoiceStage;
            invoiceStage.setTitle("InvoiceController");
            Parent root= null;
            loaderInvoice = new FXMLLoader(getClass().getResource("/sample/Invoice/invoiceFormat.fxml"));
            InvoiceController invCont = new InvoiceController();

            invCont.setInvoice(invoice);
            loaderInvoice.setController(invCont);
            root=loaderInvoice.load();
            Scene scene=new Scene(root);
            scene.getStylesheets().add("/rsc/Theme1.css");
            invoiceStage.setScene(scene);
            invoiceStage.setMaximized(true);
            invoiceStage.show();
            System.out.println("Holaaaaaa");
        }catch (IOException e ){
            e.printStackTrace();
        }
    }
    //------------------------
    private void showCustomers(){
        try {
            Stage customersStage =new Stage();
            customersStage.setTitle("Clientes");
            Parent root = null;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/customers/CustomersFormat.fxml"));
            ControllerCustomers controllerCustomers = new ControllerCustomers();
            // Mostrar clientes
            loader.setController(controllerCustomers);

            root=loader.load();
            Scene scene=new Scene(root);
            scene.getStylesheets().add("/rsc/Theme1.css");
            customersStage.setScene(scene);
            customersStage.setMaximized(true);
            customersStage.show();
        }catch (IOException e ){
            e.printStackTrace();
        }
    }
    //------------------------
    private void showInvoicesFormat(){
        try {
            Stage invoicesStage =new Stage();
            invoicesStage.setTitle("Facturas");
            Parent root = null;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/InvoicesFormat/FormatInvoices.fxml"));
            ControllerInvoices controllerInvoices = new ControllerInvoices();
            // Mostrar clientes
            loader.setController(controllerInvoices);

            root=loader.load();
            Scene scene=new Scene(root);
            scene.getStylesheets().add("/rsc/Theme1.css");
            invoicesStage.setScene(scene);
            invoicesStage.setMaximized(true);
            invoicesStage.show();
        }catch (IOException e ){
            e.printStackTrace();
        }
    }

    private boolean validatePhoneNumber(){
        boolean result = false;
        String phoneNumber = tfNumber.getText();
        customer = customerDAO.fetchByPhoneNumber(phoneNumber);
        if(customer != null){
            result = true;
        }
        else showDialog("ALERTA","Error, numero no encontrado",false);
        return result;}

    private EventHandler<ActionEvent> eventBtn = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            String texto;
            if(event.getSource().equals(btnBorrar))
                if(!tfNumber.getText().equals("")){
                    texto = tfNumber.getText();
                    texto = texto.substring(0, texto.length()-1);
                    tfNumber.setText(texto);}
                else tfNumber.setText("");
            else {
                JFXButton btn = (JFXButton) event.getSource();

                texto = tfNumber.getText();
                tfNumber.setText(texto + btn.getText());
            }
        }
    };


    private void showDialog(String titulo, String text, Boolean Recibo){
        String title = titulo ;
        String content = text;

        JFXDialogLayout dialogContent = new JFXDialogLayout();
        dialogContent.setHeading(new Text(title));
        dialogContent.setBody(new Text(content));
        dialogContent.setAlignment(Pos.CENTER);

        JFXButton close = new JFXButton();
        close.setButtonType(JFXButton.ButtonType.RAISED);
        if(!Recibo) close.setText("OK");
        close.setText("NO");
        close.setStyle("-fx-background-color: #3a97ff;-fx-font-size:15;" +
                "-fx-text-fill: White; -fx-font-family: 'Roboto Bold'; -fx-pref-width: 50");

        JFXButton open = new JFXButton();
        open.setButtonType(JFXButton.ButtonType.RAISED);
        open.setText("SI");
        open.setStyle("-fx-background-color: #3a97ff;-fx-font-size:15;" +
                "-fx-text-fill: White; -fx-font-family: 'Roboto Bold'; -fx-pref-width: 50");

        if(Recibo)
        dialogContent.setActions(close, open);
        else dialogContent.setActions(close);
        JFXDialog dialog = new JFXDialog(myStackPane, dialogContent, JFXDialog.DialogTransition.TOP);
        close.setOnAction(eventClose -> {
            dialog.close();
            tfNumber.setText("");
        });

        open.setOnAction(eventOpen -> {
            //------------------------------------------------------------
            dialog.close();
            tfNumber.setText("");
        });
        dialog.show();
    }
}
