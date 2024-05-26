package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import jakarta.servlet.http.HttpServletRequest;
import modelo.Usuario;
import modelo.Usuario.Rol;

public class DaoUsuario {

	private Connection con = null;
	private static DaoUsuario instance = null;

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

		String sql = "INSERT INTO usuarios (nombre, email, contrasena,fechaNacimiento,recibeNotificaciones,intereses,roles,permiso,consentimientoDatos,aceptacionTerminosWeb) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		// System.out.println(sql);
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, usuario.getNombre());
		ps.setString(2, usuario.getEmail());
		ps.setString(3, usuario.getcontrasena());
		ps.setDate(4, usuario.getFechaNacimiento());
		ps.setBoolean(5, usuario.isRecibeNotificaciones());
		ps.setString(6, usuario.getIntereses());
		ps.setString(7, (usuario.getRoles() != null) ? usuario.getRoles().toString() : Rol.usuario.toString());
		ps.setInt(8, (usuario.getPermiso() != 0) ? usuario.getPermiso() : 1);
		ps.setDate(9, usuario.getConsentimientoDatos());
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
	 * @throws SQLException Si ocurre un error al iniciar sesión.
	 */
	public Usuario iniciarSesion(String usuario, String contrasena) throws SQLException {
		// Preparar la consulta SQL para validar el usuario y contraseña
		String sql = "SELECT * FROM usuarios WHERE nombre =? AND contrasena =?";
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, usuario);
			ps.setString(2, contrasena); // Ya se está pasando la contraseña cifrada
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
	 * Obtiene un usuario por su ID. (verUsuario en el servlet GestorUsuario y en
	 * cambiarContrasena)
	 * 
	 * @param idUsuario Identificador del usuario.
	 * @return Objeto Usuario con la información del usuario, o null si no se
	 *         encuentra.
	 * @throws Exception Si ocurre un error al obtener el usuario.
	 */
	public Usuario obtenerINFOUsuarioPorID(int idUsuario) throws SQLException {
		// Preparar la consulta SQL para obtener el usuario por ID
		String sql = "SELECT * FROM usuarios WHERE idUsuario = ?";
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setInt(1, idUsuario);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				 // Obtener los campos necesarios
                int id = rs.getInt("idUsuario");
                String nombre = rs.getString("nombre");
                String email = rs.getString("email");
                boolean recibeNotificaciones = rs.getBoolean("recibeNotificaciones");
                String intereses = rs.getString("intereses");
                int permiso = rs.getInt("permiso");
                String rolStr = rs.getString("roles"); 
                Rol rol = Rol.valueOf(rolStr); 
                Date fechaNacimiento = rs.getDate("fechaNacimiento");


				// Crear y retornar el objeto Usuario con los campos obtenidos
				return new Usuario(id, nombre, email, recibeNotificaciones, intereses, permiso,rol,fechaNacimiento);

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
	/**
	 * Edita un usuario en la base de datos con los nuevos datos proporcionados.
	 * 
	 * @param usuario El objeto Usuario con los datos actualizados.
	 * @throws SQLException Si ocurre algún error de SQL al intentar editar el
	 *                      usuario.
	 */
	public void editarUsuario(Usuario usuario) throws SQLException {
		String sql = "UPDATE usuarios SET nombre = ?,  email = ?, fechaNacimiento = ?, intereses = ?, recibeNotificaciones = ? WHERE idUsuario = ?";
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, usuario.getNombre());
			ps.setString(2, usuario.getEmail());
			ps.setDate(3, usuario.getFechaNacimiento());
			ps.setString(4, usuario.getIntereses());
			ps.setBoolean(5, usuario.isRecibeNotificaciones());
			ps.setInt(6, usuario.getIdUsuario());
			ps.executeUpdate();
		}
	}

	
	/**
	 * Actualiza la información de un usuario en la base de datos.
	 * 
	 * @param usuario Objeto Usuario con la información actualizada del usuario.
	 * @throws Exception Si ocurre un error al editar el usuario.
	 */
	
	/**
	 * Edita un usuario en la base de datos con los nuevos datos proporcionados por el perfil de Administrador.
	 * 
	 * @param usuario El objeto Usuario con los datos actualizados.
	 * @throws SQLException Si ocurre algún error de SQL al intentar editar el
	 *                      usuario.
	 */
	public void editarUsuarioAdmin(Usuario usuario) throws SQLException {
		String sql = "UPDATE usuarios SET nombre = ?,  email = ?, fechaNacimiento = ?, intereses = ?, recibeNotificaciones = ?, roles=?,permiso=? WHERE idUsuario = ?";
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, usuario.getNombre());
			ps.setString(2, usuario.getEmail());
			ps.setDate(3, usuario.getFechaNacimiento());
			ps.setString(4, usuario.getIntereses());
			ps.setBoolean(5, usuario.isRecibeNotificaciones());
			ps.setString(6,usuario.getRoles().name());
			ps.setInt(7,usuario.getPermiso());
			ps.setInt(8, usuario.getIdUsuario());
			ps.executeUpdate();
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
	public void cambiarContrasena(int idUsuario, String contrasenaNueva) throws Exception {

		// Actualizar la contraseña en la base de datos
		String sql = "UPDATE usuarios SET contrasena = ? WHERE idUsuario = ?";
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, contrasenaNueva);
			ps.setInt(2, idUsuario);
			ps.executeUpdate();
		}
	}

	/**
	 * Obtiene una lista de todos los usuarios en la base de datos.
	 *
	 * @return Una lista de usuarios.
	 * @throws SQLException Si ocurre un error al acceder a la base de datos.
	 */
	public List<Usuario> obtenerUsuarios() throws SQLException {
	    
	    String sql = "SELECT * FROM usuarios ORDER BY idUsuario";
	    List<Usuario> usuarios = new ArrayList<>();

	    try (PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
	        while (rs.next()) {
	            Usuario usuario = new Usuario();
	            usuario.setIdUsuario(rs.getInt("idUsuario")); // ID del usuario
	            usuario.setNombre(rs.getString("nombre")); // Nombre del usuario
	            usuario.setEmail(rs.getString("email")); // Email del usuario
	            //usuario.setContrasena(rs.getString("contrasena")); // Contraseña del usuario
	            usuario.setFechaNacimiento(rs.getDate("fechaNacimiento")); // Fecha de nacimiento del usuario
	            usuario.setRecibeNotificaciones(rs.getBoolean("recibeNotificaciones")); // Si recibe notificaciones
	            usuario.setIntereses(rs.getString("intereses")); // Intereses del usuario
	            usuario.setPermiso(rs.getInt("permiso")); // Permiso del usuario
	            usuario.setRoles(Rol.valueOf(rs.getString("roles"))); // Rol del usuario
	           // usuario.setConsentimientoDatos(rs.getDate("consentimientoDatos")); // Fecha de consentimiento de datos
	           // usuario.setAceptacionTerminosWeb(rs.getDate("aceptacionTerminosWeb")); // Fecha de aceptación de términos web

	            //System.out.println(usuario);
	            usuarios.add(usuario);
	        }
	    } catch (SQLException e) {
	        // Manejar la excepción aquí
	        e.printStackTrace();
	    }

	    return usuarios;
	}

	/**
	 * Retorna una lista de usuarios filtrada por tipo de permiso.
	 *
	 * @param PERMISO El tipo de permiso por el que se desea filtrar los usuarios.
	 * @return Una lista de usuarios que tienen el permiso especificado.
	 * @throws SQLException Si ocurre algún error de SQL al intentar obtener los
	 *                      usuarios.
	 */
	public List<Usuario> obtenerUsuarios(int PERMISO) throws SQLException {
		String sql = "SELECT * FROM usuarios WHERE permiso=? ORDER BY nombre";
		List<Usuario> usuarios = new ArrayList<>();

		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, PERMISO);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					Usuario usuario = new Usuario(rs.getInt("idUsuario"), rs.getString("nombre"), rs.getString("email"),
							 rs.getBoolean("recibeNotificaciones"),rs.getString("intereses"), rs.getInt("permiso"), 
							 Rol.valueOf(rs.getString("rol")),rs.getDate("fechaNacimiento")

					);
					usuarios.add(usuario);
				}
			}
		}

		return usuarios;
	}

	/**
	 * Obtiene el ID del usuario actual de la sesión HTTP. Se necesita STATIC por
	 * que se hace uso de este metodo en la clase DaoFavorito.
	 * 
	 * @param request Objeto HttpServletRequest para obtener la información de la
	 *                sesión.
	 * @return El ID del usuario actual, o -1 si no se encuentra el usuario en la
	 *         sesión.
	 */
	public int obtenerIdUsuarioActual(HttpServletRequest request) {
		Usuario usuarioSesion = (Usuario) request.getSession().getAttribute("usuario");
		int IdUsuario = -1;
		if (usuarioSesion != null) {
			IdUsuario = usuarioSesion.getIdUsuario();
		}
		return IdUsuario;
	}

	/**
	 * Busca el permiso de un usuario en la base de datos.
	 * 
	 * @param idUsuario El ID del usuario cuyo permiso se desea buscar.
	 * @return El permiso del usuario.
	 * @throws SQLException Si ocurre algún error de SQL al intentar buscar el
	 *                      permiso del usuario.
	 */
	public int buscarPermisoUsuario(int idUsuario) throws SQLException {
		int permiso = 0;
		String sql = "SELECT permiso FROM usuario WHERE id = ?";
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, idUsuario);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				permiso = rs.getInt("permiso");
			}
		}
		return permiso;
	}

	/**
	 * Obtiene la contraseña de un usuario por su ID.
	 * 
	 * @param idUsuario El ID del usuario.
	 * @return La contraseña del usuario, o null si no se encuentra.
	 * @throws SQLException Si ocurre un error al obtener la contraseña del usuario.
	 */
	public String obtenerContrasena(int idUsuario) throws SQLException {
		// Preparar la consulta SQL para obtener la contraseña por ID de usuario
		String sql = "SELECT contrasena FROM usuarios WHERE idUsuario = ?";
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, idUsuario);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getString("contrasena");
			}
		}
		return null;
	}

	// ---------------------------------------------------------------------------------
	// VOLCADOS JSON
	// ---------------------------------------------------------------------------------

	/**
	 * Genera una representación JSON de la lista de usuarios.
	 * 
	 * @return Una cadena JSON que representa la lista de usuarios.
	 * @throws SQLException Si ocurre un error al acceder a la base de datos.
	 */
	public String listarUsuariosJson() throws SQLException {
		String json = "";
		Gson gson = new Gson();
		json = gson.toJson(this.obtenerUsuarios());
		return json;
	}

	/**
	 * Genera una representación JSON de la lista de usuarios filtrada por tipo de
	 * permiso.
	 * 
	 * @param tipo El tipo de permiso por el que se desea filtrar los usuarios.
	 * @return Una cadena JSON que representa la lista de usuarios filtrada por tipo
	 *         de permiso.
	 * @throws SQLException Si ocurre un error al acceder a la base de datos.
	 */
	public String listarUsuariosJson(int tipo) throws SQLException {
		String json = "";
		Gson gson = new Gson();
		json = gson.toJson(this.obtenerUsuarios(tipo));
		return json;
	}

	/**
	 * Devuelve la información de un usuario en formato JSON.
	 * 
	 * @param idUsuario El ID del usuario del cual se desea obtener la información.
	 * @return La información del usuario en formato JSON.
	 * @throws SQLException Si ocurre un error al acceder a la base de datos.
	 */
	public String listariNFOUsuarioJson(int idUsuario) throws SQLException {
		Gson gson = GsonHelper.getGson();
		return gson.toJson(this.obtenerINFOUsuarioPorID(idUsuario));
	}

	/**
	 * Clase de ayuda para obtener una instancia de Gson con una configuración
	 * específica.
	 */
	public class GsonHelper {

		private static Gson gson;

		/**
		 * Obtiene una instancia de Gson con el formato de fecha deseado.
		 * 
		 * @return Una instancia de Gson con el formato de fecha "yyyy-MM-dd".
		 */
		public static Gson getGson() {
			if (gson == null) {
				GsonBuilder gsonBuilder = new GsonBuilder();
				gsonBuilder.setDateFormat("yyyy-MM-dd"); // Establece el formato de fecha deseado
				gson = gsonBuilder.create();
			}
			return gson;
		}
	}

	/**
	 * Obtiene la contraseña de un usuario en formato JSON.
	 * 
	 * @param idUsuario El ID del usuario del cual se desea obtener la contraseña.
	 * @return La contraseña del usuario en formato JSON.
	 * @throws SQLException Si ocurre un error al interactuar con la base de datos.
	 */
	public String ObtenerContrasenaJson(int idUsuario) throws SQLException {
		String json = "";
		Gson gson = new Gson();
		json = gson.toJson(this.obtenerContrasena(idUsuario));
		return json;
	}

	/**
	 * Busca el permiso de un usuario en formato JSON.
	 * 
	 * @param idUsuario El ID del usuario del cual se desea buscar el permiso.
	 * @return El permiso del usuario en formato JSON.
	 * @throws SQLException Si ocurre un error al interactuar con la base de datos.
	 */
	public String BuscarPermisoJson(int idUsuario) throws SQLException {
		String json = "";
		Gson gson = new Gson();
		json = gson.toJson(this.buscarPermisoUsuario(idUsuario));
		return json;
	}

	/**
	 * Verifica si existe un usuario con el nombre especificado, excluyendo al
	 * usuario actual que se está editando.
	 *
	 * @param nombre          El nombre del usuario que se desea verificar.
	 * @param idUsuarioActual El ID del usuario actual que se está editando.
	 * @return true si existe un usuario con el nombre especificado, excluyendo al
	 *         usuario actual; false en caso contrario.
	 * @throws SQLException Si ocurre un error al acceder a la base de datos.
	 */
	public boolean existeUsuarioConNombre(String nombre, int idUsuarioActual) throws SQLException {

		String sql = "SELECT COUNT(*) FROM usuarios WHERE nombre = ? AND idUsuario != ?";
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, nombre);
			ps.setInt(2, idUsuarioActual);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					int count = rs.getInt(1);
					return count > 0;
				}
			}
		}
		return false;
	}
}
