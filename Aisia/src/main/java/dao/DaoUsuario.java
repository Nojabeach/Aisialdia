package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import modelo.Evento;
import modelo.PermisoUsuario;
import modelo.Usuario;
import modelo.Usuario.Rol;

public class DaoUsuario {

	private Connection con = null;
	private static DaoUsuario instance;

	/**
	 * Clase de Acceso a Datos (DAO) para la gestión de usuarios en el sistema.
	 * Implementa el patrón Singleton y utiliza controladores mediante Servlets para
	 * la interacción con la base de datos.
	 * 
	 * @author Maitane Ibañez Irazabal
	 * @version 1.0
	 */
	public DaoUsuario() throws SQLException {

		con = DBConection.getConection();

	}

	/**
	 * Este metodo es el que se utiliza para aplicar el patron SINGLETON
	 * 
	 * @return
	 * @throws SQLException
	 */

	public static DaoUsuario getInstance() throws SQLException {
		if (instance == null) {
			instance = new DaoUsuario();
		}
		return instance;
	}

	/**
	 * Registra un nuevo usuario en la base de datos.
	 * 
	 * @param usuario Objeto Usuario con la información del nuevo usuario.
	 * @throws SQLException             Si ocurre un error SQL al registrar el
	 *                                  usuario.
	 * @throws IllegalArgumentException Si el objeto usuario es nulo o contiene
	 *                                  valores nulos.
	 */
	public void registrarUsuario(Usuario usuario) throws SQLException, IllegalArgumentException {

		String sql = "INSERT INTO usuarios (nombre, email, contrasena,fechaNacimiento,recibeNotificaciones,intereses,roles,permiso,consentimiento_datos,aceptacionTerminosWeb) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		System.out.println(sql);
		System.out.println(sql);
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, usuario.getNombre());
		ps.setString(2, usuario.getEmail());
		ps.setString(3, usuario.getcontrasena());
		ps.setDate(4, usuario.getFechaNacimiento());
		ps.setBoolean(5, usuario.isRecibeNotificaciones());
		ps.setString(6, usuario.getIntereses());
		ps.setString(7, (usuario.getRoles() != null) ? usuario.getRoles().toString() : Rol.USUARIO.toString());
		ps.setInt(8, (usuario.getPermiso() != 0) ? usuario.getPermiso() : 1);
		ps.setDate(9, usuario.getConsentimiento_datos());
		ps.setDate(10, usuario.getAceptacionTerminosWeb());
		ps.executeUpdate();
	}

	/**
	 * Valida el usuario y contraseña, y devuelve un objeto Usuario si la
	 * autenticación es correcta. Además, registra el acceso en la tabla de accesos.
	 * 
	 * @param email      Correo electrónico del usuario.
	 * @param contrasena Contraseña del usuario (no se almacena, solo se utiliza
	 *                   para autenticar).
	 * @return Objeto Usuario si la autenticación es correcta, null si no lo es.
	 * @throws Exception Si ocurre un error al iniciar sesión.
	 */
	public Usuario iniciarSesion(String usuario, String contrasena) throws Exception {
		// Preparar la consulta SQL para validar el usuario y contraseña
		String sql = "SELECT * FROM usuarios WHERE usuario =? AND contrasena =?";
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, usuario);
			ps.setString(2, contrasena); // Ya se está pasando la contraseña cifrada
			System.out.println(sql);
			System.out.println(usuario);
			System.out.println(contrasena);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				// Registramos el acceso en la tabla de accesos
				registrarAcceso(rs.getInt("idUsuario"));
				return new Usuario(rs.getInt("idUsuario"), rs.getString("nombre"), rs.getString("email"),
						rs.getInt("permiso"));
			}
		}
		return null;
	}

	/**
	 * Registra un acceso en la tabla de accesos.
	 * 
	 * @param idUsuario ID del usuario que accede.
	 * @throws SQLException Si se produce un error al acceder a la base de datos.
	 */
	private void registrarAcceso(int idUsuario) throws SQLException {
		String sql = "INSERT INTO accesos (idUsuario, fechaAcceso) VALUES (?, NOW())";
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, idUsuario);
			ps.executeUpdate();
		}
	}

	/**
	 * Obtiene un usuario por su ID.
	 * 
	 * @param idUsuario Identificador del usuario.
	 * @return Objeto Usuario con la información del usuario, o null si no se
	 *         encuentra.
	 * @throws Exception Si ocurre un error al obtener el usuario.
	 */
	public Usuario obtenerINFOUsuarioPorID(int idUsuario) throws Exception {
		// Preparar la consulta SQL para obtener el usuario por ID
		String sql = "SELECT * FROM usuarios WHERE idUsuario = ?";
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setInt(1, idUsuario);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return new Usuario(rs.getInt("idUsuario"), rs.getString("nombre"), rs.getString("email"),
						rs.getInt("permiso"));
			}
		}
		return null;
	}

	/**
	 * Actualiza la información de un usuario en la base de datos.
	 * 
	 * @param usuario Objeto Usuario con la información actualizada del usuario.
	 * @throws Exception Si ocurre un error al editar el usuario.
	 */
	public void editarUsuario(Usuario usuario) throws Exception {
		// Preparar la consulta SQL para actualizar el usuario
		String sql = "UPDATE usuarios SET nombre = ?, email = ? WHERE idUsuario = ?";
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setString(1, usuario.getNombre());
			stmt.setString(2, usuario.getEmail());
			stmt.setInt(3, usuario.getIdUsuario());
			stmt.executeUpdate();
		}
	}

	/**
	 * Elimina un usuario de la base de datos.
	 * 
	 * @param idUsuario Identificador del usuario.
	 * @throws Exception Si ocurre un error al eliminar el usuario.
	 */
	public void eliminarUsuario(int idUsuario) throws Exception {
		// Preparar la consulta SQL para eliminar el usuario
		String sql = "DELETE FROM usuarios WHERE idUsuario = ?";
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setInt(1, idUsuario);
			stmt.executeUpdate();
		}
	}

	/**
	 * Obtiene la lista de eventos a los que está asistiendo un usuario.
	 * 
	 * @param idUsuario Identificador del usuario.
	 * @return Lista de objetos Evento con la información de los eventos.
	 * @throws Exception Si ocurre un error al obtener los eventos.
	 */
	public List<Evento> obtenerEventos(int idUsuario) throws Exception {
		// Preparar la consulta SQL para obtener los eventos del usuario
		String sql = "SELECT e.* FROM eventos e JOIN asistencia a ON e.idEvento = a.idEvento WHERE a.idUsuario = ?";
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setInt(1, idUsuario);
			ResultSet rs = stmt.executeQuery();
			List<Evento> eventos = new ArrayList<>();
			while (rs.next()) {
				eventos.add(new Evento(rs.getInt("idEvento"), rs.getString("nombre"), rs.getString("detalles"),
						rs.getDate("fechaEvento")));
			}
			return eventos;
		}
	}

	/**
	 * Busca eventos por un criterio de búsqueda.
	 * 
	 * @param criterio Criterio de búsqueda (nombre, fecha, etc.).
	 * @return Lista de objetos Evento con la información de los eventos
	 *         coincidentes.
	 * @throws Exception Si ocurre un error al buscar los eventos.
	 */
	public List<Evento> buscarEventos(String criterio) throws Exception {
		// Preparar la consulta SQL para buscar eventos con el criterio especificado
		String sql = "SELECT * FROM eventos WHERE nombre LIKE? OR descripcion LIKE?";
		List<Evento> eventos = new ArrayList<>();
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setString(1, "%" + criterio + "%");
			stmt.setString(2, "%" + criterio + "%");
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					eventos.add(new Evento(rs.getInt("idEvento"), rs.getString("nombre"), rs.getString("detalles"),
							rs.getDate("fechaEvento")));
				}
			}
		} catch (SQLException e) {
			throw new Exception("Error al buscar eventos", e);
		}
		return eventos;
	}

	/**
	 * Obtiene la información de un evento por su ID.
	 * 
	 * @param idEvento Identificador del evento.
	 * @return Objeto Evento con la información del evento, o null si no se
	 *         encuentra.
	 * @throws Exception Si ocurre un error al obtener el evento.
	 */
	public Evento verEvento(int idEvento) throws Exception {
		// Preparar la consulta SQL para obtener el evento por ID
		String sql = "SELECT * FROM eventos WHERE idEvento = ?";
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setInt(1, idEvento);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return new Evento(rs.getInt("idEvento"), rs.getString("nombre"), rs.getString("detalles"),
						rs.getDate("fechaEvento"));
			}
		}
		return null;
	}

	/**
	 * Marca un evento como favorito para un usuario.
	 * 
	 * @param idUsuario Identificador del usuario.
	 * @param idEvento  Identificador del evento.
	 * @throws Exception Si ocurre un error al marcar el evento como favorito.
	 */
	public void marcarEventoFavorito(int idUsuario, int idEvento) throws Exception {
		// Preparar la consulta SQL para marcar el evento como favorito
		String sql = "INSERT INTO favoritos (idUsuario, idEvento) VALUES (?, ?)";
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setInt(1, idUsuario);
			stmt.setInt(2, idEvento);
			stmt.executeUpdate();
		}
	}

	/**
	 * Elimina un evento de la lista de favoritos de un usuario.
	 * 
	 * @param idUsuario Identificador del usuario.
	 * @param idEvento  Identificador del evento.
	 * @throws Exception Si ocurre un error al desmarcar el evento como favorito.
	 */
	public void desmarcarEventoFavorito(int idUsuario, int idEvento) throws Exception {
		// Preparar la consulta SQL para desmarcar el evento como favorito
		String sql = "DELETE FROM favoritos WHERE idUsuario = ? AND idEvento = ?";
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setInt(1, idUsuario);
			stmt.setInt(2, idEvento);
			stmt.executeUpdate();
		}

	}

	/**
	 * Permite a un usuario cambiar su contraseña.
	 * 
	 * @param idUsuario        Identificador del usuario.
	 * @param contrasenaActual Contraseña actual del usuario.
	 * @param contrasenaNueva  Nueva contraseña del usuario.
	 * @throws Exception Si ocurre un error al cambiar la contraseña.
	 */
	public void cambiarContrasena(int idUsuario, String contrasenaActual, String contrasenaNueva) throws Exception {
		// Validar la contraseña actual
		Usuario usuarioActual = obtenerINFOUsuarioPorID(idUsuario);
		if (usuarioActual == null || !usuarioActual.getcontrasena().equals(contrasenaActual)) {
			throw new Exception("Contraseña actual incorrecta.");
		}

		// Actualizar la contraseña en la base de datos
		String sql = "UPDATE usuarios SET contrasena = ? WHERE idUsuario = ?";
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, contrasenaNueva);
			ps.setInt(2, idUsuario);
			ps.executeUpdate();
		}
	}

	/**
	 * Obtiene una lista de todos los usuarios.
	 * 
	 * Este método consulta la tabla `usuarios` de la base de datos y devuelve una
	 * lista de objetos `Usuario` que representan a todos los usuarios registrados.
	 * 
	 * @return La lista de usuarios o una lista vacía si no se encuentran usuarios.
	 * @throws SQLException Si ocurre un error al consultar la base de datos.
	 */
	public List<Usuario> obtenerUsuarios() throws SQLException {
		String sql = "SELECT * FROM usuarios";
		List<Usuario> usuarios = new ArrayList<>();

		try (PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				int idUsuario = rs.getInt("idUsuario");
				String nombre = rs.getString("nombre");
				String email = rs.getString("email");
				String contrasena = rs.getString("contrasena");
				Date fechaNacimiento = rs.getDate("fechaNacimiento");
				boolean recibeNotificaciones = rs.getBoolean("recibeNotificaciones");
				String intereses = rs.getString("intereses");
				int permiso = rs.getInt("permiso");
				Rol rol = Rol.valueOf(rs.getString("roles"));
				Date consentimiento_datos = rs.getDate("consentimiento_datos");
				Date aceptacionTerminosWeb = rs.getDate("aceptacionTerminosWeb");

				Usuario usuario = new Usuario(idUsuario, nombre, email, contrasena, fechaNacimiento,
						recibeNotificaciones, intereses, permiso, rol, consentimiento_datos, aceptacionTerminosWeb);
				usuarios.add(usuario);
			}
		}

		return usuarios;
	}

	/**
	 * Obtiene el ID del usuario actual de la sesión HTTP.
	 * 
	 * @param request Objeto HttpServletRequest para obtener la información de la
	 *                sesión.
	 * @return El ID del usuario actual, o -1 si no se encuentra el usuario en la
	 *         sesión.
	 */
	public static int obtenerIdUsuarioActual(HttpServletRequest request) {
		Usuario usuarioSesion = (Usuario) request.getSession().getAttribute("usuario");
		if (usuarioSesion != null) {
			return usuarioSesion.getIdUsuario();
		}
		return -1;
	}

	/**
	 * Busca el permiso de un usuario en la base de datos según su ID.
	 *
	 * @param idUsuario ID del usuario del cual se desea obtener el permiso.
	 * @return Objeto PermisoUsuario que representa el permiso del usuario.
	 */
	public PermisoUsuario buscarPermisoUsuario(int idUsuario) {
		PermisoUsuario permiso = null;

		String sql = "SELECT permiso FROM usuario WHERE id = ?";
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, idUsuario);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				int permisoNumero = rs.getInt("permiso");
				permiso = new PermisoUsuario(permisoNumero);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return permiso;
	}

	/**
	 * Obtiene la contraseña de un usuario por su ID.
	 *
	 * @param idUsuario El ID del usuario del que se desea obtener la contraseña.
	 * @return La contraseña del usuario como una cadena, o null si el usuario no
	 *         existe.
	 * @throws SQLException Si ocurre un error al acceder a la base de datos.
	 */
	public String obtenerContrasena(int idUsuario) throws SQLException {
		String contrasena = null;
		String sql = "SELECT contrasena FROM usuarios WHERE id_usuario = ?";

		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, idUsuario);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					contrasena = rs.getString("contrasena");
				}
			}
		}

		return contrasena;
	}

}
