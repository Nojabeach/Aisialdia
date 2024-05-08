package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import jakarta.servlet.http.HttpServletRequest;
import modelo.Actividad;
import modelo.Evento;

public class DaoEvento {

	private Connection con = null;
	private static DaoEvento instance = null;

	/**
	 * Clase de Acceso a Datos (DAO) para la gestión de eventos en el sistema.
	 * Implementa el patrón Singleton y utiliza controladores mediante Servlets para
	 * la interacción con la base de datos.
	 * 
	 * @author Maitane Ibañez Irazabal
	 * @version 1.5
	 */
	public DaoEvento() throws SQLException {
		con = DBConection.getConection();
	}

	/**
	 * Este metodo es el que se utiliza para aplicar el patron SINGLETON  
	 * 
	 * @return
	 * @throws SQLException
	 */
	public static DaoEvento getInstance() throws SQLException {
		if (instance == null) {
			instance = new DaoEvento();
		}
		return instance;
	}

	/**
	 * Crea un nuevo evento en la base de datos y vincula las actividades
	 * relacionadas.
	 *
	 * @param evento      El evento a crear.
	 * @param actividades Las actividades a vincular al evento.
	 * @throws SQLException Si ocurre un error al crear el evento o las
	 *                      vinculaciones en la base de datos
	 */
	public int crearEvento(Evento evento, List<Actividad> actividades, Timestamp fechaUltimaModificacion)
			throws SQLException {
		String sqlEvento = "INSERT INTO eventos (nombre, detalles, idUsuariocreador, fechaUltimaModificacion, ubicacion) VALUES (?,?,?,?,?)";
		PreparedStatement psEvento = con.prepareStatement(sqlEvento, Statement.RETURN_GENERATED_KEYS);
		psEvento.setString(1, evento.getNombre());
		psEvento.setString(2, evento.getDetalles());
		psEvento.setInt(3, evento.getIdUsuarioCreador());
		psEvento.setTimestamp(4, fechaUltimaModificacion);
		psEvento.setString(5, evento.getUbicacion());
		psEvento.executeUpdate();

		ResultSet rs = psEvento.getGeneratedKeys();
		if (rs.next()) {
			int idEvento = rs.getInt(1);

			// Crear vinculaciones con actividades
			String sqlClasificacion = "INSERT INTO clasificacionEventos (idActividad, idEvento) VALUES (?,?)";
			PreparedStatement psClasificacion = con.prepareStatement(sqlClasificacion);
			for (Actividad actividad : actividades) {
				psClasificacion.setInt(1, actividad.getIdActividad());
				psClasificacion.setInt(2, idEvento);
				psClasificacion.executeUpdate();
			}

			return idEvento;
		} else {
			throw new SQLException("No se pudo obtener el ID del evento creado.");
		}
	}

	/**
	 * Edita un evento existente en la base de datos.
	 *
	 * @param evento El evento a editar.
	 * @throws SQLException
	 * 
	 */
	public void editarEvento(Evento evento) throws SQLException {

		String sql = "UPDATE eventos SET nombre = ?, detalles = ?, fechaUltimaModificacion = ?, fechaPublicacion = ?, "
				+ "idModeradorPublicacion = ?, fechaFinalizacion = ?, idModeradorFinalizacion = ?, motivoFinalizacion = ?,"
				+ " ubicacion = ? WHERE idEvento = ?";
		PreparedStatement ps = con.prepareStatement(sql);

		ps.setString(1, evento.getNombre());
		ps.setString(2, evento.getDetalles());
		ps.setTimestamp(3, new Timestamp(System.currentTimeMillis())); // Actualizar la fecha de última modificación
		ps.setDate(4, evento.getFechaPublicacion());
		ps.setInt(5, evento.getIdModeradorPublicacion());
		ps.setDate(6, evento.getFechaFinalizacion());
		ps.setInt(7, evento.getIdModeradorFinalizacion());
		ps.setString(8, evento.getMotivoFinalizacion().toString());
		ps.setString(9, evento.getUbicacion());
		ps.setInt(10, evento.getIdEvento());
		ps.executeUpdate();
		ps.close();
	}

	/**
	 * Elimina un evento de la base de datos.
	 *
	 * @param idEvento El identificador del evento a eliminar.
	 * @throws SQLException Si ocurre un error al eliminar el evento en la BD.
	 */
	public void eliminarEvento(Evento evento) throws SQLException {

		String sql = "DELETE FROM eventos WHERE idEvento = ?";
		PreparedStatement ps = con.prepareStatement(sql);

		ps.setInt(1, evento.getIdEvento());
		ps.executeUpdate();

	}

	/**
	 * Publica un evento aprobado de publicacion
	 * 
	 * @param idEvento        El identificador del evento a publicar.
	 * @param idUsuarioActual El identificador del usuario que publica o planifica
	 *                        la publicacion el evento.
	 * @throws SQLException Si ocurre un error al publicar el evento en la BD
	 */
	public void publicarEvento(int idEvento, HttpServletRequest request) throws SQLException {

		String sql = "UPDATE eventos SET fechaPublicacion = current_date,fechaUltimaModificacion=current_date, "
				+ "idModeradorPublicacion = ? WHERE idEvento = ?";
		PreparedStatement ps = con.prepareStatement(sql);
		int idUsuarioActual = DaoUsuario.obtenerIdUsuarioActual(request);
		ps.setInt(1, idUsuarioActual);
		ps.setInt(2, idEvento);
		ps.executeUpdate();
		ps.close();
	}

	/**
	 * Rechaza un evento pendiente de aprobación.
	 * 
	 * @param idEvento      El identificador del evento a rechazar.
	 * @param motivoRechazo El motivo del rechazo.
	 * @param request       Objeto HttpServletRequest para obtener el ID del usuario
	 *                      actual.
	 * @throws SQLException Si ocurre un error al rechazar el evento en la BD.
	 */
	public void rechazarEvento(int idEvento, HttpServletRequest request) throws SQLException {
		int idUsuarioActual = DaoUsuario.obtenerIdUsuarioActual(request);

		String sql = "UPDATE eventos SET motivoFinalizacion = 'Rechazado',fechaFinalizacion=current_date,"
				+ "fechaUltimaModificacion=current_date,idModeradorFinalizacion=? WHERE idEvento = ?";
		PreparedStatement ps = con.prepareStatement(sql);

		ps.setInt(1, idUsuarioActual);
		ps.setInt(2, idEvento);
		ps.executeUpdate();
		ps.close();
	}

	/**
	 * Aprobar publicacion de un evento
	 * 
	 * @param idEvento El identificador del evento
	 * @param request  El identificador del usuario
	 * @throws SQLException En caso de error con la BD.
	 */
	public void aprobarPublicacionEvento(int idEvento, HttpServletRequest request) throws SQLException {
		int idUsuarioActual = DaoUsuario.obtenerIdUsuarioActual(request);
		String sql = "UPDATE eventos SET fechaAprobacion = current_date,fechaUltimaModificacion=current_date,"
				+ " idModeradorAprobacion = ? WHERE idEvento = ?";
		PreparedStatement ps = con.prepareStatement(sql);

		ps.setInt(1, idUsuarioActual);
		ps.setInt(2, idEvento);
		ps.executeUpdate();
		ps.close();
	}

	/**
	 * Finaliza la publicación de un evento en la base de datos.
	 *
	 * @param idEvento    El ID del evento que se desea finalizar.
	 * @param idModerador El ID del moderador que finaliza el evento.
	 * @throws SQLException si ocurre algún error al interactuar con la base de
	 *                      datos.
	 */
	public void finalizarPublicacionEvento(int idEvento, int idModerador) throws SQLException {
		String sql = "UPDATE eventos SET motivoFinalizacion = 'FinVisibilidad', fechaFinalizacion = current_date, "
				+ "fechaUltimaModificacion = current_date, idModeradorFinalizacion = ? WHERE idEvento = ?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, idModerador);
		ps.setInt(2, idEvento);
		ps.executeUpdate();
		ps.close();
	}

	/**
	 * Obtiene la lista de eventos que están pendientes de aprobación que no hayan
	 * sido finalizados (rechazos, por ejemplo)
	 * 
	 * @return Lista de eventos pendientes de aprobación.
	 * @throws SQLException Si ocurre un error al obtener los eventos.
	 */
	public List<Evento> obtenerEventosPendientesAprobacion() throws SQLException {
		String sql = "SELECT * FROM eventos WHERE fechaAprobacion IS NULL and fechaFinalizacion is null";
		List<Evento> eventos = new ArrayList<>();

		try (PreparedStatement pstmt = con.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
			while (rs.next()) {
				Evento evento = new Evento();
				evento.setIdEvento(rs.getInt("idEvento"));
				evento.setNombre(rs.getString("nombre"));
				evento.setDetalles(rs.getString("detalles"));
				evento.setFechaUltimaModificacion(rs.getDate("fechaUltimaModificacion"));
				evento.setUbicacion(rs.getString("ubicacion"));

				eventos.add(evento);
			}
		}

		return eventos;
	}

	/**
	 * Obtiene la lista de eventos que están pendientes de publicacion.
	 * 
	 * @return Lista de eventos pendientes de aprobación.
	 * @throws SQLException Si ocurre un error al obtener los eventos.
	 */
	public List<Evento> obtenerEventosPendientesPublicacion() throws SQLException {
		String sql = "SELECT * FROM eventos WHERE fechaPublicacion IS NULL and fechaAprobacion IS not NULL";
		List<Evento> eventos = new ArrayList<>();

		try (PreparedStatement pstmt = con.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
			while (rs.next()) {
				Evento evento = new Evento();
				evento.setIdEvento(rs.getInt("idEvento"));
				evento.setNombre(rs.getString("nombre"));
				evento.setDetalles(rs.getString("detalles"));
				evento.setFechaUltimaModificacion(rs.getDate("fechaUltimaModificacion"));
				evento.setUbicacion(rs.getString("ubicacion"));

				eventos.add(evento);
			}
		}

		return eventos;
	}

	/**
	 * Obtiene todos los eventos publicados activos de la base de datos (sin
	 * finalizar) que coinciden con los filtros especificados.
	 *
	 * @param actividad   Filtro por actividad (opcional)
	 * @param descripcion Filtro por descripción (opcional)
	 * @param ubicacion   Filtro por ubicación (opcional)
	 * @param fecha       Filtro por fecha (opcional)
	 * @return Una lista de objetos Evento que representan todos los eventos en la
	 *         base de datos que coinciden con los filtros especificados.
	 * @throws SQLException Si ocurre un error al acceder a la base de datos.
	 */
	public List<Evento> obtenerTodosLosEventosActivos(String actividad, String descripcion, String ubicacion,
			Date fecha) throws SQLException {
		List<Evento> eventos = new ArrayList<>();
		String sql = "SELECT * FROM eventos where fechafinalizacion is null and fechapublicacion is not null";

		// Agregar cláusulas WHERE según sea necesario
		if (actividad != null && !actividad.isEmpty()) {
			sql += " AND idActividad =?";
		}
		if (descripcion != null && !descripcion.isEmpty()) {
			sql += " AND descripcion LIKE?";
		}
		if (ubicacion != null && !ubicacion.isEmpty()) {
			sql += " AND ubicacion LIKE?";
		}
		if (fecha != null) {
			sql += " AND fechaEvento =?";
		}

		sql += " ORDER BY id";

		PreparedStatement ps = con.prepareStatement(sql);

		// Agregar parámetros a la consulta según sea necesario
		int paramIndex = 1;
		if (actividad != null && !actividad.isEmpty()) {
			ps.setInt(paramIndex++, Integer.parseInt(actividad));
		}
		if (descripcion != null && !descripcion.isEmpty()) {
			ps.setString(paramIndex++, "%" + descripcion + "%");
		}
		if (ubicacion != null && !ubicacion.isEmpty()) {
			ps.setString(paramIndex++, "%" + ubicacion + "%");
		}
		if (fecha != null) {
			ps.setDate(paramIndex++, fecha);
		}

		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			Evento evento = new Evento();
			evento.setIdEvento(rs.getInt("id"));
			evento.setNombre(rs.getString("nombre"));
			evento.setDetalles(rs.getString("detalles"));
			evento.setIdUsuarioCreador(rs.getInt("idUsuarioCreador"));
			evento.setFechaUltimaModificacion(rs.getDate("fechaUltimaModificacion"));
			evento.setUbicacion(rs.getString("ubicacion"));
			eventos.add(evento);
		}
		return eventos;
	}

	/**
	 * Busca eventos por un criterio de búsqueda.
	 * 
	 * @param criterio Criterio de búsqueda (nombre, fecha, etc.).
	 * @return Lista de objetos Evento con la información de los eventos
	 *         coincidentes.
	 * @throws Exception Si ocurre un error al buscar los eventos.
	 */
	public List<Evento> buscarEventos(String criterio) throws Exception {
		// Preparar la consulta SQL para buscar eventos con el criterio especificado
		String sql = "SELECT * FROM eventos WHERE nombre LIKE? OR descripcion LIKE?";
		List<Evento> eventos = new ArrayList<>();
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setString(1, "%" + criterio + "%");
			stmt.setString(2, "%" + criterio + "%");
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					eventos.add(new Evento(rs.getInt("idEvento"), rs.getString("nombre"), rs.getString("detalles"),
							rs.getDate("fechaEvento")));
				}
			}
		} catch (SQLException e) {
			throw new Exception("Error al buscar eventos", e);
		}
		return eventos;
	}

	// ---------------------------------------------------------------------------------
	// VOLCADOS JSON
	// ---------------------------------------------------------------------------------

	/**
	 * Genera un objeto JSON que representa todos los eventos activos que coinciden
	 * con los filtros especificados.
	 *
	 * @param actividad   Filtro por actividad.
	 * @param descripcion Filtro por descripción.
	 * @param ubicacion   Filtro por ubicación.
	 * @param fecha       Filtro por fecha.
	 * @return Una cadena JSON que representa los eventos activos que coinciden con
	 *         los filtros especificados.
	 * @throws SQLException Si ocurre un error al acceder a la base de datos.
	 */
	public String listarJsonObtenerTodosLosEventosActivos(String actividad, String descripcion, String ubicacion,
			Date fecha) throws SQLException {
		String json = "";
		Gson gson = new Gson();
		json = gson.toJson(this.obtenerTodosLosEventosActivos(actividad, descripcion, ubicacion, fecha));
		return json;
	}

	/**
	 * Genera un objeto JSON que representa los eventos pendientes de aprobación.
	 *
	 * @return Una cadena JSON que representa los eventos pendientes de aprobación.
	 * @throws SQLException Si ocurre un error al acceder a la base de datos.
	 */
	public String listarJsonPendientesAprobacion() throws SQLException {
		String json = "";
		Gson gson = new Gson();
		json = gson.toJson(this.obtenerEventosPendientesAprobacion());
		return json;
	}

	/**
	 * Genera un objeto JSON que representa los eventos pendientes de publicación.
	 *
	 * @return Una cadena JSON que representa los eventos pendientes de publicación.
	 * @throws SQLException Si ocurre un error al acceder a la base de datos.
	 */
	public String listarJsonPendientesPublicacion() throws SQLException {
		String json = "";
		Gson gson = new Gson();
		json = gson.toJson(this.obtenerEventosPendientesPublicacion());
		return json;
	}

	/**
	 * Genera un objeto JSON que representa los eventos que coinciden con el
	 * criterio de búsqueda.
	 *
	 * @param criterio Criterio de búsqueda.
	 * @return Una cadena JSON que representa los eventos coincidentes con el
	 *         criterio de búsqueda.
	 * @throws Exception Si ocurre un error al buscar los eventos.
	 */
	public String listarJsonBuscarEventos(String criterio) throws Exception {
		String json = "";
		Gson gson = new Gson();
		json = gson.toJson(this.buscarEventos(criterio));
		return json;
	}

}
