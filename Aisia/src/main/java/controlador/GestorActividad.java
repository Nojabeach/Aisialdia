package controlador;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modelo.Actividad;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.google.gson.Gson;

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
		// TODO Auto-generated method stub
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
		System.out.println("Llegó una solicitud POST al servlet");
		String action = request.getParameter("action");
		System.out.println("Action: " + action);
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
			case "visualizarActividades":
                visualizarActividades(request, response);
                break;
			default:
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				ControlErrores.mostrarErrorGenerico("{\"error\": \"Acción no válida\"}" , response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			ControlErrores.mostrarErrorGenerico("{\"error\": \"" + e.getMessage() + "\"}" , response);
			
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
		System.out.println(tipoActividad);
		String fileName = GestionFotos.subirFotoAlServidor(request.getPart("fotoActividad"), response);
		System.out.println(fileName);
		Actividad actividad = new Actividad(tipoActividad, fileName);

		try {
			actividad.crearActividad(actividad);
			response.setStatus(HttpServletResponse.SC_CREATED);
			response.getWriter().println("Actividad creada exitosamente!");
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
	 * @throws SQLException Si se produce un error en la base de datos.
	 * @throws IOException  Si se produce un error de entrada/salida.
	 */
	private void editarActividad(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException {
		int idActividad = Integer.parseInt(request.getParameter("idActividad"));
		Actividad a = new Actividad(idActividad);
		try {
			a.editarActividad();
		} catch (SQLException e) {
			e.printStackTrace();
		
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
		Actividad a = new Actividad(idActividad);
		try {
			a.eliminarActividad();
		} catch (SQLException e) {
			e.printStackTrace();

			 ControlErrores.mostrarErrorGenerico("Error al editar la actividad. Contacte al administrador.", response);
		}
	}
	
	/**
	 * Recupera todas las actividades existentes y las envía como respuesta al cliente en formato JSON.
	 *
	 * @param request  Objeto HttpServletRequest que contiene la solicitud HTTP.
	 * @param response Objeto HttpServletResponse que se utilizará para enviar la respuesta HTTP.
	 * @throws IOException  Si ocurre un error de entrada/salida al enviar la respuesta.
	 * @throws SQLException Si ocurre un error al recuperar las actividades de la base de datos.
	 */
	private void visualizarActividades(HttpServletRequest request, HttpServletResponse response)
	        throws IOException, SQLException {
	    try {
	        // Obtener lista de actividades existentes
	        List<Actividad> actividades = Actividad.obtenerTodasLasActividades();

	        // Convertir la lista de actividades a JSON
	        String actividadesJSON = new Gson().toJson(actividades);

	        // Establecer el tipo de contenido y encabezados de la respuesta
	        response.setContentType("application/json");
	        response.setCharacterEncoding("UTF-8");

	        // Enviar la lista de actividades como respuesta
	        response.getWriter().write(actividadesJSON);
	    } catch (SQLException e) {
	        ControlErrores.mostrarErrorGenerico("Error al obtener las actividades. Intente de nuevo.", response);
	    }
	}


}
