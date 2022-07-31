window.addEventListener('load', function () {

    // Al cargar la pagina buscamos y obtenemos el formulario donde estarán
    // los datos que el usuario cargará del nuevo odontólogo
    const formulario = document.querySelector('#add_new_odontologo');

    // Ante un submit del formulario se ejecutará la siguiente funcion
    formulario.addEventListener('submit', function (event) {
        
        // Creamos un JSON que tendrá los datos del nuevo odontólogo
        const formData = {
            apellido: document.querySelector('#apellido').value,
            nombre: document.querySelector('#nombre').value,
            matricula: document.querySelector('#matricula').value,
        };

        // Invocamos utilizando la función fetch la API odontólogos con el método POST que guardará
        // el odontólogo que enviaremos en formato JSON
        const url = '/odontologos';
        const settings = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(formData)
        }

        fetch(url, settings)
            .then(response => response.json())
            .then(data => {
                 // Si no hay ningún error se muestra un mensaje diciendo que el odontólogo
                 // se agregó bien
                let successAlert = '<div class="alert alert-success alert-dismissible">' +
                    '<button type="button" class="close" data-dismiss="alert">&times;</button>' +
                    '<strong> Odontologo agregado </strong></div>'

                document.querySelector('#response').innerHTML = successAlert;
                document.querySelector('#response').style.display = "block";
                resetUploadForm();
            })
            .catch(error => {
                    // Si hay algún error se muestra un mensaje diciendo que el odontólogo
                    // no se pudo guardar y se intente nuevamente
                    let errorAlert = '<div class="alert alert-danger alert-dismissible">' +
                        '<button type="button" class="close" data-dismiss="alert">&times;</button>' +
                        '<strong> Error intente nuevamente</strong> </div>'

                    document.querySelector('#response').innerHTML = errorAlert;
                    document.querySelector('#response').style.display = "block";
                    // Se dejan todos los campos vacíos por si se quiere ingresar otro odontólogo
                    resetUploadForm();
            })
    });


    function resetUploadForm(){
        document.querySelector('#apellido').value = "";
        document.querySelector('#nombre').value = "";
        document.querySelector('#matricula').value = "";
    }

    (function(){
        let pathname = window.location.pathname;
        if(pathname === "/"){
            document.querySelector(".nav .nav-item a:first").addClass("active");
        } else if (pathname == "/get_all_odontologos.html") {
            document.querySelector(".nav .nav-item a:last").addClass("active");
        }
    })();
    
});