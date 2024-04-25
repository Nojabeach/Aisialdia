package controlador;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modelo.Actividad;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.google.gson.Gson;

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
		String action = request.getParameter("action"); // Obtener la acción del parámetro

		try {
			switch (action) {
			case "obtenerActividades":
				obtenerActividadesPorEventoID(request, response);
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
			case "crearClasificacion":
				crearClasificacionEventos(request, response);
				break;
			case "eliminarClasificacion":
				eliminarClasificacionEventos(request, response);
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
	 * Obtiene las actividades asociadas a un evento específico y las envía como
	 * respuesta.
	 *
	 * @param request  Objeto HttpServletRequest que contiene la solicitud HTTP.
	 * @param response Objeto HttpServletResponse que se utilizará para enviar la
	 *                 respuesta HTTP.
	 * @throws IOException  Si se produce un error de entrada/salida.
	 * @throws SQLException Si se produce un error al acceder a la base de datos.
	 */
	private void obtenerActividadesPorEventoID(HttpServletRequest request, HttpServletResponse response)
			throws IOException, SQLException {
		int idEvento = Integer.parseInt(request.getParameter("idEvento"));

		try {
			List<Actividad> actividades = DaoClasificacionEventos.getInstance().obtenerActividadesPorEventoID(idEvento);

			// Convertir la lista de actividades a JSON
			String actividadesJSON = new Gson().toJson(actividades);

			// Establecer el tipo de contenido y encabezados de la respuesta
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");

			// Enviar la lista de actividades como respuesta
			response.getWriter().write(actividadesJSON);
		} catch (SQLException e) {
			ControlErrores.mostrarErrorGenerico("Error al obtener las actividades del evento. Intente de nuevo.",
					response);
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
