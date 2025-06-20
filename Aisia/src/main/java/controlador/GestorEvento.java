package controlador;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.annotation.WebServlet;
import dao.DaoEvento;
import dao.DaoEventoConActividad;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import modelo.Actividad;
import modelo.Evento;
import modelo.Evento.motivoFinalizacion;
import modelo.EventoConActividad;

/**
 * Servlet implementation class GestorEvento
 * 
 * @author Maitane Ibañez Irazabal
 * @version 1.0
 */
public class GestorEvento extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor por defecto de la clase
	 * 
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
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				ControlErrores.mostrarErrorGenerico("Error: La fecha del evento es inválida.", response);
				return;
			}
		} else {
			// Asignar la fecha de hoy si no se proporciona una fecha
			fechaEvento = new Date(System.currentTimeMillis());
		}
		Date fechaCreacion = new Date(System.currentTimeMillis());

		HttpSession session = request.getSession();
		int idUsuarioCreador = (int) session.getAttribute("idUsuario");

		String ubicacion = request.getParameter("ubicacion");

		// Validar los campos nombre, detalles y ubicación
		if (nombre == null || nombre.isEmpty() || detalles == null || detalles.isEmpty() || ubicacion == null
				|| ubicacion.isEmpty()) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			ControlErrores.mostrarErrorGenerico("Error: Todos los campos son obligatorios.", response);
			return;
		}

		// Obtener la lista de actividades seleccionadas
		String[] actividadIds = request.getParameterValues("activity");
		List<Actividad> actividades = new ArrayList<>();
		if (actividadIds != null) {
			for (String actividadId : actividadIds) {
				int idActividad = Integer.parseInt(actividadId);
				String tipoActividad = request.getParameter("activity-" + actividadId + "-tipo");
				// Puedes obtener la fotoActividad si la necesitas
				String fotoActividad = request.getParameter("activity-" + actividadId + "-foto");

				Actividad actividad = new Actividad();
				actividad.setIdActividad(idActividad);
				actividad.setTipoActividad(tipoActividad);
				// Puedes guardar la fotoActividad si la necesitas
				actividad.setFotoActividad(fotoActividad);

				actividades.add(actividad);
			}
		}

		// Crear el objeto Evento
		Evento evento = new Evento(nombre, detalles, fechaEvento, idUsuarioCreador, ubicacion, fechaCreacion);

		try {
			// Intenta crear el evento en la base de datos
			DaoEvento.getInstance().crearEvento(evento, actividades, new Timestamp(System.currentTimeMillis()));
			// Si tiene éxito, establece el código de estado HTTP 201 (Created) y envía un
			// mensaje de éxito
			response.setStatus(HttpServletResponse.SC_CREATED);

			response.sendRedirect("eventos.html");

		} catch (SQLException e) {
			// En caso de error, muestra un mensaje de error genérico y establece el código
			// de estado HTTP 500 (Internal Server Error)
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			ControlErrores.mostrarErrorGenerico("Error al crear el evento. Intente de nuevo.", response);
		}
	}

	/**
	 * Edita un evento existente en la base de datos.
	 *
	 * @param request  Objeto HttpServletRequest que contiene la solicitud HTTP.
	 * @param response Objeto HttpServletResponse que se utilizará para enviar la
	 *                 respuesta HTTP.
	 * @throws SQLException   Si se produce un error en la base de datos.
	 * @throws IOException    Si se produce un error de entrada/salida.
	 * @throws ParseException Si se prodcue un error a la hora de parsear las fechas
	 *                        de nacimiento
	 */
	private void editarEvento(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ParseException {
		int idEvento = Integer.parseInt(request.getParameter("idEvento"));
		String nombre = request.getParameter("nombre");
		String detalles = request.getParameter("detalles");
		String fechaEventoStr = request.getParameter("EDITfechaEvento");
		String tipoActividad = request.getParameter("EDITactivity");
		String ubicacion = request.getParameter("ubicacion");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date fechaEvento = null;
		try {
			fechaEvento = new Date(dateFormat.parse(fechaEventoStr).getTime());
		} catch (ParseException e) {
			// Manejar el error de formato de fecha
			ControlErrores.mostrarErrorGenerico("{\"error\": \"Formato de fecha de evento inválido\"}", response);
			return;
		}

		EventoConActividad evento = new EventoConActividad(idEvento, nombre, detalles, ubicacion, tipoActividad,
				fechaEvento);

		try {
			DaoEventoConActividad.getInstance().editarEvento(evento, tipoActividad);
			// Evento editado exitosamente
		} catch (SQLException e) {
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			ControlErrores.mostrarErrorGenerico("Error al editar el evento. Intente de nuevo.", response);
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
			DaoEvento.getInstance().eliminarEvento(evento, 0);
			response.setStatus(HttpServletResponse.SC_OK);
			// System.out.println("Evento eliminado exitosamente!");
		} catch (SQLException e) {
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			ControlErrores.mostrarErrorGenerico("Error al eliminar el evento. Intente de nuevo.", response);
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
			response.sendRedirect("admin.html");
			// System.out.println("Evento publicado exitosamente!");
		} catch (SQLException e) {
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			ControlErrores.mostrarErrorGenerico("Error al publicar el evento. Intente de nuevo.", response);
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
			// System.out.println("Evento rechazado exitosamente!");
		} catch (SQLException e) {
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			ControlErrores.mostrarErrorGenerico("Error al rechazar el evento. Intente de nuevo.", response);
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
			response.sendRedirect("admin.html");
			// System.out.println("Evento aprobado para su publicación!");
		} catch (SQLException e) {
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			ControlErrores.mostrarErrorGenerico("Error al aprobar la publicación del evento. Intente de nuevo.",
					response);
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
		HttpSession session = request.getSession();
		int idModerador = (int) session.getAttribute("idUsuario");
		// Obtener el motivo finalización desde el parámetro de la solicitud
		String motivoFinalizacionStr = request.getParameter("motivoFinalizacion");

		// Convertir el valor del motivo finalización a un enum
		motivoFinalizacion motivo = motivoFinalizacion.valueOf(motivoFinalizacionStr);

		Date fechaFinalizacion = new Date(System.currentTimeMillis());
		Evento evento = new Evento(idEvento, fechaFinalizacion, idModerador, motivo);

		try {
			DaoEvento.getInstance().finalizarPublicacionEvento(evento);
			response.sendRedirect("admin.html");
			// System.out.println("Evento finalizado exitosamente!" );
		} catch (SQLException e) {
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			ControlErrores.mostrarErrorGenerico("Error al finalizar el evento. Intente de nuevo.", response);
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
		// System.out.println(criterio + " : " + textoBusqueda);

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
				// System.out.println("fechaEvento : "+ fechaEvento);

				fechaEvento = Date.valueOf(fechaEventoStr);

				// System.out.println(fechaEvento);

			}

			/*
			 * String datos = "numEventos: " + numEventos + ", tipoActividad: " +
			 * tipoActividad + ", nombre: " + nombre + ", ubicacion: " + ubicacion +
			 * ", fechaEvento: " + fechaEvento;
			 * 
			 * System.out.println(datos);
			 */

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

		// System.out.println("Entró en obtenerEventosRechazados");

		// Obtener fechas de la solicitud, si están presentes
		String fechaInicioParam = request.getParameter("fechaInicio");
		String fechaFinParam = request.getParameter("fechaFin");
		// System.out.println("Parámetro fechaInicio: " + fechaInicioParam);
		// System.out.println("Parámetro fechaFin: " + fechaFinParam);

		// Convertir fechas a objetos Date
		Date fechaInicio = null;
		Date fechaFin = null;

		if (fechaInicioParam != null && fechaFinParam != null) {
			try {
				fechaInicio = Date.valueOf(fechaInicioParam);
				fechaFin = Date.valueOf(fechaFinParam);
				// System.out.println("Fecha de inicio convertida: " + fechaInicio);
				// System.out.println("Fecha de fin convertida: " + fechaFin);
			} catch (IllegalArgumentException e) {
				// Manejar error de formato de fecha
				// System.out.println("Error en el formato de las fechas: " + e.getMessage());
				ControlErrores.mostrarErrorGenerico("{\"error\": \"Formato de fecha incorrecto\"}", response);
				return;
			}
		}

		try {
			// Crear un objeto DaoEvento para manejar la obtención de eventos
			DaoEvento daoEvento = new DaoEvento();
			// System.out.println("DaoEvento creado");

			// Obtener eventos rechazados en formato JSON y enviarlos al PrintWriter
			String eventosJson = daoEvento.listarJsonRechazados(fechaInicio, fechaFin);
			// System.out.println("Eventos rechazados obtenidos: " + eventosJson);
			out.print(eventosJson);

		} catch (SQLException e) {
			// En caso de error, mostrar un mensaje de error genérico y enviarlo en formato
			// JSON a la respuesta
			// System.out.println("Error en la consulta SQL: " + e.getMessage());
			e.printStackTrace();
			ControlErrores.mostrarErrorGenerico("{\"error\": \"" + e.getMessage() + "\"}", response);
		}
	}

}
