package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import modelo.Actividad;
import modelo.Evento;
import modelo.Evento.motivoFinalizacion;

/**
 * Clase de Acceso a Datos (DAO) para la gestión de eventos en el sistema.
 * Implementa el patrón Singleton y utiliza controladores mediante Servlets para
 * la interacción con la base de datos.
 *
 * @author Maitane Ibañez Irazabal
 * @version 1.0
 */
public class DaoClasificacionEventos {
	private Connection con = null;
	private static DaoClasificacionEventos instance = null;

	/**
	 * Constructor de la clase DaoClasificacionEventos. Establece una conexión con la base de datos.
	 *
	 * @throws SQLException Si ocurre un error al establecer la conexión con la base de datos.
	 */
	public DaoClasificacionEventos() throws SQLException {
		con = DBConection.getConection();
	}

	/**
	 * Método que implementa el patrón Singleton para asegurar que solo exista una instancia de esta clase.
	 *
	 * @return La única instancia existente de la clase DaoClasificacionEventos.
	 * @throws SQLException Si ocurre un error al establecer la conexión con la base de datos.
	 */
	public static DaoClasificacionEventos getInstance() throws SQLException {
		if (instance == null) {
			instance = new DaoClasificacionEventos();
		}
		return instance;
	}

	/**
	 * Crea una clasificación de eventos vinculándola a las posibles actividades que pueda tener.
	 *
	 * @param idActividad  ID de la actividad a vincular con el evento.
	 * @param idEvento     ID del evento a vincular con la actividad.
	 * @throws SQLException Si ocurre un error al insertar la clasificación de eventos en la base de datos.
	 */
	public void crearClasificacionEventos(int idActividad, int idEvento) throws SQLException {
		// Preparar la consulta SQL para crear la clasificación de eventos
		String sql = "INSERT INTO clasificacionEventos (idActividad, idEvento) VALUES (?, ?)";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, idActividad);
		ps.setInt(2, idEvento);
		ps.executeUpdate();
		ps.close();
	}

	/**
	 * Elimina la clasificación de eventos de la base de datos.
	 *
	 * @param idClasificacion ID de la clasificación de eventos a eliminar.
	 * @throws SQLException Si ocurre un error al eliminar la clasificación de eventos de la base de datos.
	 */
	public void eliminarClasificacionEventos(int idClasificacion) throws SQLException {
		// Preparar la consulta SQL para eliminar la clasificación de eventos
		String sql = "DELETE FROM clasificacionEventos WHERE idClasificacion = ?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, idClasificacion);
		ps.executeUpdate();
		ps.close();
	}

	/**
	 * Obtiene la lista de actividades asociadas a un evento específico.
	 *
	 * @param idEvento El ID del evento del que se desean obtener las actividades.
* @return Una lista de objetos `Actividad` que contienen la información de las actividades asociadas al evento especificado.
	 * @throws SQLException Si ocurre un error al acceder a la base de datos.
	 */
	public List<Actividad> obtenerActividadesPorEventoID(int idEvento) throws SQLException {
		List<Actividad> actividades = new ArrayList<>();

		// Preparar la consulta SQL para obtener las actividades del evento
		String sql = "SELECT a.ID_actividad, a.tipoActividad,a.fotoActividad FROM actividades a JOIN clasificacionEventos ce ON a.ID_actividad = ce.idActividad WHERE ce.idEvento = ?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, idEvento);

		// Ejecutar la consulta y procesar los resultados
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			Actividad actividad = new Actividad(rs.getInt("ID_actividad"), rs.getString("tipoActividad"),
					rs.getString("fotoActividad"));
			actividades.add(actividad);
		}

		// Cerrar recursos
		rs.close();
		ps.close();

		return actividades;
	}

	/**
	 * Obtiene la lista de eventos asociados a una actividad específica.
	 *
	 * @param idActividad El ID de la actividad.
	 * @return Lista de eventos asociados a la actividad especificada.
	 * @throws SQLException Si ocurre un error al acceder a la base de datos.
	 */
	public List<Evento> obtenerEventosPorActividadID(int idActividad) throws SQLException {
		List<Evento> eventos = new ArrayList<>();

		// Preparar la consulta SQL para obtener los eventos de la actividad
		String sql = "SELECT e.idEvento, e.nombre, e.detalles, e.fechaPublicacion, e.idModeradorPublicacion, e.fechaFinalizacion, e.idModeradorFinalizacion, e.motivoFinalizacion, e.ubicacion "
				+ "FROM eventos e JOIN clasificacionEventos ce ON e.idEvento = ce.idEvento WHERE ce.idActividad = ?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, idActividad);

		// Ejecutar la consulta y procesar los resultados
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			Evento evento = new Evento();
			evento.setIdEvento(rs.getInt("idEvento"));
			evento.setNombre(rs.getString("nombre"));
			evento.setDetalles(rs.getString("detalles"));
			evento.setFechaPublicacion(rs.getDate("fechaPublicacion"));
			evento.setIdModeradorPublicacion(rs.getInt("idModeradorPublicacion"));
			evento.setFechaFinalizacion(rs.getDate("fechaFinalizacion"));
			evento.setIdModeradorFinalizacion(rs.getInt("idModeradorFinalizacion"));
			evento.setmotivoFinalizacion(motivoFinalizacion.valueOf(rs.getString("motivoFinalizacion")));
			evento.setUbicacion(rs.getString("ubicacion"));

			eventos.add(evento);
		}

		// Cerrar recursos
		rs.close();
		ps.close();

		return eventos;
	}

	// ---------------------------------------------------------------------------------
	// VOLCADOS JSON
	//---------------------------------------------------------------------------------

	/**
	 * Genera un objeto JSON que representa las actividades asociadas a un evento específico.
	 *
	 * @param IdEvento El ID del evento.
	 * @return Una cadena JSON que representa las actividades asociadas al evento.
	 * @throws SQLException Si ocurre un error al obtener las actividades asociadas al evento de la base de datos.
	 */
	public String listarJsonActividadesPorEventoID(int IdEvento) throws SQLException {
		String json = "";
		Gson gson = new Gson();
		json = gson.toJson(this.obtenerActividadesPorEventoID(IdEvento));
		return json;
	}

	/**
	 * Genera un objeto JSON que representa los eventos asociados a una actividad específica.
	 *
	 * @param IdActividad El ID de la actividad.
	 * @return Una cadena JSON que representa los eventos asociados a la actividad.
	 * @throws SQLException Si ocurre un error al obtener los eventos asociados a la actividad de la base de datos.
	 */
	public String listarJsonEventosporActividadID(int IdActividad) throws SQLException {
		String json = "";
		Gson gson = new Gson();
		json = gson.toJson(this.obtenerEventosPorActividadID(IdActividad));
		return json;
	}

}
