package controlador;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MetodosComunes {
	/**
	 * Calcula el valor hash MD5 de una cadena de entrada.
	 *
	 * @param input La cadena de entrada para la cual se calculará el hash MD5.
	 * @return La representación hexadecimal del hash MD5 de la cadena de entrada.
	 */
	public static String getMD5(String input) {
		try {
			// Obtener una instancia de MessageDigest con el algoritmo MD5
			MessageDigest md = MessageDigest.getInstance("MD5");

			// Calcular el hash de la cadena de entrada
			byte[] messageDigest = md.digest(input.getBytes());

			// Convertir el hash en una representación hexadecimal
			BigInteger number = new BigInteger(1, messageDigest);
			String hashtext = number.toString(16);

			// Asegurarse de que la representación hexadecimal tenga 32 caracteres
			while (hashtext.length() < 32) {
				hashtext = "0" + hashtext;
			}

			return hashtext;
		} catch (NoSuchAlgorithmException e) {
			// Lanzar una RuntimeException si ocurre un error al calcular el hash
			throw new RuntimeException(e);
		}
	}
}
