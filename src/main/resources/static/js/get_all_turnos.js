window.addEventListener('load', function () {
    
    listarPacientes();
    listarOdontologos();

    (function(){

    // Con fetch invocamos a la API de turnos con el método GET
    // nos devolverá un JSON con una colección de turnos
    const url = '/turnos';
    const settings = {
        method: 'GET'
    }

    fetch(url,settings)
        .then(response => response.json())
        .then(data => {
            // Recorremos la colección de odontólogos del JSON
            for(turno of data){
                // Por cada turno armaremos una fila de la tabla
                // cada fila tendrá un id que luego nos permitirá borrar la fila si eliminamos el turno
                var table = document.getElementById("turnoTable");
                var turnoRow = table.insertRow();
                let tr_id = 'tr_' + turno.id;
                turnoRow.id = tr_id;

                // Por cada turno creamos un boton delete que agregaremos en cada fila para poder eliminar 
                // el mismo. Dicho botón invocará a la funcion de javascript deleteByKey que se encargará
                // de llamar a la API para eliminar un turno
                let deleteButton = '<button' +
                    ' id=' + '\"' + 'btn_delete_' + turno.id + '\"' +
                    ' type="button" onclick="deleteBy('+turno.id+')" class="btn btn-danger btn_delete">' +
                    '&times' +
                    '</button>';

                // Por cada turno creamos un botón que muestra el id y que al hacerle clic invocará
                // a la función de java script findBy que se encargará de buscar el turno que queremos
                // modificar y mostrar los datos del mismo en un formulario.
                let updateButton = '<button' +
                    ' id=' + '\"' + 'btn_id_' + turno.id + '\"' +
                    ' type="button" onclick="findBy('+turno.id+')" class="btn btn-info btn_id">' +
                    turno.id +
                    '</button>';

                // Armamos cada columna de la fila
                // como primer columna pondremos el boton modificar
                // luego los datos del odontólogo
                // como ultima columna el boton eliminar
                turnoRow.innerHTML = '<td>' + updateButton + '</td>' +
                    '<td class=\"td_paciente\">' + turno.paciente.apellido.toUpperCase() + ' ' + turno.paciente.nombre.toUpperCase() + '</td>' +
                    '<td class=\"td_odontologo\">' + turno.odontologo.apellido.toUpperCase() + ' ' + turno.odontologo.nombre.toUpperCase() + '</td>' +
                    '<td class=\"td_fecha\">' + turno.fecha + '</td>' +
                    '<td>' + deleteButton + '</td>';
            };

        })

    })

    (function(){

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
                var pacientesList = document.querySelector("#pacientes");
                for(paciente of data){
                    var pacienteOption = '<option value=\"' + paciente.id + '\">' + 
                        paciente.nombre + ' ' + paciente.apellido + 
                        '</option>';
                    pacientesList.innerHTML += pacienteOption;
            };
        })
    })

    (function(){

        // Con fetch invocamos a la API de odontologos con el método GET
        // nos devolverá un JSON con una colección de odontologos
        const url = '/odontologos';
        const settings = {
            method: 'GET'
        }

        fetch(url,settings)
            .then(response => response.json())
            .then(data => {
            // Recorremos la colección de odontologos del JSON
                var odontologosList = document.querySelector("#odontologos");
                for(odontologo of data){
                    var odontologoOption = '<option value=\"' + odontologo.id + '\">' + 
                        odontologo.nombre + ' ' + odontologo.apellido + 
                        '</option>';
                    odontologosList.innerHTML += odontologoOption;
            };
        })
    })

    (function(){
        let pathname = window.location.pathname;
        if (pathname == "/get_all_turnos.html") {
            document.querySelector(".nav .nav-item a:last").addClass("active");
        }
    })


})

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