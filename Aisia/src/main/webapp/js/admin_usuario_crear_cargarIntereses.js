window.onload = function () {
    // Obtener los select de intereses en el formulario de creación de usuario
    const interesesCrearSelect = document.getElementById("intereses");
    const interesesEditarSelect = document.getElementById("intereses-editar");

    // Realizar una solicitud Fetch al servidor para obtener los datos de intereses
    fetch("GestorIntereses?accion=listar")
        .then((response) => response.json())
        .then((data) => {
            // Iterar sobre los datos y agregar cada nombre de interés como una opción en los select
            data.forEach((nombreInteres) => {
                const option1 = new Option(nombreInteres, nombreInteres);
                const option2 = new Option(nombreInteres, nombreInteres);

                if (interesesCrearSelect) {
                    interesesCrearSelect.add(option1);
                  //  console.log(`Agregado opción "${nombreInteres}" a interesesCrearSelect`);
                }

                if (interesesEditarSelect) {
                    interesesEditarSelect.add(option2);
                  //  console.log(`Agregado opción "${nombreInteres}" a interesesEditarSelect`);
                }
            });
        })
        .catch((error) => console.error(error)); // Capturar y mostrar cualquier error que ocurra durante la solicitud
};