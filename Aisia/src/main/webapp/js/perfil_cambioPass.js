document.addEventListener("DOMContentLoaded", function () {
    let cambiarContrasenaBtn = document.getElementById("cambiarContrasena");
    if (cambiarContrasenaBtn) {
        cambiarContrasenaBtn.addEventListener("click", function (event) {
            event.preventDefault();
            
            let contrasenaOR = document.getElementById("contrasenaOR").value;
            let contrasenaAC = document.getElementById("contrasenaAC").value;
            
            cambiarContrasena(contrasenaOR, contrasenaAC);
        });
    }
});

function cambiarContrasena(contrasenaOR, contrasenaAC) {
    fetch("GestorUsuario?action=cambiarContrasena", {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        },
        body: `contrasenaOR=${contrasenaOR}&contrasenaAC=${contrasenaAC}`
    })
    .then(response => {
        if (!response.ok) {
            throw new Error("Error al cambiar la contraseña");
        }
        console.log("Contraseña cambiada exitosamente");
        // Aquí puedes realizar acciones adicionales después de cambiar la contraseña si lo necesitas
    })
    .catch(error => {
        console.error("Error:", error);
    });
}
