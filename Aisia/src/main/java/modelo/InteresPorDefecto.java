package modelo;

import java.sql.SQLException;
import java.util.List;

import dao.DaoInteresPorDefecto;

/**
 * 
 * Clase que representa un interés por defecto en el sistema. Permite
 * interactuar con la capa de datos para insertar, eliminar y listar intereses
 * por defecto.
 * 
 * @author Maitane Ibañez Irazabal
 * @version 1.0
 */
public class InteresPorDefecto {
	private int idInteres;
	private String nombreInteres;

	/**
	 * Constructor por defecto de la clase InteresPorDefecto.
	 */
	public InteresPorDefecto() {
	}

	/**
	 * Constructor que permite crear un objeto InteresPorDefecto con un nombre de
	 * interés.
	 * 
	 * @param nombreInteres El nombre del interés.
	 */
	public InteresPorDefecto(String nombreInteres) {
		this.nombreInteres = nombreInteres;
	}

	/**
	 * Obtiene el ID del interés por defecto.
	 * 
	 * @return El ID del interés.
	 */
	public int getIdInteres() {
		return idInteres;
	}

	/**
	 * Establece el ID del interés por defecto.
	 * 
	 * @param idInteres El ID del interés.
	 */
	public void setIdInteres(int idInteres) {
		this.idInteres = idInteres;
	}

	/**
	 * Obtiene el nombre del interés por defecto.
	 * 
	 * @return El nombre del interés.
	 */
	public String getNombreInteres() {
		return nombreInteres;
	}

	/**
	 * Establece el nombre del interés por defecto.
	 * 
	 * @param nombreInteres El nombre del interés.
	 */
	public void setNombreInteres(String nombreInteres) {
		this.nombreInteres = nombreInteres;
	}

	/**
	 * Retorna una representación en forma de cadena del objeto InteresPorDefecto.
	 * 
	 * @return Una cadena que representa el objeto.
	 */
	@Override
	public String toString() {
		return "InteresPorDefecto [idInteres=" + idInteres + ", nombreInteres=" + nombreInteres + "]";
	}

	/**
	 * Inserta una lista de intereses por defecto en la base de datos.
	 * 
	 * @param intereses La lista de intereses por defecto a insertar.
	 * @throws SQLException Si ocurre un error al insertar los intereses en la base
	 *                      de datos.
	 */
	public void insertarInteresesPorDefecto(List<InteresPorDefecto> intereses) throws SQLException {
		DaoInteresPorDefecto.getInstance().insertarInteresesPorDefecto(intereses);
	}

	/**
	 * Elimina un interés por defecto de la base de datos por su ID.
	 * 
	 * @param idInteres El ID del interés por defecto a eliminar.
	 * @throws SQLException Si ocurre un error al eliminar el interés de la base de
	 *                      datos.
	 */
	public void eliminarInteresPorDefecto(int idInteres) throws SQLException {
		DaoInteresPorDefecto.getInstance().eliminarInteresPorDefecto(idInteres);
	}

	/**
	 * Lista los nombres de los intereses por defecto almacenados en la base de
	 * datos.
	 * 
	 * @return Una lista de cadenas que representan los nombres de los intereses por
	 *         defecto.
	 * @throws SQLException Si ocurre un error al obtener los nombres de los
	 *                      intereses de la base de datos.
	 */
	public List<String> listarNombresInteresesPorDefecto() throws SQLException {
		return DaoInteresPorDefecto.getInstance().listarNombresInteresesPorDefecto();
	}


}
