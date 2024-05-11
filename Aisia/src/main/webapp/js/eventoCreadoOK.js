// eventoCreadoOK.js

window.onload = function() {
    // Lee el parámetro "mensaje" de la URL
    var urlParams = new URLSearchParams(window.location.search);
    var mensaje = urlParams.get('mensaje');
    // Si hay un mensaje, muestra el alert
    if (mensaje) {
        alert(mensaje);
        // Espera 3 segundos y redirige a eventos.html
        setTimeout(function() {
            window.location.href = "eventos.html";
        }, 3000); // 3000 milisegundos = 3 segundos

    }
};
