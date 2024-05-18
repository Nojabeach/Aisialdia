document.addEventListener("DOMContentLoaded", function() {

    const actividadesNavbar = document.getElementById('actividades-navbar');
    const actividadesTabs = document.querySelectorAll('.actividades-tab');

    // Ocultar todas las pestañas excepto la primera al cargar la página
    actividadesTabs.forEach(tab => {
        if (!tab.classList.contains('actividades-crear')) {
            tab.style.display = 'none';
        }
    });

    // Agregar evento de clic a los elementos de la barra de navegación de actividades
    actividadesNavbar.addEventListener('click', function(e) {
        e.preventDefault();
        if (e.target.tagName === 'A') {
            const targetTabId = e.target.getAttribute('href').substring(1);
            const targetTab = document.getElementById(targetTabId);

            // Si la pestaña ya está visible, ocultarla, de lo contrario, mostrarla
            if (targetTab.style.display === 'block') {
                targetTab.style.display = 'none';
            } else {
                // Mostrar la pestaña seleccionada y ocultar las demás
                actividadesTabs.forEach(tab => {
                    if (tab.id === targetTabId) {
                        tab.style.display = 'block';
                    } else {
                        tab.style.display = 'none';
                    }
                });
            }

            // Mostrar u ocultar el botón de "Crear Actividad" y "Editar Actividad" según la pestaña seleccionada
            if (targetTabId === 'actividades-crear') {
                document.getElementById('crear-actividad-button').style.display = 'block';
                document.getElementById('editar-actividad-button').style.display = 'none';
            } else {
                document.getElementById('crear-actividad-button').style.display = 'none';
                document.getElementById('editar-actividad-button').style.display = 'block';
            }
        }
    });

    // Agregar evento de clic al botón "Editar" en la tabla dinámica
    const editarBotones = document.querySelectorAll('.editar-actividad-button');
    editarBotones.forEach(boton => {
        boton.addEventListener('click', function() {
            const actividadId = this.getAttribute('data-actividad-id');
            const actividadNombre = this.getAttribute('data-actividad-nombre');
            const actividadDetalles = this.getAttribute('data-actividad-detalles');

            // Llenar el formulario con los datos de la actividad
            document.getElementById('idActividad').value = actividadId;
            document.getElementById('name').value = actividadNombre;
            document.getElementById('details').value = actividadDetalles;

            // Mostrar el formulario y ocultar el botón "Crear Actividad"
            document.getElementById('activity-form').style.display = 'block';
            document.getElementById('crear-actividad-button').style.display = 'none';
            document.getElementById('editar-actividad-button').style.display = 'block';
        });
    });
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    const eventosNavbar = document.getElementById('eventos-navbar');
    const eventoTabs = document.querySelectorAll('.evento-tab');

    // Ocultar todas las pestañas excepto la primera al cargar la página
    eventoTabs.forEach(tab => {
        if (!tab.classList.contains('eventos-crear')) {
            tab.style.display = 'none';
        }
    });

    // Agregar evento de clic a los elementos de la barra de navegación de eventos
    eventosNavbar.addEventListener('click', function(e) {
        e.preventDefault();
        if (e.target.tagName === 'A') {
            const targetTabId = e.target.getAttribute('href').substring(1);
            const targetTab = document.getElementById(targetTabId);

            // Si la pestaña ya está visible, ocultarla, de lo contrario, mostrarla
            if (targetTab.style.display === 'block') {
                targetTab.style.display = 'none';
            } else {
                // Mostrar la pestaña seleccionada y ocultar las demás
                eventoTabs.forEach(tab => {
                    if (tab.id === targetTabId) {
                        tab.style.display = 'block';
                    } else {
                        tab.style.display = 'none';
                    }
                });
            }
        }
    });

    const usuariosNavbar = document.getElementById('usuarios-navbar');
    const usuariosTabs = document.querySelectorAll('.usuarios-tab');

    // Ocultar todas las pestañas excepto la primera al cargar la página
    usuariosTabs.forEach(tab => {
        if (!tab.classList.contains('usuarios-crear')) {
            tab.style.display = 'none';
        }
    });

    // Agregar evento de clic a los elementos de la barra de navegación de usuarios
    usuariosNavbar.addEventListener('click', function(e) {
        e.preventDefault();
        if (e.target.tagName === 'A') {
            const targetTabId = e.target.getAttribute('href').substring(1);
            const targetTab = document.getElementById(targetTabId);

            // Si la pestaña ya está visible, ocultarla, de lo contrario, mostrarla
            if (targetTab.style.display === 'block') {
                targetTab.style.display = 'none';
            } else {
                // Mostrar la pestaña seleccionada y ocultar las demás
                usuariosTabs.forEach(tab => {
                    if (tab.id === targetTabId) {
                        tab.style.display = 'block';
                    } else {
                        tab.style.display = 'none';
                    }
                });
            }
        }
    });

});
