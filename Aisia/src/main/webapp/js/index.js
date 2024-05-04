// Resumen: 
//1 . Este script obtiene los últimos eventos del servlet y los muestra en el aside de la página index.html.
//2 . Comprueba si el usuario está logueado y muestra el nombre del usuario y un botón para cerrar sesión si es así.
//3 . Si el usuario no está logueado, muestra el formulario de login.
//4 . Si el usuario se registra, redirige a la página de inicio de sesión.
//5 . Obtiene el permiso del usuario desde la base de datos.
//6 . Si el usuario tiene permiso, muestra un mensaje de bienvenida con su nombre y permiso.
//7 . Si el usuario no tiene permiso, muestra un mensaje de error.
//8 . Muestra la navbar correspondiente al permiso del usuario.
//9 . Si el usuario cierra sesión, muestra de nuevo el formulario de login.
//10 . Si hay un error, lo muestra en la consola.

// Simulación de datos de eventos
const eventosSimulados = [
  { nombre: "Evento 1", tipoActividad: "Conferencia" },
  { nombre: "Evento 2", tipoActividad: "Taller" },
  { nombre: "Evento 3", tipoActividad: "Concierto" }
];

// Espera a que el DOM esté completamente cargado antes de ejecutar el código
document.addEventListener("DOMContentLoaded", function () {
  const eventosList = document.getElementById("ultimosEventosList");

  // Obtener los datos de los últimos eventos del servlet
  fetch("UltimosEventos")
      .then((response) => {
          // Si la respuesta del servidor es un éxito, usar los datos del servidor
          if (response.ok) {
              return response.json();
          } else {
              // Si hay algún error al obtener los datos del servidor, usar los datos simulados
              throw new Error("Error al obtener los datos del servlet");
          }
      })
      .then((data) => {
          // Limpiar la lista antes de agregar los nuevos eventos
          eventosList.innerHTML = '';
          // Recorrer cada evento obtenido y crear elementos para mostrarlos en el aside
          data.forEach((evento) => {
              const li = document.createElement("li");
              const img = document.createElement("img");
              img.src = `./img/Iconos/${evento.tipoActividad.toLowerCase()}.png`;
              img.alt = `Icono ${evento.tipoActividad}`;
              const a = document.createElement("a");
              a.href = "#";
              a.textContent = evento.nombre;
              li.appendChild(img);
              li.appendChild(a);
              eventosList.appendChild(li);
          });
      })
      .catch((error) => {
          console.error("Error al obtener los últimos eventos del servlet:", error);
          // Si hay algún error al obtener los datos del servlet, usar los datos simulados
          // Limpiar la lista antes de agregar los nuevos eventos
          eventosList.innerHTML = '';
          // Recorrer cada evento simulado y crear elementos para mostrarlos en el aside
          eventosSimulados.forEach((evento) => {
              const li = document.createElement("li");
              const img = document.createElement("img");
              img.src = `./img/Iconos/${evento.tipoActividad.toLowerCase()}.png`;
              img.alt = `Icono ${evento.tipoActividad}`;
              const a = document.createElement("a");
              a.href = "#";
              a.textContent = evento.nombre;
              li.appendChild(img);
              li.appendChild(a);
              eventosList.appendChild(li);
          });
      });

  // Llamar a comprobarLogin
  fetch("GestorUsuario?action=comprobarLogin")
      .then((response) => response.json()) // Parsear la respuesta como JSON
      .then((data) => {
          const loginForm = document.getElementById("LoginForm");
          if (data.logged) {
              // Si el usuario está logueado
              const userName = data.userName;
              const loginFormContainer = document.getElementById("loginFormContainer");

              // Ocultar el formulario de login
              loginForm.style.display = "none";

              // Crear un elemento para mostrar el nombre del usuario y el botón de cerrar sesión
              const userInfoElement = document.createElement("div");
              userInfoElement.innerHTML = `
                  <p>Bienvenido, ${userName}</p>
                  <button id="logoutButton">Cerrar sesión</button>
              `;
              loginFormContainer.appendChild(userInfoElement);

              // Agregar evento al botón de cerrar sesión
              const logoutButton = document.getElementById("logoutButton");
              logoutButton.addEventListener("click", () => {
                  // Llamar al servlet para cerrar sesión
                  fetch("GestorUsuario?action=cerrarSesion")
                      .then((response) => response.text())
                      .then((data) => {
                          // Mostrar de nuevo el formulario de login
                          loginForm.style.display = "block";
                          userInfoElement.remove();
                      })
                      .catch((error) => console.error(error));
              });

              // Obtener el permiso del usuario
              fetch("GestorUsuario?action=verificarPermiso")
                  .then((response) => response.json())
                  .then((data) => {
                      const permiso = data.permiso;
                      const userPermisoElement = document.getElementById("userPermiso");
                      userPermisoElement.textContent = `Permiso: ${permiso}`;

                      // Mostrar la navbar correspondiente al permiso del usuario
                      const navbar = document.getElementById("navbar");
                      if (permiso === "administrador") {
                          navbar.innerHTML = `
                              <ul>
                                  <li><a href="index.html">Inicio</a></li>
                                  <li><a href="admin.html">Administrador</a></li>
                              </ul>
                          `;
                      } else if (permiso === "moderador") {
                          navbar.innerHTML = `
                              <ul>
                                  <li><a href="index.html">Inicio</a></li>
                                  <li><a href="mod.html">Moderador</a></li>
                              </ul>
                          `;
                      }
                  })
                  .catch((error) => console.error(error));

          } else {
              // Si el usuario no está logueado
              // Mostrar el formulario de login
              loginForm.style.display = "block";
          }
      })
      .catch((error) => console.error(error));
});
