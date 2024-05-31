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
import modelo.Evento.motivoFinalizacion;

/**
 * Clase de Acceso a Datos (DAO) para la gestión de eventos en el sistema.
 * Implementa el patrón Singleton y utiliza controladores mediante Servlets para
 * la interacción con la base de datos.
 * 
 * <p>Esta clase proporciona métodos para acceder y manipular datos relacionados con eventos en la base de datos.</p>
 * 
 * @author Maitane Ibañez Irazabal
 * @version 1.8
 */
public class DaoEvento {

    private Connection con = null;
    private static DaoEvento instance = null;

    /**
     * Constructor privado para aplicar el patrón Singleton.
     * 
     * @throws SQLException Si ocurre un error al establecer la conexión con la base de datos.
     */
    public DaoEvento() throws SQLException {
        con = DBConection.getConection();
    }


	/**
	 * Este metodo es el que se utiliza para aplicar el patron SINGLETON  
	 * 
	 * @return La instancia única de DaoEvento.
	 * @throws SQLException Si ocurre un error al crear la instancia de DaoEvento.
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
	 * @param fechaUltimaModificacion Fecha de la ultima modificación del evento
	 * @throws SQLException Si ocurre un error al crear el evento o las
	 *                      vinculaciones en la base de datos
	 * @return El ID del evento creado.
	 */
	public int crearEvento(Evento evento, List<Actividad> actividades, Timestamp fechaUltimaModificacion)
			throws SQLException {
		String sqlEvento = "INSERT INTO eventos (nombre, detalles, idUsuariocreador, fechaUltimaModificacion, ubicacion,fechaEvento,fechaCreacion,MotivoFinalizacion) VALUES (?,?,?,?,?,?,?,?)";
		PreparedStatement psEvento = con.prepareStatement(sqlEvento, Statement.RETURN_GENERATED_KEYS);
		psEvento.setString(1, evento.getNombre());
		psEvento.setString(2, evento.getDetalles());
		psEvento.setInt(3, evento.getIdUsuarioCreador());
		psEvento.setTimestamp(4, fechaUltimaModificacion);
		psEvento.setString(5, evento.getUbicacion());
		psEvento.setDate(6, evento.getFechaEvento());
		psEvento.setDate(7, evento.getFechaCreacion());
		psEvento.setString(8, null);
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
	 * Elimina un evento junto con sus favoritos y clasificaciones asociadas.
	 * Si se proporciona un idUsuario mayor que 0, elimina todos los eventos asociados a ese usuario. función recursiva.
	 *
	 * @param evento    El evento a eliminar.
	 * @param idUsuario El ID del usuario cuyos eventos se eliminarán (si es mayor que 0).
	 * @throws SQLException Si ocurre algún error al interactuar con la base de datos.
	 */
	public void eliminarEvento(Evento evento, int idUsuario) throws SQLException {
	    if (idUsuario > 0) {
	    	//System.out.println("entro por eliminarEV x idUsuario:"+idUsuario);
	        // Eliminar todos los eventos del usuario
	        String sqlEventosUsuario = "SELECT idEvento FROM eventos WHERE idUsuariocreador = ?";
	        PreparedStatement psEventosUsuario = con.prepareStatement(sqlEventosUsuario);
	        psEventosUsuario.setInt(1, idUsuario);
	        ResultSet rsEventosUsuario = psEventosUsuario.executeQuery();
	        
	        while (rsEventosUsuario.next()) {
	            int idEventoUsuario = rsEventosUsuario.getInt("idEvento");
	           // System.out.println("Eliminando evento con ID: " + idEventoUsuario);
	            eliminarEvento(new Evento(idEventoUsuario), 0); // Llama a sí misma sin el idUsuario :recursiva
	        }
	        
	        rsEventosUsuario.close();
	        psEventosUsuario.close();
	    } else {
	    	
	        // Eliminar los favoritos asociados al evento
	        String sqlFavoritos = "DELETE FROM gestionFavoritos WHERE idEvento = ?";
	        PreparedStatement psFavoritos = con.prepareStatement(sqlFavoritos);
	        psFavoritos.setInt(1, evento.getIdEvento());
	        psFavoritos.executeUpdate();
	        //System.out.println("Favoritos asociados al evento con ID " + evento.getIdEvento() + " eliminados.");

	        // Eliminar la clasificación del evento
	        String sqlClasificacion = "DELETE FROM clasificacionEventos WHERE idEvento = ?";
	        PreparedStatement psClasificacion = con.prepareStatement(sqlClasificacion);
	        psClasificacion.setInt(1, evento.getIdEvento());
	        psClasificacion.executeUpdate();
	        //System.out.println("Clasificación del evento con ID " + evento.getIdEvento() + " eliminada.");

	        // Eliminar el evento en sí
	        String sqlEventos = "DELETE FROM eventos WHERE idEvento = ?";
	        PreparedStatement psEventos = con.prepareStatement(sqlEventos);
	        psEventos.setInt(1, evento.getIdEvento());
	        psEventos.executeUpdate();
	        psEventos.close();
	        //System.out.println("Evento con ID " + evento.getIdEvento() + " eliminado.");
	    }
	}



	/**
	 * Publica un evento aprobado para su publicación.
	 * 
	 * @param idEvento El identificador del evento a publicar.
	 * @param request  La solicitud HTTP asociada a la publicación del evento.
	 * @throws SQLException Si ocurre un error al publicar el evento en la base de datos.
	 */
	public void publicarEvento(int idEvento, HttpServletRequest request) throws SQLException {

		String sql = "UPDATE eventos SET fechaPublicacion = current_date,fechaUltimaModificacion=current_date, "
				+ "idModeradorPublicacion = ? WHERE idEvento = ?";
		PreparedStatement ps = con.prepareStatement(sql);
		int idUsuarioActual = DaoUsuario.getInstance().obtenerIdUsuarioActual(request);
		ps.setInt(1, idUsuarioActual);
		ps.setInt(2, idEvento);
		ps.executeUpdate();
		ps.close();
	}

	/**
	 * Rechaza un evento pendiente de aprobación.
	 * 
	 * @param idEvento      El identificador del evento a rechazar.
	 * @param request       Objeto HttpServletRequest para obtener el ID del usuario
	 *                      actual.
	 * @throws SQLException Si ocurre un error al rechazar el evento en la BD.
	 */
	public void rechazarEvento(int idEvento, HttpServletRequest request) throws SQLException {
		int idUsuarioActual = DaoUsuario.getInstance().obtenerIdUsuarioActual(request);

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
		int idUsuarioActual = 0;
		try {
			idUsuarioActual = DaoUsuario.getInstance().obtenerIdUsuarioActual(request);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	 * @param evento    El objeto evento que se desea finalizar.
	 * @throws SQLException si ocurre algún error al interactuar con la base de
	 *                      datos.
	 */
	public void finalizarPublicacionEvento(Evento evento) throws SQLException {
		String sql = "UPDATE eventos SET fechaFinalizacion = current_date, "
				+ "fechaUltimaModificacion = current_date, idModeradorFinalizacion = ?, motivoFinalizacion=? WHERE idEvento = ?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, evento.getIdModeradorFinalizacion());
		ps.setString(2, evento.getmotivoFinalizacion().name());
		ps.setInt(3, evento.getIdEvento());

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
	 * Obtiene una lista de eventos rechazados dentro del rango de fechas
	 * especificado. Si no se proporcionan fechas, se devuelven los últimos 10
	 * eventos rechazados.
	 *
	 * @param fechaInicio Fecha de inicio del rango de búsqueda.
	 * @param fechaFin    Fecha de fin del rango de búsqueda.
	 * @return Una lista de eventos rechazados dentro del rango de fechas
	 *         especificado o los últimos 10 eventos rechazados si no se
	 *         proporcionan fechas.
	 * @throws SQLException Si ocurre algún error al interactuar con la base de
	 *                      datos.
	 */
	public List<Evento> obtenerEventosRechazados(Date fechaInicio, Date fechaFin) throws SQLException {
		List<Evento> eventos = new ArrayList<>();
		String sql = "";

		if (fechaInicio != null && fechaFin != null) {
			sql = "SELECT * FROM eventos WHERE fechaFinalizacion BETWEEN ? AND ? AND motivoFinalizacion = 'Rechazado'";
		} else {
			sql = "SELECT * FROM eventos WHERE motivoFinalizacion = 'Rechazado' ORDER BY fechaUltimaModificacion DESC LIMIT 10";
		}

		try (PreparedStatement pstmt = con.prepareStatement(sql)) {
			if (fechaInicio != null && fechaFin != null) {
				pstmt.setDate(1, fechaInicio);
				pstmt.setDate(2, fechaFin);
			}

			try (ResultSet rs = pstmt.executeQuery()) {
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

	// ---------------------------------------------------------------------------------
	// VOLCADOS JSON
	// ---------------------------------------------------------------------------------

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
	 * Genera un JSON con los eventos rechazados dentro del rango de fechas
	 * especificado. Si no se proporcionan fechas, se devuelven los últimos 10
	 * eventos rechazados.
	 *
	 * @param FechaD Fecha de inicio del rango de búsqueda.
	 * @param FechaH    Fecha de fin del rango de búsqueda.
	 * @return Una cadena JSON que contiene los eventos rechazados dentro del rango
	 *         de fechas especificado o los últimos 10 eventos rechazados si no se
	 *         proporcionan fechas.
	 * @throws SQLException Si ocurre algún error al interactuar con la base de
	 *                      datos.
	 */

	public String listarJsonRechazados(Date FechaD, Date FechaH) throws SQLException {
		String json = "";
		Gson gson = new Gson();
		json = gson.toJson(this.obtenerEventosRechazados(FechaD, FechaH));
		return json;
	}
}
