package sample.Docs;

import com.itextpdf.io.font.FontConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.TextAlignment;
import javafx.collections.ObservableList;
import sample.models.InvoicesView;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class InvoicesPaidPDF {

    Connection conn;
    public InvoicesPaidPDF(Connection conn) { this.conn = conn; }


    public static final String DATA = "src/sample/resources/data/united_states.csv";

    public void createInvoicesPdf(String dest, ObservableList<InvoicesView> invoicesViews) throws IOException {
        try {
            String query = "SELECT * FROM invoicesView";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            int i = 1;

            //Initialize PDF writer
            PdfWriter writer = new PdfWriter(dest);
            //Initialize PDF document
            PdfDocument pdf = new PdfDocument(writer);
            // Initialize document
            Document document = new Document(pdf, PageSize.A4.rotate());
            document.setMargins(40, 40, 40, 40);
            PdfFont font = PdfFontFactory.createFont(FontConstants.HELVETICA);
            PdfFont bold = PdfFontFactory.createFont(FontConstants.HELVETICA_BOLD);
            PdfFont font1 = PdfFontFactory.createFont(FontConstants.TIMES_BOLD);
            PdfFont font2 = PdfFontFactory.createFont(FontConstants.TIMES_ROMAN);
            Text title = new Text("TELEFONOS DE MEXICO S.A.B DE C.V.").setFont(font1).setFontSize(20);
            Text subtitle = new Text("Reporte de Facturas").setFont(font2).setFontSize(16);
            Paragraph p1 = new Paragraph().add(title).add("\n").add(subtitle).add("\n");
            p1.setTextAlignment(TextAlignment.CENTER);

            Table table = new Table(new float[]{4, 1, 3, 4, 3, 3, 3, 3, 1});
            table.useAllAvailableWidth();
            Cell cell;

            cell = new Cell().add(new Paragraph("No.")).setFont(bold);
            table.addHeaderCell(cell);
            cell = new Cell().add(new Paragraph("Mes")).setFont(bold);
            table.addHeaderCell(cell);
            cell = new Cell().add(new Paragraph("Nombre")).setFont(bold);
            table.addHeaderCell(cell);
            cell = new Cell().add(new Paragraph("Total")).setFont(bold);
            table.addHeaderCell(cell);
            cell = new Cell().add(new Paragraph("Pagado")).setFont(bold);
            table.addHeaderCell(cell);
            cell = new Cell().add(new Paragraph("Fecha limite")).setFont(bold);
            table.addHeaderCell(cell);
            cell = new Cell().add(new Paragraph("Fecha Pago")).setFont(bold);
            table.addHeaderCell(cell);
            cell = new Cell().add(new Paragraph("Telefono")).setFont(bold);
            table.addHeaderCell(cell);
            cell = new Cell().add(new Paragraph("Plan").setFont(bold));
            table.addHeaderCell(cell);
            table.startNewRow();
            for (InvoicesView invoicesView : invoicesViews){
                cell = new Cell().add(new Paragraph(i+"")).setFont(font);
                table.addCell(cell);
                String name_month = invoicesView.getName_month();
                cell = new Cell().add(new Paragraph(name_month)).setFont(font);
                table.addCell(cell);
                String nameCustomer = invoicesView.getNameCustomer();
                cell = new Cell().add(new Paragraph(nameCustomer)).setFont(font);
                table.addCell(cell);
                String total = ""+invoicesView.getTotal();
                cell = new Cell().add(new Paragraph(total)).setFont(font);
                table.addCell(cell);
                String paid_amount = ""+invoicesView.getPaid_amount();
                cell = new Cell().add(new Paragraph(paid_amount)).setFont(font);
                table.addCell(cell);
                String limit_date = ""+invoicesView.getLimit_date();
                cell = new Cell().add(new Paragraph(limit_date)).setFont(font);
                table.addCell(cell);

                String paid_date = ""+invoicesView.getPaid_date();
                cell = new Cell().add(new Paragraph(paid_date)).setFont(font);
                table.addCell(cell);

                String phone_number = ""+invoicesView.getPhone_number();
                cell = new Cell().add(new Paragraph(phone_number)).setFont(font);
                table.addCell(cell);
                String name_plan = invoicesView.getName_plan();
                cell = new Cell().add(new Paragraph(name_plan)).setFont(font);
                table.addCell(cell);
                table.startNewRow();
                i++;
            }

            rs.close();
            st.close();
            document.add(p1).add(table);
            document.close();
            } catch (SQLException ex) {
        ex.printStackTrace();
        System.out.println("Error al recuperar información...");
        }
    }

}
