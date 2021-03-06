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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.models.Customer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.StringTokenizer;

public class CustomersPDF {

    Connection conn;
    public CustomersPDF(Connection conn) { this.conn = conn; }

    public void createPdf(String dest) throws IOException {
        try {
            String query = "SELECT * FROM viewcustomer";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);

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
            Text subtitle = new Text("Reporte de Clientes").setFont(font2).setFontSize(16);
            Paragraph p1 = new Paragraph().add(title).add("\n").add(subtitle).add("\n");
            p1.setTextAlignment(TextAlignment.CENTER);


            Table table = new Table(new float[]{4, 1, 3, 4, 3, 3, 3, 3, 1});
            table.useAllAvailableWidth();
            Cell cell = new Cell();

            cell = new Cell().add(new Paragraph("idCustomer")).setFont(bold);
            table.addHeaderCell(cell);
            cell = new Cell().add(new Paragraph("First Name")).setFont(bold);
            table.addHeaderCell(cell);
            cell = new Cell().add(new Paragraph("Last Name")).setFont(bold);
            table.addHeaderCell(cell);
            cell = new Cell().add(new Paragraph("Address")).setFont(bold);
            table.addHeaderCell(cell);
            cell = new Cell().add(new Paragraph("Phone number")).setFont(bold);
            table.addHeaderCell(cell);
            cell = new Cell().add(new Paragraph("CP")).setFont(bold);
            table.addHeaderCell(cell);
            cell = new Cell().add(new Paragraph("Name City").setFont(bold));
            table.addHeaderCell(cell);
            table.startNewRow();
            while (rs.next()){
                String id_customer = ""+rs.getInt("id_customer");
                cell = new Cell().add(new Paragraph(id_customer)).setFont(font);
                table.addCell(cell);
                String first_name = rs.getString("first_name");
                cell = new Cell().add(new Paragraph(first_name)).setFont(font);
                table.addCell(cell);
                String last_name = rs.getString("last_name");
                cell = new Cell().add(new Paragraph(last_name)).setFont(font);
                table.addCell(cell);
                String address = rs.getString("address");
                cell = new Cell().add(new Paragraph(address)).setFont(font);
                table.addCell(cell);
                String phone_number = rs.getString("phone_number");
                cell = new Cell().add(new Paragraph(phone_number)).setFont(font);
                table.addCell(cell);
                String cp = ""+rs.getInt("cp");
                cell = new Cell().add(new Paragraph(cp)).setFont(font);
                table.addCell(cell);
                String name_city = rs.getString("name_city");
                cell = new Cell().add(new Paragraph(name_city)).setFont(font);
                table.addCell(cell);
                table.startNewRow();
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
