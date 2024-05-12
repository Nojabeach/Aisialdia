function cargarFormularioDesdeServlet(servlet, action, op, formularioId, method) {
  console.log("Realizando llamada a:", servlet);
  fetch(`${servlet}?action=${action}&id=${op}`, {
      method: method // Pasar el método de solicitud como atributo
  })
  .then((response) => {
      if (!response.ok) {
          throw new Error("Error al cargar los datos");
      }
      return response.json();
  })
  .then((data) => {
      console.log("Datos recibidos:", data);
      llenarFormulario(data, formularioId);
  })
  .catch((error) => {
      console.error("Error:", error);
  });
}

  
function llenarFormulario(data, formularioId) {
  console.log("Llenando formulario con datos:", data);
  let form = document.getElementById(formularioId);
  Object.keys(data).forEach((key) => {
      let element = form.elements[key];
      if (element) {
          if (element.type === "date") {
              // Para campos de fecha
              element.value = data[key] ? new Date(data[key]).toISOString().split('T')[0] : ""; // Formatear fecha si no es null
          } else if (element.type === "checkbox") {
              // Para campos checkbox
              element.checked = (data[key] === "true");
          } else if (element.tagName === "SELECT") {
              // Para campos select
              let options = element.options;
              for (let i = 0; i < options.length; i++) {
                  if (options[i].value === data[key]) {
                      options[i].selected = true;
                      break;
                  }
              }
          } else {
              // Para otros tipos de campos
              element.value = data[key];
          }
      }
  });
}


  function validarFormulario(formularioId) {
    console.log("Validando formulario:", formularioId);
    let form = document.getElementById(formularioId);
    let inputs = form.querySelectorAll("input, textarea");
    let ok = true;
  
    inputs.forEach((input) => {
      if (input.value.trim() === "") {
        ok = false;
        input.style.background = "rgba(255, 0, 0, 0.1)"; // Fondo rojo claro
      } else {
        input.style.background = "";
      }
    });
  
    if (!ok) {
      console.log("Algunos campos están vacíos o incorrectos.");
    }
  
    return ok;
  }
  function getParameterByName(name) {
    name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
    var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
      results = regex.exec(location.search);
    return results === null
      ? ""
      : decodeURIComponent(results[1].replace(/\+/g, " "));
  }
  