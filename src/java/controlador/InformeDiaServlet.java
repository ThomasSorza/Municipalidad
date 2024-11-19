package controlador;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import config.clsconexion;
import java.sql.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@WebServlet("/InformeDiaServlet")
public class InformeDiaServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=InformeDia.pdf");

        try (OutputStream out = response.getOutputStream()) {
            Document document = new Document();
            PdfWriter.getInstance(document, out);

            document.open();
            document.add(new Paragraph("Informe Diario"));
            document.add(new Paragraph("Fecha: " + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
            document.add(new Paragraph(" "));
            
            clsconexion conexion = new clsconexion();
            Connection conn = conexion.getConnection();

            agregarDatosGastoDiario(conn, document);

            document.close();
        } catch (DocumentException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void agregarDatosGastoDiario(Connection conn, Document document) throws DocumentException, SQLException {
        String query = """
            SELECT 
                gd.ID_Gasto,
                gd.MontoAsignado,
                gd.TotalGastado,
                gd.Diferencia,
                gd.FechaGasto,
                ep.ID_Entrega,
                ep.FechaHoraEntrega AS FechaEntrega,
                c.Nombre AS NombreCocinero,
                uc.Direccion AS UbicacionComedor
            FROM 
                GastoDiario gd
            LEFT JOIN 
                Cocinero c ON gd.ID_Cocinero = c.ID_Cocinero
            LEFT JOIN 
                EntregaPlato ep ON c.ID_Cocinero = ep.ID_Cocinero
            LEFT JOIN 
                PersonaPobre pp ON ep.ID_PersonaPobre = pp.ID_PersonaPobre
            LEFT JOIN 
                UbicacionComedor uc ON pp.ID_Comedor = uc.ID_Comedor
            WHERE 
                DATE(gd.FechaGasto) = CURDATE();
        """;

        try (PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            document.add(new Paragraph("Datos de Gasto Diario"));

            if (!rs.isBeforeFirst()) {
                document.add(new Paragraph("No hay datos para el día actual."));
                System.out.println("No se encontraron datos para el día actual.");
            } else {
                while (rs.next()) {
                    document.add(new Paragraph("ID Gasto: " + rs.getInt("ID_Gasto")));
                    document.add(new Paragraph("Monto Asignado: " + (rs.getObject("MontoAsignado") != null ? rs.getDouble("MontoAsignado") : "problema")));
                    document.add(new Paragraph("Total Gastado: " + (rs.getObject("TotalGastado") != null ? rs.getDouble("TotalGastado") : "problema")));
                    document.add(new Paragraph("Diferencia: " + (rs.getObject("Diferencia") != null ? rs.getDouble("Diferencia") : "problema")));
                    document.add(new Paragraph("Fecha Gasto: " + (rs.getObject("FechaGasto") != null ? rs.getTimestamp("FechaGasto") : "problema")));
                    document.add(new Paragraph("ID Entrega: " + (rs.getObject("ID_Entrega") != null ? rs.getInt("ID_Entrega") : "problema")));
                    document.add(new Paragraph("Fecha Entrega: " + (rs.getObject("FechaEntrega") != null ? rs.getTimestamp("FechaEntrega") : "problema")));
                    document.add(new Paragraph("Nombre Cocinero: " + (rs.getObject("NombreCocinero") != null ? rs.getString("NombreCocinero") : "problema")));
                    document.add(new Paragraph("Ubicación Comedor: " + (rs.getObject("UbicacionComedor") != null ? rs.getString("UbicacionComedor") : "problema")));
                    document.add(new Paragraph("----------------------------------------------------"));
                }
                System.out.println("Informe detallado de gasto diario generado correctamente.");
            }
            document.add(new Paragraph(" "));
        }
    }
}
