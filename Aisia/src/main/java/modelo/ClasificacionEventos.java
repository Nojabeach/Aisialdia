package modelo;

import java.sql.SQLException;
import java.util.List;

import dao.DaoClasificacionEventos;
import dao.DaoEvento;

/**
 * Clase que representa una clasificación de eventos-actividad en el sistema.
 * 
 * @author Maitane Ibañez Irazabal
 * @version 1.1
 */
public class ClasificacionEventos {
	// ATRIBUTOS
	// --------------------------------------------------------------------------------------------
	/**
	 * Identificador único de la clasificación de eventos (auto-incrementado).
	 */
	private int idClasificacion;

	/**
	 * Identificador de la actividad asociada a la clasificación.
	 */
	private int idActividad;

	/**
	 * Identificador del evento asociado a la clasificación.
	 */
	private int idEvento;

	// CONSTRUCTORES
	// --------------------------------------------------------------------------------------------
	/**
	 * Constructor vacío.
	 */
	public ClasificacionEventos() {

	}

	/**
	 * Constructor que inicializa los identificadores de la actividad y el evento.
	 * 
	 * @param idActividad Identificador de la actividad asociada.
	 * @param idEvento    Identificador del evento asociado.
	 */
	public ClasificacionEventos(int idActividad, int idEvento) {
		this.idActividad = idActividad;
		this.idEvento = idEvento;
	}

	// GETTERS Y SETTERS PARA TODOS LOS ATRIBUTOS
	// --------------------------------------------------------------------------------------------

	/**
	 * Obtiene el identificador único de la clasificación de eventos.
	 * 
	 * @return El identificador de la clasificación de eventos.
	 */
	public int getIdClasificacion() {
		return idClasificacion;
	}

	/**
	 * Establece el identificador único de la clasificación de eventos.
	 * 
	 * @param idClasificacion El nuevo identificador de la clasificación de eventos.
	 */
	public void setIdClasificacion(int idClasificacion) {
		this.idClasificacion = idClasificacion;
	}

	/**
	 * Obtiene el identificador de la actividad asociada a la clasificación.
	 * 
	 * @return El identificador de la actividad.
	 */
	public int getIdActividad() {
		return idActividad;
	}

	/**
	 * Establece el identificador de la actividad asociada a la clasificación.
	 * 
	 * @param idActividad El nuevo identificador de la actividad.
	 */
	public void setIdActividad(int idActividad) {
		this.idActividad = idActividad;
	}

	/**
	 * Obtiene el identificador del evento asociado a la clasificación.
	 * 
	 * @return El identificador del evento.
	 */
	public int getIdEvento() {
		return idEvento;
	}

	/**
	 * Establece el identificador del evento asociado a la clasificación.
	 * 
	 * @param idEvento El nuevo identificador del evento.
	 */
	public void setIdEvento(int idEvento) {
		this.idEvento = idEvento;
	}

	// TOSTRING
	// --------------------------------------------------------------------------------------------

	/**
	 * Representación textual de la clasificación de eventos.
	 * 
	 * @return Una cadena que contiene los detalles de la clasificación.
	 */
	@Override
	public String toString() {
		return "ClasificacionEventos [idClasificacion=" + idClasificacion + ", idActividad=" + idActividad
				+ ", idEvento=" + idEvento + "]";
	}

	// MÉTODOS DE NEGOCIO
	// --------------------------------------------------------------------------------------------

	/**
	 * Crea una nueva clasificación de eventos en la base de datos.
	 * 
	 * @param idActividad Identificador de la actividad asociada.
	 * @param idEvento    Identificador del evento asociado.
	 * @throws SQLException Si ocurre un error al crear la clasificación en la base
	 *                      de datos.
	 * @throws Exception    Si ocurre una excepción general durante la creación.
	 */
	public void crearClasificacionEventos(int idActividad, int idEvento) throws SQLException, Exception {
		DaoClasificacionEventos.getInstance().crearClasificacionEventos(idActividad, idEvento);
	}

	/**
	 * Elimina una clasificación de eventos existente de la base de datos.
	 * 
	 * @throws SQLException Si ocurre un error al eliminar la clasificación en la
	 *                      base de datos.
	 */
	public void eliminarClasificacionEventos() throws SQLException {
		DaoClasificacionEventos.getInstance().eliminarClasificacionEventos(idClasificacion);
	}

	/**
	 * Obtiene la lista de eventos asociados a una actividad específica.
	 * 
	 * @param idActividad El identificador de la actividad.
	 * @return Lista de eventos asociados a la actividad indicada.
	 * @throws SQLException Si ocurre un error al obtener los eventos.
	 */
	public List<Evento> obtenerEventosPorActividadID(int idActividad) throws SQLException {
		return DaoClasificacionEventos.getInstance().obtenerEventosPorActividadID(idActividad);
	}

	/**
	 * Obtiene la lista de actividades asociadas a un evento específico.
	 * 
	 * Este método recupera la información de las actividades relacionadas con un
	 * evento en particular desde la base de datos.
	 * 
	 * @param idEvento El identificador del evento del que se desean obtener las
	 *                 actividades.
	 * @return Una lista de objetos {@code Actividad} que contienen la información
	 *         de las actividades asociadas al evento especificado.
	 * @throws SQLException Si ocurre un error al acceder a la base de datos.
	 */
	public List<Actividad> obtenerActividadesPorEventoID(int idEvento) throws SQLException {
		return DaoClasificacionEventos.getInstance().obtenerActividadesPorEventoID(idEvento);
	}

}
