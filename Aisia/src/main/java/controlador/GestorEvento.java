package controlador;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import dao.DaoEvento;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modelo.Evento;

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
		String action = request.getParameter("action");
		try {
			switch (action) {
			case "obtenerEventoPorId":
				obtenerEventoPorId(request, response);
				break;
			case "obtenerEventosPorUsuario":
				obtenerEventosPorUsuario(request, response);
				break;
			case "obtenerEventosPendientesAprobacion":
				obtenerEventosPendientesAprobacion(request, response);
				break;
			case "obtenerTodosLosEventosActivos":
				obtenerTodosLosEventosActivos(request, response);
				break;
			default:
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				ControlErrores.mostrarErrorGenerico("{\"error\": \"Acción no válida\"}", response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			ControlErrores.mostrarErrorGenerico("{\"error\": \"" + e.getMessage() + "\"}", response);
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
			case "finalizarPublicacionEvento":
				finalizarPublicacionEvento(request, response);
				break;
			default:
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				ControlErrores.mostrarErrorGenerico("{\"error\": \"Acción no válida\"}", response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			ControlErrores.mostrarErrorGenerico("{\"error\": \"" + e.getMessage() + "\"}", response);
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
		String fechaEventoStr = request.getParameter("fechaEvento");
		Date fechaEvento = Date.valueOf(fechaEventoStr); // Convertir la cadena a Date
		int idUsuarioCreador = Integer.parseInt(request.getParameter("idUsuarioCreador"));

		String ubicacion = request.getParameter("ubicacion");
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		Evento evento = new Evento(nombre, detalles, fechaEvento, idUsuarioCreador, ubicacion);
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
		String fechaEventoStr = request.getParameter("fechaEvento");
		Date fechaEvento = Date.valueOf(fechaEventoStr); // Convertir la cadena a Date
		Evento evento = new Evento(idEvento, nombre, detalles, fechaEvento);
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

	/**
	 * Finaliza la publicación de un evento.
	 * 
	 * @param request  Objeto HttpServletRequest que contiene la solicitud HTTP.
	 * @param response Objeto HttpServletResponse que se utilizará para enviar la
	 *                 respuesta HTTP.
	 * @throws SQLException Si se produce un error en la base de datos.
	 * @throws IOException  Si se produce un error de entrada/salida.
	 */
	private void finalizarPublicacionEvento(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException {
		int idEvento = Integer.parseInt(request.getParameter("idEvento"));
		int idModerador = Integer.parseInt(request.getParameter("idModerador"));
		try {
			DaoEvento.getInstance().finalizarPublicacionEvento(idEvento, idModerador);
			response.getWriter().println("Evento finalizado exitosamente!");
		} catch (SQLException e) {
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().println("Error al finalizar el evento. Intente de nuevo.");
		}
	}

	/**
	 * Obtiene un evento específico por su ID.
	 * 
	 * @param request  Objeto HttpServletRequest que contiene la solicitud HTTP.
	 * @param response Objeto HttpServletResponse que se utilizará para enviar la
	 *                 respuesta HTTP.
	 * @throws SQLException Si se produce un error en la base de datos.
	 * @throws IOException  Si se produce un error de entrada/salida.
	 */
	private void obtenerEventoPorId(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException {
		int idEvento = Integer.parseInt(request.getParameter("idEvento"));
		try {
			Evento evento = DaoEvento.getInstance().obtenerEventoPorId(idEvento);
			if (evento != null) {
				response.getWriter().println("Evento encontrado: " + evento.toString());
			} else {
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
				response.getWriter().println("Evento no encontrado.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().println("Error al obtener el evento. Intente de nuevo.");
		}
	}

	/**
	 * Obtiene la lista de eventos organizados por un usuario específico.
	 * 
	 * @param request  Objeto HttpServletRequest que contiene la solicitud HTTP.
	 * @param response Objeto HttpServletResponse que se utilizará para enviar la
	 *                 respuesta HTTP.
	 * @throws SQLException Si se produce un error en la base de datos.
	 * @throws IOException  Si se produce un error de entrada/salida.
	 */
	private void obtenerEventosPorUsuario(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException {
		int idUsuario = Integer.parseInt(request.getParameter("idUsuario"));
		try {
			List<Evento> eventos = DaoEvento.getInstance().obtenerEventosPorUsuario(idUsuario);
			if (eventos != null && !eventos.isEmpty()) {
				response.getWriter().println("Eventos del usuario: ");
				for (Evento evento : eventos) {
					response.getWriter().println(evento.toString());
				}
			} else {
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
				response.getWriter().println("No se encontraron eventos para el usuario.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().println("Error al obtener los eventos del usuario. Intente de nuevo.");
		}
	}

	/**
	 * Obtiene la lista de eventos pendientes de aprobación.
	 * 
	 * @param request  Objeto HttpServletRequest que contiene la solicitud HTTP.
	 * @param response Objeto HttpServletResponse que se utilizará para enviar la
	 *                 respuesta HTTP.
	 * @throws SQLException Si se produce un error en la base de datos.
	 * @throws IOException  Si se produce un error de entrada/salida.
	 */
	private void obtenerEventosPendientesAprobacion(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException {
		try {
			List<Evento> eventos = DaoEvento.getInstance().obtenerEventosPendientesAprobacion();
			if (eventos != null && !eventos.isEmpty()) {
				response.getWriter().println("Eventos pendientes de aprobación: ");
				for (Evento evento : eventos) {
					response.getWriter().println(evento.toString());
				}
			} else {
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
				response.getWriter().println("No se encontraron eventos pendientes de aprobación.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().println("Error al obtener los eventos pendientes de aprobación. Intente de nuevo.");
		}
	}

	/**
	 * Obtiene la lista de todos los eventos activos.
	 *
	 * @param request  Objeto HttpServletRequest que contiene la solicitud HTTP.
	 * @param response Objeto HttpServletResponse que se utilizará para enviar la
	 *                 respuesta HTTP.
	 * @throws SQLException Si se produce un error en la base de datos.
	 * @throws IOException  Si se produce un error de entrada/salida.
	 */
	private void obtenerTodosLosEventosActivos(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException {

		String actividad = request.getParameter("actividad");
		String descripcion = request.getParameter("descripcion");
		String ubicacion = request.getParameter("ubicacion");
		String fechaString = request.getParameter("fecha");
		java.util.Date fechaUtil = null;
		java.sql.Date fechaSql = null;
		if (fechaString != null && !fechaString.isEmpty()) {
			try {
				fechaUtil = new SimpleDateFormat("yyyy-MM-dd").parse(fechaString);
				fechaSql = new java.sql.Date(fechaUtil.getTime());
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		try {
			List<Evento> eventos = DaoEvento.getInstance().obtenerTodosLosEventosActivos(actividad, descripcion,
					ubicacion, fechaSql);
			if (eventos != null && !eventos.isEmpty()) {
				response.getWriter().println("Todos los eventos activos: ");
				for (Evento evento : eventos) {
					response.getWriter().println(evento.toString());
				}
			} else {
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
				response.getWriter().println("No se encontraron eventos activos.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().println("Error al obtener los eventos activos. Intente de nuevo.");
		}
	}

}
