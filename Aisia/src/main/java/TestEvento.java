import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import dao.DaoEvento;
import modelo.Evento;
import modelo.Evento.MotivoFinalizacion;

/**
 * Clase de prueba para la clase DaoEvento.
 * 
 * Esta clase contiene métodos de prueba para verificar el funcionamiento correcto de la clase DaoEvento.
 * 
 * @author Maitane Ibañez Irazabal
 * @version 1.0
 */
@TestMethodOrder(OrderAnnotation.class)
class TestEvento {

	private DaoEvento dao;


	/**
	 * Método que se ejecuta antes de cada prueba para inicializar la instancia de
	 * DaoEvento.
	 * 
	 * @throws SQLException si ocurre un error al inicializar la instancia de
	 *                      DaoEvento.
	 */
	@BeforeEach
	public void setUp() throws SQLException {
		dao = DaoEvento.getInstance();
	}


    @Test
    @Order(1)
    void testCrearEvento() throws SQLException {
        // Crear un nuevo evento
        Evento evento = new Evento();
        evento.setNombre("Evento de prueba");
        evento.setDetalles("Detalles del evento de prueba");
        evento.setIdUsuarioCreador(1);
        evento.setUbicacion("Ubicación de prueba");

        // Insertar el evento en la base de datos
        dao.crearEvento(evento);

        // Verificar que el evento se haya creado correctamente
        Evento eventoCreado = dao.obtenerEventoPorId(evento.getIdEvento());
        assertNotNull(eventoCreado);
        assertEquals("Evento de prueba", eventoCreado.getNombre());
        assertEquals("Detalles del evento de prueba", eventoCreado.getDetalles());
        assertEquals(1, eventoCreado.getIdUsuarioCreador());
        assertEquals("Ubicación de prueba", eventoCreado.getUbicacion());
    }
    @Test
    @Order(2)
    void testObtenerEventosPendientesAprobacion() throws SQLException {
        List<Evento> eventos = dao.obtenerEventosPendientesAprobacion();
        assertFalse(eventos.isEmpty());
        Evento eventoObtenido = eventos.get(0);
        assertNull(eventoObtenido.getFechaPublicacion());
    }
    @Test
    @Order(3)
    void testAprobarPublicacionEventos() throws SQLException {
        // Obtener los eventos pendientes de aprobación
        List<Evento> eventosPendientes = dao.obtenerEventosPendientesAprobacion();
        assertFalse(eventosPendientes.isEmpty());

        // Aprobar la publicación del primer evento pendiente
        Evento eventoPendiente = eventosPendientes.get(0);
        dao.aprobarPublicacionEvento(eventoPendiente.getIdEvento(), null);

        // Verificar que el evento aprobado tenga una fecha de aprobación
        Evento eventoAprobado = dao.obtenerEventoPorId(eventoPendiente.getIdEvento());
        assertNotNull(eventoAprobado.getFechaAprobacion());
    }
    @Test
    @Order(4)
    void testRechazarEvento() throws SQLException {
        // Obtener los eventos pendientes de aprobación
        List<Evento> eventosPendientes = dao.obtenerEventosPendientesAprobacion();
        assertFalse(eventosPendientes.isEmpty());

        // Rechazar el último evento pendiente
        Evento eventoPendiente = eventosPendientes.get(eventosPendientes.size() - 1);
        dao.rechazarEvento(eventoPendiente.getIdEvento(), null);

        // Verificar que el evento rechazado tenga un motivo de finalización
        Evento eventoRechazado = dao.obtenerEventoPorId(eventoPendiente.getIdEvento());
        assertEquals(MotivoFinalizacion.REPORTE_NEGATIVO, eventoRechazado.getMotivoFinalizacion());
    }
    @Test
    @Order(5)
    void testPublicarEvento() throws SQLException {
        // Obtener el evento pendiente de publicación
        List<Evento> eventosPendientes = dao.obtenerEventosPendientesAprobacion();
        assertFalse(eventosPendientes.isEmpty());
        Evento eventoPendiente = eventosPendientes.get(0);

        // Publicar el evento
        dao.publicarEvento(eventoPendiente.getIdEvento(), null);

        // Verificar que el evento se haya publicado correctamente
        Evento eventoPublicado = dao.obtenerEventoPorId(eventoPendiente.getIdEvento());
        assertNotNull(eventoPublicado.getFechaPublicacion());
    }

    @Test
    @Order(6)
    void testObtenerEventoPorId() throws SQLException {
    	 // Obtener el último evento creado
        List<Evento> eventos = dao.obtenerTodosLosEventos();
        Evento ultimoEvento = eventos.get(eventos.size() - 1);
        int idEvento = ultimoEvento.getIdEvento();
        // Obtener el evento por ID
        Evento eventoObtenido = dao.obtenerEventoPorId(idEvento);
        assertNotNull(eventoObtenido);

        // Verificar que los datos del evento sean correctos
        assertEquals("Evento de prueba", eventoObtenido.getNombre());
        assertEquals("Detalles del evento de prueba", eventoObtenido.getDetalles());
        assertEquals(1, eventoObtenido.getIdUsuarioCreador());
    }

    @Test
    @Order(7)
    void testObtenerEventosPorUsuario() throws SQLException {
        // Obtener los eventos del usuario
        List<Evento> eventos = dao.obtenerEventosPorUsuario(1);
        assertFalse(eventos.isEmpty());

        // Verificar que los eventos sean correctos
        for (Evento evento : eventos) {
            assertEquals("Evento de prueba", evento.getNombre());
            assertEquals("Detalles del evento de prueba", evento.getDetalles());
            assertEquals(1, evento.getIdUsuarioCreador());
        }
    }

    @Test
    @Order(8)
    void testEditarEvento() throws SQLException {
        // Obtener el último evento creado
        List<Evento> eventos = dao.obtenerTodosLosEventos();
        Evento eventoOriginal = eventos.get(eventos.size() - 1);
        assertNotNull(eventoOriginal);

        // Realizar la edición
        eventoOriginal.setNombre("Evento editado");
        eventoOriginal.setDetalles("Detalles del evento editado");

        // Actualizar el evento en la base de datos
        dao.editarEvento(eventoOriginal);

        // Obtener el evento editado de nuevo
        Evento eventoEditado = dao.obtenerEventoPorId(eventoOriginal.getIdEvento());
        assertNotNull(eventoEditado);

        // Comparar propiedades
        assertEquals("Evento editado", eventoEditado.getNombre());
        assertEquals("Detalles del evento editado", eventoEditado.getDetalles());

        // Comprobar si la fecha de última modificación cambió
        assertNotEquals(eventoOriginal.getFechaUltimaModificacion(), eventoEditado.getFechaUltimaModificacion());
    }

    @Test
    @Order(9)
    void testEliminarEvento() throws SQLException {
        // Obtener el último evento creado
        List<Evento> eventos = dao.obtenerTodosLosEventos();
        Evento eventoAEliminar = eventos.get(eventos.size() - 1);
        assertNotNull(eventoAEliminar);

        // Eliminar el evento
        dao.eliminarEvento(eventoAEliminar);

        // Verificar que el evento se haya eliminado correctamente
        Evento eventoEliminado = dao.obtenerEventoPorId(eventoAEliminar.getIdEvento());
        assertNull(eventoEliminado);
    }



}
