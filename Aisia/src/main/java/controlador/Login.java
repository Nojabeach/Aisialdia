package controlador;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Servlet implementation class Login
 */
public class Login extends HttpServlet {
	// Instancio la sesi�n
	HttpSession sesion;

	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Login() {
		super();
		// TODO Auto-generated constructor stub
	}

	public static String getMD5(String input) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] messageDigest = md.digest(input.getBytes());
			BigInteger number = new BigInteger(1, messageDigest);
			String hashtext = number.toString(16);

			while (hashtext.length() < 32) {
				hashtext = "0" + hashtext;
			}
			return hashtext;
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	private int comprobaLogeo(String nombreInsertado, String passInsertado) {
		int ok = 0;

		/*
		 * Simulamos con variables el nombre y la contrase�a. En un caso real esto ser�a
		 * un m�todo de la clase DAO donde consultariamos us el usuario existe y esta en
		 * la base de datos.
		 */

		// Datos que deberian estar en la BD
		String nombre = "Antonio";
		String pass = getMD5("1234"); // ciframos siempre la contrase�a
		int permiso = 1;

		System.out.println(nombre + " | " + pass + " | " + permiso);

		if (nombre.equals(nombreInsertado) || getMD5(passInsertado).equals(pass)) {
			// El usuario y contrase�a es correcto
			ok = permiso;

		}

		return ok;
	}

	private boolean estaLogeado() {

		boolean ok = false;
		String nombre = (String) sesion.getAttribute("nombre");
		if (nombre != null) {
			ok = true;
		}

		return ok;

	}

	private int permiso() {

		int permiso = (int) sesion.getAttribute("permiso");

		if (!estaLogeado()) {
			permiso = 0;
		}
		return permiso;
	}

	private void cerrarSesion(HttpServletRequest request, HttpServletResponse response) throws IOException {

		sesion.invalidate();
		response.sendRedirect("/Login.html");
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter respuesta = response.getWriter(); // para poder enviar la respuesta por http
		sesion = request.getSession(); // abro la sesi�n para poderla utilizar.

		String nombreInsertado = request.getParameter("nombre");
		String passInsertado = request.getParameter("pass");
		int opcion = Integer.parseInt(request.getParameter("option"));

		int permiso = this.comprobaLogeo(nombreInsertado, passInsertado);

		if (permiso > 0) {
			sesion.setAttribute("nombre", "Antonio");
			sesion.setAttribute("permiso", permiso);
			respuesta.print("Usuario logeado: " + sesion.getAttribute("nombre"));
		} else {
			respuesta.print("Usuario no logeado: " + sesion.getAttribute("nombre"));

		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
