function procesarEnvioFormularioRegistro(evento) {
  console.log("Iniciando procesamiento de envío de formulario de registro...");

  evento.preventDefault(); // Evita que el formulario se envíe automáticamente

  const nombre = obtenerValorCampo("nombre");
  const email = obtenerValorCampo("email");
  const contrasena = obtenerValorCampo("password");
  const fechaNacimiento = obtenerValorCampo("fechaNacimiento");
  const recibeNotificaciones = obtenerValorCheckbox("recibeNotificaciones");
  const intereses = obtenerValorCampo("intereses");
  let consentimientoDatos;
  if (obtenerValorCheckbox("consentimiento-datos")) {
    consentimientoDatos = new Date();
  } else {
    consentimientoDatos = obtenerValorCampo("consentimiento_datos");
  }
  const aceptacionTerminosWeb = obtenerValorCheckbox("terminos-condiciones");

  console.log("Valores obtenidos:", {
    nombre,
    email,
    contrasena,
    fechaNacimiento,
    recibeNotificaciones,
    intereses,
    consentimientoDatos,
    aceptacionTerminosWeb
  });

  const validacion = validarCamposRequeridos([nombre, email, contrasena]);
  if (!validacion.valido) {
    console.log("Validación fallida:", validacion);
    alert(validacion.mensaje);
    return;
  }

  const validacionTerminos = validarTerminosYCondiciones(consentimientoDatos, aceptacionTerminosWeb);
  if (!validacionTerminos) {
    console.log("Validación fallida de términos y condiciones:", validacionTerminos);
    alert("Debes aceptar los términos y condiciones y el consentimiento de datos.");
    return;
  }

  console.log("Validación exitosa, enviando solicitud de registro al servidor...");

  // Elimina roles y permiso de la llamada a la función
  enviarSolicitudRegistroAlServidor(nombre, email, contrasena, fechaNacimiento, recibeNotificaciones, intereses, consentimientoDatos)
    .then(response => {
      if (!response.ok) {
        throw new Error("Hubo un problema con la solicitud");
      }
      // Verificar si la respuesta está vacía
      if (response.status === 204) {
        // Si la respuesta está vacía, devolver un objeto vacío
        return {};
      }
      // Si la respuesta no está vacía, analizarla como JSON
      return response.json();
    })
    .then(data => {
      console.log("Respuesta del servidor:", data);
      if (data.logged) {
        console.log("Registro exitoso, redirigiendo al usuario a eventos.html...");
        window.location.href = "eventos.html";
      } else {
        console.log("Error al registrar usuario:", data);
        alert("Error al registrar usuario");
      }
    })
    .catch(error => console.error(error));
}
