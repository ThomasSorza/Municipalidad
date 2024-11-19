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
@WebServlet("/InformeAñoServlet")
public class InformeAñoServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=InformeAño.pdf");

        try (OutputStream out = response.getOutputStream()) {
            Document document = new Document();
            PdfWriter.getInstance(document, out);

            document.open();
            document.add(new Paragraph("Informe Anual de Gastos"));
            document.add(new Paragraph("Fecha de generación: " + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
            document.add(new Paragraph(" ")); // Espacio en blanco

            // Establece la conexión con la base de datos
            clsconexion conexion = new clsconexion();
            Connection conn = conexion.getConnection();

            // Agregar datos de gasto anual
            agregarDatosGastoAnual(conn, document);

            document.close();
        } catch (DocumentException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void agregarDatosGastoAnual(Connection conn, Document document) throws DocumentException, SQLException {
        String query = """
            SELECT 
                YEAR(gd.FechaGasto) AS Año,
                SUM(gd.MontoAsignado) AS TotalMontoAsignado,
                SUM(gd.TotalGastado) AS TotalGastadoAnual,
                SUM(gd.Diferencia) AS TotalDiferencia,
                COUNT(gd.ID_Gasto) AS NumeroGastos,
                COUNT(DISTINCT ep.ID_Entrega) AS NumeroEntregas,
                COUNT(DISTINCT c.ID_Cocinero) AS NumeroCocineros,
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
            GROUP BY 
                Año, uc.Direccion
            ORDER BY 
                Año;
        """;

        try (PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            document.add(new Paragraph("Resumen de Gastos Anuales"));

            if (!rs.isBeforeFirst()) {
                document.add(new Paragraph("No hay datos para el año actual."));
                System.out.println("No se encontraron datos para el año actual.");
            } else {
                while (rs.next()) {
                    document.add(new Paragraph("Año: " + rs.getInt("Año")));
                    document.add(new Paragraph("Total Monto Asignado: " + rs.getDouble("TotalMontoAsignado")));
                    document.add(new Paragraph("Total Gastado: " + rs.getDouble("TotalGastadoAnual")));
                    document.add(new Paragraph("Total Diferencia: " + rs.getDouble("TotalDiferencia")));
                    document.add(new Paragraph("Número de Gastos: " + rs.getInt("NumeroGastos")));
                    document.add(new Paragraph("Número de Entregas: " + rs.getInt("NumeroEntregas")));
                    document.add(new Paragraph("Número de Cocineros: " + rs.getInt("NumeroCocineros")));
                    document.add(new Paragraph("Ubicación Comedor: " + rs.getString("UbicacionComedor")));
                    document.add(new Paragraph("----------------------------------------------------"));
                }
                System.out.println("Informe anual de gastos generado correctamente.");
            }
            document.add(new Paragraph(" "));
        }
    }
}