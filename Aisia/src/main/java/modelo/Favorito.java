package modelo;

import java.sql.SQLException;
import java.util.List;

import dao.DaoFavorito;
import jakarta.servlet.http.HttpServletRequest;


/**
 * Clase que representa un favorito de un usuario en un evento.
 *
 * @author Maitane Ibañez Irazabal
 * @version 1.0
 */
public class Favorito {

	// ATRIBUTOS
	// --------------------------------------------------------------------------------------------
	/**
	 * Identificador único de favorito (clave primaria).
	 */
	private int idFavorito;

	/**
	 * Asociación con la clase Evento del cual marcamos si es favorito. (n:n)
	 */
	private Evento evento;

	/**
	 * Asociación con la clase usuario el cual marca si es su favorito. (n:n)
	 */
	private Usuario usuario;

	// CONSTRUCTORES
	// --------------------------------------------------------------------------------------------
	/**
	 * Constructor vacío para inicializar la clase.
	 */
	public Favorito() {
	}

	/**
	 * Crea un nuevo objeto Favorito con los atributos especificados.
	 *
	 * @param idFavorito Identificador único del favorito.
	 * @param evento     evento que se ha marcado como favorito.
	 * @param usuario    usuario que agregó el favorito.
	 */

	public Favorito(int idFavorito, Evento evento, Usuario usuario) {
		this.idFavorito = idFavorito;
		this.evento = evento;
		this.usuario = usuario;
	}

	// GETTERS Y SETTERS PARA TODOS LOS ATRIBUTOS
	// --------------------------------------------------------------------------------------------
	/**
	 * Obtiene el identificador único del favorito.
	 *
	 * @return Identificador único del favorito.
	 */
	public int getIdFavorito() {
		return idFavorito;
	}

	/**
	 * Establece el identificador único del favorito.
	 *
	 * @param idFavorito Nuevo identificador único del favorito.
	 */
	public void setIdFavorito(int idFavorito) {
		this.idFavorito = idFavorito;
	}

	/**
	 * Obtiene el evento asociado al favorito.
	 *
	 * @return El evento asociado al favorito.
	 */
	public Evento getEvento() {
		return evento;
	}

	/**
	 * Establece el evento asociado al favorito.
	 *
	 * @param evento El evento asociado al favorito.
	 */
	public void setEvento(Evento evento) {
		this.evento = evento;
	}

	/**
	 * Obtiene el usuario dueño del favorito.
	 *
	 * @return El usuario dueño del favorito.
	 */
	public Usuario getUsuario() {
		return usuario;
	}

	/**
	 * Establece el usuario dueño del favorito.
	 *
	 * @param usuario El usuario dueño del favorito.
	 */
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	// TOSTRING
	// --------------------------------------------------------------------------------------------

	@Override
	public String toString() {
		return "Favorito [idFavorito=" + idFavorito + ", evento=" + evento + ", usuario=" + usuario + "]";
	}

	// MÉTODOS DE NEGOCIO
	// --------------------------------------------------------------------------------------------

	/**
	 * Agrega un favorito a una actividad para un usuario específico.
	 *
	 * @param idEvento  Identificador del evento que se quiere agregar como favorito
	 * @param idUsuario Identificador del usuario que desea agregar el favorito.
	 * @throws SQLException Si ocurre un error al agregar el favorito a la base de
	 *                      datos.
	 */

	public void agregarFavorito(int idEvento, HttpServletRequest request) throws SQLException {
		DaoFavorito.getInstance().agregarFavoritoEvento(idEvento, request);

	}

	/**
	 * Elimina un favorito de una actividad para un usuario específico.
	 *
	 * @param idEvento  Identificador del evento que se quiere eliminar de favorito
	 * @param idUsuario Identificador del usuario que desea eliminar el favorito.
	 * @throws Exception Si ocurre un error al eliminar el favorito de la base de
	 *                   datos.
	 */
	public void eliminarFavorito(int idEvento, HttpServletRequest request) throws SQLException {
		DaoFavorito.getInstance().eliminarFavoritoEvento(idEvento, request);
	}

	/**
	 * Comprueba si un evento es favorito de un usuario específico.
	 *
	 * @param idEvento  Identificador del evento que se desea verificar.
	 * @param idUsuario Identificador del usuario dueño del favorito.
	 * @return `true` si el evento es favorito del usuario, `false` en caso
	 *         contrario.
	 * @throws Exception Si ocurre un error al acceder a la base de datos.
	 */
	public static boolean esFavorito(int idEvento, HttpServletRequest request) throws SQLException {
		return DaoFavorito.getInstance().verificarEventoFavoritoUsuario(idEvento, request);
	}

	/**
	 * Obtiene la lista de eventos favoritos de un usuario específico.
	 *
	 * @param idUsuario Identificador del usuario del que se desean obtener los
	 *                  eventos favoritos.
	 * @return Lista de objetos Evento que representan los eventos favoritos del
	 *         usuario. La lista puede estar vacía si no hay favoritos.
	 * @throws Exception Si ocurre un error al acceder a la base de datos o procesar
	 *                   los resultados.
	 */
	public static List<Evento> obtenerEventosFavoritosUsuario(int idUsuario) throws Exception {
		return DaoFavorito.getInstance().obtenerEventosFavoritosUsuario(idUsuario);
	}

}
