document.addEventListener("DOMContentLoaded", function () {
    const activitySelect = document.getElementById("activity");

    // Obtener actividades disponibles al cargar la página
    cargarActividades();

    function cargarActividades() {
        fetch("GestorActividad?action=visualizarActividades", {
            method: "POST"
        })
        .then(response => response.json())
        .then(data => {
            data.forEach(actividad => {
                const option = document.createElement("option");
                option.value = actividad.idActividad;
                option.textContent = actividad.nombre;
                activitySelect.appendChild(option);
            });
        })
        .catch(error => {
            console.error("Error al cargar las actividades:", error);
        });
    }
});
