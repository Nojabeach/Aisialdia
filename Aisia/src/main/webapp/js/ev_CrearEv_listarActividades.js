// Llamar a la función al cargar la página
window.onload = function () {
    obtenerActividades();
  };
  
  function  obtenerActividades(){

    fetch('GestorActividad?action=visualizarActividades')
    .then(response => response.json())
    .then(data => pintarSelect(data))
  };

  function pintarSelect(datos) {
    let select = document.getElementById("activity");

    datos.forEach(actividad => {
        let option = document.createElement("option");
        option.value = actividad.idActividad;
        
        // Crear un elemento span para el icono
        let icono = document.createElement("span");
        icono.className = "icon";
        let img = document.createElement("img");
        img.src = `img/Iconos/${actividad.tipoActividad.toLowerCase()}.png`;
        img.alt = `Icono ${actividad.tipoActividad}`;
        icono.appendChild(img);
        
        // Texto de la opción
        let texto = document.createTextNode(` ${actividad.tipoActividad}`);
        
        // Agregar icono y texto a la opción
        option.appendChild(icono);
        option.appendChild(texto);
        
        // Guardar idActividad, tipoActividad y fotoActividad en atributos personalizados
        option.setAttribute("data-id", actividad.idActividad); 
        option.setAttribute("data-tipo", actividad.tipoActividad); 
        option.setAttribute("data-foto", actividad.fotoActividad); 
        select.appendChild(option);
    });
}


