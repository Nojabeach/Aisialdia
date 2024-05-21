package controlador;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import modelo.Actividad;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import dao.DaoActividad;

@MultipartConfig
/**
 * Servlet implementation class GestorActividad
 */
public class GestorActividad extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GestorActividad() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Maneja las solicitudes GET para este servlet, realizando diferentes acciones
	 * según el parámetro "action" proporcionado.
	 * 
	 * <p>
	 * Las acciones disponibles son:
	 * <ul>
	 * <li><b>visualizarActividades:</b> Muestra las actividades disponibles y sus
	 * detalles.</li>
	 * <li><b>obtenerActividadporID:</b> Obtiene los detalles de una actividad
	 * específica identificada por su ID.</li>
	 * </ul>
	 * </p>
	 * 
	 * @param request  La solicitud HTTP que contiene el parámetro "action" para
	 *                 determinar la acción a realizar.
	 * @param response La respuesta HTTP donde se enviará el resultado.
	 * @throws ServletException Si ocurre un error en el servlet.
	 * @throws IOException      Si ocurre un error de entrada/salida.
	 */

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String accion = request.getParameter("action");

		try {
			switch (accion) {
			case "visualizarActividades":
				// System.out.println("entro en visualizar");
				visualizarActividades(request, response, out);
				break;
			case "obtenerActividadporID":
				obtenerActividadporID(request, response, out);
				break;
			default:
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				ControlErrores.mostrarErrorGenerico("{\"error\": \"Acción no válida\"}", response);
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			ControlErrores.mostrarErrorGenerico("{\"error\": \"" + e.getMessage() + "\"}", response);
		}
	}

	/**
	 * Maneja las solicitudes POST para este servlet, realizando diferentes acciones
	 * según el parámetro "action" proporcionado.
	 * 
	 * <p>
	 * Las acciones disponibles son:
	 * <ul>
	 * <li><b>crearActividad:</b> Crea una nueva actividad con la información
	 * proporcionada en la solicitud.</li>
	 * <li><b>editarActividad:</b> Edita una actividad existente con la información
	 * proporcionada en la solicitud.</li>
	 * <li><b>eliminarActividad:</b> Elimina una actividad existente según el ID
	 * proporcionado en la solicitud.</li>
	 * </ul>
	 * </p>
	 * 
	 * @param request  La solicitud HTTP que contiene el parámetro "action" para
	 *                 determinar la acción a realizar.
	 * @param response La respuesta HTTP donde se enviará el resultado o el mensaje
	 *                 de error.
	 * @throws ServletException Si se produce un error grave durante la ejecución
	 *                          del servlet.
	 * @throws IOException      Si se produce un error de entrada/salida al escribir
	 *                          en el PrintWriter.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// System.out.println("Llegó una solicitud POST al servlet");
		String action = request.getParameter("action");
		// System.out.println("Action: " + action);
		try {
			switch (action) {
			case "crearActividad":
				crearActividad(request, response);
				break;
			case "editarActividad":
				editarActividad(request, response);
				break;
			case "eliminarActividad":
				eliminarActividad(request, response);
				break;
			default:
				// Si la acción no es válida, se establece un código de estado HTTP 400 (Bad
				// Request)
				// y se envía un mensaje de error genérico
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				ControlErrores.mostrarErrorGenerico("{\"error\": \"Acción no válida\"}", response);
			}
		} catch (Exception e) {
			// En caso de error, se muestra un mensaje de error genérico y se establece un
			// código de estado HTTP 500 (Internal Server Error)
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			ControlErrores.mostrarErrorGenerico("{\"error\": \"" + e.getMessage() + "\"}", response);
		}
	}

	/**
	 * Crea una nueva actividad en la base de datos y sube una foto si se
	 * selecciona.
	 *
	 * @param request  Objeto HttpServletRequest que contiene la solicitud HTTP.
	 * @param response Objeto HttpServletResponse que se utilizará para enviar la
	 *                 respuesta HTTP.
	 * @throws IOException      Si se produce un error de entrada/salida.
	 * @throws ServletException Si se produce un error en el servlet.
	 */
	private void crearActividad(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		String tipoActividad = request.getParameter("tipoActividad");
		String fileName = GestionFotos.subirFotoAlServidor(request.getPart("fotoActividad"), response);

		if (fileName == null) {
			return; // Si no se pudo subir la foto, salimos del método
		}

		// Actualizar el tipo de actividad con el nombre de la foto si no está vacío
		// accede al primer elemento del array resultante, que es la parte del nombre
		// del archivo antes del primer punto. Esa es la parte que se asigna a la
		// variable tipoActividad
		if (!fileName.isEmpty()) {
			tipoActividad = fileName.split("\\.")[0];
		}

		Actividad actividad = new Actividad(tipoActividad, fileName);

		try {
			System.out.println("crearActividad");
			DaoActividad.getInstance().crearActividad(actividad);
			System.out.println("actividad creada");
			response.setStatus(HttpServletResponse.SC_OK);
			

			// response.getWriter().println("Actividad creada exitosamente!");
		} catch (SQLException e) {
			ControlErrores.mostrarErrorGenerico("Error al crear la actividad. Intente de nuevo." + e, response);
		}
	}

	/**
	 * Edita una actividad existente en la base de datos.
	 *
	 * @param request  Objeto HttpServletRequest que contiene la solicitud HTTP.
	 * @param response Objeto HttpServletResponse que se utilizará para enviar la
	 *                 respuesta HTTP.
	 * @throws SQLException     Si se produce un error en la base de datos.
	 * @throws IOException      Si se produce un error de entrada/salida.
	 * @throws ServletException Si ocurre un error durante el procesamiento de la
	 *                          solicitud.
	 */
	private void editarActividad(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		// Obtener los parámetros del formulario
		int idActividad = Integer.parseInt(request.getParameter("idActividad"));
		String tipoActividad = request.getParameter("tipoActividad");
		String fotoActividad = request.getParameter("fotoActividad");

		// Crear una instancia de Actividad con los datos obtenidos
		Actividad actividad = new Actividad(idActividad, tipoActividad, fotoActividad);

		try {
			// Editar la actividad en la base de datos
			DaoActividad.getInstance().editarActividad(actividad);

			// Verificar si la imagen ya existe en el servidor
			String nombreArchivo = new File(fotoActividad).getName();
			String rutaCompleta = GestionFotos.PATH_FILES + File.separator + nombreArchivo;
			File imagenServidor = new File(rutaCompleta);

			if (!imagenServidor.exists()) {
				// Copiar la imagen desde el cliente al servidor
				Part imagenPart = request.getPart("fotoActividad");
				String fileName = GestionFotos.subirFotoAlServidor(imagenPart, response);
				if (fileName == null) {
					// Error al subir la foto
					return;
				}
			}

			response.setStatus(HttpServletResponse.SC_OK);

		} catch (SQLException e) {
			e.printStackTrace();

			// En caso de error, mostrar un mensaje de error genérico
			ControlErrores.mostrarErrorGenerico("Error al editar la actividad. Contacte al administrador.", response);
		}
	}

	/**
	 * Elimina una actividad existente en la base de datos.
	 *
	 * @param request  Objeto HttpServletRequest que contiene la solicitud HTTP.
	 * @param response Objeto HttpServletResponse que se utilizará para enviar la
	 *                 respuesta HTTP.
	 * @throws Exception   Si se produce un error en la base de datos.
	 * @throws IOException Si se produce un error de entrada/salida.
	 */
	private void eliminarActividad(HttpServletRequest request, HttpServletResponse response)
			throws Exception, IOException {
		int idActividad = Integer.parseInt(request.getParameter("idActividad"));

		try {
			// Obtener la actividad antes de eliminarla para conocer el nombre del archivo
			Actividad actividad = DaoActividad.getInstance().obtenerActividadPorId(idActividad);
			if (actividad == null) {
				ControlErrores.mostrarErrorGenerico("La actividad no existe.", response);
				return;
			}

			String fileName = actividad.getFotoActividad();

			// Eliminar la actividad de la base de datos
			DaoActividad.getInstance().eliminarActividad(actividad);

			// Eliminar la foto del servidor si el nombre del archivo no es nulo o vacío
			if (fileName != null && !fileName.isEmpty()) {
				boolean fotoEliminada = GestionFotos.eliminarFotoDelServidor(fileName, response);
				if (!fotoEliminada) {
					ControlErrores.mostrarErrorGenerico("Error al eliminar la foto del servidor.", response);
				}
			}

			response.setStatus(HttpServletResponse.SC_OK);
			response.sendRedirect("admin.html");

		} catch (SQLException e) {
			e.printStackTrace();

			// Mostrar error al usuario indicando que la actividad está vinculada a algún
			// evento
			ControlErrores.mostrarErrorGenerico(
					"Error al eliminar la actividad. Vinculado ya con algún evento. No se puede borrar.", response);
		}
	}

	/**
	 * Recupera todas las actividades existentes y las envía como respuesta al
	 * cliente en formato JSON.
	 *
	 * @param request  Objeto HttpServletRequest que contiene la solicitud HTTP.
	 * @param response Objeto HttpServletResponse que se utilizará para enviar la
	 *                 respuesta HTTP.
	 * @throws IOException  Si ocurre un error de entrada/salida al enviar la
	 *                      respuesta.
	 * @throws SQLException Si ocurre un error al recuperar las actividades de la
	 *                      base de datos.
	 */
	private void visualizarActividades(HttpServletRequest request, HttpServletResponse response, PrintWriter out)
			throws IOException, SQLException {

		try {
			DaoActividad actividad = new DaoActividad();
			// System.out.println("Entro a ver actividades");
			out.print(actividad.listarJsonTodasActividades());
			// System.out.println(actividad.listarJsonTodasActividades());
		} catch (SQLException e) {
			ControlErrores.mostrarErrorGenerico("Error al obtener las actividades. Intente de nuevo.", response);
		}
	}

	private void obtenerActividadporID(HttpServletRequest request, HttpServletResponse response, PrintWriter out)
			throws IOException, SQLException {
		int idActividad = Integer.parseInt(request.getParameter("idActividad"));
		try {
			DaoActividad actividad = new DaoActividad();
			out.print(actividad.listarJsonActividadPorID(idActividad));
		} catch (SQLException e) {
			ControlErrores.mostrarErrorGenerico("Error al obtener la actividade por ID. Intente de nuevo.", response);
		}
	}

}
