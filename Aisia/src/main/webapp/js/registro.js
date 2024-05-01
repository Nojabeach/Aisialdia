        // Redireccionar al servlet GestorUsuario con action iniciarSesion
        // Una vez el usuario se registra
        window.location.href = "GestorUsuario?action=iniciarSesion";

        // Se ejecutan las acciones comprobarLogin y verificarPermiso
        // Estas acciones deben estar implementadas en el servlet GestorUsuario
        // Llamar a comprobarLogin
        fetch("GestorUsuario?action=comprobarLogin")
            .then(response => {
                if (!response.ok) {
                    throw new Error("No se pudo iniciar sesión.");
                }
                return response.text();
            })
            .then(data => console.log(data))
            .catch(error => console.error(error));

        // Llamar a verificarPermiso con permiso=1
        fetch("GestorUsuario?action=verificarPermiso&permiso=" + encodeURIComponent(permisoDeUsuarioDesdeBD))")
            .then(response => {
                if (!response.ok) {
                    throw new Error("No tienes permiso para acceder.");
                }
                return response.text();
            })
            .then(data => console.log(data))
            .catch(error => console.error(error));

// Obtener permiso del usuario desde la base de datos
int permisoDeUsuarioDesdeBD = DaoUsuario.getInstance().buscarPermisoUsuario(usuario.getId());

// Llamar a verificarPermiso con el permiso del usuario obtenido de la BD
fetch("/GestorUsuario?action=verificarPermiso&permiso=" + encodeURIComponent(permisoDeUsuarioDesdeBD))
    .then(response => {
        if (!response.ok) {
            throw new Error("No tienes permiso para acceder.");
        }
        return response.text();
    })
    .then(data => console.log(data))
    .catch(error => console.error(error));