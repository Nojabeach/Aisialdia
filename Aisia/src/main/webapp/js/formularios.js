function llamada(servlet, action, op, formularioId) {
    fetch(`${servlet}?action=${action}&id=${op}`)
    .then(response => {
        if (!response.ok) {
            throw new Error('Error al cargar los datos');
        }
        return response.json();
    })
    .then(data => {
        llenarFormulario(data, formularioId);
    })
    .catch(error => {
        console.error('Error:', error);
    });
}

function llenarFormulario(data, formularioId) {
    // Obtener el formulario
    let form = document.getElementById(formularioId);

    // Llenar los campos del formulario con los datos recibidos
    Object.keys(data).forEach(key => {
        let element = form.elements[key];
        if (element) {
            element.value = data[key];
        }
    });
}

function getParameterByName(name) {
    name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
    var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
    results = regex.exec(location.search);
    return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
}

function validarFormulario(formularioId) {
    let form = document.getElementById(formularioId);
    let inputs = form.querySelectorAll('input, textarea');
    let ok = true;

    inputs.forEach(input => {
        if (input.value.trim() === "") {
            ok = false;
            input.style.background = "red";
        } else {
            input.style.background = "";
        }
    });

    if (ok) {
        form.submit();
    }
}
