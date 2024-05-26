package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import dao.DaoUsuario.GsonHelper;
import modelo.Evento;
import modelo.EventoConActividad;

public class DaoEventoConActividad {

	private Connection con = null;
	private static DaoEventoConActividad instance = null;

	/**
	 * Clase de Acceso a Datos (DAO) para la gestión de actividades de eventos en el
	 * sistema. Implementa el patrón Singleton y utiliza controladores mediante
	 * Servlets para la interacción con la base de datos.
	 * 
	 * @author Maitane Ibañez Irazabal
	 * @version 1.0
	 */
	public DaoEventoConActividad() throws SQLException {
		con = DBConection.getConection();
	}

	/**
	 * Este metodo es el que utilizo para implementar el patron singleton
	 * 
	 * @return
	 * @throws SQLException
	 */
	public static DaoEventoConActividad getInstance() throws SQLException {
		if (instance == null) {
			instance = new DaoEventoConActividad();
		}
		return instance;

	}

	/**
	 * Obtiene un evento con la información de su actividad relacionada, basado en
	 * el identificador del evento.
	 * 
	 * @param idEvento Identificador del evento del que se desea obtener la
	 *                 información.
	 * @return Un objeto {@link EventoConActividad} que contiene los detalles del
	 *         evento y su actividad asociada, o null si no se encuentra ningún
	 *         evento relacionado.
	 * @throws SQLException Si ocurre un error al acceder a la base de datos.
	 */

	public EventoConActividad obtenerEventoConActividad(int idEvento) throws SQLException {
		EventoConActividad eventoConActividad = null;
		String sql = "SELECT e.idEvento, e.nombre, e.detalles, e.ubicacion, a.tipoActividad, a.fotoActividad ,e.fechaEvento "
				+ "FROM eventos e " + "INNER JOIN clasificacionEventos ce ON e.idEvento = ce.idEvento "
				+ "INNER JOIN actividades a ON ce.idActividad = a.idActividad " + "WHERE e.idEvento = ?";
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, idEvento);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					int id = rs.getInt("idEvento");
					String nombre = rs.getString("nombre");
					String detalles = rs.getString("detalles");
					String ubicacion = rs.getString("ubicacion");
					String tipoActividad = rs.getString("tipoActividad");
					String fotoActividad = rs.getString("fotoActividad");
					Date fechaEvento = rs.getDate("fechaEvento");
					eventoConActividad = new EventoConActividad(id, nombre, detalles, ubicacion, tipoActividad,
							fotoActividad, fechaEvento);
				}
			}
		}

		return eventoConActividad;
	}

	/**
	 * Obtiene una lista de los últimos eventos publicados no finalizados, ordenados
	 * por fecha de publicación descendente.
	 * 
	 * @param numEventos Número máximo de eventos a recuperar. Si es null o
	 *                   negativo, se recuperarán todos los eventos.
	 * @param actividad  Nombre de la actividad para filtrar.
	 * @param nombre     Nombre del evento para filtrar.
	 * @param ubicacion  Ubicación del evento para filtrar.
	 * @param fecha      Fecha del evento para filtrar.
	 * @return Una lista de {@link EventoConActividad} que contiene los eventos
	 *         recientes y sus actividades asociadas, o una lista vacía si no se
	 *         encuentran eventos.
	 * @throws SQLException Si ocurre un error al acceder a la base de datos.
	 */
	public List<EventoConActividad> obtenerUltimosEventos(int numEventos, String actividad, String nombre,
			String ubicacion, Date fecha) throws SQLException {
		List<EventoConActividad> eventosConActividad = new ArrayList<>();

		// Preparar la consulta SQL para obtener los últimos eventos que no estén ya
		// finalizados
		String sql = "SELECT e.idEvento, e.nombre, e.detalles, e.fechaPublicacion, e.idModeradorPublicacion, e.fechaFinalizacion, e.idModeradorFinalizacion, e.motivoFinalizacion, e.ubicacion, e.fechaEvento,a.tipoActividad, a.fotoActividad "
				+ "FROM eventos e " + "INNER JOIN clasificacionEventos ce ON e.idEvento = ce.idEvento "
				+ "INNER JOIN actividades a ON ce.idActividad = a.idActividad "
				+ "WHERE e.fechaFinalizacion is null and e.fechapublicacion is not null ";

		// Agregar cláusulas WHERE según sea necesario
		if (actividad != null && !actividad.isEmpty()) {
			sql += " AND a.tipoActividad LIKE ?";
		}
		if (nombre != null && !nombre.isEmpty()) {
			sql += " AND e.nombre LIKE ?";
		}
		if (ubicacion != null && !ubicacion.isEmpty()) {
			sql += " AND e.ubicacion LIKE ?";
		}
		if (fecha != null) {
			sql += " AND DATE(e.fechaEvento) = ?";
		}

		sql += " ORDER BY e.fechaPublicacion DESC";

		// Si numEventos es mayor que 0 o igual a -1, limitar la consulta
		if (numEventos != -1) {
			sql += " LIMIT ?";
		}
		//System.out.println(sql);
		PreparedStatement ps = con.prepareStatement(sql);

		int paramIndex = 1;

		// Agregar parámetros a la consulta según sea necesario
		if (actividad != null && !actividad.isEmpty()) {
			ps.setString(paramIndex++, "%" + actividad + "%");
		}
		if (nombre != null && !nombre.isEmpty()) {
			ps.setString(paramIndex++, "%" + nombre + "%");
		}
		if (ubicacion != null && !ubicacion.isEmpty()) {
			ps.setString(paramIndex++, "%" + ubicacion + "%");
		}
		if (fecha != null) {
			ps.setDate(paramIndex++, fecha);
		}
		// Si numEventos es mayor que 0 o igual a -1, establecer el límite
		if (numEventos != -1) {
			ps.setInt(paramIndex++, numEventos);
		}

		// Ejecutar la consulta y procesar los resultados
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			int idEvento = rs.getInt("idEvento");
			String nombreEvento = rs.getString("nombre");
			String detalles = rs.getString("detalles");
			String ubicacionEvento = rs.getString("ubicacion");
			String tipoActividad = rs.getString("tipoActividad");
			String fotoActividad = rs.getString("fotoActividad");
			Date fechaEvento = rs.getDate("fechaEvento");

			EventoConActividad eventoConActividad = new EventoConActividad(idEvento, nombreEvento, detalles,
					ubicacionEvento, tipoActividad, fotoActividad, fechaEvento);

			eventosConActividad.add(eventoConActividad);
		}

		// Cerrar recursos
		rs.close();
		ps.close();

		return eventosConActividad;
	}

	// ---------------------------------------------------------------------------------
	// VOLCADOS JSON
	// ---------------------------------------------------------------------------------

	/**
	 * Genera una representación JSON de Obtiene una lista de los últimos eventos
	 * publicados no finalizados, ordenados por fecha de publicación descendente.
	 * 
	 * @param numEventos Filtra la cantidad de num eventos para listar
	 * @return Una cadena JSON que representa los últimos eventos publicados no
	 *         finalizados, ordenados por fecha de publicación descendente.
	 * @throws SQLException Si ocurre un error al acceder a la base de datos.
	 */
	public String listarJsonUltimosEventos(int numEventos, String actividad, String nombre, String ubicacion,
			Date fecha) throws SQLException {
		String json = "";
		Gson gson = new Gson();
		json = gson.toJson(this.obtenerUltimosEventos(numEventos, actividad, nombre, ubicacion, fecha));
		return json;
	}

	/**
	 * Genera un objeto JSON que representa un evento con actividad a partir de su
	 * ID.
	 *
	 * @param idEvento El ID del evento con actividad.
	 * @return Una cadena JSON que representa el evento con actividad.
	 * @throws SQLException Si ocurre un error al obtener el evento con actividad de
	 *                      la base de datos.
	 */
	public String listarJsonEventosConActividad(int idEvento) throws SQLException {
		Gson gson = GsonHelper.getGson();
		return gson.toJson(this.obtenerEventoConActividad(idEvento));
	}

	/**
	 * Obtiene el identificador de la actividad por su tipo.
	 *
	 * @param tipoActividad El tipo de actividad para buscar su identificador.
	 * @return El identificador de la actividad o -1 si no se encuentra ninguna
	 *         actividad con el tipo especificado.
	 * @throws SQLException Si ocurre un error al acceder a la base de datos.
	 */

	public int obtenerIdActividadPorTipo(String tipoActividad) throws SQLException {
		int idActividad = -1; // Valor predeterminado si no se encuentra ninguna actividad

		String sql = "SELECT idActividad FROM actividades WHERE tipoActividad=?";
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, tipoActividad);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					idActividad = rs.getInt("idActividad");
				}
			}
		}

		return idActividad;
	}

	/**
	 * Edita un evento con actividad asociada en la base de datos.
	 *
	 * @param evento        El evento con los nuevos datos a editar.
	 * @param tipoActividad El tipo de actividad asociada al evento.
	 * @throws SQLException Si ocurre un error al acceder a la base de datos.
	 */

	public void editarEvento(EventoConActividad evento, String tipoActividad) throws SQLException {
		// Actualizar datos en la tabla eventos
		String sqlEvento = "UPDATE eventos SET nombre=?, detalles=?, fechaEvento=?, ubicacion=? WHERE idEvento=?";
		try (PreparedStatement psEvento = con.prepareStatement(sqlEvento)) {
			psEvento.setString(1, evento.getNombre());
			psEvento.setString(2, evento.getDetalles());
			psEvento.setDate(3, evento.getFechaEvento());
			psEvento.setString(4, evento.getUbicacion());
			psEvento.setInt(5, evento.getIdEvento());
			psEvento.executeUpdate();
		}

		// Buscar el ID de la actividad asociada al evento
		int idActividad = obtenerIdActividadPorTipo(tipoActividad);
		System.out.println("ID de actividad obtenido: " + idActividad);

		// Verificar que el ID de actividad sea válido
		if (idActividad == 0) {
			System.out.println("Error: el ID de actividad no se ha podido obtener o es inválido.");
			throw new SQLException("El ID de actividad no es válido.");
		}

		// Verificar si ya existe una entrada en la tabla clasificacionEventos para este
		// evento
		String sqlBuscarClasificacion = "SELECT * FROM clasificacionEventos WHERE idEvento=?";
		try (PreparedStatement psBuscarClasificacion = con.prepareStatement(sqlBuscarClasificacion)) {
			psBuscarClasificacion.setInt(1, evento.getIdEvento());
			try (ResultSet rs = psBuscarClasificacion.executeQuery()) {
				if (rs.next()) {
					// Si ya existe una entrada, actualizarla
					String sqlActualizarClasificacion = "UPDATE clasificacionEventos SET idActividad=? WHERE idEvento=?";
					try (PreparedStatement psActualizarClasificacion = con
							.prepareStatement(sqlActualizarClasificacion)) {
						psActualizarClasificacion.setInt(1, idActividad);
						psActualizarClasificacion.setInt(2, evento.getIdEvento());
						psActualizarClasificacion.executeUpdate();
						System.out.println("Entrada en clasificacionEventos actualizada correctamente.");
					}
				} else {
					// Si no existe una entrada, insertar una nueva
					String sqlInsertarClasificacion = "INSERT INTO clasificacionEventos (idActividad, idEvento) VALUES (?, ?)";
					try (PreparedStatement psInsertarClasificacion = con.prepareStatement(sqlInsertarClasificacion)) {
						psInsertarClasificacion.setInt(1, idActividad);
						psInsertarClasificacion.setInt(2, evento.getIdEvento());
						psInsertarClasificacion.executeUpdate();
						System.out.println("Nueva entrada en clasificacionEventos insertada correctamente.");
					}
				}
			}
		}
	}

}