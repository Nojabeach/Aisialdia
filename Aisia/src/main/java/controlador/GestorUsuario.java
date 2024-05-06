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
			case "verificarPermiso":
				verificarPermiso(request, response);
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
	private void registrarUsuario(HttpServletRequest request, HttpServletResponse response)
	        throws IOException {
	    // Obtener parámetros del formulario
	    String nombre = request.getParameter("nombre");
	    String email = request.getParameter("email");
	    String contrasena = request.getParameter("contrasena");
	    Date fechaNacimiento = Date.valueOf(request.getParameter("fechaNacimiento"));
	    boolean recibeNotificaciones = Boolean.parseBoolean(request.getParameter("recibeNotificaciones"));
	    String intereses = request.getParameter("intereses");
	    String rolesStr = request.getParameter("roles");
	    Rol roles = (rolesStr != null && !rolesStr.isEmpty()) ? Rol.valueOf(rolesStr) : Rol.USUARIO;
	    int permiso = Integer.parseInt(request.getParameter("permiso"));
	    Date consentimiento_datos = Date.valueOf(request.getParameter("consentimiento_datos"));
	    Date aceptacionTerminosWeb = Date.valueOf(request.getParameter("aceptacionTerminosWeb"));

	    // Verificar si se proporcionaron todos los datos necesarios
	    if (nombre == null || email == null || contrasena == null || nombre.isEmpty() || email.isEmpty() || contrasena.isEmpty()) {
	        ControlErrores.mostrarErrorGenerico("Todos los campos son obligatorios. Por favor, complete el formulario.", response);
	        return;
	    }

	    // Crear un nuevo usuario
	    Usuario usuario = new Usuario(nombre, email, contrasena);
	    usuario.setFechaNacimiento(fechaNacimiento);
	    usuario.setRecibeNotificaciones(recibeNotificaciones);
	    usuario.setIntereses(intereses);
	    usuario.setRoles(roles);
	    usuario.setPermiso(permiso);
	    usuario.setConsentimiento_datos(consentimiento_datos);
	    usuario.setAceptacionTerminosWeb(aceptacionTerminosWeb);

	    // Registrar el usuario en la base de datos
	    try {
	        DaoUsuario.getInstance().registrarUsuario(usuario);
	        response.setStatus(HttpServletResponse.SC_CREATED);
	        response.getWriter().println("Usuario registrado exitosamente!");
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
		String email = request.getParameter("email");

		// Iniciar sesión
		try {
			// Verificamos el inicio de sesión con la contraseña cifrada
			Usuario usuario = DaoUsuario.getInstance().iniciarSesion(email, getMD5(request.getParameter("contrasena")));
			if (usuario != null) {
				HttpSession session = request.getSession();
				session.setAttribute("usuario", usuario);
				response.getWriter().println("Inicio de sesión exitoso!");
			} else {
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				response.getWriter().println("Credenciales incorrectas.");
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
			response.getWriter().println("Estás logueado!");
		} else {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.getWriter().println("No estás logueado.");
		}
	}

	/**
	 * Verifica si el usuario tiene permiso para acceder a cierta funcionalidad.
	 *
	 * @param request  Objeto HttpServletRequest que contiene la solicitud HTTP.
	 * @param response Objeto HttpServletResponse que se utilizará para enviar la
	 *                 respuesta HTTP.
	 * @throws IOException Si se produce un error de entrada/salida.
	 */
	private void verificarPermiso(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		if (usuario != null) {
			String permisoStr = request.getParameter("permiso");
			if (permisoStr != null) {
				int permiso = Integer.parseInt(permisoStr);
				if (usuario.tienePermiso(permiso)) {
					response.setStatus(HttpServletResponse.SC_OK);
					response.getWriter().println("Tienes permiso para acceder.");
				} else {
					response.setStatus(HttpServletResponse.SC_FORBIDDEN);
					response.getWriter().println("No tienes permiso para acceder.");
				}
			} else {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				response.getWriter().println("Falta el parámetro 'permiso'.");
			}
		} else {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.getWriter().println("No estás logueado.");
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
	 * @param request  Objeto HttpServletRequest que contiene los parámetros del formulario.
	 * @param response Objeto HttpServletResponse utilizado para enviar la respuesta al cliente.
	 * 
	 * @throws IOException  Si ocurre un error de entrada/salida al escribir la respuesta.
	 * @throws SQLException Si ocurre un error de base de datos al editar el usuario.
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

		// Cambiar la contraseña del usuario
		try {
			DaoUsuario.getInstance().cambiarContrasena(idUsuario, contrasenaActual, contrasenaNueva);
			response.getWriter().println("Contraseña cambiada exitosamente!");
		} catch (Exception e) {
			ControlErrores.mostrarErrorGenerico("Error al cambiar la contraseña. Intente de nuevo.", response);
		}
	}

}
