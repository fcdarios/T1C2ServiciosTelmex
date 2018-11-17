package sample.payment;
import javafx.fxml.Initializable;
import sample.models.Invoice;
import sample.models.dao.InvoiceDAO;
import sample.models.dao.MySQL;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerPayment implements Initializable {
    private Invoice invoice = new Invoice();
    private InvoiceDAO invoiceDAO = new InvoiceDAO(MySQL.getConnection());
    public void setInvoice(Invoice invoice)
    {
        this.invoice = invoice;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    /*
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
    * */
}
