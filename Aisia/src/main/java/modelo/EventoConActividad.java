package modelo;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import dao.DaoEvento;
import dao.DaoEventoConActividad;

/**
 * Clase que representa un evento con información adicional de su actividad
 * relacionada.
 * 
 * @author Maitane Ibañez Irazabal
 * @version 1.0
 */
public class EventoConActividad {
	// ATRIBUTOS
	// --------------------------------------------------------------------------------------------
	/**
	 * Identificador del evento.
	 */
	private int idEvento;

	/**
	 * Nombre del evento.
	 */
	private String nombre;

	/**
	 * Detalles del evento.
	 */
	private String detalles;

	/**
	 * Ubicación del evento.
	 */
	private String ubicacion;

	/**
	 * Tipo de actividad asociada al evento.
	 */
	private String tipoActividad;

	/**
	 * Ruta de la imagen que representa la actividad asociada.
	 */
	private String fotoActividad;
	/**
	 * Fecha en la que tiene lugar el evento.
	 */
	private Date fechaEvento;

	// CONSTRUCTORES
	// --------------------------------------------------------------------------------------------
	/**
	 * Constructor vacío.
	 */
	public EventoConActividad() {

	}

	/**
	 * Constructor con todos los parámetros.
	 * 
	 * @param idEvento      Identificador del evento.
	 * @param nombre        Nombre del evento.
	 * @param detalles      Detalles del evento.
	 * @param ubicacion     Ubicación del evento.
	 * @param tipoActividad Tipo de actividad asociada.
	 * @param fotoActividad Ruta de la imagen de la actividad.
	 * @param fechaEvento   Fecha en la que tiene lugar el evento.
	 */
	public EventoConActividad(int idEvento, String nombre, String detalles, String ubicacion, String tipoActividad,
			String fotoActividad, Date fechaEvento) {
		super();
		this.idEvento = idEvento;
		this.nombre = nombre;
		this.detalles = detalles;
		this.ubicacion = ubicacion;
		this.tipoActividad = tipoActividad;
		this.fotoActividad = fotoActividad;
		this.fechaEvento = fechaEvento;
	}

	/**
	 * Constructor con algunos parametros
	 * 
	 * @param idEvento      Identificador del evento.
	 * @param tipoActividad Tipo de actividad asociada.
	 * @param fotoActividad Ruta de la imagen de la actividad.
	 */

	public EventoConActividad(int idEvento, String tipoActividad, String fotoActividad) {

		this.idEvento = idEvento;
		this.tipoActividad = tipoActividad;
		this.fotoActividad = fotoActividad;
	}

	// GETTERS Y SETTERS PARA TODOS LOS ATRIBUTOS
	// --------------------------------------------------------------------------------------------

	/**
	 * Obtiene el identificador del evento.
	 * 
	 * @return El identificador del evento.
	 */
	public int getIdEvento() {
		return idEvento;
	}

	/**
	 * Establece el identificador del evento.
	 * 
	 * @param idEvento El nuevo identificador del evento.
	 */
	public void setIdEvento(int idEvento) {
		this.idEvento = idEvento;
	}

	/**
	 * Obtiene el nombre del evento.
	 * 
	 * @return El nombre del evento.
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Establece el nombre del evento.
	 * 
	 * @param nombre El nuevo nombre del evento.
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Obtiene los detalles del evento.
	 * 
	 * @return Los detalles del evento.
	 */
	public String getDetalles() {
		return detalles;
	}

	/**
	 * Establece los detalles del evento.
	 * 
	 * @param detalles Los nuevos detalles del evento.
	 */
	public void setDetalles(String detalles) {
		this.detalles = detalles;
	}

	/**
	 * Obtiene la ubicación del evento.
	 * 
	 * @return La ubicación del evento.
	 */
	public String getUbicacion() {
		return ubicacion;
	}

	/**
	 * Establece la ubicación del evento.
	 * 
	 * @param ubicacion La nueva ubicación del evento.
	 */
	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}

	/**
	 * Obtiene el tipo de actividad asociada al evento.
	 * 
	 * @return El tipo de actividad asociada.
	 */
	public String getTipoActividad() {
		return tipoActividad;
	}

	/**
	 * Establece el tipo de actividad asociada al evento.
	 * 
	 * @param tipoActividad El nuevo tipo de actividad asociada.
	 */

	public void setTipoActividad(String tipoActividad) {
		this.tipoActividad = tipoActividad;
	}

	/**
	 * Obtiene la ruta de la imagen que representa la actividad asociada.
	 * 
	 * @return La ruta de la imagen de la actividad.
	 */
	public String getFotoActividad() {
		return fotoActividad;
	}

	/**
	 * Establece la ruta de la imagen que representa la actividad asociada.
	 * 
	 * @param fotoActividad La nueva ruta de la imagen de la actividad.
	 */
	public void setFotoActividad(String fotoActividad) {
		this.fotoActividad = fotoActividad;
	}

	/**
	 * Obtiene la fecha del evento.
	 * 
	 * @return la fecha del evento.
	 * 
	 */
	public Date getFechaEvento() {
		return fechaEvento;
	}

	/**
	 * Establece la fecha del evento.
	 * 
	 * @param fechaEvento la fecha del evento.
	 * 
	 */
	public void setFechaEvento(Date fechaEvento) {
		this.fechaEvento = fechaEvento;
	}

// TOSTRING
// --------------------------------------------------------------------------------------------

	/**
	 * Representación textual del evento con la información de la actividad
	 * relacionada.
	 * 
	 * @return Una cadena que contiene los detalles del evento y su actividad.
	 */

	@Override
	public String toString() {
		return "EventoConActividad [idEvento=" + idEvento + ", nombre=" + nombre + ", detalles=" + detalles
				+ ", ubicacion=" + ubicacion + ", tipoActividad=" + tipoActividad + ", fotoActividad=" + fotoActividad
				+ ", fechaEvento=" + fechaEvento + "]";
	}

	// MÉTODOS DE NEGOCIO
	// --------------------------------------------------------------------------------------------
	/**
	 * Obtiene los últimos eventos con actividad asociada de la base de datos.
	 *
	 * @param numEventos El número de eventos a obtener.
	 * @return Lista de objetos EventoConActividad que representan los últimos
	 *         eventos con actividad asociada.
	 * @throws SQLException Si ocurre un error al obtener los eventos.
	 */
	public List<EventoConActividad> obtenerUltimosEventos(int numEventos) throws SQLException {
		return DaoEventoConActividad.getInstance().obtenerUltimosEventos(numEventos);
	}

	/**
	 * Obtiene un evento con actividad asociada por su ID.
	 *
	 * @param idEvento El ID del evento.
	 * @return Objeto EventoConActividad que representa el evento con su actividad
	 *         asociada.
	 * @throws SQLException Si ocurre un error al obtener el evento.
	 */
	public EventoConActividad obtenerEventoConActividad(int idEvento) throws SQLException {
		return DaoEventoConActividad.getInstance().obtenerEventoConActividad(idEvento);
	}

	/**
	 * Genera un objeto JSON que representa los últimos eventos registrados en el
	 * sistema.
	 *
	 * @param numEventos Número de eventos a listar.
	 * @return Una cadena JSON que contiene la información de los últimos eventos
	 *         registrados.
	 * @throws SQLException Si ocurre un error al acceder a la base de datos.
	 */
	public String listarJsonUltimosEventos(int numEventos) throws SQLException {
		return DaoEventoConActividad.getInstance().listarJsonUltimosEventos(numEventos);
	}

	/**
	 * Genera un objeto JSON que representa un evento específico con su información
	 * detallada, incluyendo la actividad asociada.
	 *
	 * @param idEvento ID del evento.
	 * @return Una cadena JSON que contiene la información del evento y su actividad
	 *         asociada.
	 * @throws SQLException Si ocurre un error al acceder a la base de datos.
	 */
	public String listarJsonEventosConActividad(int idEvento) throws SQLException {
		return DaoEventoConActividad.getInstance().listarJsonEventosConActividad(idEvento);
	}

}
