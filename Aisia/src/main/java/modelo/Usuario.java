package modelo;

import java.sql.SQLException;
import java.util.List;

import dao.DaoUsuario;

/**
 * Clase que representa un usuario del sistema.
 * 
 * @author Maitane Ibañez Irazabal
 * @version 1.0
 */
public class Usuario {
	// ATRIBUTOS
	// --------------------------------------------------------------------------------------------
	/**
	 * Identificador único del usuario (clave primaria).
	 */
	private int idUsuario;

	/**
	 * Nombre del usuario.
	 */
	private String nombre;

	/**
	 * Dirección de correo electrónico del usuario.
	 */
	private String email;

	/**
	 * Hash de la contraseña del usuario.
	 */
	private String contrasena;

	/**
	 * Fecha de nacimiento del usuario (formato dd/mm/aaaa).
	 */
	private String fechaNacimiento;

	/**
	 * Intereses del usuario (por ejemplo, "Música", "Deporte", "Lectura").
	 */
	private String intereses;

	/**
	 * Control de permisos de los usuarios en la web.
	 */
	private int permiso;
	/**
	 * Rol del usuario en el sistema (USUARIO, MODERADOR, ADMINISTRADOR).
	 */
	private Rol rol;

	/**
	 * Enum que define los roles posibles para un usuario.
	 */
	public enum Rol {
		/**
		 * Rol de usuario básico del sistema.
		 */
		USUARIO,

		/**
		 * Rol de usuario con permisos moderadores.
		 */
		MODERADOR,

		/**
		 * Rol de usuario con permisos administrativos.
		 */
		ADMINISTRADOR
	}

	// CONSTRUCTORES
	// --------------------------------------------------------------------------------------------
	/**
	 * Constructor vacío para inicializar la clase.
	 */
	public Usuario() {
	}

	/**
	 * Constructor completo para inicializar la clase con todos sus atributos.
	 *
	 * @param idUsuario       Identificador único del usuario.
	 * @param nombre          Nombre del usuario.
	 * @param email           Dirección de correo electrónico del usuario.
	 * @param hashContrasena  Hash de la contraseña del usuario.
	 * @param fechaNacimiento Fecha de nacimiento del usuario (formato dd/mm/aaaa).
	 * @param intereses       Lista de intereses del usuario.
	 * @param rol             Rol del usuario en el sistema.
	 */
	public Usuario(int idUsuario, String nombre, String email, String hashContrasena, String fechaNacimiento,
			String intereses, int permiso, Rol rol) {
		this.idUsuario = idUsuario;
		this.nombre = nombre;
		this.email = email;
		this.contrasena = hashContrasena;
		this.fechaNacimiento = fechaNacimiento;
		this.intereses = intereses;
		this.permiso = permiso;
		this.rol = rol;
	}

	/**
	 * Constructor con idUsuario, nombre, email y hashContrasena.
	 *
	 * @param idUsuario      Identificador único del usuario.
	 * @param nombre         Nombre del usuario.
	 * @param email          Dirección de correo electrónico del usuario.
	 * @param hashContrasena Hash de la contraseña del usuario.
	 */
	public Usuario(String nombre, String email, String Contrasena) {
		this.nombre = nombre;
		this.email = email;
		this.contrasena = Contrasena;
	}

	/**
	 * Crea un nuevo objeto Usuario con el ID, nombre y correo electrónico
	 * especificados.
	 * 
	 * @param idUsuario El ID del usuario.
	 * @param nombre    El nombre del usuario.
	 * @param email     El correo electrónico del usuario.
	 */
	public Usuario(int idUsuario, String nombre, String email) {
		super();
		this.idUsuario = idUsuario;
		this.nombre = nombre;
		this.email = email;
	}

	/**
	 * Crea un nuevo objeto Usuario con el ID, nombre, correo electrónico y
	 * contraseña especificados.
	 * 
	 * @param idUsuario  El ID del usuario.
	 * @param nombre     El nombre del usuario.
	 * @param email      El correo electrónico del usuario.
	 * @param contrasena La contraseña del usuario.
	 */
	public Usuario(int idUsuario, String nombre, String email, String contrasena) {
		this.idUsuario = idUsuario;
		this.nombre = nombre;
		this.email = email;
		this.contrasena = contrasena;
	}

	// GETTERS Y SETTERS PARA TODOS LOS ATRIBUTOS
	// --------------------------------------------------------------------------------------------

	/**
	 * Obtiene el identificador único del usuario.
	 *
	 * @return Identificador único del usuario.
	 */
	public int getIdUsuario() {
		return idUsuario;
	}

	/**
	 * Establece el identificador único del usuario.
	 *
	 * @param idUsuario Nuevo identificador único del usuario.
	 */
	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	/**
	 * Obtiene el nombre del usuario.
	 *
	 * @return Nombre del usuario.
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Establece el nombre del usuario.
	 *
	 * @param nombre Nuevo nombre del usuario.
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Obtiene la dirección de correo electrónico del usuario.
	 *
	 * @return Dirección de correo electrónico del usuario.
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Establece la dirección de correo electrónico del usuario.
	 *
	 * @param email Nueva dirección de correo electrónico del usuario.
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Obtiene el hash de la contraseña del usuario.
	 *
	 * @return Hash de la contraseña del usuario.
	 */
	public String getcontrasena() {
		return contrasena;
	}

	/**
	 * Establece el hash de la contraseña del usuario.
	 *
	 * @param hashContrasena Nuevo hash de la contraseña del usuario.
	 */
	public void setcontrasena(String hashContrasena) {
		this.contrasena = hashContrasena;
	}

	/**
	 * Obtiene la fecha de nacimiento del usuario (formato dd/mm/aaaa).
	 *
	 * @return Fecha de nacimiento del usuario.
	 */
	public String getFechaNacimiento() {
		return fechaNacimiento;
	}

	/**
	 * Establece la fecha de nacimiento del usuario (formato dd/mm/aaaa).
	 *
	 * @param fechaNacimiento Nueva fecha de nacimiento del usuario.
	 */
	public void setFechaNacimiento(String fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	/**
	 * Obtiene la lista de intereses del usuario.
	 *
	 * @return Lista de intereses del usuario.
	 */
	public String getIntereses() {
		return intereses;
	}

	/**
	 * Establece la lista de intereses del usuario.
	 *
	 * @param intereses Nueva lista de intereses del usuario.
	 */
	public void setIntereses(String intereses) {
		this.intereses = intereses;
	}

	/**
	 * Obtiene el rol del usuario en el sistema.
	 *
	 * @return Rol del usuario en el sistema.
	 */
	public Rol getRol() {
		return rol;
	}

	/**
	 * Establece el rol del usuario en el sistema.
	 *
	 * @param rol Nuevo rol del usuario en el sistema.
	 */
	public void setRol(Rol rol) {
		this.rol = rol;
	}

	public int getPermiso() {
		return permiso;
	}

	public void setPermiso(int permiso) {
		this.permiso = permiso;
	}
	// MÉTODO TOSTRING
	// --------------------------------------------------------------------------------------------

	/**
	 * Representación textual completa de la clase Usuario.
	 *
	 * @return Representación textual del usuario.
	 */
	@Override
	public String toString() {
		return "Usuario [idUsuario=" + idUsuario + ", nombre=" + nombre + ", email=" + email + ", hashContrasena="
				+ contrasena + ", fechaNacimiento=" + fechaNacimiento + ", intereses=" + intereses + ", permiso="
				+ permiso + ", rol=" + rol + "]";
	}

	// MÉTODOS DE NEGOCIO
	// --------------------------------------------------------------------------------------------
	/**
	 * <strong>Registra un nuevo usuario</strong> en la base de datos.
	 *
	 * @param usuario Objeto Usuario con la información del nuevo usuario. No se
	 *                permiten valores nulos.
	 * 
	 * @throws Exception Si ocurre un error al registrar el usuario. Las posibles
	 *                   excepciones incluyen:
	 *                   <ul>
	 *                   <li>SQLException: Si ocurre un error al acceder a la base
	 *                   de datos.</li>
	 *                   <li>Exception: Si el objeto `usuario` es nulo o contiene
	 *                   valores nulos.</li>
	 * 
	 *                   </ul>
	 */
	public void registrarUsuario(Usuario usuario) throws SQLException, Exception {
		DaoUsuario.getInstance().registrarUsuario(usuario);
	}

	/**
	 * Valida el usuario y contraseña, y devuelve un objeto Usuario si la
	 * autenticación es correcta.
	 *
	 * @param email      Correo electrónico del usuario. No puede ser nulo ni vacío.
	 * @param contrasena Contraseña del usuario. No puede ser nula ni vacía.
	 * @return Objeto Usuario si la autenticación es correcta, null si no lo es.
	 * @throws Exception Si ocurre un error al iniciar sesión. Las posibles
	 *                   excepciones incluyen:
	 *                   <ul>
	 *                   <li>SQLException: Si ocurre un error al acceder a la base
	 *                   de datos.</li>
	 *                   <li>IllegalArgumentException: Si el email o la contraseña
	 *                   son nulos o vacíos.</li>
	 *                   <li>DataAccessException: Si ocurre un error inesperado al
	 *                   iniciar sesión.</li>
	 *                   </ul>
	 */
	public Usuario iniciarSesion(String email, String contrasena) throws Exception {
		return DaoUsuario.getInstance().iniciarSesion(email, contrasena);
	}

	/**
	 * Obtiene un usuario por su ID.
	 *
	 * @param idUsuario Identificador del usuario. Debe ser un valor positivo mayor
	 *                  que 0.
	 * @return Objeto Usuario con la información del usuario, o null si no se
	 *         encuentra.
	 * @throws Exception Si ocurre un error al obtener el usuario. Las posibles
	 *                   excepciones incluyen:
	 *                   <ul>
	 *                   <li>SQLException: Si ocurre un error al acceder a la base
	 *                   de datos.</li>
	 *                   <li>IllegalArgumentException: Si el idUsuario es menor o
	 *                   igual a 0.</li>
	 *                   <li>DataAccessException: Si ocurre un error inesperado al
	 *                   obtener el usuario.</li>
	 *                   </ul>
	 */
	public Usuario obtenerINFOUsuarioPorID(int idUsuario) throws Exception {
		return DaoUsuario.getInstance().obtenerINFOUsuarioPorID(idUsuario);
	}

	/**
	 * Actualiza la información de un usuario en la base de datos.
	 *
	 * @param usuario Objeto Usuario con la información actualizada del usuario. No
	 *                se permiten valores nulos.
	 * @throws Exception Si ocurre un error al editar el usuario. Las posibles
	 *                   excepciones incluyen:
	 *                   <ul>
	 *                   <li>SQLException: Si ocurre un error al acceder a la base
	 *                   de datos.</li>
	 *                   <li>IllegalArgumentException: Si el objeto `usuario` es
	 *                   nulo o contiene valores nulos.</li>
	 *                   <li>DataAccessException: Si ocurre un error inesperado al
	 *                   editar el usuario.</li>
	 *                   </ul>
	 */
	public void editarUsuario(Usuario usuario) throws Exception {
		DaoUsuario.getInstance().editarUsuario(usuario);
	}

	/**
	 * Elimina un usuario de la base de datos.
	 *
	 * @param idUsuario Identificador del usuario. Debe ser un valor positivo mayor
	 *                  que 0.
	 * @throws Exception Si ocurre un error al eliminar el usuario. Las posibles
	 *                   excepciones incluyen:
	 *                   <ul>
	 *                   <li>SQLException: Si ocurre un error al acceder a la base
	 *                   de datos.</li>
	 *                   <li>IllegalArgumentException: Si el idUsuario es menor o
	 *                   igual a 0.</li>
	 *                   <li>DataAccessException: Si ocurre un error inesperado al
	 *                   eliminar el usuario.</li>
	 *                   </ul>
	 */
	public void eliminarUsuario(int idUsuario) throws Exception {
		DaoUsuario.getInstance().eliminarUsuario(idUsuario);
	}

	/**
	 * Obtiene la lista de eventos a los que está asistiendo un usuario.
	 *
	 * @param idUsuario Identificador del usuario. Debe ser un valor positivo mayor
	 *                  que 0.
	 * @return Lista de objetos Evento con la información de los eventos. La lista
	 *         puede estar vacía si el usuario no asiste a ningún evento.
	 * @throws Exception Si ocurre un error al obtener los eventos. Las posibles
	 *                   excepciones incluyen:
	 *                   <ul>
	 *                   <li>SQLException: Si ocurre un error al acceder a la base
	 *                   de datos.</li>
	 *                   <li>IllegalArgumentException: Si el idUsuario es menor o
	 *                   igual a 0.</li>
	 *                   <li>DataAccessException: Si ocurre un error inesperado al
	 *                   obtener los eventos.</li>
	 *                   </ul>
	 */
	public List<Evento> obtenerEventos(int idUsuario) throws Exception {
		return DaoUsuario.getInstance().obtenerEventos(idUsuario);
	}

	/**
	 * Busca eventos por un criterio de búsqueda.
	 *
	 * @param criterio Criterio de búsqueda (nombre, fecha, etc.). No puede ser nulo
	 *                 ni vacío.
	 * @return Lista de objetos Evento con la información de los eventos
	 *         coincidentes. La lista puede estar vacía si no se encuentran eventos.
	 * @throws Exception Si ocurre un error al buscar los eventos. Las posibles
	 *                   excepciones incluyen:
	 *                   <ul>
	 *                   <li>SQLException: Si ocurre un error al acceder a la base
	 *                   de datos.</li>
	 *                   <li>IllegalArgumentException: Si el criterio de búsqueda es
	 *                   nulo o vacío.</li>
	 *                   <li>DataAccessException: Si ocurre un error inesperado al
	 *                   buscar los eventos.</li>
	 *                   </ul>
	 */
	public List<Evento> buscarEventos(String criterio) throws Exception {
		return DaoUsuario.getInstance().buscarEventos(criterio);
	}

	/**
	 * Obtiene la información de un evento por su ID.
	 *
	 * @param idEvento Identificador del evento. Debe ser un valor positivo mayor
	 *                 que 0.
	 * @return Objeto Evento con la información del evento, o null si no se
	 *         encuentra.
	 * @throws Exception Si ocurre un error al obtener el evento. Las posibles
	 *                   excepciones incluyen:
	 *                   <ul>
	 *                   <li>SQLException: Si ocurre un error al acceder a la base
	 *                   de datos.</li>
	 *                   <li>IllegalArgumentException: Si el idEvento es menor o
	 *                   igual a 0.</li>
	 *                   <li>DataAccessException: Si ocurre un error inesperado al
	 *                   obtener el evento.</li>
	 *                   </ul>
	 */
	public Evento verEvento(int idEvento) throws Exception {
		return DaoUsuario.getInstance().verEvento(idEvento);
	}

	/**
	 * Marca un evento como favorito para un usuario.
	 *
	 * @param idUsuario Identificador del usuario. Debe ser un valor positivo mayor
	 *                  que 0.
	 * @param idEvento  Identificador del evento. Debe ser un valor positivo mayor
	 *                  que 0.
	 * @throws Exception Si ocurre un error al marcar el evento como favorito. Las
	 *                   posibles excepciones incluyen:
	 *                   <ul>
	 *                   <li>SQLException: Si ocurre un error al acceder a la base
	 *                   de datos.</li>
	 *                   <li>IllegalArgumentException: Si el idUsuario o el idEvento
	 *                   son menores o iguales a 0.</li>
	 *                   <li>DataAccessException: Si ocurre un error inesperado al
	 *                   marcar el evento como favorito.</li>
	 *                   </ul>
	 */
	public void marcarEventoFavorito(int idUsuario, int idEvento) throws Exception {
		DaoUsuario.getInstance().marcarEventoFavorito(idUsuario, idEvento);
	}

	/**
	 * Elimina un evento de la lista de favoritos de un usuario.
	 *
	 * @param idUsuario Identificador del usuario. Debe ser un valor positivo mayor
	 *                  que 0.
	 * @param idEvento  Identificador del evento. Debe ser un valor positivo mayor
	 *                  que 0.
	 * @throws Exception Si ocurre un error al desmarcar el evento como favorito.
	 *                   Las posibles excepciones incluyen:
	 *                   <ul>
	 *                   <li>SQLException: Si ocurre un error al acceder a la base
	 *                   de datos.</li>
	 *                   <li>IllegalArgumentException: Si el idUsuario o el idEvento
	 *                   son menores o iguales a 0.</li>
	 *                   <li>DataAccessException: Si ocurre un error inesperado al
	 *                   desmarcar el evento como favorito.</li>
	 *                   </ul>
	 */
	public void desmarcarEventoFavorito(int idUsuario, int idEvento) throws Exception {
		DaoUsuario.getInstance().desmarcarEventoFavorito(idUsuario, idEvento);
	}

	/**
	 * Permite a un usuario cambiar su contraseña.
	 *
	 * @param idUsuario        Identificador del usuario. Debe ser un valor positivo
	 *                         mayor que 0.
	 * @param contrasenaActual Contraseña actual del usuario. No puede ser nula ni
	 *                         vacía.
	 * @param contrasenaNueva  Nueva contraseña del usuario. No puede ser nula ni
	 *                         vacía.
	 * @throws Exception Si ocurre un error al cambiar la contraseña. Las posibles
	 *                   excepciones incluyen:
	 *                   <ul>
	 *                   <li>SQLException: Si ocurre un error al acceder a la base
	 *                   de datos.</li>
	 *                   <li>IllegalArgumentException: Si el idUsuario es menor o
	 *                   igual a 0, o si la contrasenaActual o la contrasenaNueva
	 *                   son nulas o vacías.</li>
	 *                   <li>DataAccessException: Si ocurre un error inesperado al
	 *                   cambiar la contraseña.</li>
	 *                   </ul>
	 */
	public void cambiarContrasena(int idUsuario, String contrasenaActual, String contrasenaNueva) throws Exception {
		DaoUsuario.getInstance().cambiarContrasena(idUsuario, contrasenaActual, contrasenaNueva);
	}

	/**
	 * Obtiene una lista de todos los usuarios.
	 *
	 * @return La lista de usuarios o una lista vacía si no se encuentran usuarios.
	 * @throws Exception Si ocurre un error al consultar la base de datos. Las
	 *                   posibles excepciones incluyen:
	 *                   <ul>
	 *                   <li>SQLException: Si ocurre un error al acceder a la base
	 *                   de datos.</li>
	 *                   <li>DataAccessException: Si ocurre un error inesperado al
	 *                   obtener la lista de usuarios.</li>
	 *                   </ul>
	 */
	public List<Usuario> obtenerUsuarios() throws Exception {

		return DaoUsuario.getInstance().obtenerUsuarios();
	}

	/**
	 * Verifica si el usuario tiene permiso para realizar cierta acción.
	 *
	 * @param permisoNecesario El nivel de permiso necesario para realizar la
	 *                         acción.
	 * @return true si el usuario tiene permiso, false de lo contrario.
	 */
	public boolean tienePermiso(int permisoNecesario) {
		PermisoUsuario permisoUsuario = buscarPermisoUsuario(idUsuario);
		return permisoUsuario != null && permisoUsuario.getPermiso() >= permisoNecesario;
	}

	// OTROS MÉTODOS
	// --------------------------------------------------------------------------------------------
	private PermisoUsuario buscarPermisoUsuario(int idUsuario) {
		try {
			return DaoUsuario.getInstance().buscarPermisoUsuario(idUsuario);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
