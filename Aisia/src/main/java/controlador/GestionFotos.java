package controlador;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
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
	 /**
     *constructor  por defecto de GestionFotos.
     */
    public GestionFotos() {
        // Initialization code, if any
    }
	/** Ruta de los archivos en el servidor. */
	public static final String PATH_FILES = Proyecto.rutaProyecto + "\\img\\Iconos";

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
				// System.out.println("No se ha seleccionado ninguna foto.");
				ControlErrores.mostrarErrorGenerico("No se ha seleccionado ninguna foto.", response);
				return null;
			}

			String fileName = Paths.get(part.getSubmittedFileName()).getFileName().toString();

			if (fileName == null || fileName.isEmpty()) {
				// System.out.println("Nombre de archivo inválido.");
				ControlErrores.mostrarErrorGenerico("Nombre de archivo inválido.", response);
				return null;
			}

			// System.out.println("Nombre del archivo: " + fileName);

			InputStream input = part.getInputStream();

			if (!uploads.exists() && !uploads.mkdirs()) {
				// System.out.println("Error al crear el directorio de subida.");
				ControlErrores.mostrarErrorGenerico("Error al crear el directorio de subida.", response);
				return null;
			}

			File file = new File(uploads, fileName);

			// Renombrar la foto si ya existe
			int i = 1;
			while (file.exists()) {
				String[] splitName = fileName.split("\\.");
				String newName = splitName[0] + "_" + i + "." + splitName[1];
				file = new File(uploads, newName);
				i++;
			}

			try {
				Files.copy(input, file.toPath());
				// System.out.println("Foto subida correctamente: " + file.getName());
				return file.getName();
			} catch (IOException e) {
				// System.out.println("Error al copiar la foto. Intente de nuevo.");
				ControlErrores.mostrarErrorGenerico("Error al copiar la foto. Intente de nuevo.", response);
				return null;
			}
		} catch (Exception e) {
			// System.out.println("Error al subir la foto. Intente de nuevo.");
			ControlErrores.mostrarErrorGenerico("Error al subir la foto. Intente de nuevo.", response);
			return null;
		}
	}

	/**
	 * Elimina una foto del servidor.
	 *
	 * @param fileName El nombre del archivo a eliminar.
	 * @param response Objeto HttpServletResponse utilizado para enviar respuestas
	 *                 HTTP.
	 * @return true si el archivo fue eliminado correctamente, false en caso
	 *         contrario.
	 * @throws IOException Si ocurre un error de entrada/salida durante la
	 *                     operación.
	 */
	public static boolean eliminarFotoDelServidor(String fileName, HttpServletResponse response) throws IOException {
		try {
			File file = new File(PATH_FILES, fileName);

			if (!file.exists()) {
				ControlErrores.mostrarErrorGenerico("El archivo no existe.", response);
				return false;
			}

			if (file.delete()) {
				return true;
			} else {
				ControlErrores.mostrarErrorGenerico("No se pudo eliminar el archivo. Intente de nuevo.", response);
				return false;
			}
		} catch (Exception e) {
			ControlErrores.mostrarErrorGenerico("Error al eliminar el archivo. Intente de nuevo.", response);
			return false;
		}
	}
}
