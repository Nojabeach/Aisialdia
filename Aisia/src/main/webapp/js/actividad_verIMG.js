document.addEventListener('DOMContentLoaded', function() {
    document.getElementById('fotoActividad').addEventListener('change', function(event) {
        const file = event.target.files[0];
        if (file) {
            const reader = new FileReader();
            reader.onload = function(e) {
                const imgPhoto = document.getElementById('img-photo');
                imgPhoto.src = e.target.result;
                imgPhoto.width = 100;
                imgPhoto.height = 100;
                imgPhoto.style.display = 'block';
                document.getElementById('delete-photo').style.display = 'inline';
            };
            reader.readAsDataURL(file);
        } else {
            resetPhotoPreview();
        }
    });

    document.getElementById('delete-photo').addEventListener('click', function() {
        document.getElementById('fotoActividad').value = '';
        resetPhotoPreview();
    });



    document.getElementById('EDITfotoActividad').addEventListener('change', function(event) {
        const file = event.target.files[0];
        if (file) {
            const reader = new FileReader();
            reader.onload = function(e) {
                const EDITtipoActividad = document.getElementById('EDITtipoActividad').value;
                const imgPhoto = `img/Iconos/${EDITtipoActividad}.png`;;
                imgPhoto.src = e.target.result;
                imgPhoto.width = 100;
                imgPhoto.height = 100;
                imgPhoto.style.display = 'block';
                document.getElementById('EDITdelete-photo').style.display = 'inline';
            };
            reader.readAsDataURL(file);
        } else {
            EDITresetPhotoPreview();
        }
    });

    document.getElementById('EDITdelete-photo').addEventListener('click', function() {
        document.getElementById('EDITfotoActividad').value = '';
        EDITresetPhotoPreview();
    });


    function resetPhotoPreview() {
        const imgPhoto = document.getElementById('img-photo');
        imgPhoto.src = '#';
        imgPhoto.style.display = 'none';
        document.getElementById('delete-photo').style.display = 'none';
    }

    function EDITresetPhotoPreview() {
        const imgPhoto = document.getElementById('EDITimg-photo');
        imgPhoto.src = '#';
        imgPhoto.style.display = 'none';
        document.getElementById('EDITdelete-photo').style.display = 'none';
    }
});
