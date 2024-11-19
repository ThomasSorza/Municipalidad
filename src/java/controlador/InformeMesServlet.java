/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import config.clsconexion;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author carlo
 */
@WebServlet("/InformeMesServlet")
public class InformeMesServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=InformeMes.pdf");

        try (OutputStream out = response.getOutputStream()) {
            Document document = new Document();
            PdfWriter.getInstance(document, out);

            document.open();
            document.add(new Paragraph("Informe Mensual de Gastos"));
            document.add(new Paragraph("Fecha de generación: " + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
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
                DATE(gd.FechaGasto) AS Fecha,
                SUM(gd.MontoAsignado) AS TotalMontoAsignado,
                SUM(gd.TotalGastado) AS TotalGastado,
                SUM(gd.Diferencia) AS TotalDiferencia,
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
                MONTH(gd.FechaGasto) = MONTH(CURDATE())
                AND YEAR(gd.FechaGasto) = YEAR(CURDATE())
            GROUP BY 
                DATE(gd.FechaGasto), c.Nombre, uc.Direccion
            ORDER BY 
                Fecha;
        """;

        try (PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            document.add(new Paragraph("Resumen de Gastos Diarios del Mes Actual"));

            if (!rs.isBeforeFirst()) {
                document.add(new Paragraph("No hay datos para el mes actual."));
                System.out.println("No se encontraron datos para el mes actual.");
            } else {
                while (rs.next()) {
                    document.add(new Paragraph("Fecha: " + rs.getDate("Fecha")));
                    document.add(new Paragraph("Total Monto Asignado: " + rs.getDouble("TotalMontoAsignado")));
                    document.add(new Paragraph("Total Gastado: " + rs.getDouble("TotalGastado")));
                    document.add(new Paragraph("Total Diferencia: " + rs.getDouble("TotalDiferencia")));
                    document.add(new Paragraph("Nombre Cocinero: " + rs.getString("NombreCocinero")));
                    document.add(new Paragraph("Ubicación Comedor: " + rs.getString("UbicacionComedor")));
                    document.add(new Paragraph("----------------------------------------------------"));
                }
                System.out.println("Informe resumido de gastos del mes generado correctamente.");
            }
            document.add(new Paragraph(" "));
        }
    }
}