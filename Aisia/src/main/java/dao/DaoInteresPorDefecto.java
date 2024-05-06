package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.InteresPorDefecto;

public class DaoInteresPorDefecto {

    private Connection con = null;
    private static DaoInteresPorDefecto instance = null;

    /**
     * Clase de Acceso a Datos (DAO) para la gestión de intereses por defecto en el sistema.
     * Implementa el patrón Singleton y utiliza controladores mediante Servlets para la interacción con la base de datos.
     *
     * @author Maitane Ibañez Irazabal
     * @version 1.0
     */
    public DaoInteresPorDefecto() throws SQLException {
        con = DBConection.getConection();
    }

    /**
     * Este metodo es el que utilizo para implementar el patron singleton
     *
     * @return
     * @throws SQLException
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
}

