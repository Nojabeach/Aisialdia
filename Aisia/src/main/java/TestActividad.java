import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;

import dao.DaoActividad;
import modelo.Actividad;

/**
 * Clase de prueba para la clase DaoActividad.
 * 
 * Esta clase contiene métodos de prueba para verificar el funcionamiento
 * correcto de la clase DaoActividad.
 * 
 * @author Maitane Ibañez Irazabal
 * @version 1.0
 */

@TestMethodOrder(OrderAnnotation.class)
class TestActividad {

	/**
	 * Instancia de la clase DaoActividad utilizada para realizar las pruebas.
	 */
	private DaoActividad dao;


	/**
	 * Método que se ejecuta antes de cada prueba para inicializar la instancia de
	 * DaoActividad.
	 * 
	 * @throws SQLException si ocurre un error al inicializar la instancia de
	 *                      DaoActividad.
	 */
	@BeforeEach
	public void setUp() throws SQLException {
		dao = DaoActividad.getInstance();
	}

	/**
	 * Prueba para obtener todas las actividades.
	 * 
	 * Verifica que se pueda obtener una lista de todas las actividades existentes
	 * en la base de datos.
	 * 
	 * @throws SQLException si ocurre un error al obtener las actividades.
	 */
	@Test
	@Order(1)
	public void testObtenerTodasLasActividades() throws SQLException {
		// Ejecutar el método para obtener todas las actividades
		List<Actividad> actividades = dao.obtenerTodasLasActividades();

		// Verificar que se obtiene una lista no nula
		assertNotNull(actividades);

		// Verificar que la lista no esté vacía
		assertFalse(actividades.isEmpty());

		// Verificar que todas las actividades tienen valores válidos
		for (Actividad actividad : actividades) {
			assertNotNull(actividad);
			assertNotNull(actividad.getIdActividad());
			assertNotNull(actividad.getTipoActividad());
			assertNotNull(actividad.getFotoActividad());
		}
	}

	/**
	 * Prueba para crear una nueva actividad.
	 * 
	 * Verifica que se pueda crear una nueva actividad correctamente y que se pueda
	 * recuperar desde la base de datos.
	 * 
	 * @throws SQLException si ocurre un error al crear la actividad.
	 */
	@Test
	@Order(2)
	public void testCrearActividad() throws SQLException {
		// Crear una nueva actividad
		Actividad actividad = new Actividad();
		actividad.setTipoActividad("Prueba de JUnit");
		actividad.setFotoActividad("fotoUnit.jpg");

		dao.crearActividad(actividad);

		// Verificar que la actividad se haya creado correctamente
		List<Actividad> actividades = dao.obtenerTodasLasActividades();
		assertNotNull(actividades);

		boolean actividadEncontrada = false;
		for (Actividad a : actividades) {
			if ("Prueba de JUnit".equals(a.getTipoActividad())) {
				actividadEncontrada = true;
				break;
			}
		}
		assertEquals(true, actividadEncontrada);

	}

	/**
	 * Prueba para editar una actividad existente.
	 * 
	 * Verifica que se pueda editar una actividad existente correctamente y que los
	 * cambios se reflejen en la base de datos.
	 * 
	 * @throws SQLException si ocurre un error al editar la actividad.
	 */
	@Test
	@Order(4)
	public void testEditarActividad() throws SQLException {

		// Obtener la actividad por su ID
		Actividad actividad = dao.obtenerUltimaActividad();
		assertNotNull(actividad, "No se pudo obtener la actividad para editar");

		// Modificar la actividad
		actividad.setTipoActividad("Actividad editada");

		// Ejecutar el método para editar la actividad
		dao.editarActividad(actividad);

		// Verificar que la actividad se haya editado correctamente
		Actividad actividadEditada = dao.obtenerUltimaActividad();
		assertEquals("Actividad editada", actividadEditada.getTipoActividad(),
				"La actividad no se editó correctamente");
	}

	/**
	 * Prueba para eliminar una actividad existente.
	 * 
	 * Verifica que se pueda eliminar una actividad existente correctamente y que no
	 * se pueda recuperar desde la base de datos.
	 * 
	 * @throws SQLException si ocurre un error al eliminar la actividad.
	 */
	@Test
	@Order(5)
	public void testEliminarActividad() throws SQLException {

		// Obtener la última actividad antes de eliminarla
		Actividad actividadAntesEliminar = dao.obtenerUltimaActividad();
		assertNotNull(actividadAntesEliminar, "No se pudo obtener la última actividad antes de eliminarla");

		int idActividadAntesEliminar = actividadAntesEliminar.getIdActividad();

		// Eliminar la actividad recién insertada
		dao.eliminarActividad(new Actividad(idActividadAntesEliminar));

		// Verificar que la actividad ha sido eliminada correctamente
		assertNull(dao.obtenerActividadPorId(idActividadAntesEliminar), "La actividad no se eliminó correctamente");

		// Verificar que la última actividad existente no sea la actividad eliminada
		assertNotEquals(idActividadAntesEliminar, dao.obtenerUltimaActividad().getIdActividad(),
				"La última actividad existente es la actividad eliminada");
	}

}
