package controlador;

import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import modelo.UsuarioMunicipalidad;
import modeloDAO.UsuarioMunicipalidadDAO;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final UsuarioMunicipalidadDAO usuarioDAO = new UsuarioMunicipalidadDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuarioLogueado") == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        request.getRequestDispatcher("dashboard.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String usuario = request.getParameter("usuario");
        String contraseña = request.getParameter("contraseña");
        
        UsuarioMunicipalidad usuarioMunicipalidad = usuarioDAO.validarUsuario(usuario, contraseña);

        if (usuarioMunicipalidad != null && usuarioMunicipalidad.getEstado() == 1) {
            HttpSession session = request.getSession(true);
            session.invalidate(); // Invalida la sesión actual
            session = request.getSession(true); // Crea una nueva sesión
            session.setAttribute("usuarioLogueado", usuarioMunicipalidad);
            response.sendRedirect("dashboard.jsp");
        } else {
            request.setAttribute("mensajeError", "Usuario o contraseña incorrecta");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}