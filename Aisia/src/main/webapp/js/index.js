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

// Función que inicia la sesión del usuario
function iniciarSesion(usuario, contrasena) {
  // Realizamos una petición al servlet GestorUsuario con la acción iniciarSesion y los valores del usuario y la contraseña
  fetch(`GestorUsuario?action=iniciarSesion&usuario=${usuario}&contrasena=${contrasena}`)
      .then(response => response.json())
      .then(data => {
          // Si el usuario está logueado
          if (data.logged) {
              // Llamamos al método comprobarLogin del servlet para comprobar que se ha logueado correctamente
              fetch(`GestorUsuario?action=comprobarLogin&userName=${data.userName}&permiso=${data.permiso}`)
                  .then(response => response.json())
                  .then(data => {
                      // Si la respuesta del servlet de comprobarLogin es correcta
                      if (data.success) {
                          // Mostramos el nombre del usuario y el botón de cerrar sesión
                          document.getElementById('userInfoContainer').style.display = 'block';
                          document.getElementById('userName').textContent = data.userName;
                          // Llamamos al método verificarPermiso del servlet para realizar acciones adicionales en función del permiso del usuario
                          fetch(`GestorUsuario?action=verificarPermiso&permiso=${data.permiso}`)
                              .then(response => response.json())
                              .then(data => {
                                  // Si la respuesta del servlet es correcta
                                  if (data.success) {
                                      // Verificamos el permiso del usuario
                                      if (data.permiso === 99) {
                                          console.log('El usuario tiene permiso de administrador');
                                      } else if (data.permiso === 1) {
                                          console.log('El usuario tiene permiso de usuario normal');
                                      } else if (data.permiso === 2) {
                                          console.log('El usuario tiene permiso de moderador');
                                      } else {
                                          console.log('El usuario no tiene permiso para acceder a la aplicación');
                                      }
                                  } else {
                                      alert('Error al verificar el permiso.');
                                  }
                              })
                              .catch(error => console.error(error));
                      } else {
                          alert('Error al comprobar el login.');
                      }
                  })
                  .catch(error => console.error(error));
          } else {
              alert('Usuario o contraseña incorrectos.');
          }
      })
      .catch(error => console.error(error));
}

