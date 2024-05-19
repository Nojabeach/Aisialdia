document.addEventListener('DOMContentLoaded', function() {
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
            .then(response => response.json())
            .then(data => {
                // Manejar la respuesta del servidor
                console.log('Actividad creada:', data);
                
                // Llamar a AD_obtenerActividades después de crear para actualizar
                AD_obtenerActividades();
            })
            .catch(error => {
                console.error('Error:', error);
            });
        });
    }
});