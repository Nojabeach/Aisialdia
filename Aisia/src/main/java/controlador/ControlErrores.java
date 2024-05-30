/**
 * La clase ControlErrores proporciona métodos para manejar errores genéricos en una aplicación web.
 * Estos métodos permiten mostrar un mensaje de error personalizado en una página HTML.
 */
package controlador;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import jakarta.servlet.http.HttpServletResponse;
import modelo.Proyecto;
/**
 * Clase de controlador que se encarga de manejar y mostrar errores en una aplicación web.
 */
public class ControlErrores {

    /**
     * Muestra un mensaje de error genérico en una página HTML.
     *
     * @param mensajeError El mensaje de error a mostrar.
     * @param response     La respuesta HTTP donde se mostrará la página HTML de error.
     * @throws IOException Si ocurre un error de entrada/salida al escribir la respuesta HTTP.
     */
    public static void mostrarErrorGenerico(String mensajeError, HttpServletResponse response) throws IOException {
        // Cargar la plantilla HTML de error genérico
        String plantillaHTML = cargarPlantillaHTML(Proyecto.rutaProyecto + "//templates//errorgenerico.html");
        // Reemplazar el marcador de posición del mensaje de error en la plantilla HTML
        String contenidoHTML = plantillaHTML.replace("{mensajeError}", mensajeError);
        contenidoHTML = contenidoHTML.replace("{rutaProyecto}", "");

        // Configurar el tipo de contenido y el estado de la respuesta HTTP
        response.setContentType("text/html; charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

        // Escribir el contenido HTML en la respuesta HTTP
        try (PrintWriter out = response.getWriter()) {
            out.println(contenidoHTML);
        }
    }

    /**
     * Carga el contenido de una plantilla HTML desde un archivo en el sistema de archivos.
     *
     * @param rutaPlantilla La ruta del archivo de la plantilla HTML.
     * @return El contenido de la plantilla HTML como una cadena.
     * @throws IOException Si ocurre un error de entrada/salida al leer el archivo de la plantilla.
     */
    private static String cargarPlantillaHTML(String rutaPlantilla) throws IOException {
        // Abrir un FileInputStream para leer el archivo de la plantilla
        try (FileInputStream fis = new FileInputStream(new File(rutaPlantilla));
             InputStreamReader reader = new InputStreamReader(fis)) {
            // Leer el contenido de la plantilla línea por línea y construir una cadena
            StringBuilder sb = new StringBuilder();
            int character;
            while ((character = reader.read()) != -1) {
                sb.append((char) character);
            }
            // Devolver el contenido HTML completo
            return sb.toString();
        }
    }
}
