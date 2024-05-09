package controlador;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import dao.DaoEventoConActividad;

/**
 * Servlet implementation class UltimosEventos
 * 
 * Este servlet proporciona la información de los últimos eventos, incluyendo el
 * nombre y la extensión de la imagen asociada.
 * 
 * @author Maitane Ibañez Irazabal
 * @version 1.0
 */
public class UltimosEventos extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UltimosEventos() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		PrintWriter out = response.getWriter();

		int cantidadEventos = 10;

		// Cantidad puesta por mi para así visualizar la barra de scroll y poder
		// editarla correctamente

		DaoEventoConActividad Eventos;
		try {
			Eventos = new DaoEventoConActividad();
			out.print(Eventos.listarJsonUltimosEventos(cantidadEventos));

		} catch (SQLException e) {
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			ControlErrores.mostrarErrorGenerico("{\"error\": \"" + e.getMessage() + "\"}", response);
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
