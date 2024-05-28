document.addEventListener('DOMContentLoaded', function () {
    // Función para mostrar la pestaña de actividades activas
    function mostrarActividadesActivas() {
        const tabActividadesActivas = document.getElementById('actividades-activas');
        if (tabActividadesActivas) {
            console.log('Tab actividades activas encontrada, haciendo click');
            tabActividadesActivas.click();
        } else {
            console.log('Tab actividades activas no encontrada');
        }
    }

    // Agregar listener al formulario de creación de actividades
    const actividadesForm = document.getElementById('actividadesForm');
    if (actividadesForm) {
        console.log('Formulario de actividades encontrado');

        // Agregar listener para el input de tipo file
        const crearACfotoActividad = document.getElementById('crearACfotoActividad');
        const imgPhoto = document.getElementById('img-photo');
        const deletePhoto = document.getElementById('delete-photo');

        crearACfotoActividad.addEventListener('change', function () {
            const file = this.files[0];
            const reader = new FileReader();
            reader.onload = function (event) {
                const img = new Image();
                img.onload = function () {
                    imgPhoto.src = event.target.result;
                    imgPhoto.style.width = '100px';
                    imgPhoto.style.height = '100px';
                    imgPhoto.style.borderRadius = '25%';
                    imgPhoto.style.border = '1px solid #ccc';
                    imgPhoto.style.display = 'block';
                    deletePhoto.style.display = 'block';
                };
                img.src = event.target.result;
            };
            reader.readAsDataURL(file);
        });

        deletePhoto.addEventListener('click', function () {
            crearACfotoActividad.value = ''; // Resetear el input file
            imgPhoto.src = ''; // Resetear la preview de la imagen
            imgPhoto.style.display = 'none'; // Ocultar la preview de la imagen
            deletePhoto.style.display = 'none'; // Ocultar el botón eliminar
        });

        actividadesForm.addEventListener('submit', function (event) {
            event.preventDefault(); // Previene el envío por defecto del formulario

            console.log('Formulario de actividades enviado');

            const formData = new FormData(actividadesForm);

            // Enviar la solicitud de creación de actividad
            fetch(actividadesForm.action, {
                method: 'POST',
                body: formData
            })
                .then(response => {
                    console.log('Respuesta recibida de la solicitud de creación de actividad');
                    if (response.ok) {
                        // Si la respuesta es exitosa (status code 200-299)
                        console.log('Actividad creada exitosamente.');

                        // Llamar a AD_obtenerActividades después de crear para actualizar
                        AD_obtenerActividades();

                        // Mostrar la pestaña de actividades activas
                        mostrarActividadesActivas();
                    } else {
                        // Si la respuesta no es exitosa
                        console.error('Error al crear la actividad:', response.statusText);
                    }
                })
                .catch(error => {
                    console.error('Error en la solicitud de creación de actividad:', error);
                });
        });
    } else {
        console.log('Formulario de actividades no encontrado');
    }

    // Agregar listener al formulario de edición de actividades
    const editarForm = document.getElementById('EDITactividadesForm');
    if (editarForm) {
        console.log('Formulario de edición de actividades encontrado');
        editarForm.addEventListener('submit', function (event) {
            event.preventDefault(); // Previene el envío por defecto del formulario

            console.log('Formulario de edición de actividades enviado');

            const formData = new FormData(editarForm);

            // Enviar la solicitud de edición de actividad
            fetch(editarForm.action, {
                method: 'POST',
                body: formData
            })
                .then(response => {
                    console.log('Respuesta recibida de la solicitud de edición de actividad');
                    if (response.ok) {
                        // Si la respuesta es exitosa (status code 200-299)
                        console.log('Actividad editada exitosamente.');

                        // Llamar a AD_obtenerActividades después de editar para actualizar
                        AD_obtenerActividades();

                        // Mostrar la pestaña de actividades activas
                        mostrarActividadesActivas();
                    } else {
                        // Si la respuesta no es exitosa
                        console.error('Error al editar la actividad:', response.statusText);
                    }
                })

                .catch(error => {
                    console.error('Error en la solicitud de edición de actividad:', error);
                });
        });
    } else {
        console.log('Formulario de edición de actividades no encontrado');
    }
});