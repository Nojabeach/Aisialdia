 // Función para verificar si hay una sesión activa
 function verificarSesion() {
    fetch('GestorUsuario?action=verificarSesion')
        .then(response => response.json())
        .then(data => {
            if (data.sessionActive) {
                // Si hay una sesión activa, redirigir a eventos.html
                window.location.href = "eventos.html";
            } else {
                // Si no hay una sesión activa, redirigir a index.html
                window.location.href = "index.html";
            }
        })
        .catch(error => console.error(error));
}

// Agregar un evento de escucha de clics al enlace de "Inicio"
document.getElementById("inicio").addEventListener("click", function(event) {
    event.preventDefault(); // Evitar el comportamiento predeterminado del enlace
    verificarSesion(); // Llamar a la función verificarSesion()
});