document.addEventListener("DOMContentLoaded", function () {
    const searchOption = document.getElementById("search-option");
    const searchInput = document.getElementById("search");

    // Función para cambiar el tipo de input según la opción seleccionada
    function cambiarTipoInput() {
        const seleccion = searchOption.value;
        const searchInputsContainer = document.getElementById("search-inputs");
        searchInputsContainer.innerHTML = ""; // Limpiar el contenido actual

        if (seleccion === "fechaEvento") {
            const inputDate = document.createElement("input");
            inputDate.type = "date";
            inputDate.id = "search";
            inputDate.name = "search";
            inputDate.value = "2024-05-01";
            searchInputsContainer.appendChild(inputDate);
        } else {
            const inputText = document.createElement("input");
            inputText.type = "text";
            inputText.id = "search";
            inputText.name = "search";
            searchInputsContainer.appendChild(inputText);
        }
    }

    // Ejecutar la función al cargar y al cambiar la opción
    cambiarTipoInput();
    searchOption.addEventListener("change", cambiarTipoInput);
});
