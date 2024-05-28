document.getElementById('EvEDITtipoActividad').addEventListener('change', function() {
    var seleccionado = this.value;
    let imgPath = `img/Iconos/`; 
    var imagenUrl = {imgPath} + seleccionado + '.png'; // Asumo que las imágenes están en una carpeta llamada "imagenes"
    console.log(imagenUrl);
    document.getElementById('imagen-actividad').innerHTML = '<img src="' + imagenUrl + '" alt="' + seleccionado + '">';
});