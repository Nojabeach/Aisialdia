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