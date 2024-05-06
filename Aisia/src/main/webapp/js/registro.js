/*
 * Este script maneja el envío del formulario de registro.
 * Verifica que se completen todos los campos requeridos,
 * que se acepten los términos y condiciones, y envía los datos al servidor
 * para registrar al usuario. Luego, redirige al usuario a la página de eventos
 * si el registro es exitoso, o muestra un mensaje de error en caso contrario.
 */
// Función para manejar el envío del formulario de registro
function procesarEnvioFormularioRegistro(evento) {
  evento.preventDefault(); // Evitar que el formulario se envíe automáticamente

  // Obtener los valores de los campos del formulario
  const nombre = document.getElementById("nombre").value;
  const email = document.getElementById("email").value;
  const password = document.getElementById("password").value;
  const aceptaConsentimientoDatos = document.getElementById("consentimiento-datos").checked;
  const aceptaTerminosCondiciones = document.getElementById("terminos-condiciones").checked;
  const interesesSeleccionados = document.getElementById("interesesSeleccionados-text").value;

  // Verificar si los campos están vacíos
  if (nombre.trim() === "" || email.trim() === "" || password.trim() === "") {
    alert("Debes rellenar todos los campos");
    return;
  }

  // Verificar si se han aceptado los términos y condiciones
  if (!aceptaConsentimientoDatos || !aceptaTerminosCondiciones) {
    alert("Debes aceptar el consentimiento de uso de datos y los términos y condiciones");
    return;
  }

  // Enviar solicitud de registro al servidor
  fetch(`GestorUsuario?action=registrarUsuario&nombre=${nombre}&email=${email}&password=${password}&interesesSeleccionados=${interesesSeleccionados}`)
    .then(response => response.json())
    .then(data => {
      if (data.logged) {
        // Redirigir al usuario a eventos.html con el usuario logueado
        window.location.href = "eventos.html";
      } else {
        alert("Error al registrar usuario");
      }
    })
    .catch(error => console.error(error));
}

// Agregar evento de envío al formulario de registro
document.getElementById("registro").addEventListener("submit", procesarEnvioFormularioRegistro);
