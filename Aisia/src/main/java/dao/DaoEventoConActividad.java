package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
     * Obtiene un evento con la información de su actividad relacionada, basado en el
     * identificador del evento.
     * 
     * @param idEvento Identificador del evento del que se desea obtener la información.
     * @return Un objeto {@link EventoConActividad} que contiene los detalles del evento
     *         y su actividad asociada, o null si no se encuentra ningún evento
     *         relacionado.
     * @throws SQLException Si ocurre un error al acceder a la base de datos.
     */

    public EventoConActividad obtenerEventoConActividad(int idEvento) throws SQLException {
        EventoConActividad eventoConActividad = null;
        String sql="SELECT e.idEvento, e.nombre, e.detalles, e.ubicacion, a.tipoActividad, a.fotoActividad " +
                "FROM eventos e " +
                "INNER JOIN clasificacionEventos ce ON e.idEvento = ce.idEvento " +
                "INNER JOIN actividades a ON ce.idActividad = a.idActividad " +
                "WHERE e.idEvento = ?";
        try (PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setInt(1, idEvento);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("idEvento");
                    String nombre = resultSet.getString("nombre");
                    String detalles = resultSet.getString("detalles");
                    String ubicacion = resultSet.getString("ubicacion");
                    String tipoActividad = resultSet.getString("tipoActividad");
                    String fotoActividad = resultSet.getString("fotoActividad");

                    eventoConActividad = new EventoConActividad(id, nombre, detalles, ubicacion, tipoActividad, fotoActividad);
                }
            }
        }

        return eventoConActividad;
    }

    
    /**
     * Obtiene una lista de los últimos eventos publicados no finalizados, ordenados por fecha de
     * publicación descendente.
     * 
     * @param numEventos Número máximo de eventos a recuperar.
     * @return Una lista de {@link EventoConActividad} que contiene los eventos
     *         recientes y sus actividades asociadas, o una lista vacía si no se
     *         encuentran eventos.
     * @throws SQLException Si ocurre un error al acceder a la base de datos.
     */
    public List<EventoConActividad> obtenerUltimosEventos(int numEventos) throws SQLException {
        List<EventoConActividad> eventosConActividad = new ArrayList<>();

        // Preparar la consulta SQL para obtener los últimos eventos que no estén ya finalizados
        String sql = "SELECT e.idEvento, e.nombre, e.detalles, e.fechaPublicacion, e.idModeradorPublicacion, e.fechaFinalizacion, e.idModeradorFinalizacion, e.motivoFinalizacion, e.ubicacion, a.tipoActividad, a.fotoActividad " +
                     "FROM eventos e " +
                     "INNER JOIN clasificacionEventos ce ON e.idEvento = ce.idEvento " +
                     "INNER JOIN actividades a ON ce.idActividad = a.idActividad " +
                     "WHERE e.fechaFinalizacion is null"+
                     "ORDER BY e.fechaPublicacion DESC LIMIT ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, numEventos);

        // Ejecutar la consulta y procesar los resultados
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int idEvento = rs.getInt("idEvento");
            String nombre = rs.getString("nombre");
            String detalles = rs.getString("detalles");
            String ubicacion = rs.getString("ubicacion");
            String tipoActividad = rs.getString("tipoActividad");
            String fotoActividad = rs.getString("fotoActividad");


            EventoConActividad eventoConActividad = new EventoConActividad(idEvento, nombre, detalles, ubicacion, tipoActividad, fotoActividad);

            eventosConActividad.add(eventoConActividad);
        }

        // Cerrar recursos
        rs.close();
        ps.close();

        return eventosConActividad;
    }
}