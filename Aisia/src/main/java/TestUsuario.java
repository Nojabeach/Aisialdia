import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import dao.DaoUsuario;
import modelo.Usuario;


@TestMethodOrder(OrderAnnotation.class)
class TestDaoUsuario {

    private DaoUsuario dao;
    private Connection con;

    @BeforeEach
    public void setUp() throws SQLException {
        dao = DaoUsuario.getInstance();

    }

    @Test
    @Order(1)
    public void testRegistrarUsuario() throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setNombre("Usuario Prueba");
        usuario.setEmail("usuario_prueba@example.com");
        usuario.setcontrasena("password");

        dao.registrarUsuario(usuario);

        PreparedStatement stmt = con.prepareStatement("SELECT * FROM usuarios WHERE email = ?");
        stmt.setString(1, usuario.getEmail());
        ResultSet rs = stmt.executeQuery();

        assertTrue(rs.next()); // Comprueba que se haya insertado el usuario en la base de datos
        assertEquals(usuario.getNombre(), rs.getString("nombre"));
        assertEquals(usuario.getEmail(), rs.getString("email"));
        assertEquals(usuario.getcontrasena(), rs.getString("contrasena"));

        con.rollback(); // Deshace los cambios en la base de datos
    }

    @Test
    @Order(2)
    public void testIniciarSesion() throws Exception {
        // Suponiendo que existe un usuario con estas credenciales en la base de datos
        Usuario usuario = dao.iniciarSesion("usuario_prueba@example.com", "password");

        assertNotNull(usuario);
        assertEquals("Usuario Prueba", usuario.getNombre());
        assertEquals("usuario_prueba@example.com", usuario.getEmail());
        assertEquals("password", usuario.getcontrasena());
    }

    @Test
    @Order(3)
    public void testEditarUsuario() throws Exception {
        // Suponiendo que existe un usuario con este ID en la base de datos
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(1);
        usuario.setNombre("Nuevo Nombre");
        usuario.setEmail("nuevo_email@example.com");

        dao.editarUsuario(usuario);

        PreparedStatement stmt = con.prepareStatement("SELECT * FROM usuarios WHERE idUsuario = ?");
        stmt.setInt(1, usuario.getIdUsuario());
        ResultSet rs = stmt.executeQuery();

        assertTrue(rs.next()); // Comprueba que se haya actualizado el usuario en la base de datos
        assertEquals(usuario.getNombre(), rs.getString("nombre"));
        assertEquals(usuario.getEmail(), rs.getString("email"));

        con.rollback(); // Deshace los cambios en la base de datos
    }

    @Test
    @Order(4)
    public void testEliminarUsuario() throws Exception {
        // Suponiendo que existe un usuario con este ID en la base de datos
        int idUsuario = 1;

        dao.eliminarUsuario(idUsuario);

        PreparedStatement stmt = con.prepareStatement("SELECT * FROM usuarios WHERE idUsuario = ?");
        stmt.setInt(1, idUsuario);
        ResultSet rs = stmt.executeQuery();

        assertFalse(rs.next()); // Comprueba que el usuario se haya eliminado de la base de datos

        con.rollback(); // Deshace los cambios en la base de datos
    }
}