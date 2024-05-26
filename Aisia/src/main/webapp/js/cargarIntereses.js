document.addEventListener("DOMContentLoaded", () => {
  // Obtener el elemento select donde se cargarán los intereses
  const interesesSelect = document.getElementById("intereses");
  const interesesEditarSelect = document.getElementById("intereses-editar");
  const interesesCrearSelect = document.getElementById("intereses-crear");

  // Realizar una solicitud Fetch al servidor para obtener los datos de intereses
  fetch("GestorIntereses?accion=listar")
    .then((response) => response.json())
    .then((data) => {
      // Iterar sobre los datos y agregar cada nombre de interés como una opción en el select
      data.forEach((nombreInteres) => {
        const option = new Option(nombreInteres, nombreInteres);

        if (interesesSelect) {
          interesesSelect.add(option);
          console.log(`Agregado opción "${nombreInteres}" a interesesSelect`);
        }

        if (interesesCrearSelect) {
          interesesCrearSelect.add(option);
          console.log(`Agregado opción "${nombreInteres}" a interesesCrearSelect`);
        }

        if (interesesEditarSelect) {
          interesesEditarSelect.add(option);
          console.log(`Agregado opción "${nombreInteres}" a interesesEditarSelect`);
        }
        
      
      });
    })
    .catch((error) => console.error(error)); // Capturar y mostrar cualquier error que ocurra durante la solicitud
});