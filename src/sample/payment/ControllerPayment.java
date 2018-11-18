package sample.payment;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sample.Controller;
import sample.Invoice.InvoiceController;
import sample.Main;
import sample.models.Invoice;
import sample.models.dao.InvoiceDAO;
import sample.models.dao.MySQL;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerPayment implements Initializable {
    private Invoice invoice = new Invoice();
    private InvoiceDAO invoiceDAO = new InvoiceDAO(MySQL.getConnection());
    public void setInvoice(Invoice invoice)
    {
        this.invoice = invoice;
    }

    @FXML
    private Label lblPago;
    @FXML
    private JFXButton btnClose, btnPaid;
    @FXML
    private JFXTextField tfPago;
    @FXML
    private StackPane myStackPane;

    private Double paid, totalPaid, res;
    private Double payment, cambio = 0.0 ;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        paid = invoice.getPaid_amount();
        totalPaid = invoice.getId_plan().getTotal();
        res = totalPaid - paid;

        lblPago.setText(invoice.getId_plan().getTotal()+"");
        tfPago.setPromptText("Pago faltante: "+res);
        btnPaid.setOnAction(eventPaid);
        btnClose.setOnAction(eventClose);
    }

        private EventHandler<ActionEvent>  eventPaid = event -> {
        if(!tfPago.getText().equals(""))
        {
            try {
                payment = Double.parseDouble(tfPago.getText()) + paid;
                cambio = 0.0;
                if(payment > totalPaid){
                    cambio = payment - totalPaid;
                    payment = totalPaid;
                }
                invoiceDAO.updatePayment(payment,invoice.getNo_invoice());
                if (cambio == 0)
                    showDialog("Payment Aplied","Pago aplicado satisfactoriamente",true, event);
                else
                    showDialog("Payment Aplied","Pago aplicado satisfactoriamente" +
                            "\n Su cambio es: "+cambio,true, event);
            }catch (Exception e){
                showDialog("Error paid","Introducir un pago valido",false, event);
            }
        }
        else
            showDialog("Alerta","Introduzca un monto",false, event);
    };

    private EventHandler<ActionEvent> eventClose = event -> {
        Main.primaryStage.show();
        ((Stage)(((Button) event.getSource()).getScene().getWindow())).close();
    };

    private void showDialog(String titulo, String text, boolean opc, ActionEvent event){
        String title = titulo ;
        String content = text;

        JFXDialogLayout dialogContent = new JFXDialogLayout();
        dialogContent.setHeading(new Text(title));
        dialogContent.setBody(new Text(content));
        dialogContent.setAlignment(Pos.CENTER);

        JFXButton close = new JFXButton();
        close.setButtonType(JFXButton.ButtonType.RAISED);
        FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
        close.setGraphic(icon);
        close.setStyle("-fx-background-color: #3a97ff;-fx-font-size:20;-fx-text-fill: White");
        dialogContent.setActions(close);
        JFXDialog dialog = new JFXDialog(myStackPane, dialogContent, JFXDialog.DialogTransition.TOP);

        close.setOnAction(__ -> {
            dialog.close();
            if (opc) {
                if (payment == totalPaid) showInvoice(event,true );
                else showInvoice(event,false );
            }
        });
        dialog.show();
    }

    private void showInvoice(ActionEvent event, boolean salir){
        InvoiceController invoiceController;
        invoiceController = Controller.loaderInvoice.getController();

        if (salir){
            Main.primaryStage.show();
            Controller.invoiceStage.close();
            ((Stage)(((Button) event.getSource()).getScene().getWindow())).close();
        }
        else {
            invoiceController.setF5();
            Controller.invoiceStage.show();
            ((Stage)(((Button) event.getSource()).getScene().getWindow())).close();
        }
    }

}
