package sample.Docs;

import com.itextpdf.io.font.FontConstants;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.draw.DashedLine;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.LineSeparator;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.test.annotations.WrapToTest;
import sample.models.Invoice;
import sample.models.dao.InvoiceDAO;
import sample.models.dao.MySQL;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Ticket example by niluxer.
 */
//@WrapToTest
public class Ticket {

    private Invoice invoice = new Invoice();
    private InvoiceDAO invoiceDAO = new InvoiceDAO(MySQL.getConnection());
    private Double money, cambio;
    public void setInvoice(Invoice invoice, Double money, Double cambio) {
        this.invoice = invoice;
        this.money = money;
        this.cambio = cambio;
    }

    public static final String TELMEX = "rsc/Telmex_logo3.png";

    public void createTicketPdf(String dest) throws IOException {
        //Initialize PDF writer
        PdfWriter writer = new PdfWriter(dest);

        //Initialize PDF document
        PdfDocument pdf = new PdfDocument(writer);

        // Initialize document
        //Document document = new Document(pdf, pageSize);
        Document document = new Document(pdf);
        document.setMargins(50, 150, 50, 150);

        PdfFont font1 = PdfFontFactory.createFont(FontConstants.TIMES_BOLD);
        PdfFont font2 = PdfFontFactory.createFont(FontConstants.TIMES_ROMAN);

        Text title = new Text("TELEFONOS DE MEXICO S.A.B DE C.V.").setFont(font1).setFontSize(15);
        Text subtitle = new Text("PARQUE VIA NO.198 \n COL. CUAUCHTEMOC C.P. 06500\n MEXICO, CIUDAD DE MEXICO \n R.F.C. TME-840315-KT6 \nCENTRO DE ATENCION").setFont(font2).setFontSize(10);

        Paragraph p1 = new Paragraph().add(title).add("\n").add(subtitle);
        p1.setTextAlignment(TextAlignment.CENTER);

        //DottedLine dottedLine = new DottedLine(1);
        DashedLine dashedLine = new DashedLine(1);

        Text text1 = new Text("COMPROBANTE DE PAGO").setFont(font2).setFontSize(12).setBold();
        Paragraph p2 = new Paragraph().add(text1).add("\n");
        p2.setTextAlignment(TextAlignment.CENTER);

        Text nameClient = new Text(""+invoice.getId_customer().getLast_name()+" "+invoice.getId_customer().getFirst_name()).setFont(font2).setFontSize(10);
        Text text2 = new Text("     TELEFONO: "+invoice.getId_customer().getPhone_number()).setFont(font2).setFontSize(10);
        Text text3 = new Text("     FECHA   : "+ LocalDate.now()).setFont(font2).setFontSize(10);
        Text text4 = new Text("     CAJERO  : CCAT1912").setFont(font2).setFontSize(10);
        Text text5 = new Text("     OFICINA : SJR").setFont(font2).setFontSize(10);
        Text text6 = new Text("     FOLIO   : "+invoice.getNo_invoice()).setFont(font2).setFontSize(10);
        Paragraph p3 = new Paragraph().add(nameClient).add(text2).add("\n").add(text3).add("\n").add(text4).add("\n").add(text5).add("\n").add(text6).add("\n");
        p3.setTextAlignment(TextAlignment.LEFT);

        Text textTi = new Text("PAYMENT").setFont(font2).setFontSize(12).setBold();
        Paragraph p4_0 = new Paragraph().add(textTi).add("\n");
        p4_0.setTextAlignment(TextAlignment.CENTER);

        Text text7 = new Text("     SALDO: "+invoice.getId_plan().getTotal()).setFont(font2).setFontSize(10);
        Text text8 = new Text("     PAGO   : "+money).setFont(font2).setFontSize(10);
        Text text9 = new Text("     CAMBIO  : "+cambio).setFont(font2).setFontSize(10);
        Paragraph p4 = new Paragraph().add(text7).add("\n").add(text8).add("\n").add(text9).add("\n");
        p4.setTextAlignment(TextAlignment.LEFT);


        //Image telmex = new Image(ImageDataFactory.create(TELMEX));
        //telmex.setWidth(200).setHeight(100).setTextAlignment(TextAlignment.CENTER);

        //Add paragraph to the document
        document.add(new Paragraph("\n")).add(p1).add(new LineSeparator(dashedLine)).add(p2).add(p3);
        document.add(new LineSeparator(dashedLine)).add(p4_0).add(p4);

        //Close document
        document.close();
    }
}
