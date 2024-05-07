package controlador;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import modelo.Evento;
import modelo.Usuario;
import modelo.Usuario.Rol;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import com.google.gson.Gson;

import dao.DaoUsuario;

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
	 * Maneja las solicitudes GET enviadas al servlet de las siguientes
	 * <strong>acciones</strong>:
	 * 
	 * <ul>
	 * <li><b>verUsuarios</b>: Obtiene la lista de usuarios del sistema.</li>
	 * <li><b>verUsuario</b>: Obtiene la información de un usuario específico.</li>
	 * <li><b>verEventosUsuario</b>: Obtiene la lista de eventos asociados a un
	 * usuario.</li>
	 * <li><b>verEventoUsuario</b>: Obtiene la información detallada de un evento
	 * específico.</li>
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

		try {
			switch (action) {
			case "verUsuarios":
				verUsuarios(request, response);
				break;
			case "verUsuario":
				verUsuario(request, response);
				break;
			case "verEventosUsuario":
				verEventosUsuario(request, response);
				break;
			case "verEventoUsuario":
				verEventoUsuario(request, response);
				break;
			case "cerrarSesion":
				cerrarSesion(request, response);
				break;
			case "comprobarLogin":
				comprobarLogin(request, response);
				break;
			case "obtenerPermiso":
				obtenerPermiso(request, response);
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
	 * Maneja las solicitudes POST enviadas al servlet de las siguientes
	 * <strong>acciones</strong>:
	 * 
	 * <ul>
	 * <li><b>registrarUsuario</b>: Registra un nuevo usuario en el sistema.</li>
	 * <li><b>iniciarSesion</b>: Inicia sesión en el sistema.</li>
	 * <li><b>editarUsuario</b>: Edita la información de un usuario existente.</li>
	 * <li><b>eliminarUsuario</b>: Elimina un usuario del sistema.</li>
	 * <li><b>marcarEventoFavorito</b>: Marca un evento como favorito para el
	 * usuario.</li>
	 * <li><b>desmarcarEventoFavorito</b>: Desmarca un evento como favorito para el
	 * usuario.</li>
	 * <li><b>cambiarContrasena</b>: Cambia la contraseña de un usuario.</li>
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
			case "iniciarSesion":
				iniciarSesion(request, response);
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
	 * Registra un nuevo usuario en el sistema.
	 *
	 * @param request  Objeto HttpServletRequest que contiene la solicitud HTTP.
	 * @param response Objeto HttpServletResponse que se utilizará para enviar la
	 *                 respuesta HTTP.
	 * @throws IOException  Si se produce un error de entrada/salida.
	 * @throws SQLException Si se produce un error al acceder a la base de datos.
	 */

	private void registrarUsuario(HttpServletRequest request, HttpServletResponse response) throws IOException {

		// Obtener parámetros del formulario
		System.out.println("Registrando usuario...");

		String nombre = request.getParameter("nombre");
		System.out.println("Nombre: " + nombre);

		String email = request.getParameter("email");
		System.out.println("Email: " + email);

		Date fechaNacimiento = Date.valueOf(request.getParameter("fechaNacimiento"));
		System.out.println("Fecha de nacimiento: " + fechaNacimiento);

		boolean recibeNotificaciones = Boolean.parseBoolean(request.getParameter("recibeNotificaciones"));
		System.out.println("Recibe notificaciones: " + recibeNotificaciones);

		String intereses = request.getParameter("intereses");
		System.out.println("Intereses: " + intereses);

		String rolesStr = request.getParameter("roles");
		Rol roles = (rolesStr != null && !rolesStr.isEmpty()) ? Rol.valueOf(rolesStr) : Rol.USUARIO;
		System.out.println("Roles: " + roles);

		int permiso = (request.getParameter("permiso") != null) ? Integer.parseInt(request.getParameter("permiso")) : 1;

		System.out.println("Permiso: " + permiso);

		System.out.println("Consentimiento de datos: " + request.getParameter("consentimiento_datos"));
		System.out.println("Aceptación de términos y condiciones: " + request.getParameter("aceptacionTerminosWeb"));

		boolean consentimientoDatos = "on".equalsIgnoreCase(request.getParameter("consentimiento_datos"));
		Date fechaConsentimientoDatos = consentimientoDatos ? new Date(System.currentTimeMillis()) : null;
		System.out.println("Consentimiento de datos: " + fechaConsentimientoDatos);

		boolean aceptacionTerminosWeb = "on".equalsIgnoreCase(request.getParameter("aceptacionTerminosWeb"));
		Date fechaAceptacionTerminosWeb = aceptacionTerminosWeb ? new Date(System.currentTimeMillis()) : null;
		System.out.println("Aceptación de términos y condiciones web: " + fechaAceptacionTerminosWeb);

		// Verificar si se proporcionaron todos los datos necesarios
		if (nombre == null || email == null || nombre.isEmpty() || email.isEmpty()) {
			ControlErrores.mostrarErrorGenerico("Todos los campos son obligatorios. Por favor, complete el formulario.",
					response);
			return;
		}

		// Crear un nuevo usuario
		Usuario usuario = new Usuario(nombre, email, getMD5(request.getParameter("contrasena")));
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
			response.setStatus(HttpServletResponse.SC_CREATED);
			response.getWriter().println("Usuario registrado exitosamente!");

			// Establecer permiso del usuario en la sesión
			HttpSession session = request.getSession();
			session.setAttribute("usuario", usuario);
			session.setAttribute("permiso", usuario.getPermiso());

			response.sendRedirect("eventos.html");

		} catch (SQLException e) {
			ControlErrores.mostrarErrorGenerico("Error al registrar el usuario. Intente de nuevo.", response);
		}
	}

	/**
	 * Inicia sesión de un usuario en el sistema.
	 *
	 * @param request  Objeto HttpServletRequest que contiene la solicitud HTTP.
	 * @param response Objeto HttpServletResponse que se utilizará para enviar la
	 *                 respuesta HTTP.
	 * @throws IOException  Si se produce un error de entrada/salida.
	 * @throws SQLException Si se produce un error al acceder a la base de datos.
	 */
	private void iniciarSesion(HttpServletRequest request, HttpServletResponse response)
			throws IOException, SQLException {
		// Obtener parámetros del formulario
		String usuarioSTR = request.getParameter("usuario");

		// Iniciar sesión
		try {
			// Verificamos el inicio de sesión con la contraseña cifrada
			System.out.println(usuarioSTR);
			System.out.println(request.getParameter("contrasena"));

			Usuario usuario = DaoUsuario.getInstance().iniciarSesion(usuarioSTR,
					getMD5(request.getParameter("contrasena")));

			System.out.println(usuario);

			if (usuario != null) {
				HttpSession session = request.getSession();
				session.setAttribute("usuario", usuario);
				session.setAttribute("permiso", usuario.getPermiso());

				// Establecer el estado de la respuesta como OK
				response.setStatus(HttpServletResponse.SC_OK);

				int permiso = usuario.getPermiso();
				System.out.println("Permiso: " + permiso); // Debugging
				if (permiso == 1) {
					System.out.println("entro en 1"); // Debugging
					response.sendRedirect("eventos.html");
				} else if (permiso == 2) {
					System.out.println("entro en 2"); // Debugging
					response.sendRedirect("moderador.html");
				} else if (permiso == 99) {
					System.out.println("entro en 99"); // Debugging
					response.sendRedirect("admin.html");
				} else {
					response.sendRedirect("index.html");
				}
			} else {
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				response.sendRedirect("index.html");
				System.out.println("Credenciales incorrectas.");
			}
		} catch (Exception e) {
			ControlErrores.mostrarErrorGenerico("Error al iniciar sesión. Intente de nuevo.", response);
		}
	}

	public static String getMD5(String input) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] messageDigest = md.digest(input.getBytes());
			BigInteger number = new BigInteger(1, messageDigest);
			String hashtext = number.toString(16);

			while (hashtext.length() < 32) {
				hashtext = "0" + hashtext;
			}
			return hashtext;
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
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

	/**
	 * Comprueba si el usuario ha iniciado sesión.
	 *
	 * @param request  Objeto HttpServletRequest que contiene la solicitud HTTP.
	 * @param response Objeto HttpServletResponse que se utilizará para enviar la
	 *                 respuesta HTTP.
	 * @throws IOException Si se produce un error de entrada/salida.
	 */
	private void comprobarLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		if (usuario != null) {
			response.setStatus(HttpServletResponse.SC_OK);

		} else {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

		}
	}

	/**
	 * Obtiene la lista de usuarios y los envía como respuesta.
	 *
	 * @param request  Objeto HttpServletRequest que contiene la solicitud HTTP.
	 * @param response Objeto HttpServletResponse que se utilizará para enviar la
	 *                 respuesta HTTP.
	 * @throws IOException  Si se produce un error de entrada/salida.
	 * @throws SQLException Si se produce un error al acceder a la base de datos.
	 */
	private void verUsuarios(HttpServletRequest request, HttpServletResponse response)
			throws IOException, SQLException {
		// Obtener lista de usuarios
		try {
			List<Usuario> usuarios = DaoUsuario.getInstance().obtenerUsuarios();
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(usuarios.toString());
		} catch (SQLException e) {
			ControlErrores.mostrarErrorGenerico("Error al obtener la lista de usuarios. Intente de nuevo.", response);
		}
	}

	/**
	 * Obtiene la información de un usuario y la envía como respuesta.
	 *
	 * @param request  Objeto HttpServletRequest que contiene la solicitud HTTP.
	 * @param response Objeto HttpServletResponse que se utilizará para enviar la
	 *                 respuesta HTTP.
	 * @throws IOException  Si se produce un error de entrada/salida.
	 * @throws SQLException Si se produce un error al acceder a la base de datos.
	 */
	private void verUsuario(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
		// Obtener parámetro del ID del usuario
		int idUsuario = Integer.parseInt(request.getParameter("idUsuario"));

		// Obtener información del usuario
		try {
			Usuario usuario = DaoUsuario.getInstance().obtenerINFOUsuarioPorID(idUsuario);
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(usuario.toString());
		} catch (Exception e) {
			ControlErrores.mostrarErrorGenerico("Error al obtener la información del usuario. Intente de nuevo.",
					response);
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
		int idUsuario = Integer.parseInt(request.getParameter("idUsuario"));
		String nombre = request.getParameter("nombre");
		String email = request.getParameter("email");

		// Crear un objeto Usuario con la información actualizada
		Usuario usuario = new Usuario(idUsuario, nombre, email);

		// Editar el usuario en la base de datos
		try {
			DaoUsuario.getInstance().editarUsuario(usuario);
			response.getWriter().println("Usuario editado exitosamente!");
		} catch (Exception e) {
			ControlErrores.mostrarErrorGenerico("Error al editar el usuario. Intente de nuevo.", response);
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
	 * Obtiene la lista de eventos asociados a un usuario.
	 *
	 * @param request  Objeto HttpServletRequest que contiene la solicitud HTTP.
	 * @param response Objeto HttpServletResponse que se utilizará para enviar la
	 *                 respuesta HTTP.
	 * @throws IOException  Si se produce un error de entrada/salida.
	 * @throws SQLException Si se produce un error al acceder a la base de datos.
	 */
	private void verEventosUsuario(HttpServletRequest request, HttpServletResponse response)
			throws IOException, SQLException {
		// Obtener parámetro del ID del usuario
		int idUsuario = Integer.parseInt(request.getParameter("idUsuario"));

		// Obtener lista de eventos del usuario
		try {
			List<Evento> eventos = DaoUsuario.getInstance().obtenerEventos(idUsuario);
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(eventos.toString());
		} catch (Exception e) {
			ControlErrores.mostrarErrorGenerico("Error al obtener la lista de eventos del usuario. Intente de nuevo.",
					response);
		}
	}

	/**
	 * Obtiene la información de un evento.
	 *
	 * @param request  Objeto HttpServletRequest que contiene la solicitud HTTP.
	 * @param response Objeto HttpServletResponse que se utilizará para enviar la
	 *                 respuesta HTTP.
	 * @throws IOException  Si se produce un error de entrada/salida.
	 * @throws SQLException Si se produce un error al acceder a la base de datos.
	 */
	private void verEventoUsuario(HttpServletRequest request, HttpServletResponse response)
			throws IOException, SQLException {
		// Obtener parámetro del ID del evento
		int idEvento = Integer.parseInt(request.getParameter("idEvento"));

		// Obtener información del evento
		try {
			Evento evento = DaoUsuario.getInstance().verEvento(idEvento);
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(evento.toString());
		} catch (Exception e) {
			ControlErrores.mostrarErrorGenerico("Error al obtener la información del evento. Intente de nuevo.",
					response);
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
			response.getWriter().println("Evento marcado como favorito!");
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
			response.getWriter().println("Evento desmarcado como favorito!");
		} catch (Exception e) {
			ControlErrores.mostrarErrorGenerico("Error al desmarcar el evento como favorito. Intente de nuevo.",
					response);
		}
	}

	/**
	 * Cambia la contraseña de un usuario.
	 *
	 * @param request  Objeto HttpServletRequest que contiene la solicitud HTTP.
	 * @param response Objeto HttpServletResponse que se utilizará para enviar la
	 *                 respuesta HTTP.
	 * @throws IOException  Si se produce un error de entrada/salida.
	 * @throws SQLException Si se produce un error al acceder a la base de datos.
	 */
	private void cambiarContrasena(HttpServletRequest request, HttpServletResponse response)
			throws IOException, SQLException {
		// Obtener parámetros del formulario
		int idUsuario = Integer.parseInt(request.getParameter("idUsuario"));
		String contrasenaActual = request.getParameter("contrasenaActual");
		String contrasenaNueva = request.getParameter("contrasenaNueva");

		// Obtener la contraseña actual del usuario desde la base de datos
		String contrasenaAlmacenada = DaoUsuario.getInstance().obtenerContrasena(idUsuario);

		// Cifrar la contraseña actual proporcionada por el usuario
		String contrasenaActualCifrada = getMD5(contrasenaActual);

		// Verificar si la contraseña actual coincide con la almacenada
		if (contrasenaActualCifrada.equals(contrasenaAlmacenada)) {
			// Cifrar la nueva contraseña
			String contrasenaNuevaCifrada = getMD5(contrasenaNueva);

			// Actualizar la contraseña en la base de datos con la nueva contraseña cifrada
			try {
				DaoUsuario.getInstance().cambiarContrasena(idUsuario, contrasenaActualCifrada, contrasenaNuevaCifrada);
				response.getWriter().println("Contraseña cambiada exitosamente!");
			} catch (Exception e) {
				ControlErrores.mostrarErrorGenerico("Error al cambiar la contraseña. Intente de nuevo.", response);
			}
		} else {
			// La contraseña actual proporcionada no coincide con la almacenada
			ControlErrores.mostrarErrorGenerico("La contraseña actual no es correcta.", response);
		}

	}

	/**
	 * Obtiene el permiso del usuario actual y lo envía como respuesta. Si el
	 * usuario no está logueado, devuelve un estado de "No autorizado".
	 * 
	 * @param request  La solicitud HTTP
	 * @param response La respuesta HTTP
	 * @throws IOException Si hay algún error de entrada/salida al escribir en el
	 *                     flujo de salida
	 */
	private void obtenerPermiso(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			HttpSession session = request.getSession();
			Usuario usuario = (Usuario) session.getAttribute("usuario");
			if (usuario == null) {
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				return;
			}

			String nombreUsuario = usuario.getNombre(); // Obtenemos el nombre del usuario

			try {
				String permiso = DaoUsuario.getInstance().obtenerPermisoPorNombre(nombreUsuario);

				// Convertir el permiso a JSON
				String permisoJSON = new Gson().toJson(permiso);

				// Establecer el tipo de contenido y encabezados de la respuesta
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");

				// Enviar el permiso como respuesta
				response.getWriter().write(permisoJSON);

			} catch (Exception e) {
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				response.getWriter().write("{\"error\": \"Error al obtener el permiso.\"}");
			}
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().write("{\"error\": \"Error interno del servidor.\"}");
		}
	}

}
