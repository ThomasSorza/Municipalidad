<%-- 
    Document   : login
    Created on : 7 nov. 2024, 1:16:56 p. m.
    Author     : carlo
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);
%>

<!DOCTYPE html>
<html lang="es">
    <head>
        <title>Login - Municipalidad</title>
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
            /* Contenedor del login */
            .login-container {
                text-align: center;
                background-color: #ffcc00;
                padding: 40px;
                border-radius: 10px;
                box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2);
                width: 80%;
                max-width: 400px;
            }
            /* Título de login */
            .login-container h2 {
                font-size: 2em;
                color: #b03060;
                margin-bottom: 30px;
            }
            /* Campos del formulario */
            .login-container label {
                display: block;
                margin: 10px 0 5px;
            }
            .login-container input[type="text"],
            .login-container input[type="password"] {
                width: 90%;
                padding: 10px;
                border: 1px solid #ccc;
                border-radius: 5px;
            }
            /* Botón de inicio de sesión */
            .login-container input[type="submit"] {
                width: 95%;
                padding: 10px;
                margin-top: 20px;
                background-color: #b03060;
                color: #fff;
                border: none;
                border-radius: 5px;
                cursor: pointer;
                font-size: 1em;
                transition: background-color 0.3s;
            }
            .login-container input[type="submit"]:hover {
                background-color: #d63b67;
            }
            /* Mensaje de error */
            .error-message {
                color: red;
                margin-top: 20px;
            }
        </style>
    </head>
    <body>
        <div class="login-container">
            <h2>Login Municipalidad</h2>
            <form action="login" method="post">
                <label for="usuario">Usuario:</label>
                <input type="text" name="usuario" id="usuario" required><br>
                
                <label for="contraseña">Contraseña:</label>
                <input type="password" name="contraseña" id="contraseña" required><br>
                
                <label for="recordar">
                    <input type="checkbox" name="recordar" id="recordar"> Recordar sesión
                </label><br>
                
                <input type="submit" value="Iniciar sesión">
            </form>
            <c:if test="${not empty mensajeError}">
                <p class="error-message">${mensajeError}</p>
            </c:if>
        </div>
    </body>
</html>