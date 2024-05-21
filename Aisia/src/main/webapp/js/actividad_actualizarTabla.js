document.addEventListener('DOMContentLoaded', function() {
    // Función para mostrar la pestaña de actividades activas
    function mostrarActividadesActivas() {
        const tabActividadesActivas = document.getElementById('actividades-activas');
        if (tabActividadesActivas) {
            tabActividadesActivas.click();
        }
    }

    // Agregar listener al formulario de creación de actividades
    const actividadesForm = document.getElementById('actividadesForm');
    if (actividadesForm) {
        actividadesForm.addEventListener('submit', function(event) {
            event.preventDefault(); // Previene el envío por defecto del formulario
            
            const formData = new FormData(actividadesForm);
            
            // Enviar la solicitud de creación de actividad
            fetch(actividadesForm.action, {
                method: 'POST',
                body: formData
            })
            .then(response => {
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
                console.error('Error en la solicitud:', error);
            });
        });
    }

    // Agregar listener al formulario de edición de actividades
    const editarForm = document.getElementById('EDITactividadesForm');
    if (editarForm) {
        editarForm.addEventListener('submit', function(event) {
            event.preventDefault(); // Previene el envío por defecto del formulario
            
            const formData = new FormData(editarForm);
            
            // Enviar la solicitud de edición de actividad
            fetch(editarForm.action, {
                method: 'POST',
                body: formData
            })
            .then(response => {
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
                console.error('Error en la solicitud:', error);
            });
        });
    }
});
