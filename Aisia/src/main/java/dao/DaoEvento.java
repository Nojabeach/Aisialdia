package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import modelo.Evento;
import modelo.Evento.MotivoFinalizacion;

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
	 * Crea un nuevo evento en la base de datos.
	 *
	 * @param evento El evento a crear.
	 * @throws SQLException Si ocurre un error al crear el evento en la base de
	 *                      datos
	 */
	public int crearEvento(Evento evento, Timestamp fechaUltimaModificacion) throws SQLException {
	    String sql = "INSERT INTO eventos (nombre, detalles, idUsuariocreador, fechaUltimaModificacion, ubicacion) VALUES (?, ?, ?, ?, ?)";
	    PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	    ps.setString(1, evento.getNombre());
	    ps.setString(2, evento.getDetalles());
	    ps.setInt(3, evento.getIdUsuarioCreador());
	    ps.setTimestamp(4, fechaUltimaModificacion);
	    ps.setString(5, evento.getUbicacion());
	    ps.executeUpdate();

	    ResultSet rs = ps.getGeneratedKeys();
	    if (rs.next()) {
	        int idEvento = rs.getInt(1);
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
	 * @param idEvento           El ID del evento que se desea finalizar.
	 * @param idModerador        El ID del moderador que finaliza el evento.
	 * @throws SQLException      si ocurre algún error al interactuar con la base de datos.
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
	 * Obtiene un evento específico de la base de datos a partir de su
	 * identificador.
	 * 
	 * @param idEvento El identificador del evento a buscar.
	 * @return Un objeto Evento con la información del evento encontrado, o null si
	 *         no se encuentra ningún evento con ese identificador.
	 * @throws SQLException Si ocurre un error al acceder a la base de datos.
	 */
	public Evento obtenerEventoPorId(int idEvento) throws SQLException {
		String sql = "SELECT * FROM eventos WHERE idEvento = ?";
		Evento evento = null;

		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, idEvento);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					evento = new Evento();
					evento.setIdEvento(rs.getInt("idEvento"));
					evento.setNombre(rs.getString("nombre"));
					evento.setDetalles(rs.getString("detalles"));
					evento.setFechaPublicacion(rs.getDate("fechaPublicacion"));
					evento.setIdModeradorPublicacion(rs.getInt("idModeradorPublicacion"));
					evento.setFechaFinalizacion(rs.getDate("fechaFinalizacion"));
					evento.setIdModeradorFinalizacion(rs.getInt("idModeradorFinalizacion"));
					evento.setMotivoFinalizacion(MotivoFinalizacion.valueOf(rs.getString("motivoFinalizacion")));
					evento.setUbicacion(rs.getString("ubicacion"));
				}
			}
		}

		return evento;
	}

	/**
	 * Obtiene la lista de eventos organizados por un usuario específico.
	 * 
	 * @param idUsuario El identificador del usuario.
	 * @return Lista de eventos organizados por el usuario indicado.
	 * @throws SQLException Si ocurre un error al obtener los eventos.
	 */
	public List<Evento> obtenerEventosPorUsuario(int idUsuario) throws SQLException {
		String sql = "SELECT * FROM eventos WHERE idUsuarioCreador = ?";
		List<Evento> eventos = new ArrayList<>();

		try (PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setInt(1, idUsuario);
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					Evento evento = new Evento();
					evento.setIdEvento(rs.getInt("idEvento"));
					evento.setNombre(rs.getString("nombre"));
					evento.setDetalles(rs.getString("detalles"));
					evento.setFechaPublicacion(rs.getDate("fechaPublicacion"));
					evento.setIdModeradorPublicacion(rs.getInt("idModeradorPublicacion"));
					evento.setFechaFinalizacion(rs.getDate("fechaFinalizacion"));
					evento.setIdModeradorFinalizacion(rs.getInt("idModeradorFinalizacion"));
					evento.setMotivoFinalizacion(MotivoFinalizacion.valueOf(rs.getString("motivoFinalizacion")));
					evento.setUbicacion(rs.getString("ubicacion"));

					eventos.add(evento);
				}
			}
		}

		return eventos;
	}

	/**
	 * Obtiene la lista de eventos que están pendientes de aprobación.
	 * 
	 * @return Lista de eventos pendientes de aprobación.
	 * @throws SQLException Si ocurre un error al obtener los eventos.
	 */
	public List<Evento> obtenerEventosPendientesAprobacion() throws SQLException {
		String sql = "SELECT * FROM eventos WHERE fechaPublicacion IS NULL";
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
	 * Obtiene todos los eventos publicados activos de la base de datos (sin finalizar)
	 *
	 * @return Una lista de objetos Evento que representan todos los eventos en la
	 *         base de datos.
	 * @throws SQLException Si ocurre un error al acceder a la base de datos.
	 */
	public List<Evento> obtenerTodosLosEventosActivos() throws SQLException {
		List<Evento> eventos = new ArrayList<>();
		String sql = "SELECT * FROM eventos where fechafinalizacion is  null and fechapublicacion is not null  order by id";
		PreparedStatement stmt = con.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
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
	 * Obtiene el último ID de evento almacenado en la base de datos.
	 *
	 * @return El último ID de evento almacenado.
	 * @throws SQLException si ocurre algún error al interactuar con la base de
	 *                      datos.
	 */
	public int obtenerUltimoIdEvento() throws SQLException {
		String sql = "SELECT MAX(idEvento) FROM eventos";
		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			return rs.getInt(1);
		} else {
			throw new SQLException("No se encontró ningún evento en la base de datos.");
		}
	}
}
