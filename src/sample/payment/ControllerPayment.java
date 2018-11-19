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
import sample.Docs.Ticket;
import sample.Invoice.InvoiceController;
import sample.Main;
import sample.models.Invoice;
import sample.models.dao.InvoiceDAO;
import sample.models.dao.MySQL;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class ControllerPayment implements Initializable {
    private Invoice invoice = new Invoice();
    private InvoiceDAO invoiceDAO = new InvoiceDAO(MySQL.getConnection());
    public void setInvoice(Invoice invoice)
    {
        this.invoice = invoice;
    }

    private String outputTicket = "PDFs/Tickets/";
    private Ticket ticket = new Ticket();

    @FXML
    private Label lblPago;
    @FXML
    private JFXButton btnClose, btnPaid;
    @FXML
    private JFXTextField tfPago;
    @FXML
    private StackPane myStackPane;

    private Double paid, totalPaid, res;
    private Double payment, cambio = 0.0, money ;

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
                money = payment;
                cambio = 0.0;
                if(payment >= totalPaid){
                    cambio = payment - totalPaid;
                    payment = totalPaid;
                }
                invoiceDAO.updatePayment(payment,invoice.getNo_invoice());
                if(payment == totalPaid)
                    if (cambio == 0) showDialogFull("FELICIDADES!","Pago aplicado satisfactoriamente" +
                            "\n Imprimir ticket: ", event);
                    else showDialogFull("Payment Aplied","Pago aplicado satisfactoriamente" +
                                "\nSu cambio es: "+cambio+"\nImprimir ticket: ", event);
                else showDialogNoFull("Payment Aplied","Pago aplicado satisfactoriamente", event);

            }catch (Exception e){
                showDialogNoFull("Error paid","Introducir un pago valido", event);
            }
        }
        else
            showDialogNoFull("Alerta","Introduzca un monto", event);
    };

    private EventHandler<ActionEvent> eventClose = event -> {
        InvoiceController invoiceController;
        invoiceController = Controller.loaderInvoice.getController();
        invoiceController.setF5();
        Controller.invoiceStage.show();
        ((Stage)(((Button) event.getSource()).getScene().getWindow())).close();
    };

    private void showDialogFull(String titulo, String text, ActionEvent event){
        boolean paidOk = false;
        String title = titulo ;
        String content = text;

        JFXDialogLayout dialogContent = new JFXDialogLayout();
        dialogContent.setHeading(new Text(title));
        dialogContent.setBody(new Text(content));
        dialogContent.setAlignment(Pos.CENTER);
        JFXButton btnClose = new JFXButton();
        JFXButton btnOk = new JFXButton();
        btnClose.setButtonType(JFXButton.ButtonType.RAISED);
        btnOk.setButtonType(JFXButton.ButtonType.RAISED);
        btnClose.setStyle("-fx-background-color: #3a97ff;-fx-font-size:15;-fx-text-fill: White;-fx-pref-width: 50");
        btnOk.setStyle("-fx-background-color: #3a97ff;-fx-font-size:15;-fx-text-fill: White;-fx-pref-width: 50");
        btnClose.setText("NO");
        btnOk.setText("SI");
        dialogContent.setActions(btnClose, btnOk);
        JFXDialog dialog = new JFXDialog(myStackPane, dialogContent, JFXDialog.DialogTransition.TOP);

        btnClose.setOnAction(eventClose -> {
            dialog.close();
            showInvoice(event,true );
        });
        btnOk.setOnAction(eventOK ->{
            try {
                outputTicket = outputTicket +"ticket_"+invoice.getNo_invoice()+"_"+invoice.getId_customer().getFirst_name()+".pdf";
                System.out.printf(outputTicket);
                File file = new File(outputTicket);
                file.getParentFile().mkdirs();
                ticket.setInvoice(invoice, money, cambio);
                ticket.createTicketPdf(outputTicket);
            }catch (IOException e){
                System.out.println(e);
            }
            dialog.close();
            showInvoice(event,true );
        });
        dialog.show();
    }

    private void showDialogNoFull(String titulo, String text, ActionEvent event){
        String title = titulo ;
        String content = text;

        JFXDialogLayout dialogContent = new JFXDialogLayout();
        dialogContent.setHeading(new Text(title));
        dialogContent.setBody(new Text(content));
        dialogContent.setAlignment(Pos.CENTER);

        JFXButton btnClose = new JFXButton();
        btnClose.setText("OK");
        btnClose.setButtonType(JFXButton.ButtonType.RAISED);
        btnClose.setStyle("-fx-background-color: #3a97ff;-fx-font-size:15;-fx-text-fill: White;-fx-pref-width: 50");
        dialogContent.setActions(btnClose);
        JFXDialog dialog = new JFXDialog(myStackPane, dialogContent, JFXDialog.DialogTransition.TOP);

        btnClose.setOnAction(__ -> {
            dialog.close();
            showInvoice(event,false );
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
