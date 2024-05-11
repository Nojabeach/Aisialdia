document.addEventListener("DOMContentLoaded", function () {
    const searchOption = document.getElementById("search-option");
    const searchInputContainer = document.getElementById("search-input");
    const searchInput = document.getElementById("search");

    // Función para cambiar el tipo de input según la opción seleccionada
    function cambiarTipoInput() {
        const seleccion = searchOption.value;
        if (seleccion === "fechaEvento") {
            searchInput.innerHTML = ""; // Limpiar el contenido actual
            const inputDate = document.createElement("input");
            inputDate.type = "date";
            inputDate.id = "search";
            inputDate.name = "search";
            searchInput.appendChild(inputDate);
        } else {
            searchInput.innerHTML = ""; // Limpiar el contenido actual
            const inputText = document.createElement("input");
            inputText.type = "text";
            inputText.id = "search";
            inputText.name = "search";
            searchInput.appendChild(inputText);
        }
    }

    // Ejecutar la función al cargar y al cambiar la opción
    cambiarTipoInput();
    searchOption.addEventListener("change", cambiarTipoInput);
});