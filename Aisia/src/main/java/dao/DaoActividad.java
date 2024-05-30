package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import modelo.Actividad;
/**
 * Clase de Acceso a Datos (DAO) para la gestión de actividades de eventos en el
 * sistema. Implementa el patrón Singleton y utiliza controladores mediante
 * Servlets para la interacción con la base de datos.
 *
 * @author Maitane Ibañez Irazabal
 * @version 1.0
 */
public class DaoActividad {
	/**
	 * Conexión a la base de datos.
	 */
	private Connection con = null;
	/**
	 * Instancia única de la clase DaoActividad.
	 */
	private static DaoActividad instance = null;

	/**
	 * Constructor para implementar el patrón Singleton.
	 *
	 * @throws SQLException Si ocurre un error al conectar con la base de datos.
	 */
	public DaoActividad() throws SQLException {
		con = DBConection.getConection();
	}

	/**
	 * Este metodo es el que utilizo para implementar el patrón singleton.
	 *
	 * @return La instancia única de DaoActividad.
	 * @throws SQLException Si ocurre un error al crear la instancia de
	 *                      DaoActividad.
	 */
	public static DaoActividad getInstance() throws SQLException {
		if (instance == null) {
			instance = new DaoActividad();
		}
		return instance;
	}

	/**
	 * Crea una nueva actividad en la base de datos.
	 * 
	 * @param actividad Objeto Actividad con la información de la nueva actividad.
	 * @throws SQLException Si ocurre un error al crear la actividad.
	 */
	public void crearActividad(Actividad actividad) throws SQLException {
		// Preparar la consulta SQL para crear la actividad
		String sql = "INSERT INTO actividades (tipoActividad, fotoActividad) VALUES (?, ?)";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, actividad.getTipoActividad());
		ps.setString(2, actividad.getFotoActividad() != null ? actividad.getFotoActividad() : null);
		ps.executeUpdate();
		ps.close();
	}

	/**
	 * Modifica una actividad existente en la base de datos.
	 * 
	 * @param actividad Objeto Actividad con la información actualizada de la
	 *                  actividad.
	 * @throws SQLException Si ocurre un error al modificar la actividad.
	 */
	public void editarActividad(Actividad actividad) throws SQLException {
		// Preparar la consulta SQL para modificar la actividad
		String sql = "UPDATE actividades SET tipoActividad = ?, fotoActividad = ? WHERE idactividad = ?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, actividad.getTipoActividad());
		ps.setString(2, actividad.getFotoActividad());
		ps.setInt(3, actividad.getIdActividad());
		ps.executeUpdate();
		ps.close();
	}

	/**
	 * Elimina una actividad de la base de datos.
	 * 
	 * @param actividad La actividad a eliminar.
	 * @throws SQLException Si ocurre un error SQL durante la eliminación de la
	 *                      actividad.
	 */
	public void eliminarActividad(Actividad actividad) throws SQLException {
		if (tieneReferenciasEnClasificacionEventos(actividad.getIdActividad())) {
			throw new SQLException(
					"Error al eliminar la actividad. Está vinculada a algún evento y no se puede borrar.");
		}
		String sql = "DELETE FROM actividades WHERE idactividad = ?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, actividad.getIdActividad());
		ps.executeUpdate();
		ps.close();
	}

	/**
	 * Verifica si la actividad tiene referencias en la tabla clasificacionEventos.
	 * 
	 * @param idActividad El ID de la actividad a verificar.
	 * @return {@code true} si la actividad tiene referencias en
	 *         clasificacionEventos, {@code false} en caso contrario.
	 * @throws SQLException Si ocurre un error SQL durante la consulta.
	 */
	private boolean tieneReferenciasEnClasificacionEventos(int idActividad) throws SQLException {
		String sql = "SELECT COUNT(*) FROM clasificacionEventos WHERE idActividad = ?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, idActividad);
		ResultSet rs = ps.executeQuery();
		rs.next();
		int count = rs.getInt(1);
		rs.close();
		ps.close();
		return count > 0;
	}

	/**
	 * Obtiene todas las actividades existentes en la base de datos.
	 *
	 * @return Una lista de todas las actividades existentes.
	 * @throws SQLException Si ocurre un error al obtener las actividades.
	 */
	public List<Actividad> obtenerTodasLasActividades() throws SQLException {
		List<Actividad> actividades = new ArrayList<>();
		String sql = "SELECT * FROM actividades order by tipoActividad";
		// System.out.println(sql);
		try (PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				Actividad actividad = new Actividad();
				actividad.setIdActividad(rs.getInt("idActividad"));
				actividad.setTipoActividad(rs.getString("tipoActividad"));
				actividad.setFotoActividad(rs.getString("fotoActividad"));

				actividades.add(actividad);
			}
		}
		return actividades;
	}

	/**
	 * Obtiene una actividad de la base de datos por su ID.. Metodo usado en JUNIT
	 *
	 * @param idActividad El ID de la actividad que se desea obtener.
	 * @return La actividad con el ID especificado, o null si no se encuentra.
	 * @throws SQLException Si ocurre un error al obtener la actividad.
	 */
	public Actividad obtenerActividadPorId(int idActividad) throws SQLException {
		Actividad actividad = null;
		String sql = "SELECT * FROM actividades WHERE idActividad = ?";
		// System.out.println(sql);
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, idActividad);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					actividad = new Actividad();
					actividad.setIdActividad(rs.getInt("idActividad"));
					actividad.setTipoActividad(rs.getString("tipoActividad"));
					actividad.setFotoActividad(rs.getString("fotoActividad"));
				}
			}
		}

		return actividad;
	}

	/**
	 * Obtiene toda la información de la última actividad creada en la base de
	 * datos. Metodo usado en JUNIT
	 *
	 * @return La última actividad creada.
	 * @throws SQLException Si ocurre un error al obtener la actividad.
	 */
	public Actividad obtenerUltimaActividad() throws SQLException {
		// Consulta SQL para obtener la última actividad creada
		String sql = "SELECT * FROM actividades WHERE idactividad = (SELECT MAX(idactividad) FROM actividades)";
		Actividad actividad = null;

		try (PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
			// Si hay resultados en el conjunto de resultados
			if (rs.next()) {
				// Crear una nueva actividad con los datos obtenidos
				actividad = new Actividad();
				actividad.setIdActividad(rs.getInt("idactividad"));
				actividad.setTipoActividad(rs.getString("tipoactividad"));
				actividad.setFotoActividad(rs.getString("fotoactividad"));
			}
		}
		// Devolver la actividad obtenida
		return actividad;
	}
	// ---------------------------------------------------------------------------------
	// VOLCADOS JSON
	// ---------------------------------------------------------------------------------

	/**
	 * Genera un objeto JSON que representa todas las actividades.
	 *
	 * @return Una cadena JSON que representa todas las actividades.
	 * @throws SQLException Si ocurre un error al obtener las actividades de la base
	 *                      de datos.
	 */
	public String listarJsonTodasActividades() throws SQLException {

		String json = "";
		Gson gson = new Gson();

		json = gson.toJson(this.obtenerTodasLasActividades());

		return json;

	}

	/**
	 * Devuelve un objeto JSON que contiene los detalles de una actividad específica
	 * identificada por su ID.
	 *
	 * @param idActividad El ID de la actividad que se desea obtener.
	 * @return Un objeto JSON que contiene los detalles de la actividad.
	 * @throws SQLException Si ocurre un error al acceder a la base de datos.
	 */

	public String listarJsonActividadPorID(int idActividad) throws SQLException {

		String json = "";
		Gson gson = new Gson();

		json = gson.toJson(this.obtenerActividadPorId(idActividad));

		return json;

	}
}
