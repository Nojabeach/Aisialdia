package modelo;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.DaoUsuario;
import jakarta.servlet.http.HttpServletRequest;

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
	private Date fechaNacimiento;

	/**
	 * Indica si el usuario recibe notificaciones.
	 */
	private boolean recibeNotificaciones;

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
	private Rol roles;

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

	/**
	 * Fecha en la que el usuario dio su consentimiento para el uso de sus datos.
	 */
	private Date consentimiento_datos;
	/**
	 * Fecha en la que el usuario acepto terminos y condiciones tras su lectura en
	 * la web
	 */
	private Date aceptacionTerminosWeb;

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
	 * @param idUsuario             Identificador único del usuario.
	 * @param nombre                Nombre del usuario.
	 * @param email                 Dirección de correo electrónico del usuario.
	 * @param contrasena            Hash de la contraseña del usuario.
	 * @param fechaNacimiento       Fecha de nacimiento del usuario (formato
	 *                              dd/mm/aaaa).
	 * @param recibeNotificaciones  Indica si el usuario recibe notificaciones.
	 * @param intereses             Intereses del usuario (por ejemplo, "Música",
	 *                              "Deporte", "Lectura").
	 * @param permiso               Control de permisos de los usuarios en la web.
	 * @param roles                 Rol del usuario en el sistema (USUARIO,
	 *                              MODERADOR, ADMINISTRADOR).
	 * @param consentimiento_datos  Fecha en la que el usuario dio su consentimiento
	 *                              para el uso de sus datos.
	 * @param aceptacionTerminosWeb Fecha en la que el usuario acepto terminos y
	 *                              condiciones tras su lectura en la web
	 */

	public Usuario(int idUsuario, String nombre, String email, String contrasena, Date fechaNacimiento,
			boolean recibeNotificaciones, String intereses, int permiso, Rol roles, Date consentimiento_datos,
			Date aceptacionTerminosWeb) {

		this.idUsuario = idUsuario;
		this.nombre = nombre;
		this.email = email;
		this.contrasena = contrasena;
		this.fechaNacimiento = fechaNacimiento;
		this.recibeNotificaciones = recibeNotificaciones;
		this.intereses = intereses;
		this.permiso = permiso;
		this.roles = roles;
		this.consentimiento_datos = consentimiento_datos;
		this.aceptacionTerminosWeb = aceptacionTerminosWeb;
	}

	/**
	 * Constructor con idUsuario, nombre, email y hashContrasena.
	 *
	 * @param idUsuario  Identificador único del usuario.
	 * @param nombre     Nombre del usuario.
	 * @param email      Dirección de correo electrónico del usuario.
	 * @param contrasena Contraseña del usuario.
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
	 * Constructor para la clase Usuario.
	 *
	 * @param idUsuario     El identificador único del usuario.
	 * @param nombre        El nombre del usuario.
	 * @param email         La dirección de correo electrónico del usuario.
	 * @param fechaNacimiento La fecha de nacimiento del usuario.
	 * @param recibeNotificaciones Una bandera que indica si el usuario recibe notificaciones.
	 * @param intereses     Los intereses del usuario.
	 */
	public Usuario(int idUsuario, String nombre, String email,  
			boolean recibeNotificaciones, String intereses,Date fechaNacimiento) {
		super();
		this.idUsuario = idUsuario;
		this.nombre = nombre;
		this.email = email;
		
		this.recibeNotificaciones = recibeNotificaciones;
		this.intereses = intereses;
		this.fechaNacimiento = fechaNacimiento;
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
	public Usuario(int idUsuario, String nombre, String email, int permiso) {
		this.idUsuario = idUsuario;
		this.nombre = nombre;
		this.email = email;
		this.permiso = permiso;
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
	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	/**
	 * Establece la fecha de nacimiento del usuario (formato dd/mm/aaaa).
	 *
	 * @param fechaNacimiento Nueva fecha de nacimiento del usuario.
	 */
	public void setFechaNacimiento(Date fechaNacimiento) {
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
	 * Obtiene la contraseña del usuario.
	 *
	 * @return La contraseña del usuario.
	 */
	public String getContrasena() {
		return contrasena;
	}

	/**
	 * Establece la contraseña del usuario.
	 *
	 * @param contrasena La nueva contraseña del usuario.
	 */
	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	/**
	 * Verifica si el usuario recibe notificaciones.
	 *
	 * @return true si el usuario recibe notificaciones, false de lo contrario.
	 */
	public boolean isRecibeNotificaciones() {
		return recibeNotificaciones;
	}

	/**
	 * Establece si el usuario recibe notificaciones.
	 *
	 * @param recibeNotificaciones true si el usuario recibe notificaciones, false
	 *                             de lo contrario.
	 */
	public void setRecibeNotificaciones(boolean recibeNotificaciones) {
		this.recibeNotificaciones = recibeNotificaciones;
	}

	/**
	 * Obtiene el rol del usuario.
	 *
	 * @return El rol del usuario.
	 */
	public Rol getRoles() {
		return roles;
	}

	/**
	 * Establece el rol del usuario.
	 *
	 * @param roles El nuevo rol del usuario.
	 */
	public void setRoles(Rol roles) {
		this.roles = roles;
	}

	/**
	 * Obtiene la fecha de consentimiento de datos del usuario.
	 *
	 * @return La fecha de consentimiento de datos del usuario.
	 */
	public Date getConsentimiento_datos() {
		return consentimiento_datos;
	}

	/**
	 * Establece la fecha de consentimiento de datos del usuario.
	 *
	 * @param consentimiento_datos La nueva fecha de consentimiento de datos del
	 *                             usuario.
	 */
	public void setConsentimiento_datos(Date consentimiento_datos) {
		this.consentimiento_datos = consentimiento_datos;
	}

	/**
	 * Obtiene el permiso del usuario en la web.
	 *
	 * @return El permiso del usuario en la web.
	 */
	public int getPermiso() {
		return permiso;
	}

	/**
	 * Establece el permiso del usuario en la web.
	 *
	 * @param permiso El nuevo permiso del usuario en la web.
	 */
	public void setPermiso(int permiso) {
		this.permiso = permiso;
	}

	/**
	 * Obtiene la fecha en la que el usuario aceptó los términos en la web.
	 *
	 * @return La fecha de aceptación de los términos en la web.
	 */
	public Date getAceptacionTerminosWeb() {
		return aceptacionTerminosWeb;
	}

	/**
	 * Establece la fecha en la que el usuario aceptó los términos en la web.
	 *
	 * @param aceptacionTerminosWeb La nueva fecha de aceptación de los términos en
	 *                              la web.
	 */
	public void setAceptacionTerminosWeb(Date aceptacionTerminosWeb) {
		this.aceptacionTerminosWeb = aceptacionTerminosWeb;
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
		return "Usuario [idUsuario=" + idUsuario + ", nombre=" + nombre + ", email=" + email + ", contrasena="
				+ contrasena + ", fechaNacimiento=" + fechaNacimiento + ", recibeNotificaciones=" + recibeNotificaciones
				+ ", intereses=" + intereses + ", permiso=" + permiso + ", roles=" + roles + ", consentimiento_datos="
				+ consentimiento_datos + "]";
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
	public void cambiarContrasena(int idUsuario,  String contrasenaNueva) throws Exception {
		DaoUsuario.getInstance().cambiarContrasena(idUsuario,  contrasenaNueva);
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
	 * Obtiene una lista de usuarios filtrada por tipo de permiso.
	 *
	 * @param PERMISO El tipo de permiso por el que se desea filtrar los usuarios.
	 * @return Una lista de usuarios que tienen el permiso especificado.
	 * @throws SQLException Si ocurre algún error de SQL al intentar obtener los
	 *                      usuarios.
	 */
	public ArrayList<Usuario> obtenerUsuarios(int PERMISO) throws SQLException {
		return DaoUsuario.getInstance().obtenerUsuarios(PERMISO);
	}

	/**
	 * Obtiene el ID del usuario actual de la sesión HTTP.
	 *
	 * @param request Objeto HttpServletRequest para obtener la información de la
	 *                sesión.
	 * @return El ID del usuario actual, o -1 si no se encuentra el usuario en la
	 *         sesión.
	 * @throws SQLException Si ocurre un error al acceder a la base de datos.
	 */
	public int obtenerIdUsuarioActual(HttpServletRequest request) throws SQLException {
		DaoUsuario.getInstance();
		return DaoUsuario.obtenerIdUsuarioActual(request);
	}

	/**
	 * Obtiene la contraseña de un usuario por su ID.
	 *
	 * @param idUsuario El ID del usuario.
	 * @return La contraseña del usuario, o null si no se encuentra.
	 * @throws SQLException Si ocurre un error al obtener la contraseña del usuario.
	 */
	public String obtenerContrasena(int idUsuario) throws SQLException {
		return DaoUsuario.getInstance().obtenerContrasena(idUsuario);
	}

	/**
	 * Genera una representación JSON de la lista de usuarios.
	 * 
	 * @return Una cadena JSON que representa la lista de usuarios.
	 * @throws SQLException Si ocurre un error al acceder a la base de datos.
	 */
	public String listarUsuariosJson() throws SQLException {
		return DaoUsuario.getInstance().listarUsuariosJson();
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
		return DaoUsuario.getInstance().listarUsuariosJson(tipo);
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
		return DaoUsuario.getInstance().buscarPermisoUsuario(idUsuario);
	}
}