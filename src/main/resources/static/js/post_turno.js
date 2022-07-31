window.addEventListener('load', function () {
    
    listarPacientes();
    listarOdontologos();

    // Al cargar la pagina buscamos y obtenemos el formulario donde estarán
    // los datos que el usuario cargará del nuevo turno
    const formulario = document.querySelector('#add_new_turno');

    // Ante un submit del formulario se ejecutará la siguiente funcion
    formulario.addEventListener('submit', function (event) {
        
        // Creamos un JSON que tendrá los datos del nuevo turno
        const formData = {
            paciente: {
                id: document.querySelector('#pacientes').value
            },
            odontologo: {
                id: document.querySelector('#odontologos').value
            },
            fecha: document.querySelector('#fecha').value,
        };

        // Invocamos utilizando la función fetch la API odontólogos con el método POST que guardará
        // el turno que enviaremos en formato JSON
        const url = '/turnos';
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
                 // Si no hay ningún error se muestra un mensaje diciendo que el turno
                 // se agregó bien
                let successAlert = '<div class="alert alert-success alert-dismissible">' +
                    '<button type="button" class="close" data-dismiss="alert">&times;</button>' +
                    '<strong> Turno agregado </strong></div>'

                document.querySelector('#response').innerHTML = successAlert;
                document.querySelector('#response').style.display = "block";
                resetUploadForm();
            })
            .catch(error => {
                    // Si hay algún error se muestra un mensaje diciendo que el turno
                    // no se pudo guardar y se intente nuevamente
                    let errorAlert = '<div class="alert alert-danger alert-dismissible">' +
                        '<button type="button" class="close" data-dismiss="alert">&times;</button>' +
                        '<strong> Error intente nuevamente</strong> </div>'

                    document.querySelector('#response').innerHTML = errorAlert;
                    document.querySelector('#response').style.display = "block";
                    // Se dejan todos los campos vacíos por si se quiere ingresar otro turno
                    resetUploadForm();
            })
    });

    function listarPacientes() {
        // Con fetch invocamos a la API de pacientes con el método GET
        // nos devolverá un JSON con una colección de pacientes
        const url = '/pacientes';
        const settings = {
            method: 'GET'
        }

        fetch(url,settings)
            .then(response => response.json())
            .then(data => {
                // Recorremos la colección de pacientes del JSON
                for(paciente of data) {
                    var pacientesList = document.getElementById("pacientes");
                    var pacienteOption = '<option value=\"' + paciente.id + '\">' + 
                        paciente.nombre + ' ' + paciente.apellido + 
                        '</option>';
                    pacientesList.innerHTML += pacienteOption;
            };
        })
    }

    function listarOdontologos() {
        // Con fetch invocamos a la API de odontologos con el método GET
        // nos devolverá un JSON con una colección de odontologos
        const url = '/odontologos';
        const settings = {
            method: 'GET'
        }

        fetch(url, settings)
            .then(response => response.json())
            .then(data => {
            // Recorremos la colección de odontologos del JSON
                for(odontologo of data){
                    var odontologosList = document.getElementById("odontologos");
                    var odontologoOption = '<option value=\"' + odontologo.id + '\">' + 
                        odontologo.nombre + ' ' + odontologo.apellido + 
                        '</option>';
                    odontologosList.innerHTML += odontologoOption;
            };
        })
    }

    function resetUploadForm(){
        document.querySelector('#pacientes').value = "";
        document.querySelector('#odontologos').value = "";
        document.querySelector('#fecha').value = "";
    }

    (function(){
        let pathname = window.location.pathname;
        if(pathname === "/"){
            document.querySelector(".nav .nav-item a:first").addClass("active");
        } else if (pathname == "/get_all_turnos.html") {
            document.querySelector(".nav .nav-item a:last").addClass("active");
        }
    })();
    
});