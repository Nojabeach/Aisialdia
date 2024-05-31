package controlador;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import jakarta.servlet.http.HttpServlet;

/**
 * Clase que proporciona métodos comunes útiles.
 */
public class MetodosComunes {
	/**
	 * Constructor por defecto de la clase
	 * 
	 * @see HttpServlet#HttpServlet()
	 */
	public MetodosComunes() {

	}

	/**
	 * Calcula el valor hash MD5 de una cadena de entrada.
	 *
	 * @param input La cadena de entrada para la cual se calculará el hash MD5.
	 * @return La representación hexadecimal del hash MD5 de la cadena de entrada.
	 * @throws IllegalArgumentException Si la cadena de entrada es nula.
	 */
	public static String getMD5(String input) {
		if (input != null) {
			// Continuar con el cálculo de MD5
			try {
				MessageDigest md = MessageDigest.getInstance("MD5");
				byte[] messageDigest = md.digest(input.getBytes());
				// Convertir el array de bytes a representación hexadecimal
				StringBuilder hexString = new StringBuilder();
				for (byte b : messageDigest) {
					String hex = Integer.toHexString(0xff & b);
					if (hex.length() == 1)
						hexString.append('0');
					hexString.append(hex);
				}
				return hexString.toString();
			} catch (NoSuchAlgorithmException e) {
				throw new RuntimeException(e);
			}
		} else {
			// Manejar el caso de entrada nula
			throw new IllegalArgumentException("Input cannot be null");
		}
	}

}
