package modelo;

import java.sql.SQLException;
import java.util.List;

import dao.DaoActividad;

/**
 * Clase que representa una actividad realizada en un evento.
 *
 * @author Maitane Ibañez Irazabal
 * @version 1.0
 */
public class Actividad {
	// ATRIBUTOS
	// --------------------------------------------------------------------------------------------

	/**
	 * Identificador único de la actividad (clave primaria).
	 */
	private int idActividad;

	/**
	 * Tipo de actividad que se realiza (por ejemplo, "Cultural",
	 * "Deportiva","Infantil","Bebés","Adolescentes", "Taller", "Exposición").
	 */
	private String tipoActividad;
	/**
	 * Variable que me guarda la ruta de la foto en caso de estar subida
	 */
	private String fotoActividad;

	// CONSTRUCTORES
	// --------------------------------------------------------------------------------------------

	/**
	 * Constructor vacío para inicializar la clase.
	 */
	public Actividad() {
	}

	/**
	 * Constructor COMPLETO para inicializar la clase con todos sus atributos.
	 *
	 * @param idActividad   Identificador único de la actividad.
	 * @param tipoActividad Tipo de actividad que se realiza.
	 * @param fotoActividad Indica la ruta de la foto (en caso de tenerla)
	 */
	public Actividad(int idActividad, String tipoActividad, String fotoActividad) {

		this.idActividad = idActividad;
		this.tipoActividad = tipoActividad;
		this.fotoActividad = fotoActividad;
	}

	/**
	 * Contructor de la actividad con tan solo el idActividad para una busqueda
	 * simple de la actividad
	 * 
	 * @param idActividad  Identificador único de la actividad.
	 */
	public Actividad(int idActividad) {
		this.idActividad = idActividad;
	}

	/**
	 * Constructor de la actividad con el tipo y la foto : usado en la creacion de
	 * la actividad
	 * 
	 * @param tipoActividad Tipo de actividad que se realiza.
	 * @param fotoActividad Indica la ruta de la foto (en caso de tenerla)
	 */
	public Actividad(String tipoActividad, String fotoActividad) {

		this.tipoActividad = tipoActividad;
		this.fotoActividad = fotoActividad;
	}

	// GETTERS Y SETTERS PARA TODOS LOS ATRIBUTOS
	// --------------------------------------------------------------------------------------------

	/**
	 * Obtiene el identificador único de la actividad.
	 *
	 * @return Identificador único de la actividad.
	 */
	public int getIdActividad() {
		return idActividad;
	}

	/**
	 * Establece el identificador único de la actividad.
	 *
	 * @param idActividad Identificador único de la actividad.
	 */
	public void setIdActividad(int idActividad) {
		this.idActividad = idActividad;
	}

	/**
	 * Obtiene el tipo de actividad que se realiza.
	 *
	 * @return Tipo de actividad que se realiza.
	 */
	public String getTipoActividad() {
		return tipoActividad;
	}

	/**
	 * Establece el tipo de actividad que se realiza.
	 *
	 * @param tipoActividad Tipo de actividad que se realiza.
	 */
	public void setTipoActividad(String tipoActividad) {
		this.tipoActividad = tipoActividad;
	}
	/**
	 * Obtiene la URL o ruta de la foto asociada a la actividad.
	 *
	 * @return La URL o ruta de la foto de la actividad como cadena de texto (String).
	 */
	public String getFotoActividad() {
		return fotoActividad;
	}
	/**
	 * Establece la URL o ruta de la foto asociada a la actividad.
	 *
	 * @param fotoActividad La URL o ruta de la foto de la actividad como cadena de texto (String).
	 */
	public void setFotoActividad(String fotoActividad) {
		this.fotoActividad = fotoActividad;
	}
	// TOSTRING
	// --------------------------------------------------------------------------------------------

	/**
	 * Representación textual de la clase Actividad.
	 *
	 * @return Representación textual de la actividad.
	 */

	@Override
	public String toString() {
		return "Actividad [idActividad=" + idActividad + ", tipoActividad=" + tipoActividad + ", fotoActividad="
				+ fotoActividad + "]";
	}

	// MÉTODOS DE NEGOCIO
	// --------------------------------------------------------------------------------------------
	/**
	 * Crea una nueva actividad en la base de datos.
	 *
	 * @param actividad Objeto `Actividad` que representa la actividad a crear.
	 * @throws SQLException Si ocurre un error al crear la actividad en la base de datos.
	 */
	public void crearActividad(Actividad actividad) throws SQLException {
		System.out.println(actividad);
		DaoActividad.getInstance().crearActividad(actividad);
	}

	/**
	 * Modifica una actividad existente en la base de datos.
	 *
	 * @throws SQLException Si ocurre un error al modificar la actividad.
	 */
	public void editarActividad() throws SQLException {
		DaoActividad.getInstance().editarActividad(this);
	}

	/**
	 * Elimina una actividad existente en la base de datos.
	 *
	 * @throws SQLException Si ocurre un error al eliminar la actividad.
	 */
	public void eliminarActividad() throws SQLException {
		DaoActividad.getInstance().eliminarActividad(this);
	}

	/**
	 * Obtiene todas las actividades existentes en la base de datos.
	 *
	 * @return Una lista de todas las actividades existentes.
	 * @throws SQLException Si ocurre un error al obtener las actividades.
	 */
	public static List<Actividad> obtenerTodasLasActividades() throws SQLException {
		return DaoActividad.getInstance().obtenerTodasLasActividades();
	}




}
