package controlador;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modelo.Evento;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.google.gson.Gson;

import dao.DaoFavorito;

/**
 * Servlet implementation class GestorFavorito
 */
public class GestorFavorito extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GestorFavorito() {
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
		String action = request.getParameter("action");

		try {
			switch (action) {

			case "obtenerEventosFavoritos":
				obtenerEventosFavoritos(request, response);
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
			case "agregarFavorito":
				agregarFavorito(request, response);
				break;
			case "eliminarFavorito":
				eliminarFavorito(request, response);
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
	 * Agrega un evento a la lista de favoritos de un usuario.
	 *
	 * @param request  Objeto HttpServletRequest que contiene la solicitud HTTP.
	 * @param response Objeto HttpServletResponse que se utilizará para enviar la
	 *                 respuesta HTTP.
	 * @throws IOException  Si se produce un error de entrada/salida.
	 * @throws SQLException Si se produce un error al acceder a la base de datos.
	 */
	private void agregarFavorito(HttpServletRequest request, HttpServletResponse response)
			throws IOException, SQLException {
		int idEvento = Integer.parseInt(request.getParameter("idEvento"));
		try {
			DaoFavorito.getInstance().agregarFavoritoEvento(idEvento, request);
			response.setStatus(HttpServletResponse.SC_CREATED);
			response.getWriter().println("Evento agregado a favoritos exitosamente!");
		} catch (SQLException e) {
			ControlErrores.mostrarErrorGenerico("Error al agregar el evento a favoritos. Intente de nuevo.", response);
		}
	}

	/**
	 * Elimina un evento de la lista de favoritos de un usuario.
	 *
	 * @param request  Objeto HttpServletRequest que contiene la solicitud HTTP.
	 * @param response Objeto HttpServletResponse que se utilizará para enviar la
	 *                 respuesta HTTP.
	 * @throws IOException  Si se produce un error de entrada/salida.
	 * @throws SQLException Si se produce un error al acceder a la base de datos.
	 */
	private void eliminarFavorito(HttpServletRequest request, HttpServletResponse response)
			throws IOException, SQLException {
		int idEvento = Integer.parseInt(request.getParameter("idEvento"));
		try {
			DaoFavorito.getInstance().eliminarFavoritoEvento(idEvento, request);
			response.setStatus(HttpServletResponse.SC_OK);
			response.getWriter().println("Evento eliminado de favoritos exitosamente!");
		} catch (SQLException e) {
			ControlErrores.mostrarErrorGenerico("Error al eliminar el evento de favoritos. Intente de nuevo.",
					response);
		}
	}

	/**
	 * Obtiene la lista de eventos favoritos de un usuario.
	 *
	 * @param request  Objeto HttpServletRequest que contiene la solicitud HTTP.
	 * @param response Objeto HttpServletResponse que se utilizará para enviar la
	 *                 respuesta HTTP.
	 * @throws IOException  Si se produce un error de entrada/salida.
	 * @throws SQLException Si se produce un error al acceder a la base de datos.
	 */
	private void obtenerEventosFavoritos(HttpServletRequest request, HttpServletResponse response)
			throws IOException, SQLException {
		int idUsuario = Integer.parseInt(request.getParameter("idUsuario"));
		try {
			List<Evento> eventosFavoritos = DaoFavorito.getInstance().obtenerEventosFavoritosUsuario(idUsuario);

			String eventosJSON = new Gson().toJson(eventosFavoritos);

			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");

			response.getWriter().write(eventosJSON);
		} catch (Exception e) {
			ControlErrores.mostrarErrorGenerico("Error al obtener los eventos favoritos del usuario. Intente de nuevo.",
					response);
		}
	}
}
