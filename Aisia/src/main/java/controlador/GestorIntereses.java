package controlador;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import dao.DaoInteresPorDefecto;

/**
 * Servlet implementation class GestorIntereses
 */
public class GestorIntereses extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor por defecto de la clases
	 * 
	 * @see HttpServlet#HttpServlet()
	 */
	public GestorIntereses() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		String accion = request.getParameter("accion");

		if (accion != null && accion.equals("listar")) {
			listarInteresesPorDefecto(request, response, out);
		} else {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "Acción no válida");
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

	/**
	 * Lista los intereses por defecto en formato JSON y los envía al PrintWriter de
	 * la respuesta HTTP.
	 * 
	 * @param request  Objeto HttpServletRequest que contiene la solicitud HTTP.
	 * @param response Objeto HttpServletResponse que se utilizará para enviar la
	 *                 respuesta HTTP.
	 * @param out      El escritor de salida para escribir el resultado JSON en la
	 *                 respuesta.
	 * @throws ServletException Si se produce un error grave durante la ejecución
	 *                          del servlet.
	 * @throws IOException      Si se produce un error de entrada/salida al escribir
	 *                          en el PrintWriter.
	 */
	private void listarInteresesPorDefecto(HttpServletRequest request, HttpServletResponse response, PrintWriter out)
			throws ServletException, IOException {

		try {
			// Crea un objeto DaoInteresPorDefecto para manejar la obtención de intereses
			// por defecto
			DaoInteresPorDefecto intereses = new DaoInteresPorDefecto();

			// Lista los intereses por defecto en formato JSON y los envía al PrintWriter
			out.print(intereses.listarJsonIntereses());

		} catch (SQLException e) {
			// En caso de error, muestra un mensaje de error genérico y lo envía en formato
			// JSON a la respuesta
			e.printStackTrace();
			ControlErrores.mostrarErrorGenerico("{\"error\": \"" + e.getMessage() + "\"}", response);
		}
	}

}
