package controlador;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import jakarta.servlet.http.HttpServletResponse;

public class ControlErrores {

	public static void mostrarErrorGenerico(String mensajeError, HttpServletResponse response) throws IOException {
		String plantillaHTML = cargarPlantillaHTML("templates\\errorgenerico.html");

		String contenidoHTML = plantillaHTML.replace("{mensajeError}", mensajeError);
		contenidoHTML = contenidoHTML.replace("{rutaProyecto}", "");

		response.setContentType("text/html; charset=UTF-8");
		response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

		try (PrintWriter out = response.getWriter()) {
			out.println(contenidoHTML);
		}
	}

	private static String cargarPlantillaHTML(String rutaPlantilla) throws IOException {
		// Abrir un FileInputStream para leer el archivo
		try (FileInputStream fis = new FileInputStream(new File(rutaPlantilla));
				InputStreamReader reader = new InputStreamReader(fis)) {
			// Leer el contenido de la plantilla línea por línea
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
