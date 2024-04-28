import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import dao.DaoFavorito;
import modelo.Evento;
import modelo.Usuario;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;


@TestMethodOrder(OrderAnnotation.class)
class TestFavorito {

    private DaoFavorito dao;
    private Usuario usuario;
    private Evento evento;

    @BeforeEach
    public void setUp() throws SQLException {
        dao = DaoFavorito.getInstance();
        usuario = new Usuario();
        evento = new Evento();
    }

    @Test
    @Order(1)
    public void testAgregarFavorito() throws SQLException {
        // Crear un evento y un usuario de prueba
        evento.setIdEvento(1); // Asigna el ID del evento existente en la base de datos
        usuario.setIdUsuario(1); // Asigna el ID del usuario existente en la base de datos

        // Agregar el evento a la lista de favoritos del usuario
        dao.agregarFavoritoEvento(evento.getIdEvento(), usuario.getIdUsuario());

        // Verificar que el evento se haya agregado correctamente
        assertTrue(dao.verificarEventoFavoritoUsuario(evento.getIdEvento(), usuario.getIdUsuario()));
    }

    @Test
    @Order(2)
    public void testEliminarFavorito() throws SQLException {
        // Crear un evento y un usuario de prueba
        evento.setIdEvento(1); // Asigna el ID del evento existente en la base de datos
        usuario.setIdUsuario(1); // Asigna el ID del usuario existente en la base de datos

        // Eliminar el evento de la lista de favoritos del usuario
        dao.eliminarFavoritoEvento(evento.getIdEvento(), usuario.getIdUsuario());

        // Verificar que el evento se haya eliminado correctamente
        assertFalse(dao.verificarEventoFavoritoUsuario(evento.getIdEvento(), usuario.getIdUsuario()));
    }

    @Test
    @Order(3)
    public void testVerificarEventoFavoritoUsuario() throws SQLException {
        // Crear un evento y un usuario de prueba
        evento.setIdEvento(1); // Asigna el ID del evento existente en la base de datos
        usuario.setIdUsuario(1); // Asigna el ID del usuario existente en la base de datos

        // Verificar que el evento es favorito del usuario
        assertTrue(dao.verificarEventoFavoritoUsuario(evento.getIdEvento(), usuario.getIdUsuario()));
    }

    @Test
    @Order(4)
    public void testObtenerEventosFavoritosUsuario() throws SQLException {
        // Crear un usuario de prueba
        usuario.setIdUsuario(1); // Asigna el ID del usuario existente en la base de datos

        // Obtener la lista de eventos favoritos del usuario
        List<Evento> eventosFavoritos = dao.obtenerEventosFavoritos(usuario.getIdUsuario());

        // Verificar que la lista no esté vacía
        assertFalse(eventosFavoritos.isEmpty());

        // Verificar que todos los eventos en la lista sean favoritos del usuario
        for (Evento eventoFavorito : eventosFavoritos) {
            assertTrue(dao.verificarEventoFavoritoUsuario(eventoFavorito.getIdEvento(), usuario.getIdUsuario()));
        }
    }
}
