package controlador;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import javax.mail.*;
import javax.mail.internet.*;

/**
 * Servlet para gestionar el envío de correos electrónicos desde el formulario de contacto.
 *
 * Este servlet procesa las solicitudes HTTP enviadas desde un formulario de contacto en un sitio web y
 * envía correos electrónicos utilizando las credenciales proporcionadas. Los correos electrónicos se envían
 * a través de un servidor SMTP (en este caso, Gmail).
 *
 * @author Maitane Ibañez Irazabal
 * @version 1.0
 * @since 2024-05-30
 */
public class Contacto extends HttpServlet {
	private static final long serialVersionUID = 1L;

	 /**
     * Constructor predeterminado.
     */
	public Contacto() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Maneja las solicitudes GET para el servlet (no utilizado en este caso).
	 * 
	 * @param request  Objeto HttpServletRequest que contiene la solicitud HTTP.
	 * @param response Objeto HttpServletResponse que se utilizará para enviar la
	 *                 respuesta HTTP.
	 * @throws ServletException Si se produce un error en el servlet.
	 * @throws IOException      Si se produce un error de entrada/salida.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * Maneja las solicitudes POST para el servlet.
	 * 
	 * Este método recibe los datos del formulario de contacto, configura y envía un
	 * correo electrónico utilizando las credenciales proporcionadas.
	 * 
	 * @param request  Objeto HttpServletRequest que contiene la solicitud HTTP.
	 * @param response Objeto HttpServletResponse que se utilizará para enviar la
	 *                 respuesta HTTP.
	 * @throws ServletException Si se produce un error en el servlet.
	 * @throws IOException      Si se produce un error de entrada/salida.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Configurar propiedades para la sesión de correo
		Properties properties = new Properties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");

		// Configurar credenciales de autenticación
		final String username = "ProyectoAisialdia@gmail.com";
		final String password = "A.12341234";

		// Obtener parámetros del formulario
		String email_from = request.getParameter("email");
		String subject = request.getParameter("subject");
		String messageBody = request.getParameter("message");

		// Crear una sesión de correo con autenticación
		Session session = Session.getInstance(properties, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		// Configurar el mensaje de correo
		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress("ProyectoAisialdia@gmail.com")); 
			message.setSubject(subject);
			message.setText("Mensaje de: " + email_from + "\n\n" + messageBody);

			// Enviar el mensaje
			Transport.send(message);

			// Respuesta al cliente
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			out.println("<html><body>");
			out.println("<h2>Correo enviado con éxito</h2>");
			out.println("<p>Gracias por contactarnos. Te responderemos pronto.</p>");
			out.println("</body></html>");
		} catch (MessagingException mex) {
			mex.printStackTrace();
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			out.println("<html><body>");
			out.println("<h2>Error al enviar el correo</h2>");
			out.println("<p>Error: " + mex.getMessage() + "</p>");
			out.println("</body></html>");
		} catch (Exception e) {
			// Otro tipo de error
			ControlErrores.mostrarErrorGenerico("Se ha producido un error. Intente de nuevo más tarde.", response);
			e.printStackTrace();
		}
	}
}
