
  document.addEventListener('DOMContentLoaded', function() {
    console.log("Cargando Actividades");
    EDITobtenerActividades();
  });
  function EDITobtenerActividades() {
    console.log("Obteniendo Actividades para el select de Editar Actividad");
    fetch("GestorActividad?action=visualizarActividades")
      .then((response) => {
        if (!response.ok) {
          throw new Error("Hubo un problema al obtener los datos.");
        }
        console.log("Actividades obtenidas correctamente");
        return response.json();
      })
      .then((data) => EDITpintarSelect(data))
      .catch((error) => console.error("Error al obtener las actividades:", error));
  }
  
  function EDITpintarSelect(datos) {
    let select = document.getElementById("EvEDITtipoActividad");
  
  
    select.innerHTML = ""; // Limpiar el select antes de agregar nuevas opciones
  
    datos.forEach(actividad => {
      let option = document.createElement("option");
      option.value = actividad.idActividad;
      option.textContent = actividad.tipoActividad;
  
      // Guardar idActividad, tipoActividad y fotoActividad en atributos personalizados
      option.setAttribute("data-id", actividad.idActividad);
      option.setAttribute("data-tipo", actividad.tipoActividad);
  
  
      select.appendChild(option);
    });
  
  }
  

  