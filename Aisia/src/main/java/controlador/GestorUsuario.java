package controlador;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;

import dao.DaoUsuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import modelo.Usuario;
import modelo.Usuario.Rol;

/**
 * Servlet implementation class GestorUsuario
 * 
 * @author Maitane Ibañez Irazabal
 * @version 1.0
 */
public class GestorUsuario extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GestorUsuario() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Maneja las solicitudes GET enviadas al servlet. Las acciones disponibles son:
	 * <ul>
	 * <li><b>obtenerUsuarios:</b> Obtiene la lista de usuarios y los devuelve en
	 * formato JSON.</li>
	 * <li><b>obtenerUsuariosSegunPermiso:</b> Obtiene la lista de usuarios según su
	 * permiso y los devuelve en formato JSON.</li>
	 * <li><b>obtenerInfoUsuario:</b> Obtiene la información de un usuario por ID y
	 * la devuelve en formato JSON.</li>
	 * <li><b>obtenerContrasena:</b> Obtiene la contraseña de un usuario por ID y la
	 * devuelve en formato JSON.</li>
	 * <li><b>buscarPermisoUsuario:</b> Busca el permiso de un usuario por ID y lo
	 * devuelve en formato JSON.</li>
	 * <li><b>checkLogin:</b> Comprueba si hay una sesión de usuario activa y
	 * devuelve la información del usuario y su permiso en formato JSON.</li>
	 * </ul>
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
		PrintWriter out = response.getWriter();

		try {
			switch (action) {
			case "obtenerUsuarios":
				obtenerUsuarios(request, response, out);
				break;
			case "obtenerUsuariosSegunPermiso":
				obtenerUsuariosPermiso(request, response, out);
				break;
			case "obtenerInfoUsuario":
				// System.out.println("entro en info user");
				obtenerINFOUsuario(request, response, out);
				break;
			case "obtenerContrasena":
				obtenerContrasena(request, response, out);
				break;
			case "buscarPermisoUsuario":
				buscarPermisoUsuario(request, response, out);
				break;
			case "checkLogin":
				checkLogin(request, response);
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
	 * Maneja las solicitudes POST enviadas al servlet. Las acciones disponibles
	 * son:
	 * <ul>
	 * <li><b>registrarUsuario:</b> Registra un nuevo usuario utilizando los datos
	 * proporcionados en la solicitud.</li>
	 * <li><b>crearUsuario:</b> Crea un nuevo usuario a partir de los datos
	 * proporcionados en la solicitud.</li>
	 * <li><b>editarUsuario:</b> Edita la información de un usuario utilizando los
	 * datos proporcionados en la solicitud.</li>
	 * <li><b>eliminarUsuario:</b> Elimina un usuario de la base de datos.</li>
	 * <li><b>marcarEventoFavorito:</b> Marca un evento como favorito para un
	 * usuario específico.</li>
	 * <li><b>desmarcarEventoFavorito:</b> Elimina la marca de favorito de un evento
	 * para un usuario específico.</li>
	 * <li><b>cambiarContrasena:</b> Cambia la contraseña de un usuario.</li>
	 * <li><b>cerrarSesion:</b> Cierra la sesión de usuario.</li>
	 * <li><b>iniciarSesion:</b> Inicia sesión para un usuario utilizando los datos
	 * proporcionados.</li>
	 * </ul>
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
			case "registrarUsuario":
				registrarUsuario(request, response);
				break;
			case "crearUsuario":
				crearUsuario(request, response);
				break;
			case "editarUsuario":
				editarUsuario(request, response);
				break;
			case "eliminarUsuario":
				eliminarUsuario(request, response);
				break;
			case "marcarEventoFavorito":
				marcarEventoFavorito(request, response);
				break;
			case "desmarcarEventoFavorito":
				desmarcarEventoFavorito(request, response);
				break;
			case "cambiarContrasena":
				cambiarContrasena(request, response);
				break;
			case "cerrarSesion":
				cerrarSesion(request, response);
				break;
			case "iniciarSesion":
				iniciarSesion(request, response, null, null);
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
	 * Obtiene la lista de usuarios y los devuelve en formato JSON.
	 * 
	 * @param request  Objeto HttpServletRequest que contiene la solicitud HTTP.
	 * @param response Objeto HttpServletResponse que se utilizará para enviar la
	 *                 respuesta HTTP.
	 * @param out      El escritor de salida para escribir la lista de usuarios en
	 *                 formato JSON en la respuesta.
	 * @throws IOException Si ocurre un error de entrada/salida al escribir en el
	 *                     PrintWriter.
	 */
	private void obtenerUsuarios(HttpServletRequest request, HttpServletResponse response, PrintWriter out)
			throws IOException {
		try {
			DaoUsuario usuario = new DaoUsuario();
			out.print(usuario.listarUsuariosJson());
		} catch (SQLException e) {
			ControlErrores.mostrarErrorGenerico("Error al obtener los usuarios. Intente de nuevo.", response);
		}
	}

	/**
	 * Obtiene la lista de usuarios según su permiso y los devuelve en formato JSON.
	 * 
	 * @param request  Objeto HttpServletRequest que contiene la solicitud HTTP.
	 * @param response Objeto HttpServletResponse que se utilizará para enviar la
	 *                 respuesta HTTP.
	 * @param out      El escritor de salida para escribir la lista de usuarios en
	 *                 formato JSON en la respuesta.
	 * @throws IOException Si ocurre un error de entrada/salida al escribir en el
	 *                     PrintWriter.
	 */
	private void obtenerUsuariosPermiso(HttpServletRequest request, HttpServletResponse response, PrintWriter out)
			throws IOException {
		try {
			int permiso = Integer.parseInt(request.getParameter("permiso"));

			DaoUsuario usuario = new DaoUsuario();
			out.print(usuario.listarUsuariosJson(permiso));
		} catch (SQLException e) {
			ControlErrores.mostrarErrorGenerico("Error al obtener los usuarios según su permiso. Intente de nuevo.",
					response);
		}
	}

	/**
	 * Obtiene la información de un usuario y la devuelve en formato JSON.
	 * 
	 * @param request  Objeto HttpServletRequest que contiene la solicitud HTTP.
	 * @param response Objeto HttpServletResponse que se utilizará para enviar la
	 *                 respuesta HTTP.
	 * @param out      El escritor de salida para escribir la información del
	 *                 usuario en formato JSON en la respuesta.
	 * @throws IOException Si ocurre un error de entrada/salida al escribir en el
	 *                     PrintWriter.
	 */
	private void obtenerINFOUsuario(HttpServletRequest request, HttpServletResponse response, PrintWriter out)
			throws IOException {
		try {
			HttpSession session = request.getSession();
			int idUsuario = (int) session.getAttribute("idUsuario");

			DaoUsuario usuario = new DaoUsuario();
			out.print(usuario.listariNFOUsuarioJson(idUsuario));
		} catch (SQLException e) {
			ControlErrores.mostrarErrorGenerico("Error al obtener la información del usuario. Intente de nuevo.",
					response);
		}
	}

	/**
	 * Obtiene la contraseña de un usuario y la devuelve en formato JSON.
	 * 
	 * @param request  Objeto HttpServletRequest que contiene la solicitud HTTP.
	 * @param response Objeto HttpServletResponse que se utilizará para enviar la
	 *                 respuesta HTTP.
	 * @param out      El escritor de salida para escribir la contraseña del usuario
	 *                 en formato JSON en la respuesta.
	 * @throws IOException Si ocurre un error de entrada/salida al escribir en el
	 *                     PrintWriter.
	 */
	private void obtenerContrasena(HttpServletRequest request, HttpServletResponse response, PrintWriter out)
			throws IOException {
		try {
			HttpSession session = request.getSession();
			int idUsuario = (int) session.getAttribute("idUsuario");

			DaoUsuario usuario = new DaoUsuario();
			out.print(usuario.ObtenerContrasenaJson(idUsuario));
		} catch (SQLException e) {
			ControlErrores.mostrarErrorGenerico("Error al obtener la contraseña del usuario. Intente de nuevo.",
					response);
		}
	}

	/**
	 * Busca el permiso de un usuario y lo devuelve en formato JSON.
	 * 
	 * @param request  Objeto HttpServletRequest que contiene la solicitud HTTP.
	 * @param response Objeto HttpServletResponse que se utilizará para enviar la
	 *                 respuesta HTTP.
	 * @param out      El escritor de salida para escribir el permiso del usuario en
	 *                 formato JSON en la respuesta.
	 * @throws IOException Si ocurre un error de entrada/salida al escribir en el
	 *                     PrintWriter.
	 */
	private void buscarPermisoUsuario(HttpServletRequest request, HttpServletResponse response, PrintWriter out)
			throws IOException {
		try {
			HttpSession session = request.getSession();
			int idUsuario = (int) session.getAttribute("idUsuario");

			DaoUsuario usuario = new DaoUsuario();
			out.print(usuario.BuscarPermisoJson(idUsuario));
		} catch (SQLException e) {
			ControlErrores.mostrarErrorGenerico("Error al buscar el permiso del usuario. Intente de nuevo.", response);
		}
	}

	/**
	 * Comprueba si hay una sesión de usuario activa y devuelve la información del
	 * usuario y su permiso en formato JSON.
	 * 
	 * @param request  Objeto HttpServletRequest que contiene la solicitud HTTP.
	 * @param response Objeto HttpServletResponse que se utilizará para enviar la
	 *                 respuesta HTTP.
	 * @throws ServletException Si se produce un error grave durante la ejecución
	 *                          del servlet.
	 * @throws IOException      Si ocurre un error de entrada/salida al escribir en
	 *                          el PrintWriter o al redirigir al usuario.
	 */
	private void checkLogin(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false); // No crea una nueva sesión si no existe

		if (session != null && session.getAttribute("usuario") != null && session.getAttribute("permiso") != null) {
			Usuario usuario = (Usuario) session.getAttribute("usuario");
			String nombreUsuario = usuario.getNombre(); // Obtener el nombre de usuario del objeto Usuario
			int permiso = (int) session.getAttribute("permiso");

			/*
			 * Cuando se recupera un objeto de una sesión (en este caso, los atributos
			 * "usuario" y "permiso"), se almacena como un objeto Object. Para utilizar
			 * estos objetos como String, es necesario realizar una conversión explícita de
			 * tipo, ya que getAttribute() devuelve un objeto de tipo Object. Por lo tanto,
			 * (String) se utiliza para convertir el objeto recuperado de la sesión al tipo
			 * String, de modo que pueda ser utilizado como tal.
			 */
			// System.out.println("{\"nombreUsuario\": \"" + nombreUsuario + "\",
			// \"permiso\": \"" + permiso + "\"}");
			// Devolver información del usuario y su permiso en formato JSON
			response.getWriter().write("{\"nombreUsuario\": \"" + nombreUsuario + "\", \"permiso\": " + permiso + "}");
		} else {
			// Si no hay sesión activa, redirigir al usuario a la página de inicio de sesión
			response.sendRedirect("index.html");
		}
	}

	/**
	 * Registra un nuevo usuario en el sistema.
	 *
	 * @param request  Objeto HttpServletRequest que contiene la solicitud HTTP.
	 * @param response Objeto HttpServletResponse que se utilizará para enviar la
	 *                 respuesta HTTP.
	 * @throws SQLException     Si se produce un error al acceder a la base de
	 *                          datos.
	 * @throws IOException
	 * @throws ServletException
	 */

	private void registrarUsuario(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {

		// Obtener parámetros del formulario
		String nombre = request.getParameter("nombre");
		String email = request.getParameter("email");
		Date fechaNacimiento = Date.valueOf(request.getParameter("fechaNacimiento"));
		boolean recibeNotificaciones = "on".equalsIgnoreCase(request.getParameter("recibeNotificaciones"));
		String intereses = request.getParameter("intereses");
		String rolesStr = request.getParameter("roles");
		Rol roles = (rolesStr != null && !rolesStr.isEmpty()) ? Rol.valueOf(rolesStr) : Rol.USUARIO;
		int permiso = (request.getParameter("permiso") != null) ? Integer.parseInt(request.getParameter("permiso")) : 1;
		boolean consentimientoDatos = "on".equalsIgnoreCase(request.getParameter("consentimiento_datos"));
		Date fechaConsentimientoDatos = consentimientoDatos ? new Date(System.currentTimeMillis()) : null;
		boolean aceptacionTerminosWeb = "on".equalsIgnoreCase(request.getParameter("aceptacionTerminosWeb"));
		Date fechaAceptacionTerminosWeb = aceptacionTerminosWeb ? new Date(System.currentTimeMillis()) : null;

		// Verificar si se proporcionaron todos los datos necesarios
		if (nombre == null || email == null || nombre.isEmpty() || email.isEmpty()) {
			ControlErrores.mostrarErrorGenerico("Todos los campos son obligatorios. Por favor, complete el formulario.",
					response);
			return;
		}

		// Verificar si el nombre de usuario ya existe
		if (DaoUsuario.getInstance().existeUsuarioConNombre(nombre, -1)) {
			ControlErrores.mostrarErrorGenerico("Ya existe un usuario con ese nombre. Por favor, elija otro nombre.",
					response);
			return;
		}

		// Crear un nuevo usuario
		Usuario usuario = new Usuario(nombre, email, MetodosComunes.getMD5(request.getParameter("contrasena")));
		usuario.setFechaNacimiento(fechaNacimiento);
		usuario.setRecibeNotificaciones(recibeNotificaciones);
		usuario.setIntereses(intereses);
		usuario.setRoles(roles);
		usuario.setPermiso(permiso);
		usuario.setConsentimiento_datos(fechaConsentimientoDatos);
		usuario.setAceptacionTerminosWeb(fechaAceptacionTerminosWeb);

		// Registrar el usuario en la base de datos
		try {
			DaoUsuario.getInstance().registrarUsuario(usuario);
			// Establecer permiso del usuario en la sesión
			iniciarSesion(request, response, usuario.getNombre(), request.getParameter("contrasena"));

		} catch (SQLException e) {
			// Mostrar mensaje de error detallado
			ControlErrores.mostrarErrorGenerico("Error al registrar el usuario: " + e.getMessage(), response);
		}
	}

	/**
	 * Crea un nuevo usuario a partir de los datos proporcionados en la solicitud
	 * HTTP. Permite al administrador asignar un rol específico al usuario creado.
	 *
	 * @param request  Objeto HttpServletRequest que contiene los datos del
	 *                 formulario enviado.
	 * @param response Objeto HttpServletResponse utilizado para enviar respuestas
	 *                 al cliente.
	 * @throws SQLException     Si ocurre un error al interactuar con la base de
	 *                          datos.
	 * @throws IOException      Si ocurre un error de entrada/salida durante la
	 *                          ejecución del método.
	 * @throws ServletException Si ocurre un error durante el procesamiento de la
	 *                          solicitud del servlet.
	 */
	private void crearUsuario(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {

		// Obtener parámetros del formulario
		String nombre = request.getParameter("nombre");
		String email = request.getParameter("email");
		Date fechaNacimiento = Date.valueOf(request.getParameter("fechaNacimiento"));
		boolean recibeNotificaciones = "on".equalsIgnoreCase(request.getParameter("recibeNotificaciones"));
		String intereses = request.getParameter("intereses");
		String rolesStr = request.getParameter("roles");
		Rol roles = (rolesStr != null && !rolesStr.isEmpty()) ? Rol.valueOf(rolesStr) : Rol.USUARIO;
		int permiso = (request.getParameter("permiso") != null) ? Integer.parseInt(request.getParameter("permiso")) : 1;
		boolean consentimientoDatos = "on".equalsIgnoreCase(request.getParameter("consentimiento_datos"));
		Date fechaConsentimientoDatos = consentimientoDatos ? new Date(System.currentTimeMillis()) : null;
		boolean aceptacionTerminosWeb = "on".equalsIgnoreCase(request.getParameter("aceptacionTerminosWeb"));
		Date fechaAceptacionTerminosWeb = aceptacionTerminosWeb ? new Date(System.currentTimeMillis()) : null;

		// Verificar si se proporcionaron todos los datos necesarios
		if (nombre == null || email == null || nombre.isEmpty() || email.isEmpty()) {
			ControlErrores.mostrarErrorGenerico("Todos los campos son obligatorios. Por favor, complete el formulario.",
					response);
			return;
		}

		// Verificar si el nombre de usuario ya existe
		if (DaoUsuario.getInstance().existeUsuarioConNombre(nombre, -1)) {
			ControlErrores.mostrarErrorGenerico("Ya existe un usuario con ese nombre. Por favor, elija otro nombre.",
					response);
			return;
		}

		// Crear un nuevo usuario
		Usuario usuario = new Usuario(nombre, email, MetodosComunes.getMD5(request.getParameter("contrasena")));
		usuario.setFechaNacimiento(fechaNacimiento);
		usuario.setRecibeNotificaciones(recibeNotificaciones);
		usuario.setIntereses(intereses);
		usuario.setRoles(roles);
		usuario.setPermiso(permiso);
		usuario.setConsentimiento_datos(fechaConsentimientoDatos);
		usuario.setAceptacionTerminosWeb(fechaAceptacionTerminosWeb);

		// Asignar el rol específico si se proporciona
		String rolEspecificoStr = request.getParameter("rolEspecifico");
		if (rolEspecificoStr != null && !rolEspecificoStr.isEmpty()) {
			Rol rolEspecifico = Rol.valueOf(rolEspecificoStr);
			usuario.setRoles(rolEspecifico);
		}

		// Registrar el usuario en la base de datos
		try {
			DaoUsuario.getInstance().registrarUsuario(usuario);
		} catch (SQLException e) {
			// Mostrar mensaje de error detallado
			ControlErrores.mostrarErrorGenerico("Error al registrar el usuario: " + e.getMessage(), response);
		}
	}

	/**
	 * Método para editar un usuario en la base de datos.
	 * 
	 * @param request  Objeto HttpServletRequest que contiene los parámetros del
	 *                 formulario.
	 * @param response Objeto HttpServletResponse utilizado para enviar la respuesta
	 *                 al cliente.
	 * 
	 * @throws IOException  Si ocurre un error de entrada/salida al escribir la
	 *                      respuesta.
	 * @throws SQLException Si ocurre un error de base de datos al editar el
	 *                      usuario.
	 */
	private void editarUsuario(HttpServletRequest request, HttpServletResponse response)
			throws IOException, SQLException {
		// Obtener parámetros del formulario
		int idUsuarioActual = DaoUsuario.getInstance().obtenerIdUsuarioActual(request);
		String nombre = request.getParameter("nombre");
		String email = request.getParameter("email");
		Date fechaNacimiento = null;
		try {
			fechaNacimiento = Date.valueOf(request.getParameter("fechaNacimiento"));
		} catch (IllegalArgumentException e) {
			// Manejar el error de formato de fecha
			ControlErrores.mostrarErrorGenerico("{\"error\": \"Formato de fecha de nacimiento inválido\"}", response);

		}
		boolean recibeNotificaciones = request.getParameter("recibeNotificaciones") != null;
		String intereses = request.getParameter("intereses");

		// Verificar si el nuevo nombre ya existe en la base de datos
		if (DaoUsuario.getInstance().existeUsuarioConNombre(nombre, idUsuarioActual)) {
			// ControlErrores.mostrarErrorGenerico( "{\"error\": \"Ya existe un usuario con
			// ese nombre, inténtelo de nuevo con otro nombre.\"}", response);

			ControlErrores.mostrarErrorGenerico(
					"{\"error\": \"Ya existe un usuario con ese nombre, inténtelo de nuevo con otro nombre. Intente de nuevo.\"}",
					response);
			return;
		}

		// Crear un objeto Usuario con la información actualizada
		Usuario usuario = new Usuario(idUsuarioActual, nombre, email, recibeNotificaciones, intereses, fechaNacimiento);

		// Editar el usuario en la base de datos
		try {
			DaoUsuario.getInstance().editarUsuario(usuario);

			response.setStatus(HttpServletResponse.SC_OK);

			response.sendRedirect("perfilusuario.html");

		} catch (Exception e) {
			// Manejar el error al editar el usuario en la base de datos
			ControlErrores.mostrarErrorGenerico("{\"error\": \"Error al editar el usuario. Intente de nuevo.\"}",
					response);

		}

	}

	/**
	 * Elimina un usuario de la base de datos.
	 *
	 * @param request  Objeto HttpServletRequest que contiene la solicitud HTTP.
	 * @param response Objeto HttpServletResponse que se utilizará para enviar la
	 *                 respuesta HTTP.
	 * @throws IOException  Si se produce un error de entrada/salida.
	 * @throws SQLException Si se produce un error al acceder a la base de datos.
	 */
	private void eliminarUsuario(HttpServletRequest request, HttpServletResponse response)
			throws IOException, SQLException {
		// Obtener parámetro del ID del usuario
		int idUsuario = Integer.parseInt(request.getParameter("idUsuario"));

		// Eliminar el usuario de la base de datos
		try {
			DaoUsuario.getInstance().eliminarUsuario(idUsuario);
			response.getWriter().println("Usuario eliminado exitosamente!");
		} catch (Exception e) {
			ControlErrores.mostrarErrorGenerico("Error al eliminar el usuario. Intente de nuevo.", response);
		}
	}

	/**
	 * Marca un evento como favorito para un usuario.
	 *
	 * @param request  Objeto HttpServletRequest que contiene la solicitud HTTP.
	 * @param response Objeto HttpServletResponse que se utilizará para enviar la
	 *                 respuesta HTTP.
	 * @throws IOException  Si se produce un error de entrada/salida.
	 * @throws SQLException Si se produce un error al acceder a la base de datos.
	 */
	private void marcarEventoFavorito(HttpServletRequest request, HttpServletResponse response)
			throws IOException, SQLException {
		// Obtener parámetros del formulario
		int idUsuario = Integer.parseInt(request.getParameter("idUsuario"));
		int idEvento = Integer.parseInt(request.getParameter("idEvento"));

		// Marcar el evento como favorito
		try {
			DaoUsuario.getInstance().marcarEventoFavorito(idUsuario, idEvento);
			// response.getWriter().println("Evento marcado como favorito!");
		} catch (Exception e) {
			ControlErrores.mostrarErrorGenerico("Error al marcar el evento como favorito. Intente de nuevo.", response);
		}
	}

	/**
	 * Desmarca un evento como favorito para un usuario.
	 *
	 * @param request  Objeto HttpServletRequest que contiene la solicitud HTTP.
	 * @param response Objeto HttpServletResponse que se utilizará para enviar la
	 *                 respuesta HTTP.
	 * @throws IOException  Si se produce un error de entrada/salida.
	 * @throws SQLException Si se produce un error al acceder a la base de datos.
	 */
	private void desmarcarEventoFavorito(HttpServletRequest request, HttpServletResponse response)
			throws IOException, SQLException {
		// Obtener parámetros del formulario
		int idUsuario = Integer.parseInt(request.getParameter("idUsuario"));
		int idEvento = Integer.parseInt(request.getParameter("idEvento"));

		// Desmarcar el evento como favorito
		try {
			DaoUsuario.getInstance().desmarcarEventoFavorito(idUsuario, idEvento);
			// response.getWriter().println("Evento desmarcado como favorito!");
		} catch (Exception e) {
			ControlErrores.mostrarErrorGenerico("Error al desmarcar el evento como favorito. Intente de nuevo.",
					response);
		}
	}

	/**
	 * Cambia la contraseña de un usuario en la base de datos.
	 * 
	 * @param request  HttpServletRequest que contiene los parámetros del
	 *                 formulario.
	 * @param response HttpServletResponse para enviar la respuesta.
	 * @throws IOException  Si ocurre algún error de entrada/salida al escribir la
	 *                      respuesta.
	 * @throws SQLException Si ocurre algún error de SQL al intentar cambiar la
	 *                      contraseña.
	 */
	private void cambiarContrasena(HttpServletRequest request, HttpServletResponse response)
			throws IOException, SQLException {
		// Obtener parámetros del formulario
		int idUsuarioActual = DaoUsuario.getInstance().obtenerIdUsuarioActual(request);

		// Obtener la contraseña actual del usuario desde la base de datos
		String contrasenaAlmacenada = DaoUsuario.getInstance().obtenerContrasena(idUsuarioActual);

		// Verificar si la contraseña actual proporcionada coincide con la almacenada
		if (contrasenaAlmacenada.equals(MetodosComunes.getMD5(request.getParameter("contrasenaOR")))) {

			// Cambiar la contraseña
			try {
				DaoUsuario.getInstance().cambiarContrasena(idUsuarioActual,
						MetodosComunes.getMD5(request.getParameter("contrasenaAC")));

				response.sendRedirect("perfilusuario.html");

			} catch (Exception e) {
				ControlErrores.mostrarErrorGenerico("Error al cambiar la contraseña. Intente de nuevo.", response);
			}
		} else {
			// La contraseña actual proporcionada no coincide con la almacenada
			ControlErrores.mostrarErrorGenerico("La contraseña actual no es correcta.", response);
		}
	}

	/**
	 * Inicia sesión para el usuario proporcionado.
	 * 
	 * @param request            El objeto HttpServletRequest que representa la
	 *                           solicitud HTTP.
	 * @param response           El objeto HttpServletResponse que representa la
	 *                           respuesta HTTP.
	 * @param usuarioRegistro    El nombre de usuario registrado (opcional). Si se
	 *                           proporciona, se usará para iniciar sesión en lugar
	 *                           del parámetro "usuario" de la solicitud.
	 * @param contrasenaRegistro La contraseña del usuario registrado (opcional). Si
	 *                           se proporciona, se usará para iniciar sesión en
	 *                           lugar del parámetro "contrasena" de la solicitud.
	 * 
	 * @throws ServletException Si ocurre un error de servlet.
	 * @throws IOException      Si ocurre un error de entrada/salida al manejar la
	 *                          solicitud o la respuesta.
	 */
	private void iniciarSesion(HttpServletRequest request, HttpServletResponse response, String usuarioRegistro,
			String contrasenaRegistro) throws ServletException, IOException {
		// Obtener parámetros del formulario
		String usuarioSTR = request.getParameter("usuario");
		String contrasena = request.getParameter("contrasena");

		// Si se proporcionan usuarioRegistro y contrasenaRegistro, usar esos valores
		if (usuarioRegistro != null && contrasenaRegistro != null) {
			usuarioSTR = usuarioRegistro;
			contrasena = contrasenaRegistro;
		}

		// Iniciar sesión
		try {
			Usuario usuario = DaoUsuario.getInstance().iniciarSesion(usuarioSTR, MetodosComunes.getMD5(contrasena));

			if (usuario != null) {
				HttpSession session = request.getSession();
				session.setAttribute("usuario", usuario);
				session.setAttribute("permiso", usuario.getPermiso());
				session.setAttribute("idUsuario", usuario.getIdUsuario());

				// Establecer el estado de la respuesta como OK
				response.setStatus(HttpServletResponse.SC_OK);

				int permiso = usuario.getPermiso();
				if (permiso == 1) {
					response.sendRedirect("eventos.html");
				} else if (permiso == 2) {
					response.sendRedirect("moderador.html");
				} else if (permiso == 99) {
					response.sendRedirect("admin.html");
				} else {
					response.sendRedirect("index.html");
				}
			} else {
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				response.sendRedirect("index.html");
			}
		} catch (Exception e) {
			ControlErrores.mostrarErrorGenerico("Error al iniciar sesión. Intente de nuevo.", response);
		}
	}

	/**
	 * Cierra la sesión del usuario.
	 *
	 * @param request  Objeto HttpServletRequest que contiene la solicitud HTTP.
	 * @param response Objeto HttpServletResponse que se utilizará para enviar la
	 *                 respuesta HTTP.
	 * @throws IOException Si se produce un error de entrada/salida.
	 */
	private void cerrarSesion(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		session.invalidate();
		response.setStatus(HttpServletResponse.SC_OK);
		response.getWriter().println("Sesión cerrada exitosamente!");
	}

}
