package sample.Invoice;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import sample.Main;
import sample.models.Invoice;
import sample.models.dao.InvoiceDAO;
import sample.models.dao.MySQL;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;


public class InvoiceController implements Initializable
{
    @FXML Label lblFirstN,lblLastN,lblCity,lblCP;
    @FXML
    Button btnPay;

    private Invoice inv = new Invoice();
    InvoiceDAO invoiceDAO = new InvoiceDAO(MySQL.getConnection());

    public void setInvoice(Invoice inv)
    {
        this.inv = inv;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        lblFirstN.setText(inv.getId_customer().getFirst_name());
        lblLastN.setText(inv.getId_customer().getLast_name());
        //lblCity.setText(inv.getId_customer().getId_city().getName_city());
        lblCP.setText(inv.getId_customer().getCp()+"");
        btnPay.setOnAction(eventBtnPay);

    }

    EventHandler<ActionEvent> eventBtnPay = new EventHandler<ActionEvent>(){
        @Override
        public void handle(ActionEvent event)
        {
            if(invoiceDAO.updatePayment(inv.getId_plan().getTotal(), inv.getNo_invoice()))
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
