<%-- 
    Document   : dashboard
    Created on : 4 nov. 2024, 10:08:26 a. m.
    Author     : carlo
--%>

<%@page import="modelo.UsuarioMunicipalidad"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);
%>
<%
    UsuarioMunicipalidad usuarioLogueado = (UsuarioMunicipalidad) session.getAttribute("usuarioLogueado");
    if (usuarioLogueado == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <title>Dashboard - Municipalidad</title>
        <style>
            /* Estilos básicos */
            body {
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100vh;
                margin: 0;
                font-family: Arial, sans-serif;
                background-color: #f7f2e8;
            }
            /* Contenedor del dashboard */
            .dashboard {
                text-align: center;
                background-color: #ffcc00;
                padding: 40px;
                border-radius: 10px;
                box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2);
                width: 80%;
                max-width: 600px;
            }
            /* Título de bienvenida */
            .dashboard h2 {
                font-size: 2em;
                color: #b03060;
                margin-bottom: 30px;
            }
            /* Botones */
            .dashboard button {
                width: 80%;
                font-size: 1.2em;
                padding: 10px;
                margin: 10px 0;
                background-color: #b03060;
                color: #fff;
                border: none;
                border-radius: 5px;
                cursor: pointer;
                transition: background-color 0.3s;
            }
            /* Efecto hover en botones */
            .dashboard button:hover {
                background-color: #d63b67;
            }
            /* Botón de cierre de sesión */
            .dashboard .logout-button {
                background-color: #333;
            }
            .dashboard .logout-button:hover {
                background-color: #555;
            }
        </style>
    </head>
    <body>
        <div class="dashboard">
            <h2>Bienvenido, <%= usuarioLogueado.getUsuario() %></h2>
            <!-- Botones de informe -->
            <form action="InformeDiaServlet" method="get">
                <button type="submit">Informe del Día</button>
            </form>
            <form action="InformeMesServlet" method="post">
                <button type="submit">Informe del Mes</button>
            </form>
            <form action="InformeAñoServlet" method="post">
                <button type="submit">Informe del Año</button>
            </form>
            <!-- Botón de cerrar sesión -->
            <form action="logout" method="post">
                <button type="submit" class="logout-button">Cerrar Sesión</button>
            </form>
        </div>
    </body>
</html>