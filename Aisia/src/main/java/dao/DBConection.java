package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Clase utilitaria para la gestión de conexiones a la base de datos.
 * 
 * Esta clase proporciona métodos estáticos para obtener y cerrar conexiones a
 * la base de datos. Utiliza el patrón Singleton para garantizar que solo exista
 * una instancia de la conexión a la base de datos en toda la aplicación.
 * 
 * @author Maitane Ibañez Irazabal
 * @version 1.0
 */
public class DBConection {

	/**
	 * La instancia única de la conexión a la base de datos. Se inicializa como
	 * `null`.
	 */
	public static Connection instance = null;

	/**
	 * URL de conexión a la base de datos MySQL.
	 */
	public static final String JDBC_URL = "jdbc:mysql://localhost:3306/aisialdia";

	/**
	 * Constructor privado para evitar la instanciación directa de la clase.
	 * 
	 * El constructor es privado para forzar el uso de los métodos estáticos
	 * `getConnection` y `closeConnection`.
	 */
	private DBConection() {
	}

	/**
	 * Obtiene una conexión a la base de datos MySQL.
	 * 
	 * Este método implementa el patrón Singleton para garantizar que solo se cree
	 * una instancia de la conexión. Si la conexión ya existe, la devuelve. Si no
	 * existe, crea una nueva conexión utilizando el controlador JDBC de MySQL y la
	 * almacena en la variable `instance`.
	 * 
	 * @return La conexión a la base de datos.
	 * @throws SQLException Si ocurre un error al establecer la conexión.
	 */
	public static Connection getConection() throws SQLException {
		if (instance == null) {
			Properties props = new Properties();
			props.put("user", "root");
			props.put("password", "");
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			instance = DriverManager.getConnection(JDBC_URL, props);
		}

		return instance;
	}

}
