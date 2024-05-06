package controlador;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modelo.InteresPorDefecto;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.google.gson.Gson;

import dao.DaoInteresPorDefecto;

/**
 * Servlet implementation class GestorIntereses
 */
public class GestorIntereses extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
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
		String accion = request.getParameter("accion");

		if (accion != null && accion.equals("listar")) {
			listarInteresesPorDefecto(request, response);
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
	 * Lista todos los intereses por defecto existentes y los envía como respuesta
	 * en formato JSON.
	 *
	 * @param request  la solicitud HTTP
	 * @param response la respuesta HTTP
	 * @throws ServletException si ocurre un error de servlet
	 * @throws IOException      si ocurre un error de E/S al procesar la solicitud
	 */
	private void listarInteresesPorDefecto(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// Obtener lista de nombres de intereses por defecto existentes
			List<String> nombresIntereses = DaoInteresPorDefecto.getInstance().listarNombresInteresesPorDefecto();

			// Convertir la lista de nombres de intereses a JSON
			String interesesJSON = new Gson().toJson(nombresIntereses);

			// Establecer el tipo de contenido y encabezados de la respuesta
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");

			// Enviar la lista de nombres de intereses como respuesta
			response.getWriter().write(interesesJSON);
		} catch (SQLException e) {
			ControlErrores.mostrarErrorGenerico("Error al obtener los intereses por defecto. Intente de nuevo.",
					response);
		}
	}

}
