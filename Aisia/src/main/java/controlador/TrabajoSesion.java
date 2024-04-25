package controlador;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet implementation class TrabajoSesion
 */
public class TrabajoSesion extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TrabajoSesion() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * Maneja las solicitudes GET enviadas al servlet.
     *
     * @param request  Objeto HttpServletRequest que contiene la solicitud HTTP.
     * @param response Objeto HttpServletResponse que se utilizará para enviar la respuesta HTTP.
     * @throws ServletException Si se produce un error en el servlet.
     * @throws IOException      Si se produce un error de entrada/salida.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession sesion = request.getSession();
        String idUsuario = (String) sesion.getAttribute("idUsuario");

        PrintWriter out = response.getWriter();
        response.setContentType("text/plain");

        if (idUsuario != null) {
            out.print("ID de Usuario: " + idUsuario);
        } else {
            out.print("No hay sesión de usuario iniciada");
        }
    }

    /**
     * Maneja las solicitudes POST enviadas al servlet.
     *
     * @param request  Objeto HttpServletRequest que contiene la solicitud HTTP.
     * @param response Objeto HttpServletResponse que se utilizará para enviar la respuesta HTTP.
     * @throws ServletException Si se produce un error en el servlet.
     * @throws IOException      Si se produce un error de entrada/salida.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

}
