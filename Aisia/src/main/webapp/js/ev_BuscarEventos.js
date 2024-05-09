function busquedaEventos(criterio){
		
    fetch('GestorEvento?action=buscarEventos&criterio='+criterio)
        .then(response => response.json())
        .then(data => pintarTabla(data))
    
}


document.addEventListener("DOMContentLoaded", function() {
    let searchOption = document.getElementById("search-option");
    searchOption.addEventListener("change", function() {
        busquedaEventos(this.value);
    });

});