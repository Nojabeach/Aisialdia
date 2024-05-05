// Resumen:
// Este archivo JavaScript contiene una función para manejar el envío del formulario de registro
// y agregar un evento de envío al formulario.

// Función para manejar el envío del formulario de registro
function procesarEnvioFormularioRegistro(evento) {
 
    
    // Obtener los valores de los campos del formulario
    const nombre = document.getElementsByName("nombre")[0].value;
    const email = document.getElementsByName("email")[0].value;
    const password = document.getElementsByName("password")[0].value;
    const aceptaConsentimientoDatos = document.getElementsByName("consentimiento-datos")[0].checked;
    const aceptaTerminosCondiciones = document.getElementsByName("terminos-condiciones")[0].checked;
    
    // Verificar si los campos están vacíos
    if (nombre === "" || email === "" || password === "") {
        // Agregar estilos para campos vacíos
        document.getElementsByName("nombre")[0].classList.add("campo-vacio");
        document.getElementsByName("email")[0].classList.add("campo-vacio");
        document.getElementsByName("password")[0].classList.add("campo-vacio");
        alert("Debes rellenar todos los campos");
        return;
    } else {
        // Remover estilos para campos vacíos
        document.getElementsByName("nombre")[0].classList.remove("campo-vacio");
        document.getElementsByName("email")[0].classList.remove("campo-vacio");
        document.getElementsByName("password")[0].classList.remove("campo-vacio");
    }
    
    // Verificar si se han aceptado los términos y condiciones
    if (!aceptaConsentimientoDatos ||!aceptaTerminosCondiciones) {
        alert("Debes aceptar el consentimiento de uso de datos y los términos y condiciones");
        return;
    }
    
    // Enviar solicitud de registro al servidor
    fetch(`GestorUsuario?action=registrarUsuario&nombre=${nombre}&email=${email}&password=${password}`)
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
const botonRegistro = document.getElementById("boton-registro");
botonRegistro.addEventListener("click", procesarEnvioFormularioRegistro);