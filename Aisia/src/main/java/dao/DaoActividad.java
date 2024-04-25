package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modelo.Actividad;

public class DaoActividad {

	private Connection con = null;
	private static DaoActividad instance = null;

	/**
	 * Clase de Acceso a Datos (DAO) para la gestión de actividades de eventos en el
	 * sistema. Implementa el patrón Singleton y utiliza controladores mediante
	 * Servlets para la interacción con la base de datos.
	 * 
	 * @author Maitane Ibañez Irazabal
	 * @version 1.0
	 */
	public DaoActividad() throws SQLException {
		con = DBConection.getConection();
	}

	/**
	 * Este metodo es el que utilizo para implementar el patron singleton
	 * 
	 * @return
	 * @throws SQLException
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
		String sql = "INSERT INTO actividades (tipoActividad,fotoActividad) VALUES (?,?)";
		PreparedStatement ps = con.prepareStatement(sql);
		System.out.println(actividad.getTipoActividad());
		ps.setString(1, actividad.getTipoActividad());
		ps.setString(2, actividad.getFotoActividad() != null ? actividad.getFotoActividad() : null); // if-else en una
																										// sola línea,
																										// si no hay
																										// foto pasa
																										// null
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
		String sql = "UPDATE actividades SET tipoActividad = ? WHERE ID_actividad = ?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, actividad.getTipoActividad());
		ps.setInt(2, actividad.getIdActividad());
		ps.executeUpdate();
		ps.close();

	}

	/**
	 * Elimina una actividad existente en la base de datos.
	 * 
	 * @param idActividad Identificador de la actividad a eliminar.
	 * @throws Exception Si ocurre un error al eliminar la actividad.
	 */
	public void eliminarActividad(Actividad actividad) throws SQLException {
		// Preparar la consulta SQL para eliminar la actividad
		String sql = "DELETE FROM actividades WHERE ID_actividad = ?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, actividad.getIdActividad());
		ps.executeUpdate();
		ps.close();
	}

	/**
	 * Obtiene todas las actividades existentes en la base de datos.
	 *
	 * @return Una lista de todas las actividades existentes.
	 * @throws SQLException Si ocurre un error al obtener las actividades.
	 */
	public List<Actividad> obtenerTodasLasActividades() throws SQLException {
		List<Actividad> actividades = new ArrayList<>();
		String sql = "SELECT * FROM actividades order by TipoActividad";

		try (PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				Actividad actividad = new Actividad();
				actividad.setIdActividad(rs.getInt("ID_actividad"));
				actividad.setTipoActividad(rs.getString("TipoActividad"));
				actividad.setFotoActividad(rs.getString("fotoActividad"));

				actividades.add(actividad);
			}
		}
		return actividades;
	}

}
