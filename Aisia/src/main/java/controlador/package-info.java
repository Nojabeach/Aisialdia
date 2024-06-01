/**
 * El paquete controlador actúa como intermediario entre la vista y el modelo. 
 * Estos controladores manejan las solicitudes del usuario, la validación de datos y 
 * la lógica empresarial.
 * 
 *  
 * Controladores clave:
 *  
 * <ul>
 *   <li>{@link GestorActividad} - Gestiona las solicitudes relacionadas con las actividades.</li>
 *   <li>{@link GestorUsuario} - Maneja las solicitudes relacionadas con los usuarios.</li>
 *   <li>{@link GestorEvento} - Controla las solicitudes relacionadas con los eventos.</li>
 *   <li>{@link GestorFavorito} - Maneja las solicitudes relacionadas con los favoritos de los usuarios.</li>
 *   <li>{@link GestorClasificadorEventos} - Maneja las solicitudes relacionadas con las vinculaciones de eventos y actividades.</li>
 *   <li>{@link GestorIntereses} - Maneja las solicitudes relacionadas con los intereses de los usuarios.</li>
 *   <li>{@link MetodosComunes} - Agrupa metodos comunes de la aplicación. </li>
 *   <li>{@link Contacto} - Gestiona el procesamiento de datos a enviar en el formulario de contacto. </li> 
 *   <li>{@link ControlErrores} - Gestiona el control de errores dedicado que se ha desarrollado para la aplicación. </li> 
 *   <li>{@link UltimosEventos} - Controla las solicitudes relacionadas con los ultimos eventos publicados.</li>  
 *    <li>{@link Proyecto} - Controla la ruta de ubicación del proyecto que se maneja en el copiado de archivos al servidor.</li> 
 * </ul>
 *
 * Funcionalidades:
 * <ul>
 *   <li>Manejo de Solicitudes - Procesa las peticiones HTTP de la vista y llama a los servicios adecuados.</li>
 *   <li>Validación de Datos - Verifica que los datos introducidos por el usuario sean correctos y seguros.</li>
 *   <li>Lógica Empresarial - Implementa las reglas de negocio y el comportamiento de la aplicación.</li>
 * </ul>
 * 
 * @version 1.0
 * @author Maitane Ibañez Irazabal
 * @since 2024
 */
package controlador;