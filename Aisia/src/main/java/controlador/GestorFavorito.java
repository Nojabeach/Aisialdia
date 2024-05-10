package controlador;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

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
	 * Maneja las solicitudes GET enviadas al servlet, realizando diferentes
	 * acciones según el parámetro "action" proporcionado.
	 * 
	 * <p>
	 * Las acciones disponibles son:
	 * <ul>
	 * <li><b>obtenerEventosFavoritos:</b> Obtiene los eventos marcados como
	 * favoritos por un usuario específico y los devuelve en formato JSON.</li>
	 * </ul>
	 * </p>
	 * 
	 * @param request  Objeto HttpServletRequest que contiene la solicitud HTTP.
	 * @param response Objeto HttpServletResponse que se utilizará para enviar la
	 *                 respuesta HTTP.
	 * @throws ServletException Si se produce un error en el servlet.
	 * @throws IOException      Si se produce un error de entrada/salida.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		PrintWriter out = response.getWriter();

		String action = request.getParameter("action");

		try {
			switch (action) {
			case "obtenerEventosFavoritos":
				obtenerEventosFavoritos(request, response, out);
				break;
			default:
				// Si la acción no es válida, se establece un código de estado HTTP 400 (Bad
				// Request)
				// y se envía un mensaje de error genérico
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				response.getWriter().write("{\"error\": \"Acción no válida\"}");
			}
		} catch (Exception e) {
			// En caso de error, se muestra un mensaje de error genérico y se establece un
			// código de estado HTTP 500 (Internal Server Error)
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().write("{\"error\": \"" + e.getMessage() + "\"}");
		}
	}

	/**
	 * Maneja las solicitudes POST enviadas al servlet, realizando diferentes
	 * acciones según el parámetro "action" proporcionado.
	 * 
	 * <p>
	 * Las acciones disponibles son:
	 * <ul>
	 * <li><b>agregarFavorito:</b> Agrega un evento a la lista de favoritos del
	 * usuario.</li>
	 * <li><b>eliminarFavorito:</b> Elimina un evento de la lista de favoritos del
	 * usuario.</li>
	 * </ul>
	 * </p>
	 *
	 * @param request  Objeto HttpServletRequest que contiene la solicitud HTTP.
	 * @param response Objeto HttpServletResponse que se utilizará para enviar la
	 *                 respuesta HTTP.
	 * @throws ServletException Si se produce un error grave durante la ejecución
	 *                          del servlet.
	 * @throws IOException      Si se produce un error de entrada/salida al escribir
	 *                          en el PrintWriter.
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
				// Si la acción no es válida, se establece un código de estado HTTP 400 (Bad
				// Request)
				// y se envía un mensaje de error genérico
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				response.getWriter().write("{\"error\": \"Acción no válida\"}");
			}
		} catch (Exception e) {
			// En caso de error, se muestra un mensaje de error genérico y se establece un
			// código de estado HTTP 500 (Internal Server Error)
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
			System.out.println("Entro a borrar favorito: " + request.getParameter("idEvento"));
			DaoFavorito.getInstance().eliminarFavoritoEvento(idEvento, request);
			response.setStatus(HttpServletResponse.SC_OK);
			response.getWriter().println("Evento eliminado de favoritos exitosamente!");
		} catch (SQLException e) {
			ControlErrores.mostrarErrorGenerico("Error al eliminar el evento de favoritos. Intente de nuevo.",
					response);
		}
	}

	/**
	 * Obtiene los eventos marcados como favoritos por un usuario específico y los
	 * devuelve en formato JSON.
	 * 
	 * @param request  La solicitud HTTP que contiene la session para identificar al
	 *                 usuario.
	 * @param response La respuesta HTTP donde se enviará el resultado en formato
	 *                 JSON.
	 * @param out      El escritor de salida para escribir el resultado JSON en la
	 *                 respuesta.
	 * @throws IOException  Si ocurre un error de entrada/salida al escribir en el
	 *                      PrintWriter.
	 * @throws SQLException Si ocurre un error al interactuar con la base de datos.
	 */
	private void obtenerEventosFavoritos(HttpServletRequest request, HttpServletResponse response, PrintWriter out)
			throws IOException, SQLException {

		HttpSession session = request.getSession();
		int idUsuario = (int) session.getAttribute("idUsuario");
		System.out.println(idUsuario);

		try {

			DaoFavorito favorito = new DaoFavorito();

			// Lista los eventos pendientes de aprobación en formato JSON y los envía al
			// PrintWriter
			out.print(favorito.listarJsonFavoritosUsuario(idUsuario));

		} catch (SQLException e) {
			// En caso de error, muestra un mensaje de error genérico y lo envía en formato
			// JSON a la respuesta
			e.printStackTrace();
			ControlErrores.mostrarErrorGenerico("{\"error\": \"" + e.getMessage() + "\"}", response);
		}
	}
}
