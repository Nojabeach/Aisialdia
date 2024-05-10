package controlador;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.annotation.WebServlet;
import dao.DaoActividad;
import dao.DaoEvento;
import dao.DaoEventoConActividad;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modelo.Actividad;
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
	 * Gestiona las solicitudes GET para este servlet, realizando diferentes
	 * acciones según el parámetro "action" proporcionado.
	 * 
	 * <p>
	 * Las acciones disponibles son:
	 * <ul>
	 * <li><b>obtenerEventosPendientesAprobacion:</b> Obtiene los eventos pendientes
	 * de aprobación y los devuelve en formato JSON.</li>
	 * <li><b>obtenerEventosPendientesPublicacion:</b> Obtiene los eventos
	 * pendientes de publicación y los devuelve en formato JSON.</li>
	 * <li><b>obtenerTodosLosEventosActivos:</b> Obtiene todos los eventos activos y
	 * los devuelve en formato JSON.</li>
	 * <li><b>obtenerEventosConActividad:</b> Obtiene los eventos con actividad
	 * asociada y los devuelve en formato JSON.</li>
	 * <li><b>buscarEventos:</b> Busca los eventos según el criterio marcado y los
	 * devuelve en formato JSON.</li>
	 * <li><b>obtenerEventosRechazados:</b> Obtiene los eventos rechazados y los
	 * devuelve en formato JSON, filtrados por fechas si se especifican.</li>
	 * </ul>
	 * </p>
	 * 
	 * @param request  La solicitud HTTP que contiene el parámetro "action" para
	 *                 determinar la acción a realizar.
	 * @param response La respuesta HTTP donde se enviará el resultado en formato
	 *                 JSON.
	 * @throws ServletException Si ocurre un error grave durante la ejecución del
	 *                          servlet.
	 * @throws IOException      Si ocurre un error de entrada/salida al escribir en
	 *                          el PrintWriter.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		PrintWriter out = response.getWriter();

		String action = request.getParameter("action");
		try {
			switch (action) {
			case "obtenerEventosPendientesAprobacion":
				obtenerEventosPendientesAprobacion(request, response, out);
				break;
			case "obtenerEventosPendientesPublicacion":
				obtenerEventosPendientesPublicacion(request, response, out);
				break;
			case "obtenerTodosLosEventosActivos":
				obtenerTodosLosEventosActivos(request, response, out, -1);
				break;
			case "obtenerEventosConActividad":
				obtenerEventosConActividad(request, response, out);
				break;
			case "obtenerEventosRechazados":
				obtenerEventosRechazados(request, response, out);
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
	 * Maneja las solicitudes POST enviadas al servlet, realizando diferentes
	 * acciones según el parámetro "action" proporcionado.
	 * 
	 * <p>
	 * Las acciones disponibles son:
	 * <ul>
	 * <li><b>crearEvento:</b> Crea un nuevo evento con la información proporcionada
	 * en la solicitud.</li>
	 * <li><b>editarEvento:</b> Edita un evento existente con la información
	 * proporcionada en la solicitud.</li>
	 * <li><b>eliminarEvento:</b> Elimina un evento existente según el ID
	 * proporcionado en la solicitud.</li>
	 * <li><b>publicarEvento:</b> Publica un evento pendiente de publicación.</li>
	 * <li><b>rechazarEvento:</b> Rechaza un evento pendiente de aprobación.</li>
	 * <li><b>aprobarPublicacionEvento:</b> Aprueba la publicación de un
	 * evento.</li>
	 * <li><b>finalizarPublicacionEvento:</b> Finaliza la publicación de un
	 * evento.</li>
	 * </ul>
	 * </p>
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
	 * Crea un nuevo evento con los datos proporcionados en la solicitud HTTP. Si la
	 * fecha del evento es nula o vacía, se asigna la fecha de hoy.
	 *
	 * @param request  la solicitud HTTP que contiene los parámetros del evento
	 * @param response la respuesta HTTP que se enviará al cliente
	 * @throws IOException      si hay un error de E/S al escribir la respuesta
	 * @throws ServletException si hay un error al procesar la solicitud del servlet
	 * @throws SQLException     si hay un error de SQL al interactuar con la base de
	 *                          datos
	 */
	private void crearEvento(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, SQLException {

		String nombre = request.getParameter("nombre");
		String detalles = request.getParameter("detalles");
		String fechaEventoStr = request.getParameter("fechaEvento");
		Date fechaEvento = null;

		// Convertir la cadena a Date si es válida o asignar la fecha de hoy si es vacía
		// o nula
		if (fechaEventoStr != null && !fechaEventoStr.isEmpty()) {
			try {
				fechaEvento = Date.valueOf(fechaEventoStr);
			} catch (IllegalArgumentException e) {
				// Manejar la excepción de fecha inválida
				e.printStackTrace();
				// Establecer la respuesta de error apropiada
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				response.getWriter().println("Error: La fecha del evento es inválida.");
				return;
			}
		} else {
			// Asignar la fecha de hoy si no se proporciona una fecha
			fechaEvento = new Date(System.currentTimeMillis());
		}

		int idUsuarioCreador = Integer.parseInt(request.getParameter("idUsuario"));
		String ubicacion = request.getParameter("ubicacion");

		// Obtener la lista de actividades seleccionadas
		String[] actividadIds = request.getParameterValues("actividad");
		List<Actividad> actividades = new ArrayList<>();
		if (actividadIds != null) {
			for (String actividadId : actividadIds) {
				int idActividad = Integer.parseInt(actividadId);
				Actividad actividad = DaoActividad.getInstance().obtenerActividadPorId(idActividad);
				if (actividad != null) {
					actividades.add(actividad);
				}
			}
		}

		// Obtener la marca de tiempo actual
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());

		// Crear el objeto Evento
		Evento evento = new Evento(nombre, detalles, fechaEvento, idUsuarioCreador, ubicacion);
		try {
			// Intenta crear el evento en la base de datos
			DaoEvento.getInstance().crearEvento(evento, actividades, timestamp);
			// Si tiene éxito, establece el código de estado HTTP 201 (Created) y envía un
			// mensaje de éxito
			response.setStatus(HttpServletResponse.SC_CREATED);
			response.getWriter().println("Evento creado exitosamente!");
		} catch (SQLException e) {
			// En caso de error, muestra un mensaje de error genérico y establece el código
			// de estado HTTP 500 (Internal Server Error)
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
	 * Obtiene los eventos pendientes de aprobación y devuelve el resultado en
	 * formato JSON.
	 * 
	 * @param request  La solicitud HTTP. No se espera ningún parámetro específico
	 *                 en esta solicitud.
	 * @param response La respuesta HTTP donde se enviará el resultado en formato
	 *                 JSON.
	 * @param out      El escritor de salida para escribir el resultado JSON en la
	 *                 respuesta.
	 * @throws SQLException Si ocurre un error al interactuar con la base de datos.
	 * @throws IOException  Si ocurre un error de entrada/salida al escribir en el
	 *                      PrintWriter.
	 */
	private void obtenerEventosPendientesAprobacion(HttpServletRequest request, HttpServletResponse response,
			PrintWriter out) throws SQLException, IOException {
		try {
			// Crea un objeto DaoEvento para manejar la obtención de eventos pendientes de
			// aprobación
			DaoEvento eventos = new DaoEvento();

			// Lista los eventos pendientes de aprobación en formato JSON y los envía al
			// PrintWriter
			out.print(eventos.listarJsonPendientesAprobacion());

		} catch (SQLException e) {
			// En caso de error, muestra un mensaje de error genérico y lo envía en formato
			// JSON a la respuesta
			e.printStackTrace();
			ControlErrores.mostrarErrorGenerico("{\"error\": \"" + e.getMessage() + "\"}", response);
		}
	}

	/**
	 * Obtiene los eventos pendientes de publicación y devuelve el resultado en
	 * formato JSON.
	 * 
	 * @param request  La solicitud HTTP. No se espera ningún parámetro específico
	 *                 en esta solicitud.
	 * @param response La respuesta HTTP donde se enviará el resultado en formato
	 *                 JSON.
	 * @param out      El escritor de salida para escribir el resultado JSON en la
	 *                 respuesta.
	 * @throws SQLException Si ocurre un error al interactuar con la base de datos.
	 * @throws IOException  Si ocurre un error de entrada/salida al escribir en el
	 *                      PrintWriter.
	 */
	private void obtenerEventosPendientesPublicacion(HttpServletRequest request, HttpServletResponse response,
			PrintWriter out) throws SQLException, IOException {

		try {
			// Crea un objeto DaoEvento para manejar la obtención de eventos pendientes de
			// publicación
			DaoEvento eventos = new DaoEvento();

			// Lista los eventos pendientes de publicación en formato JSON y los envía al
			// PrintWriter
			out.print(eventos.listarJsonPendientesPublicacion());

		} catch (SQLException e) {
			// En caso de error, muestra un mensaje de error genérico y lo envía en formato
			// JSON a la respuesta
			e.printStackTrace();
			ControlErrores.mostrarErrorGenerico("{\"error\": \"" + e.getMessage() + "\"}", response);
		}

	}

	/**
	 * Obtiene la lista de todos los eventos activos.
	 *
	 * @param request  Objeto HttpServletRequest que contiene la solicitud HTTP.
	 * @param response Objeto HttpServletResponse que se utilizará para enviar la
	 *                 respuesta HTTP.
	 * @throws SQLException Si ocurre un error al interactuar con la base de datos.
	 * @throws IOException  Si ocurre un error de entrada/salida al escribir en el
	 *                      PrintWriter.
	 */
	private void obtenerTodosLosEventosActivos(HttpServletRequest request, HttpServletResponse response,
	        PrintWriter out, int numEventos) throws SQLException, IOException {

	    String tipoActividad = "";
	    String nombre = "";
	    String ubicacion = "";
	    String fechaEventoStr = "";
	    String criterio = request.getParameter("criterio");
	    String textoBusqueda = request.getParameter("textoBusqueda");
	    System.out.println(criterio + " : " + textoBusqueda);

	    try {
	        DaoEventoConActividad eventosDao = new DaoEventoConActividad();

	        // Convertir la fecha solo si se proporcionó un valor
	        Date fechaEvento = null;
	        if (textoBusqueda != null && !textoBusqueda.isEmpty()) {
	            switch (criterio) {
	                case "tipoActividad":
	                    tipoActividad = textoBusqueda;
	                    break;
	                case "nombre":
	                    nombre = textoBusqueda;
	                    break;
	                case "ubicacion":
	                    ubicacion = textoBusqueda;
	                    break;
	                case "fechaEvento":
	                    fechaEventoStr = textoBusqueda;
	                    break;
	                default:
	                    break;
	            }
	        }

	        if (fechaEventoStr != null && !fechaEventoStr.isEmpty()) {
	            fechaEvento = Date.valueOf(fechaEventoStr);
	        }

	       /* String datos = "numEventos: " + numEventos + ", tipoActividad: " + tipoActividad + ", nombre: " + nombre
	                + ", ubicacion: " + ubicacion + ", fechaEvento: " + fechaEvento;

	        System.out.println(datos); */

	        out.print(eventosDao.listarJsonUltimosEventos(numEventos, tipoActividad, nombre, ubicacion, fechaEvento));
	    } catch (IllegalArgumentException e) {
	        // La fecha proporcionada no está en el formato correcto
	        ControlErrores.mostrarErrorGenerico("{\"error\": \"La fecha proporcionada no es válida\"}", response);
	    } catch (SQLException e) {
	        // Error de SQL, manejar según sea necesario
	        e.printStackTrace();
	        ControlErrores.mostrarErrorGenerico("{\"error\": \"" + e.getMessage() + "\"}", response);
	    }
	}

	/**
	 * Obtiene los eventos con actividad asociada y devuelve el resultado en formato
	 * JSON.
	 * 
	 * @param request  La solicitud HTTP que contiene el parámetro "idEvento" para
	 *                 identificar el evento.
	 * @param response La respuesta HTTP donde se enviará el resultado en formato
	 *                 JSON.
	 * @param out      El escritor de salida para escribir el resultado JSON en la
	 *                 respuesta.
	 * @throws SQLException Si ocurre un error al interactuar con la base de datos.
	 * @throws IOException  Si ocurre un error de entrada/salida al escribir en el
	 *                      PrintWriter.
	 */
	public void obtenerEventosConActividad(HttpServletRequest request, HttpServletResponse response, PrintWriter out)
			throws SQLException, IOException {

		// Obtiene el ID del evento de la solicitud
		int idEvento = Integer.parseInt(request.getParameter("idEvento"));

		try {
			// Crea un objeto DaoEventoConActividad para manejar la obtención de eventos con
			// actividad
			DaoEventoConActividad eventos = new DaoEventoConActividad();

			// Lista los eventos con actividad en formato JSON y los envía al PrintWriter
			out.print(eventos.listarJsonEventosConActividad(idEvento));

		} catch (SQLException e) {
			// En caso de error, muestra un mensaje de error genérico y lo envía en formato
			// JSON a la respuesta
			e.printStackTrace();
			ControlErrores.mostrarErrorGenerico("{\"error\": \"" + e.getMessage() + "\"}", response);
		}

	}

	/**
	 * Obtiene los eventos rechazados en formato JSON, filtrados por fechas si se
	 * especifican.
	 *
	 * @param request  La solicitud HTTP que contiene los parámetros de fechaInicio
	 *                 y fechaFin para filtrar los eventos.
	 * @param response La respuesta HTTP donde se enviará el resultado en formato
	 *                 JSON.
	 * @param out      El PrintWriter para escribir la respuesta.
	 * @throws SQLException Si ocurre un error al interactuar con la base de datos.
	 * @throws IOException  Si ocurre un error de entrada/salida al escribir la
	 *                      respuesta.
	 */

	public void obtenerEventosRechazados(HttpServletRequest request, HttpServletResponse response, PrintWriter out)
			throws SQLException, IOException {

		// Obtener fechas de la solicitud, si están presentes
		String fechaInicioParam = request.getParameter("fechaInicio");
		String fechaFinParam = request.getParameter("fechaFin");

		// Convertir fechas a objetos Date
		Date fechaInicio = null;
		Date fechaFin = null;

		if (fechaInicioParam != null && fechaFinParam != null) {
			try {
				fechaInicio = Date.valueOf(fechaInicioParam);
				fechaFin = Date.valueOf(fechaFinParam);
			} catch (IllegalArgumentException e) {
				// Manejar error de formato de fecha
				ControlErrores.mostrarErrorGenerico("{\"error\": \"Formato de fecha incorrecto\"}", response);
				return;
			}
		}

		try {
			// Crear un objeto DaoEvento para manejar la obtención de eventos
			DaoEvento daoEvento = new DaoEvento();

			// Obtener eventos rechazados en formato JSON y enviarlos al PrintWriter
			out.print(daoEvento.listarJsonRechazados(fechaInicio, fechaFin));

		} catch (SQLException e) {
			// En caso de error, mostrar un mensaje de error genérico y enviarlo en formato
			// JSON a la respuesta
			e.printStackTrace();
			ControlErrores.mostrarErrorGenerico("{\"error\": \"" + e.getMessage() + "\"}", response);
		}
	}

}
