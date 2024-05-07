// Resumen:
// Este código JavaScript se encarga de manejar el evento click del botón de inicio de sesión y verificar que el usuario y la contraseña estén introducidos.
// Si los campos están llenos, se realiza una petición al servlet GestorUsuario con la acción iniciarSesion y se envían los valores del usuario y la contraseña.
// Si la respuesta del servlet indica que el usuario está logueado, se llama al método comprobarLogin del servlet para comprobar que se ha logueado correctamente.
// Si la respuesta del servlet de comprobarLogin es correcta, se visualiza el nombre del usuario y el botón de cerrar sesión.
// Además, se llama al método verificarPermiso del servlet para realizar acciones adicionales en función del permiso del usuario.
// Si el permiso es 99, se muestra un mensaje en la consola indicando que el usuario tiene permiso de administrador.
// Si el permiso es 1, se muestra un mensaje en la consola indicando que el usuario tiene permiso de usuario normal.
// Si el permiso es 2, se muestra un mensaje en la consola indicando que el usuario tiene permiso de moderador.
// Si el permiso es otro valor, se muestra un mensaje en la consola indicando que el usuario no tiene permiso para acceder a la aplicación.

// Función para mostrar mensajes en la consola y redirigir a diferentes páginas
function manejarPermiso(permission) {
    switch (permission) {
      case 99:
        console.log("El usuario tiene permiso de administrador");
        window.location.href = "admin.html";
        break;
      case 1:
        console.log("El usuario tiene permiso de usuario normal");
        window.location.href = "events.html";
        break;
      case 2:
        console.log("El usuario tiene permiso de moderador");
        window.location.href = "moderator.html";
        break;
      default:
        console.log("El usuario no tiene permiso para acceder a la aplicación");
    }
  }
  
  // Función para iniciar la sesión del usuario
  function iniciarSesion(usuario, contrasena) {
    const data = new URLSearchParams();
    data.append("action", "iniciarSesion");
    data.append("usuario", usuario);
    data.append("contrasena", contrasena);
  
    fetch("GestorUsuario", {
      method: "POST",
      body: data,
    })
      .then((response) => response.json())
      .then((data) => {
        console.log("Datos recibidos del servidor (iniciarSesion):", data); // Aquí visualizamos los datos recibidos
        if (data.logged) {
          console.log("Usuario logueado.");
          alert("Usuario logueado.");
  
          // Llamamos al método comprobarLogin del servlet para comprobar que se ha logueado correctamente
          fetch(
            `GestorUsuario?action=comprobarLogin&userName=${data.userName}&permiso=${data.permiso}`
          )
            .then((response) => response.json())
            .then((data) => {
              console.log("Datos recibidos del servidor (comprobarLogin):", data); // Aquí visualizamos los datos recibidos
              if (data.success) {
                console.log("Login comprobado.");
  
                // Mostramos el nombre del usuario y el botón de cerrar sesión
                document.getElementById("userInfoContainer").style.display =
                  "block";
                document.getElementById("nombreUsuario").textContent =
                  data.userName;
                document.getElementById(
                  "PermisoUsuario"
                ).textContent = `Permiso: ${data.permiso}`;
                console.log("Nombre de usuario y permiso mostrados.");
  
                // Llamamos al método verificarPermiso del servlet para realizar acciones adicionales en función del permiso del usuario
                fetch(
                  `GestorUsuario?action=verificarPermiso&permiso=${data.permiso}`
                )
                  .then((response) => response.json())
                  .then((data) => {
                    console.log(
                      "Datos recibidos del servidor (verificarPermiso):",
                      data
                    ); // Aquí visualizamos los datos recibidos
                    if (data.success) {
                      console.log("Permiso verificado.");
                      manejarPermiso(data.permiso);
                    } else {
                      alert("Error al verificar el permiso.");
                    }
                  })
                  .catch((error) => console.error(error));
              } else {
                alert("Error al comprobar el login.");
              }
            })
            .catch((error) => console.error(error));
        } else {
          alert("Usuario o contraseña incorrectos.");
        }
      })
      .catch((error) => console.error(error));
  }
  
  // Agregar evento al botón de inicio de sesión
  document
    .getElementById("botonIniciarSesion")
    .addEventListener("click", function (event) {
      event.preventDefault(); // Evitar comportamiento por defecto del botón
  
      iniciarSesion(
        document.getElementById("inputUsuario").value,
        document.getElementById("inputContrasena").value
      );
    });
  