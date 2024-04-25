package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import modelo.Evento;
import modelo.Evento.MotivoFinalizacion;

public class DaoFavorito {

	private Connection con = null;
	private static DaoFavorito instance = null;

	/**
	 * Clase de Acceso a Datos (DAO) para la gestión de favoritos de eventos en el
	 * sistema. Implementa el patrón Singleton y utiliza controladores mediante
	 * Servlets para la interacción con la base de datos.
	 * 
	 * @author Maitane Ibañez Irazabal
	 * @version 1.0
	 */
	public DaoFavorito() throws SQLException {
		con = DBConection.getConection();
	}

	/**
	 * Este metodo es el que se utiliza para aplicar el patron SINGLETON  
	 * 
	 * @return
	 * @throws SQLException
	 */
	public static DaoFavorito getInstance() throws SQLException {
		if (instance == null) {
			instance = new DaoFavorito();
		}
		return instance;
	}

	/**
	 * Agrega un evento a la lista de favoritos de un usuario.
	 *
	 * @param idEvento  El identificador del evento a agregar.
	 * @param idUsuario El identificador del usuario al que se agrega el favorito.
	 * @throws SQLException Si ocurre un error al agregar el favorito.
	 */
	public void agregarFavoritoEvento(int idEvento, HttpServletRequest request) throws SQLException {
		String sql = "INSERT INTO favoritos (idEvento, idUsuario) VALUES (?, ?)";
		PreparedStatement ps = con.prepareStatement(sql);
		int idUsuarioActual = DaoUsuario.obtenerIdUsuarioActual(request);

		ps = con.prepareStatement(sql);
		ps.setInt(1, idEvento);
		ps.setInt(2, idUsuarioActual);
		ps.executeUpdate();

	}

	/**
	 * Elimina un evento de la lista de favoritos de un usuario.
	 *
	 * @param idEvento  El identificador del evento a eliminar.
	 * @param idUsuario El identificador del usuario al que se elimina el favorito.
	 * @throws SQLException Si ocurre un error al eliminar el favorito.
	 */
	public void eliminarFavoritoEvento(int idEvento, HttpServletRequest request) throws SQLException {
		String sql = "DELETE FROM favoritos WHERE idEvento = ? AND idUsuario = ?";
		PreparedStatement ps = con.prepareStatement(sql);
		int idUsuarioActual = DaoUsuario.obtenerIdUsuarioActual(request);

		ps = con.prepareStatement(sql);
		ps.setInt(1, idEvento);
		ps.setInt(2, idUsuarioActual);
		ps.executeUpdate();

	}

	/**
	 * Verifica si un evento es favorito de un usuario.
	 *
	 * @param idEvento  El identificador del evento a verificar.
	 * @param idUsuario El identificador del usuario.
	 * @return `true` si el evento es favorito del usuario, `false` en caso
	 *         contrario.
	 * @throws SQLException Si ocurre un error al verificar el favorito.
	 */
	public boolean verificarEventoFavoritoUsuario(int idEvento, HttpServletRequest request) throws SQLException {
		String sql = "SELECT COUNT(*) FROM favoritos WHERE idEvento = ? AND idUsuario = ?";
		PreparedStatement ps = con.prepareStatement(sql);
		int idUsuarioActual = DaoUsuario.obtenerIdUsuarioActual(request);
		ResultSet rs = null;
		int count = 0;

		ps = con.prepareStatement(sql);
		ps.setInt(1, idEvento);
		ps.setInt(2, idUsuarioActual);
		rs = ps.executeQuery();

		if (rs.next()) {
			count = rs.getInt(1);
		}

		return count > 0;
	}

	/**
	 * Obtiene una lista de eventos favoritos de un usuario específico.
	 *
	 * @param idUsuario El identificador del usuario del que se desean obtener los
	 *                  eventos favoritos (tipo `int`).
	 * @return Una lista de objetos `Evento` que representan los eventos favoritos
	 *         del usuario. La lista puede estar vacía si no hay favoritos.
	 * @throws Exception Si ocurre un error al acceder a la base de datos o procesar
	 *                   los resultados.
	 * 
	 *                   **Ejemplo de uso:** ```java // En un controlador Servlet:
	 * 
	 *                   DaoFavorito daoFavorito = DaoFavorito.getInstance(); int
	 *                   idUsuario = 10; // ID del usuario
	 * 
	 *                   List<Evento> eventosFavoritos =
	 *                   daoFavorito.obtenerEventosFavoritosUsuario(idUsuario);
	 * 
	 *                   if (eventosFavoritos.isEmpty()) { // No hay eventos
	 *                   favoritos para este usuario } else { // Procesar la lista
	 *                   de eventos favoritos for (Evento evento : eventosFavoritos)
	 *                   { System.out.println("Evento Favorito: " +
	 *                   evento.getNombre()); } } ```
	 */
	public List<Evento> obtenerEventosFavoritosUsuario(int idUsuario) throws Exception {

		String sql = "SELECT e.* FROM Evento e " // Consulta SQL para obtener eventos favoritos
				+ "JOIN Favorito f ON e.idEvento = f.idEvento " + "JOIN Usuario u ON f.idUsuario = u.idUsuario "
				+ "WHERE u.idUsuario = ?"; // WHERE para filtrar por idUsuario

		PreparedStatement ps = con.prepareStatement(sql);

		ResultSet rs = null; // Conjunto de resultados de la consulta
		List<Evento> eventosFavoritos = new ArrayList<>(); // Lista para almacenar eventos favoritos

		ps = con.prepareStatement(sql); // Preparar la consulta SQL
		ps.setInt(1, idUsuario); // Asignar el idUsuario al parámetro 1
		rs = ps.executeQuery(); // Ejecutar la consulta y obtener resultados

		while (rs.next()) { // Recorrer cada fila del conjunto de resultados
			// Mapear datos del resultset a un objeto Evento
			Evento evento = new Evento();
			evento.setIdEvento(rs.getInt("idEvento"));
			evento.setNombre(rs.getString("nombre"));
			evento.setDetalles(rs.getString("detalles"));
			evento.setIdUsuarioCreador(rs.getInt("idUsuariocreador"));
			evento.setFechaAprobacion(rs.getDate("fechaAprobacion"));
			evento.setIdModeradorAprobacion(rs.getInt("idModeradorAprobacion"));
			evento.setFechaPublicacion(rs.getDate("fechaPublicacion"));
			evento.setIdModeradorPublicacion(rs.getInt("idModeradorPublicacion"));
			evento.setFechaFinalizacion(rs.getDate("fechaFinalizacion"));
			evento.setIdModeradorFinalizacion(rs.getInt("idModeradorFinalizacion"));

			// Control de que no sea null el motivoFinalización
			String motivoFinalizacionString = rs.getString("motivoFinalizacion");
			if (motivoFinalizacionString != null) {
				try {
					MotivoFinalizacion motivoFinalizacion = MotivoFinalizacion.valueOf(motivoFinalizacionString);
					evento.setMotivoFinalizacion(motivoFinalizacion);
				} catch (IllegalArgumentException e) {
					System.err.println("Error al convertir motivoFinalizacion: " + motivoFinalizacionString);
				}
			} else {
				evento.setMotivoFinalizacion(null);
			}

			evento.setUbicacion(rs.getString("ubicacion"));

			eventosFavoritos.add(evento); // Agregar evento a la lista de favoritos
		}

		return eventosFavoritos; // Retornar la lista de eventos favoritos
	}
}
