/**
 * El paquete pruebas contiene clases y métodos para realizar pruebas unitarias
 * sobre las distintas funcionalidades de la aplicación. En este paquete se emplea
 * la biblioteca JUnit 5 para crear, ejecutar y organizar las pruebas.
 *
 * La clase principal de prueba es {@link pruebas.TestActividad}, que verifica el correcto
 * funcionamiento de la clase {@link dao.DaoActividad}.
 *
 * <h2>Clases de prueba incluidas:</h2>
 * <ul>
 *   <li>{@link pruebas.TestActividad} - Prueba las operaciones CRUD en la clase {@link dao.DaoActividad}.</li>
 * </ul>
 *
 * <h2>Metodología de pruebas:</h2>
 * <ul>
 *   <li>Las pruebas se organizan y ejecutan en un orden específico utilizando la
 *       anotación {@link org.junit.jupiter.api.TestMethodOrder}.</li>
 *   <li>La inicialización de las pruebas se maneja mediante el método {@link org.junit.jupiter.api.BeforeEach}.</li>
 *   <li>Las pruebas verifican la creación, obtención, edición y eliminación de actividades
 *       en la base de datos.</li>
 * </ul>
 *
 * <h2>Ejemplo de métodos de prueba:</h2>
 * <ul>
 *   <li>{@link pruebas.TestActividad#testObtenerTodasLasActividades} - Verifica la obtención de todas las actividades.</li>
 *   <li>{@link pruebas.TestActividad#testCrearActividad} - Verifica la creación de una nueva actividad.</li>
 *   <li>{@link pruebas.TestActividad#testEditarActividad} - Verifica la edición de una actividad existente.</li>
 *   <li>{@link pruebas.TestActividad#testEliminarActividad} - Verifica la eliminación de una actividad existente.</li>
 * </ul>
 * 
 * Este paquete es crucial para asegurar la integridad y funcionalidad de la clase
 * {@link dao.DaoActividad}, garantizando que las operaciones de acceso a datos se realicen correctamente.
 * 
 * @version 1.0
 * @since 2024
 */
package pruebas;

