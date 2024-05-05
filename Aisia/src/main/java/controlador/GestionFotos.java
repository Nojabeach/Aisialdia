package controlador;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import modelo.Proyecto;

/**
 * Clase que gestiona la subida de fotos al servidor.
 * 
 * @author Maitane Ibañez Irazabal
 * 
 * @version 1.0
 */
public class GestionFotos {

	/** Ruta de los archivos en el servidor. */
	private static final String PATH_FILES = Proyecto.rutaProyecto + "\\img\\Iconos";

	/**
	 * Sube una foto al servidor.
	 *
	 * @param part     Parte del archivo a subir.
	 * @param response Objeto HttpServletResponse utilizado para enviar respuestas
	 *                 HTTP.
	 * @return El nombre del archivo subido, o null si no se pudo subir la foto.
	 * @throws IOException Si ocurre un error de entrada/salida durante la
	 *                     operación.
	 */
	public static String subirFotoAlServidor(Part part, HttpServletResponse response) throws IOException {
		try {
			File uploads = new File(PATH_FILES);

			if (part == null || part.getSize() == 0) {
				ControlErrores.mostrarErrorGenerico("No se ha seleccionado ninguna foto.", response);
				return null;
			}

			String fileName = Paths.get(part.getSubmittedFileName()).getFileName().toString();

			if (fileName == null || fileName.isEmpty()) {
				ControlErrores.mostrarErrorGenerico("Nombre de archivo inválido.", response);
				return null;
			}

			InputStream input = part.getInputStream();

			if (!uploads.exists() && !uploads.mkdirs()) {
				ControlErrores.mostrarErrorGenerico("Error al crear el directorio de subida.", response);
				return null;
			}

			File file = new File(uploads, fileName);

			try {
				Files.copy(input, file.toPath());
				return fileName;
			} catch (IOException e) {
				ControlErrores.mostrarErrorGenerico("Error al copiar la foto. Intente de nuevo.", response);
				return null;
			}
		} catch (Exception e) {
			ControlErrores.mostrarErrorGenerico("Error al subir la foto. Intente de nuevo.", response);
			return null;
		}
	}
}
