/**
 * El paquete DAO (Data Access Object) contiene las clases que manejan la comunicación
 * con la base de datos. Estas clases proporcionan métodos para realizar operaciones
 * CRUD (Crear, Leer, Actualizar, Eliminar) sobre las entidades del dominio.
 *
 *
 * DAOs clave:
 * <ul>
 *   <li>{@link DaoActividad} - Maneja las operaciones de base de datos para las actividades.</li>
 *    <li>{@link DaoClasificacionEventos} - Controla las operaciones de base de datos para las relaciones entre las actividades y los eventos.</li>
 *   <li>{@link DaoUsuario} - Gestiona las operaciones de base de datos para los usuarios.</li>
 *   <li>{@link DaoEvento} - Controla las operaciones de base de datos para los eventos.</li>
 *   <li>{@link DaoEventoConActividad} - Maneja las operaciones de base de datos para listar los datos vinculados de los eventos y las actividades.</li>
 *   <li>{@link DaoFavorito} - Controla las operaciones de base de datos sobre los favoritos de los usuarios.</li>
 *    <li>{@link DaoInteresPorDefecto} - Controla las operaciones de base de datos sobre los intereses de los usuarios</li>
 *   <li>{@link DBConection} - Maneja la gestión de conexiones a la base de datos.</li>  
 * </ul>
 * 
 * 
 *  @version 1.0
 * @author Maitane Ibañez Irazabal
 * @since 2024
 */
package dao;
