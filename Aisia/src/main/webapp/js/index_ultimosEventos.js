// Llamar a la función al cargar la página
window.onload = function () {
    obtenerUltimosEventos();
  };
  
  function obtenerUltimosEventos() {
    console.log("Obteniendo últimos eventos...");
    fetch("UltimosEventos")
      .then((response) => {
        console.log("Respuesta del servidor:", response);
        if (response.ok) {
          return response.json();
        } else {
          throw new Error("Error al obtener los datos del servlet");
        }
      })
      .then((data) => {
        console.log("Datos obtenidos:", data);
        const eventosList = document.getElementById("ultimosEventosList");
        eventosList.innerHTML = "";
        data.forEach((evento) => {
          console.log("Evento:", evento);
          const li = document.createElement("li");
          const img = document.createElement("img");
          img.src = `img/Iconos/${evento.tipoActividad.toLowerCase()}.png`;
          console.log("img.src:", img.src);
          img.alt = `Icono ${evento.tipoActividad}`;
          const a = document.createElement("a");
          a.href = "#";
          const fecha = new Date(evento.fechaEvento);
          const fechaFormateada = `${fecha.getDate()}/${fecha.getMonth() + 1}/${fecha.getFullYear()}`;
          a.textContent = `${evento.nombre} - ${fechaFormateada}`;
          li.appendChild(img);
          li.appendChild(a);
          eventosList.appendChild(li);
        });
      })
      .catch((error) => console.error(error));
  }
  