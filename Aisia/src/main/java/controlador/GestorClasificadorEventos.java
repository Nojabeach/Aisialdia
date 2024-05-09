package controlador;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import dao.DaoClasificacionEventos;

/**
 * Servlet implementation class GestorClasificadorEventos
 * 
 * @author Maitane Ibañez Irazabal
 * @version 1.0
 */
public class GestorClasificadorEventos extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GestorClasificadorEventos() {
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
	 * <li><b>obtenerActividades:</b> Obtiene las actividades asociadas a un evento
	 * específico y las devuelve en formato JSON.</li>
	 * <li><b>obtenerEvento:</b> Obtiene los eventos asociados a una actividad
	 * específica y los devuelve en formato JSON.</li>
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
		String action = request.getParameter("action"); // Obtener la acción del parámetro
		PrintWriter out = response.getWriter();
		try {
			switch (action) {
			case "obtenerActividades":
				obtenerActividadesPorEventoID(request, response, out);
				break;
			case "obtenerEvento":
				obtenerEventosPorActividadID(request, response, out);
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
	 * <li><b>crearClasificacion:</b> Crea una nueva clasificación de eventos con la
	 * información proporcionada en la solicitud.</li>
	 * <li><b>eliminarClasificacion:</b> Elimina una clasificación de eventos según
	 * el ID proporcionado en la solicitud.</li>
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
			case "crearClasificacion":
				crearClasificacionEventos(request, response);
				break;
			case "eliminarClasificacion":
				eliminarClasificacionEventos(request, response);
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
	 * Obtiene las actividades asociadas a un evento específico y las devuelve en
	 * formato JSON.
	 * 
	 * @param request  La solicitud HTTP que contiene el parámetro "idEvento" para
	 *                 identificar el evento.
	 * @param response La respuesta HTTP donde se enviará el resultado en formato
	 *                 JSON.
	 * @param out      El escritor de salida para escribir el resultado JSON en la
	 *                 respuesta.
	 * @throws IOException  Si ocurre un error de entrada/salida al escribir en el
	 *                      PrintWriter.
	 * @throws SQLException Si ocurre un error al interactuar con la base de datos.
	 */
	private void obtenerActividadesPorEventoID(HttpServletRequest request, HttpServletResponse response,
			PrintWriter out) throws IOException, SQLException {
		int idEvento = Integer.parseInt(request.getParameter("idEvento"));

		try {
			// Crea un objeto DaoClasificacionEventos para manejar la obtención de eventos
			// con actividad
			DaoClasificacionEventos eventos = new DaoClasificacionEventos();

			// Lista las actividades asociadas al evento en formato JSON y las envía al
			// PrintWriter
			out.print(eventos.listarJsonActividadesPorEventoID(idEvento));

		} catch (SQLException e) {
			// En caso de error, muestra un mensaje de error genérico y lo envía en formato
			// JSON a la respuesta
			e.printStackTrace();
			ControlErrores.mostrarErrorGenerico("{\"error\": \"" + e.getMessage() + "\"}", response);
		}
	}

	/**
	 * Obtiene los eventos asociados a una actividad específica y los devuelve en
	 * formato JSON.
	 * 
	 * @param request  La solicitud HTTP que contiene el parámetro "idActividad"
	 *                 para identificar la actividad.
	 * @param response La respuesta HTTP donde se enviará el resultado en formato
	 *                 JSON.
	 * @param out      El escritor de salida para escribir el resultado JSON en la
	 *                 respuesta.
	 * @throws IOException  Si ocurre un error de entrada/salida al escribir en el
	 *                      PrintWriter.
	 * @throws SQLException Si ocurre un error al interactuar con la base de datos.
	 */
	private void obtenerEventosPorActividadID(HttpServletRequest request, HttpServletResponse response, PrintWriter out)
			throws IOException, SQLException {
		int idActividad = Integer.parseInt(request.getParameter("idActividad"));

		try {
			// Crea un objeto DaoClasificacionEventos para manejar la obtención de eventos
			// con actividad
			DaoClasificacionEventos eventos = new DaoClasificacionEventos();

			// Lista los eventos asociados a la actividad en formato JSON y los envía al
			// PrintWriter
			out.print(eventos.listarJsonEventosporActividadID(idActividad));

		} catch (SQLException e) {
			// En caso de error, muestra un mensaje de error genérico y lo envía en formato
			// JSON a la respuesta
			e.printStackTrace();
			ControlErrores.mostrarErrorGenerico("{\"error\": \"" + e.getMessage() + "\"}", response);
		}
	}

	/**
	 * Crea una nueva clasificación de eventos en la base de datos.
	 *
	 * @param request  Objeto HttpServletRequest que contiene la solicitud HTTP.
	 * @param response Objeto HttpServletResponse que se utilizará para enviar la
	 *                 respuesta HTTP.
	 * @throws IOException  Si se produce un error de entrada/salida.
	 * @throws SQLException Si se produce un error al acceder a la base de datos.
	 */
	private void crearClasificacionEventos(HttpServletRequest request, HttpServletResponse response)
			throws IOException, SQLException {
		int idActividad = Integer.parseInt(request.getParameter("idActividad"));
		int idEvento = Integer.parseInt(request.getParameter("idEvento"));
		try {
			DaoClasificacionEventos.getInstance().crearClasificacionEventos(idActividad, idEvento);
			response.setStatus(HttpServletResponse.SC_CREATED);
			response.getWriter().println("Clasificación de eventos creada exitosamente!");
		} catch (SQLException e) {
			ControlErrores.mostrarErrorGenerico("Error al crear la clasificación de eventos. Intente de nuevo.",
					response);
		}
	}

	/**
	 * Elimina una clasificación de eventos de la base de datos.
	 *
	 * @param request  Objeto HttpServletRequest que contiene la solicitud HTTP.
	 * @param response Objeto HttpServletResponse que se utilizará para enviar la
	 *                 respuesta HTTP.
	 * @throws IOException  Si se produce un error de entrada/salida.
	 * @throws SQLException Si se produce un error al acceder a la base de datos.
	 */
	private void eliminarClasificacionEventos(HttpServletRequest request, HttpServletResponse response)
			throws IOException, SQLException {
		int idClasificacion = Integer.parseInt(request.getParameter("idClasificacion"));
		try {
			DaoClasificacionEventos.getInstance().eliminarClasificacionEventos(idClasificacion);
			response.setStatus(HttpServletResponse.SC_OK);
			response.getWriter().println("Clasificación de eventos eliminada exitosamente!");
		} catch (SQLException e) {
			ControlErrores.mostrarErrorGenerico("Error al eliminar la clasificación de eventos. Intente de nuevo.",
					response);
		}
	}
}
