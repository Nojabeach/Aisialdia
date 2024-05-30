package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import modelo.InteresPorDefecto;
/**
 * Clase de Acceso a Datos (DAO) para la gestión de intereses por defecto en el sistema.
 * Implementa el patrón Singleton y utiliza controladores mediante Servlets para la interacción con la base de datos.
 *
 * @author Maitane Ibañez Irazabal
 * @version 1.0
 */
public class DaoInteresPorDefecto {

    private Connection con = null;
    private static DaoInteresPorDefecto instance = null;

    /**
     * Constructor de la clase DaoInteresPorDefecto. Establece una conexión con la base de datos al inicializar un objeto DaoInteresPorDefecto.
     *
     * @throws SQLException Si ocurre un error al establecer la conexión con la base de datos.
     */
    public DaoInteresPorDefecto() throws SQLException {
        con = DBConection.getConection();
    }

    /**
     * Método utilizado para implementar el patrón Singleton y obtener una instancia única de DaoInteresPorDefecto.
     * Si no existe una instancia previamente creada, se crea una nueva; de lo contrario, se devuelve la instancia existente.
     *
     * @return La instancia única de DaoInteresPorDefecto.
     * @throws SQLException Si ocurre un error al crear la instancia de DaoInteresPorDefecto.
     */
    public static DaoInteresPorDefecto getInstance() throws SQLException {
        if (instance == null) {
            instance = new DaoInteresPorDefecto();
        }
        return instance;
    }

    /**
     * Inserta una lista de intereses por defecto en la base de datos.
     *
     * @param intereses la lista de intereses por defecto a insertar
     * @throws SQLException si ocurre algún error de SQL
     */
    public void insertarInteresesPorDefecto(List<InteresPorDefecto> intereses) throws SQLException {
        String sql = "INSERT INTO interesesPorDefecto (nombreInteres) VALUES (?)";
        PreparedStatement pstmt = null;

        try {
            pstmt = con.prepareStatement(sql);

            for (InteresPorDefecto interes : intereses) {
                pstmt.setString(1, interes.getNombreInteres());
                pstmt.executeUpdate();
            }

        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
        }
    }
    
    /**
     * Elimina un interés por defecto de la base de datos dado su identificador.
     * 
     * @param idInteres El identificador del interés por defecto a eliminar.
     * @throws SQLException Si ocurre algún error de SQL al intentar eliminar el interés por defecto.
     */
    public void eliminarInteresPorDefecto(int idInteres) throws SQLException {
        String sql = "DELETE FROM interesesPorDefecto WHERE idInteres = ?";
        PreparedStatement pstmt = null;

        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, idInteres);
            pstmt.executeUpdate();
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
        }
    }

    /**
     * Recupera una lista de nombres de todos los intereses por defecto de la base de datos.
     *
     * @return una lista de nombres de interés
     * @throws SQLException si ocurre algún error de SQL
     */
    public List<String> listarNombresInteresesPorDefecto() throws SQLException {
        List<String> nombresIntereses = new ArrayList<>();
        String sql = "SELECT nombreInteres FROM interesesPorDefecto ORDER BY nombreInteres";

        try (PreparedStatement pstmt = con.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            // Iterar sobre el conjunto de resultados y obtener los nombres de interés
            while (rs.next()) {
                String nombreInteres = rs.getString("nombreInteres");
                nombresIntereses.add(nombreInteres);
            }
        }

        // Devolver la lista de nombres de interés
        return nombresIntereses;
    }
    
	// ---------------------------------------------------------------------------------
	// VOLCADOS JSON
	// ---------------------------------------------------------------------------------

    /**
     * Genera un objeto JSON que representa la lista de nombres de intereses por defecto.
     *
     * @return Una cadena JSON que contiene los nombres de intereses por defecto.
     * @throws SQLException Si ocurre un error al acceder a la base de datos.
     */
    public String listarJsonIntereses() throws SQLException {

		String json = "";
		Gson gson = new Gson();

		json = gson.toJson(this.listarNombresInteresesPorDefecto());

		return json;

	}
}

