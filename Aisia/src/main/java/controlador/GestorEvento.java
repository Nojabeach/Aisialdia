package controlador;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modelo.Evento;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;

import dao.DaoEvento;

/**
 * Servlet implementation class GestorEvento
 * 
 * @author Maitane Ibañez Irazabal
 * @version 1.0
 */
public class GestorEvento extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GestorEvento() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Maneja las solicitudes GET enviadas al servlet.
	 *
	 * @param request  Objeto HttpServletRequest que contiene la solicitud HTTP.
	 * @param response Objeto HttpServletResponse que se utilizará para enviar la
	 *                 respuesta HTTP.
	 * @throws ServletException Si se produce un error en el servlet.
	 * @throws IOException      Si se produce un error de entrada/salida.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * Maneja las solicitudes POST enviadas al servlet.
	 *
	 * @param request  Objeto HttpServletRequest que contiene la solicitud HTTP.
	 * @param response Objeto HttpServletResponse que se utilizará para enviar la
	 *                 respuesta HTTP.
	 * @throws ServletException Si se produce un error en el servlet.
	 * @throws IOException      Si se produce un error de entrada/salida.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		try {
			switch (action) {
			case "crearEvento":
				crearEvento(request, response);
				break;
			case "editarEvento":
				editarEvento(request, response);
				break;
			case "eliminarEvento":
				eliminarEvento(request, response);
				break;
			case "publicarEvento":
				publicarEvento(request, response);
				break;
			case "rechazarEvento":
				rechazarEvento(request, response);
				break;
			case "aprobarPublicacionEvento":
				aprobarPublicacionEvento(request, response);
				break;
			default:
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				response.getWriter().write("{\"error\": \"Acción no válida\"}");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().write("{\"error\": \"" + e.getMessage() + "\"}");
		}
	}

	/**
	 * Crea un nuevo evento en la base de datos.
	 *
	 * @param request  Objeto HttpServletRequest que contiene la solicitud HTTP.
	 * @param response Objeto HttpServletResponse que se utilizará para enviar la
	 *                 respuesta HTTP.
	 * @throws IOException      Si se produce un error de entrada/salida.
	 * @throws ServletException Si se produce un error en el servlet.
	 */
	private void crearEvento(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String nombre = request.getParameter("nombre");
		String detalles = request.getParameter("detalles");
		int idUsuarioCreador = Integer.parseInt(request.getParameter("idUsuarioCreador"));
		String ubicacion = request.getParameter("ubicacion");
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		Evento evento = new Evento(nombre, detalles, idUsuarioCreador, ubicacion);
		try {
			DaoEvento.getInstance().crearEvento(evento, timestamp);
			response.setStatus(HttpServletResponse.SC_CREATED);
			response.getWriter().println("Evento creado exitosamente!");
		} catch (SQLException e) {
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().println("Error al crear el evento. Intente de nuevo.");
		}
	}

	/**
	 * Edita un evento existente en la base de datos.
	 *
	 * @param request  Objeto HttpServletRequest que contiene la solicitud HTTP.
	 * @param response Objeto HttpServletResponse que se utilizará para enviar la
	 *                 respuesta HTTP.
	 * @throws SQLException Si se produce un error en la base de datos.
	 * @throws IOException  Si se produce un error de entrada/salida.
	 */
	private void editarEvento(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException {
		int idEvento = Integer.parseInt(request.getParameter("idEvento"));
		String nombre = request.getParameter("nombre");
		String detalles = request.getParameter("detalles");
		Evento evento = new Evento(idEvento, nombre, detalles);
		try {
			DaoEvento.getInstance().editarEvento(evento);
			response.getWriter().println("Evento editado exitosamente!");
		} catch (SQLException e) {
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().println("Error al editar el evento. Intente de nuevo.");
		}
	}

	/**
	 * Elimina un evento existente en la base de datos.
	 *
	 * @param request  Objeto HttpServletRequest que contiene la solicitud HTTP.
	 * @param response Objeto HttpServletResponse que se utilizará para enviar la
	 *                 respuesta HTTP.
	 * @throws SQLException Si se produce un error en la base de datos.
	 * @throws IOException  Si se produce un error de entrada/salida.
	 */
	private void eliminarEvento(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException {
		int idEvento = Integer.parseInt(request.getParameter("idEvento"));
		Evento evento = new Evento(idEvento);
		try {
			DaoEvento.getInstance().eliminarEvento(evento);
			response.getWriter().println("Evento eliminado exitosamente!");
		} catch (SQLException e) {
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().println("Error al eliminar el evento. Intente de nuevo.");
		}
	}

	/**
	 * Publica un evento aprobado de publicación.
	 * 
	 * @param request  Objeto HttpServletRequest que contiene la solicitud HTTP.
	 * @param response Objeto HttpServletResponse que se utilizará para enviar la
	 *                 respuesta HTTP.
	 * @throws SQLException Si se produce un error en la base de datos.
	 * @throws IOException  Si se produce un error de entrada/salida.
	 */
	private void publicarEvento(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException {
		int idEvento = Integer.parseInt(request.getParameter("idEvento"));
		try {
			DaoEvento.getInstance().publicarEvento(idEvento, request);
			response.getWriter().println("Evento publicado exitosamente!");
		} catch (SQLException e) {
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().println("Error al publicar el evento. Intente de nuevo.");
		}
	}

	/**
	 * Rechaza un evento pendiente de aprobación.
	 * 
	 * @param request  Objeto HttpServletRequest que contiene la solicitud HTTP.
	 * @param response Objeto HttpServletResponse que se utilizará para enviar la
	 *                 respuesta HTTP.
	 * @throws SQLException Si se produce un error en la base de datos.
	 * @throws IOException  Si se produce un error de entrada/salida.
	 */
	private void rechazarEvento(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException {
		int idEvento = Integer.parseInt(request.getParameter("idEvento"));

		try {
			DaoEvento.getInstance().rechazarEvento(idEvento, request);
			response.getWriter().println("Evento rechazado exitosamente!");
		} catch (SQLException e) {
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().println("Error al rechazar el evento. Intente de nuevo.");
		}
	}

	/**
	 * Aprueba la publicación de un evento.
	 * 
	 * @param request  Objeto HttpServletRequest que contiene la solicitud HTTP.
	 * @param response Objeto HttpServletResponse que se utilizará para enviar la
	 *                 respuesta HTTP.
	 * @throws SQLException Si se produce un error en la base de datos.
	 * @throws IOException  Si se produce un error de entrada/salida.
	 */
	private void aprobarPublicacionEvento(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException {
		int idEvento = Integer.parseInt(request.getParameter("idEvento"));
		try {
			DaoEvento.getInstance().aprobarPublicacionEvento(idEvento, request);
			response.getWriter().println("Evento aprobado para su publicación!");
		} catch (SQLException e) {
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().println("Error al aprobar la publicación del evento. Intente de nuevo.");
		}
	}

}
