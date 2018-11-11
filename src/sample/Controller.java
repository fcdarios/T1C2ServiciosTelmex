package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import sample.Invoice.InvoiceController;
import sample.customers.ControllerCustomers;
import sample.models.Invoice;
import sample.models.dao.InvoiceDAO;
import sample.models.dao.MySQL;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    JFXButton btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn10, btnBorrar, btnConsultar, btnCancelar, btnAyuda;
    @FXML
    JFXTextField tfNumber;
    @FXML
    MenuItem menuItemCustomers;
    Invoice invoice = new Invoice();
    InvoiceDAO invoiceDAO = new InvoiceDAO(MySQL.getConnection());

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
    }

    EventHandler<ActionEvent> eventMenuItemCustomers = event -> {
        showCustomers(event);
    };


    EventHandler<ActionEvent> eventConsultar = event -> {
        if(validatePhoneNumber())
        {
            if(invoice.getPaid_amount() > 0)
            {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("WARNING");
                alert.setContentText("Invoice already paid");
                alert.show();
            }
            else
            {
                showInvoice(event);
            }
            tfNumber.setText("");
        }
    };

    private void showInvoice(ActionEvent event){
        try {
            Stage invoiceStage=new Stage();
            invoiceStage.setTitle("InvoiceController");
            Parent root= null;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/Invoice/invoiceFormat.fxml"));

            InvoiceController invCont = new InvoiceController();
            invCont.setInvoice(invoice);
            loader.setController(invCont);

            root=loader.load();
            Scene scene=new Scene(root);
            scene.getStylesheets().add("bootstrapfx.css");
            invoiceStage.setScene(scene);
            invoiceStage.setMaximized(true);
            //invoice.setResizable(false);
            invoiceStage.show();
            ((Stage)(((Button) event.getSource()).getScene().getWindow())).close();
        }catch (IOException e ){
            e.printStackTrace();
        }
    }

    private void showCustomers(ActionEvent event){
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
            scene.getStylesheets().add("/rsc/DarkTheme2.css");
            customersStage.setScene(scene);
            customersStage.setMaximized(true);

            customersStage.show();
        }catch (IOException e ){
            e.printStackTrace();
        }
    }

    private boolean validatePhoneNumber(){
        boolean result = false;
        String phoneNumber = tfNumber.getText();
        invoice = invoiceDAO.fetchByPhoneNumber(phoneNumber);

        if(invoice != null){
            result = true;
        }
        else sendMessege("Error, numero no encontrado");
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

    void sendMessege(String alerta){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Detalles");
        alert.setHeaderText("");
        alert.setContentText(alerta);
        alert.show();
    }
}
