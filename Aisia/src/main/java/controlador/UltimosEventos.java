package controlador;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modelo.EventoConActividad;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.google.gson.Gson;

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
		try {
			// Obtener la cantidad de eventos a mostrar
			int cantidadEventos = 10; // Cantidad puesta por mi para así visualizar la barra de scroll y poder
										// editarla correctamente
			System.out.println("Cantidad de eventos: " + cantidadEventos);

			// Obtener los últimos eventos con su información asociada
			List<EventoConActividad> eventos = EventoConActividad.obtenerUltimosEventos(cantidadEventos);
			System.out.println("Eventos obtenidos: " + eventos.size());

			// Convertir la lista de eventos a JSON
			String eventosJSON = new Gson().toJson(eventos);
			System.out.println("Eventos en formato JSON: " + eventosJSON);

			// Establecer el tipo de contenido y encabezados de la respuesta
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");

			// Enviar la lista de eventos como respuesta

			response.getWriter().write(eventosJSON);

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
