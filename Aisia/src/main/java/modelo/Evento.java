package modelo;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import dao.DaoEvento;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Clase que representa un evento del sistema.
 *
 * @author Maitane Ibañez Irazabal
 * @version 1.0
 */
public class Evento {

	// ATRIBUTOS
	// --------------------------------------------------------------------------------------------

	/**
	 * Identificador único del evento (clave primaria).
	 */
	private int idEvento;

	/**
	 * Nombre del evento.
	 */
	private String nombre;

	/**
	 * Descripción detallada del evento.
	 */
	private String detalles;
	/**
	 * Identificador del usuario creador del evento
	 */
	private int idUsuarioCreador;
	/**
	 * Fecha de aprobacion de publicacion del evento.
	 */
	private Date fechaAprobacion;
	/**
	 * Fecha de ultima modificación realizada en el evento.
	 */
	private Date fechaUltimaModificacion;
	/**
	 * Identificador del usuario que aprueba publicacion del evento.
	 */
	private int idModeradorAprobacion;

	/**
	 * Fecha de publicación del evento.
	 */
	private Date fechaPublicacion;

	/**
	 * Identificador del usuario que publicó el evento.
	 */
	private int idModeradorPublicacion;

	/**
	 * Fecha de finalización del evento (si ha finalizado).
	 */
	private Date fechaFinalizacion;

	/**
	 * Identificador del usuario que finalizó el evento (si ha finalizado).
	 */
	private int idModeradorFinalizacion;

	/**
	 * Motivo de finalización del evento (si ha finalizado).
	 */
	private MotivoFinalizacion motivoFinalizacion;

	/**
	 * Ubicación del evento.
	 */
	private String ubicacion;
	/**
	 * Fecha del evento en sí.
	 */
	private Date fechaEvento;

	/**
	 * Enumeración que define los posibles motivos de finalización de un evento.
	 */

	public enum MotivoFinalizacion {
		/**
		 * El evento ha finalizado por falta de visibilidad.
		 */
		FinVisibilidad,

		/**
		 * El evento ha finalizado por un rechazo de publicación de algun moderador o
		 * administrador
		 */
		Rechazado,
		/**
		 * El evento ha finalizado por un reporte negativo.
		 */
		ReporteNegativo,

		/**
		 * El evento ha finalizado por otros motivos.
		 */
		Otros
	}

	// CONSTRUCTORES
	// --------------------------------------------------------------------------------------------

	/**
	 * Constructor vacío para inicializar la clase.
	 */
	public Evento() {

	}

	/**
	 * Constructor completo para inicializar la clase con todos sus atributos.
	 * 
	 * @param idEvento                Identificador único del evento.
	 * @param nombre                  Nombre del evento.
	 * @param detalles                Detalles o descripción del evento.
	 * @param idUsuarioCreador        Identificador del usuario que creó el evento.
	 * @param fechaAprobacion         Fecha de aprobación del evento (formato Date).
	 * @param idModeradorAprobacion   Identificador del moderador que aprobó el
	 *                                evento.
	 * @param fechaPublicacion        Fecha de publicación del evento (formato
	 *                                Date).
	 * @param idModeradorPublicacion  Identificador del moderador que publicó el
	 *                                evento.
	 * @param fechaFinalizacion       Fecha de finalización del evento (formato
	 *                                Date).
	 * @param idModeradorFinalizacion Identificador del moderador que finalizó el
	 *                                evento.
	 * @param motivoFinalizacion      Motivo por el que se finalizó el evento.
	 * @param ubicacion               Ubicación del evento.
	 * @param fechaEvento             Fecha del evento (formato Date)
	 */
	public Evento(int idEvento, String nombre, String detalles, int idUsuarioCreador, Date fechaAprobacion,
			int idModeradorAprobacion, Date fechaPublicacion, int idModeradorPublicacion, Date fechaFinalizacion,
			int idModeradorFinalizacion, MotivoFinalizacion motivoFinalizacion, String ubicacion, Date fechaEvento) {

		this.idEvento = idEvento;
		this.nombre = nombre;
		this.detalles = detalles;
		this.idUsuarioCreador = idUsuarioCreador;
		this.fechaAprobacion = fechaAprobacion;
		this.idModeradorAprobacion = idModeradorAprobacion;
		this.fechaPublicacion = fechaPublicacion;
		this.idModeradorPublicacion = idModeradorPublicacion;
		this.fechaFinalizacion = fechaFinalizacion;
		this.idModeradorFinalizacion = idModeradorFinalizacion;
		this.motivoFinalizacion = motivoFinalizacion;
		this.ubicacion = ubicacion;
		this.fechaEvento = fechaEvento;
	}

	/**
	 * Constructor con algunos atributos.
	 * 
	 * @param idEvento Identificador único del evento.
	 * @param nombre   Nombre del evento.
	 * @param detalles Detalles o descripción del evento.
	 */
	public Evento(int idEvento, String nombre, String detalles, Date fechaEvento) {

		this.idEvento = idEvento;
		this.nombre = nombre;
		this.detalles = detalles;
		this.fechaEvento = fechaEvento;
	}

	/**
	 * Constructor con algunos atributos.
	 * 
	 * @param idEvento Identificador único del evento.
	 */
	public Evento(int idEvento) {

		this.idEvento = idEvento;
	}

	/**
	 * Constructor con algunos atributos.
	 * 
	 * @param idEvento         Identificador único del evento.
	 * @param nombre           Nombre del evento.
	 * @param detalles         Detalles o descripción del evento.
	 * @param idUsuarioCreador Id del usuario creador del evento
	 */
	public Evento(String nombre, String detalles, Date fechaEvento, int idUsuarioCreador, String ubicacion) {
		super();
		this.nombre = nombre;
		this.detalles = detalles;
		this.fechaEvento = fechaEvento;
		this.idUsuarioCreador = idUsuarioCreador;
		this.ubicacion = ubicacion;
	}

	/**
	 * Constructor con algunos atributos.
	 * 
	 * @param idEvento  Identificador único del evento.
	 * @param nombre    Nombre del evento.
	 * @param detalles  Detalles o descripción del evento.
	 * @param ubicacion Detalles de la ubicación del evento.
	 */
	public Evento(int idEvento, String nombre, String detalles, Date fechaEvento, String ubicacion) {
		super();
		this.idEvento = idEvento;
		this.nombre = nombre;
		this.detalles = detalles;
		this.fechaEvento = fechaEvento;
		this.ubicacion = ubicacion;
	}

	// GETTERS Y SETTERS
	// --------------------------------------------------------------------------------------------
	/**
	 * Obtiene el identificador único del evento.
	 *
	 * @return Identificador único del evento.
	 */
	public int getIdEvento() {
		return idEvento;
	}

	/**
	 * Establece el identificador único del evento.
	 *
	 * @param idEvento Nuevo identificador único del evento.
	 */
	public void setIdEvento(int idEvento) {
		this.idEvento = idEvento;
	}

	/**
	 * Obtiene el nombre del evento.
	 *
	 * @return Nombre del evento.
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Establece el nombre del evento.
	 *
	 * @param nombre Nuevo nombre del evento.
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Obtiene la descripción detallada del evento.
	 *
	 * @return Descripción detallada del evento.
	 */
	public String getDetalles() {
		return detalles;
	}

	/**
	 * Establece la descripción detallada del evento.
	 *
	 * @param detalles Nueva descripción detallada del evento.
	 */
	public void setDetalles(String detalles) {
		this.detalles = detalles;
	}

	/**
	 * Obtiene el ID del usuario creador del evento.
	 * 
	 * @return El ID del usuario creador del evento.
	 */
	public int getIdUsuarioCreador() {
		return idUsuarioCreador;
	}

	/**
	 * Establece el ID del usuario creador del evento.
	 * 
	 * @param idUsuarioCreador El ID del usuario creador del evento.
	 */
	public void setIdUsuarioCreador(int idUsuarioCreador) {
		this.idUsuarioCreador = idUsuarioCreador;
	}

	/**
	 * Obtiene la fecha de aprobación del evento.
	 * 
	 * @return La fecha de aprobación del evento.
	 */
	public Date getFechaAprobacion() {
		return fechaAprobacion;
	}

	/**
	 * Establece la fecha de aprobación del evento.
	 * 
	 * @param fechaAprobacion La fecha de aprobación del evento.
	 */
	public void setFechaAprobacion(Date fechaAprobacion) {
		this.fechaAprobacion = fechaAprobacion;
	}

	/**
	 * Obtiene el ID del moderador que aprobó el evento.
	 * 
	 * @return El ID del moderador que aprobó el evento.
	 */
	public int getIdModeradorAprobacion() {
		return idModeradorAprobacion;
	}

	/**
	 * Obtiene la fecha de ultima mofificación del evento.
	 * 
	 * @return la fecha de ultima modificación realizada en el evento.
	 */
	public Date getFechaUltimaModificacion() {
		return fechaUltimaModificacion;
	}

	/**
	 * Establece la fecha de la ultima modificación del evento
	 * 
	 * @param fechaUltimaModificacion
	 */
	public void setFechaUltimaModificacion(Date fechaUltimaModificacion) {
		this.fechaUltimaModificacion = fechaUltimaModificacion;
	}

	/**
	 * Establece el ID del moderador que aprobó el evento.
	 * 
	 * @param idModeradorAprobacion El ID del moderador que aprobó el evento.
	 */
	public void setIdModeradorAprobacion(int idModeradorAprobacion) {
		this.idModeradorAprobacion = idModeradorAprobacion;
	}

	/**
	 * Obtiene la fecha de publicación del evento.
	 *
	 * @return Fecha de publicación del evento.
	 */
	public Date getFechaPublicacion() {
		return fechaPublicacion;
	}

	/**
	 * Establece la fecha de publicación del evento.
	 *
	 * @param date Nueva fecha de publicación del evento.
	 */
	public void setFechaPublicacion(Date date) {
		this.fechaPublicacion = date;
	}

	/**
	 * Obtiene el identificador del usuario que publicó el evento.
	 *
	 * @return Identificador del usuario que publicó el evento.
	 */
	public int getIdModeradorPublicacion() {
		return idModeradorPublicacion;
	}

	/**
	 * Establece el identificador del usuario que publicó el evento.
	 *
	 * @param idModeradorPublicacion Nuevo identificador del usuario que publicó el
	 *                               evento.
	 */
	public void setIdModeradorPublicacion(int idModeradorPublicacion) {
		this.idModeradorPublicacion = idModeradorPublicacion;
	}

	/**
	 * Obtiene la fecha de finalización del evento (si ha finalizado).
	 *
	 * @return Fecha de finalización del evento (si ha finalizado). Si el evento no
	 *         ha finalizado, se devuelve `null`.
	 */
	public Date getFechaFinalizacion() {
		return fechaFinalizacion;
	}

	/**
	 * Establece la fecha de finalización del evento (si ha finalizado).
	 *
	 * @param fechaFinalizacion Nueva fecha de finalización del evento (si ha
	 *                          finalizado). Si el evento no ha finalizado, se debe
	 *                          pasar `null`.
	 */
	public void setFechaFinalizacion(Date fechaFinalizacion) {
		this.fechaFinalizacion = fechaFinalizacion;
	}

	/**
	 * Obtiene el identificador del usuario que finalizó el evento (si ha
	 * finalizado).
	 *
	 * @return Identificador del usuario que finalizó el evento (si ha finalizado).
	 *         Si el evento no ha finalizado, se devuelve 0.
	 */
	public int getIdModeradorFinalizacion() {
		return idModeradorFinalizacion;
	}

	/**
	 * Establece el identificador del usuario que finalizó el evento (si ha
	 * finalizado).
	 *
	 * @param idModeradorFinalizacion Nuevo identificador del usuario que finalizó
	 *                                el evento (si ha finalizado). Si el evento no
	 *                                ha finalizado, se debe pasar 0.
	 */
	public void setIdModeradorFinalizacion(int idModeradorFinalizacion) {
		this.idModeradorFinalizacion = idModeradorFinalizacion;
	}

	/**
	 * Obtiene el motivo de finalización del evento (si ha finalizado).
	 *
	 * @return Motivo de finalización del evento (si ha finalizado). Si el evento no
	 *         ha finalizado, se devuelve `null`.
	 */
	public MotivoFinalizacion getMotivoFinalizacion() {
		return motivoFinalizacion;
	}

	/**
	 * Establece el motivo de finalización del evento (si ha finalizado).
	 *
	 * @param motivoFinalizacion Nuevo motivo de finalización del evento (si ha
	 *                           finalizado). Si el evento no ha finalizado, se debe
	 *                           pasar `null`.
	 */
	public void setMotivoFinalizacion(MotivoFinalizacion motivoFinalizacion) {
		this.motivoFinalizacion = motivoFinalizacion;
	}

	/**
	 * Obtiene la ubicación del evento.
	 *
	 * @return Ubicación del evento.
	 */
	public String getUbicacion() {
		return ubicacion;
	}

	/**
	 * Establece la ubicación del evento.
	 *
	 * @param ubicacion Nueva ubicación del evento.
	 */
	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}

	/**
	 * Obtiene la fecha del evento.
	 *
	 * @return La fecha del evento.
	 */
	public Date getFechaEvento() {
		return fechaEvento;
	}

	/**
	 * Establece la fecha del evento.
	 *
	 * @param fechaEvento La nueva fecha del evento.
	 */
	public void setFechaEvento(Date fechaEvento) {
		this.fechaEvento = fechaEvento;
	}

	// TOSTRING
	// --------------------------------------------------------------------------------------------
	/**
	 * Representación textual de TODOS los atributos del evento.
	 *
	 * @return String con la información del evento.
	 */
	@Override
	public String toString() {
		return "Evento [idEvento=" + idEvento + ", nombre=" + nombre + ", detalles=" + detalles + ", idUsuarioCreador="
				+ idUsuarioCreador + ", fechaAprobacion=" + fechaAprobacion + ", fechaUltimaModificacion="
				+ fechaUltimaModificacion + ", idModeradorAprobacion=" + idModeradorAprobacion + ", fechaPublicacion="
				+ fechaPublicacion + ", idModeradorPublicacion=" + idModeradorPublicacion + ", fechaFinalizacion="
				+ fechaFinalizacion + ", idModeradorFinalizacion=" + idModeradorFinalizacion + ", motivoFinalizacion="
				+ motivoFinalizacion + ", ubicacion=" + ubicacion + ", fechaEvento=" + fechaEvento + "]";
	}

	// MÉTODOS DE NEGOCIO
	// --------------------------------------------------------------------------------------------
	/**
	 * Crea un nuevo evento en la base de datos.
	 * 
	 * @param evento El evento a crear.
	 * @throws SQLException Si ocurre un error al crear el evento.
	 */
	public void crearEvento(Evento evento, List<Actividad> actividades, Timestamp timestamp) throws SQLException {
		DaoEvento.getInstance().crearEvento(evento, actividades, timestamp);
	}

	/**
	 * Edita un evento existente en la base de datos.
	 * 
	 * @param evento El evento a editar.
	 * @throws SQLException Si ocurre un error al editar el evento.
	 */
	public void editarEvento(Evento evento, int idUsuario) throws SQLException {
		DaoEvento.getInstance().editarEvento(this);
	}

	/**
	 * Elimina un evento de la base de datos.
	 * 
	 * @param idEvento El identificador del evento a eliminar.
	 * @throws SQLException Si ocurre un error al eliminar el evento.
	 */
	public void eliminarEvento(Evento evento) throws SQLException {
		DaoEvento.getInstance().eliminarEvento(this);
	}

	/**
	 * Obtiene la lista de eventos que están pendientes de aprobación.
	 * 
	 * @return Lista de eventos pendientes de aprobación.
	 * @throws SQLException Si ocurre un error al obtener los eventos.
	 */

	public List<Evento> obtenerEventosPendientesAprobacion() throws SQLException {
		return DaoEvento.getInstance().obtenerEventosPendientesAprobacion();
	}

	/**
	 * Obtiene una lista de eventos pendientes de publicación.
	 *
	 * @return Una lista de eventos pendientes de publicación.
	 * @throws SQLException Si ocurre un error al acceder a la base de datos.
	 */
	public List<Evento> obtenerEventosPendientesPublicacion() throws SQLException {
		return DaoEvento.getInstance().obtenerEventosPendientesPublicacion();
	}

	/**
	 * Obtiene todos los eventos publicados activos de la base de datos (sin
	 * finalizar) que coinciden con los filtros especificados.
	 *
	 * @param actividad   Filtro por actividad (opcional).
	 * @param descripcion Filtro por descripción (opcional).
	 * @param ubicacion   Filtro por ubicación (opcional).
	 * @param fecha       Filtro por fecha (opcional).
	 * @return Una lista de objetos Evento que representan todos los eventos en la
	 *         base de datos que coinciden con los filtros especificados.
	 * @throws SQLException Si ocurre un error al acceder a la base de datos.
	 */
	public List<Evento> obtenerTodosLosEventosActivos(String actividad, String descripcion, String ubicacion,
			Date fecha) throws SQLException {
		return DaoEvento.getInstance().obtenerTodosLosEventosActivos(actividad, descripcion, ubicacion, fecha);
	}

	/**
	 * Aprueba un evento pendiente de aprobación.
	 * 
	 * @param idEvento        ID del evento a aprobar.
	 * @param idUsuarioActual ID del usuario actual que aprueba el evento.
	 * @param request         Objeto HttpServletRequest para obtener información
	 *                        adicional (opcional).
	 * @throws Exception Si ocurre un error al aprobar el evento o el usuario no
	 *                   tiene el rol adecuado.
	 */
	public void aprobarPublicacionEvento(int idEvento, HttpServletRequest request) throws SQLException {
		DaoEvento.getInstance().aprobarPublicacionEvento(idEvento, request);
	}

	/**
	 * Busca eventos en la base de datos que coincidan con el criterio especificado.
	 *
	 * @param criterio El criterio de búsqueda.
	 * @return Una lista de eventos que coinciden con el criterio de búsqueda.
	 * @throws Exception Si ocurre un error al buscar eventos.
	 */
	public List<Evento> buscarEventos(String criterio) throws Exception {
		return DaoEvento.getInstance().buscarEventos(criterio);
	}

	/**
	 * Finaliza la publicación de un evento en la base de datos.
	 *
	 * @param idEvento    El ID del evento que se va a finalizar.
	 * @param idModerador El ID del moderador que realiza la acción.
	 * @throws SQLException Si ocurre un error al finalizar la publicación del
	 *                      evento.
	 */
	public void finalizarPublicacionEvento(int idEvento, int idModerador) throws SQLException {
		DaoEvento.getInstance().finalizarPublicacionEvento(idEvento, idModerador);
	}

	/**
	 * Rechaza un evento pendiente de aprobación.
	 * 
	 * @param idEvento ID del evento a rechazar
	 * @param request  Objeto HttpServletRequest para obtener el ID del usuario
	 *                 actual.
	 * @throws Exception Si ocurre un error al rechazar el evento o el usuario no
	 *                   tiene el rol adecuado.
	 */
	public void rechazarEvento(int idEvento, HttpServletRequest request) throws SQLException {
		DaoEvento.getInstance().rechazarEvento(idEvento, request);
	}

	/**
	 * Publica un evento aprobado de publicacion
	 * 
	 * @param idEvento        El identificador del evento a publicar.
	 * @param idUsuarioActual El identificador del usuario que publica o planifica
	 *                        la publicacion el evento.
	 * @throws SQLException Si ocurre un error al publicar el evento o el usuario no
	 *                      tiene el rol adecuado.
	 */
	public void publicarEvento(int idEvento, HttpServletRequest request) throws SQLException {
		DaoEvento.getInstance().publicarEvento(idEvento, request);
	}

	/**
	 * Genera un objeto JSON que representa todos los eventos activos de la base de
	 * datos que coinciden con los filtros especificados.
	 *
	 * @param actividad   Filtro por actividad (opcional).
	 * @param descripcion Filtro por descripción (opcional).
	 * @param ubicacion   Filtro por ubicación (opcional).
	 * @param fecha       Filtro por fecha (opcional).
	 * @return Una cadena JSON que contiene la información de los eventos activos
	 *         que coinciden con los filtros especificados.
	 * @throws SQLException Si ocurre un error al acceder a la base de datos.
	 */
	public String listarJsonObtenerTodosLosEventosActivos(String actividad, String descripcion, String ubicacion,
			Date fecha) throws SQLException {
		return DaoEvento.getInstance().listarJsonObtenerTodosLosEventosActivos(actividad, descripcion, ubicacion,
				fecha);
	}

	/**
	 * Genera un objeto JSON que representa los eventos que coinciden con un
	 * criterio de búsqueda.
	 *
	 * @param criterio Criterio de búsqueda (nombre, fecha, etc.).
	 * @return Una cadena JSON que contiene la información de los eventos
	 *         coincidentes con el criterio de búsqueda.
	 * @throws Exception Si ocurre un error al buscar los eventos.
	 */
	public String listarJsonBuscarEventos(String criterio) throws Exception {
		return DaoEvento.getInstance().listarJsonBuscarEventos(criterio);
	}

	/**
	 * Genera un objeto JSON que representa los eventos pendientes de aprobación.
	 *
	 * @return Una cadena JSON que contiene la información de los eventos pendientes
	 *         de aprobación.
	 * @throws SQLException Si ocurre un error al acceder a la base de datos.
	 */
	public String listarJsonPendientesAprobacion() throws SQLException {
		return DaoEvento.getInstance().listarJsonPendientesAprobacion();
	}

	/**
	 * Genera un objeto JSON que representa los eventos pendientes de publicación.
	 *
	 * @return Una cadena JSON que contiene la información de los eventos pendientes
	 *         de publicación.
	 * @throws SQLException Si ocurre un error al acceder a la base de datos.
	 */
	public String listarJsonPendientesPublicacion() throws SQLException {
		return DaoEvento.getInstance().listarJsonPendientesPublicacion();
	}

}