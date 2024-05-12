package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

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
		String sql = "INSERT INTO gestionfavoritos (idEvento, idUsuario, fechaCreacionFavorito) " + "SELECT ?, ?, ? "
				+ "FROM dual " + "WHERE NOT EXISTS ( " + "    SELECT 1 " + "    FROM gestionfavoritos "
				+ "    WHERE idEvento = ? AND idUsuario = ? )";
		int idUsuarioActual = DaoUsuario.obtenerIdUsuarioActual(request);
		Date fechaCreacion = new Date(System.currentTimeMillis());

		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, idEvento);
			ps.setInt(2, idUsuarioActual);
			ps.setDate(3, fechaCreacion);
			ps.setInt(4, idEvento);
			ps.setInt(5, idUsuarioActual);

			ps.executeUpdate();
		}
	}

	/**
	 * Elimina un evento de la lista de favoritos de un usuario.
	 *
	 * @param idEvento  El identificador del evento a eliminar.
	 * @param idUsuario El identificador del usuario al que se elimina el favorito.
	 * @throws SQLException Si ocurre un error al eliminar el favorito.
	 */
	public void eliminarFavoritoEvento(int idEvento, HttpServletRequest request) throws SQLException {
		String sql = "DELETE FROM gestionfavoritos WHERE idEvento = ? AND idUsuario = ?";
		PreparedStatement ps = con.prepareStatement(sql);
		int idUsuarioActual = DaoUsuario.obtenerIdUsuarioActual(request);
		// System.out.println(idUsuarioActual);
		ps = con.prepareStatement(sql);
		ps.setInt(1, idEvento);
		ps.setInt(2, idUsuarioActual);
		ps.executeUpdate();

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
	public List<Evento> obtenerEventosFavoritosUsuario(int idUsuario) throws SQLException {

		String sql = "SELECT e.idEvento,e.nombre,e.fechaEvento,e.detalles,e.ubicacion FROM Eventos e "
				+ "JOIN gestionfavoritos f ON e.idEvento = f.idEvento JOIN usuarios u ON f.idUsuario = u.idUsuario  WHERE u.idUsuario  = ? ";

		PreparedStatement ps = con.prepareStatement(sql);
		// System.out.println(sql);

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
			evento.setFechaEvento(rs.getDate("fechaEvento"));
			evento.setUbicacion(rs.getString("ubicacion"));

			eventosFavoritos.add(evento); // Agregar evento a la lista de favoritos
		}

		return eventosFavoritos; // Retornar la lista de eventos favoritos
	}

	// ---------------------------------------------------------------------------------
	// VOLCADOS JSON
	// ---------------------------------------------------------------------------------

	/**
	 * Genera un objeto JSON que representa los eventos favoritos de un usuario.
	 *
	 * @param idUsuario ID del usuario.
	 * @return Una cadena JSON que representa los eventos favoritos del usuario.
	 * @throws SQLException Si ocurre un error al acceder a la base de datos.
	 */
	public String listarJsonFavoritosUsuario(int idUsuario) throws SQLException {
		String json = "";
		Gson gson = new Gson();
		json = gson.toJson(this.obtenerEventosFavoritosUsuario(idUsuario));
		return json;
	}
}
