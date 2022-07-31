window.addEventListener('load', function () {
  (function(){

    // Con fetch invocamos a la API de odontólogos con el método GET
    // nos devolverá un JSON con una colección de odontólogos
    const url = '/odontologos';
    const settings = {
      method: 'GET'
    }

    fetch(url,settings)
      .then(response => response.json())
      .then(data => {
        // Recorremos la colección de odontólogos del JSON
        for(odontologo of data){
          // Por cada odontólogo armaremos una fila de la tabla
          // cada fila tendrá un id que luego nos permitirá borrar la fila si eliminamos el odontólogo
          var table = document.getElementById("odontologoTable");
          var odontologoRow = table.insertRow();
          let tr_id = 'tr_' + odontologo.id;
          odontologoRow.id = tr_id;

          // Por cada odontólogo creamos un boton delete que agregaremos en cada fila para poder eliminar 
          // el mismo. Dicho botón invocará a la funcion de javascript deleteByKey que se encargará
          // de llamar a la API para eliminar un odontólogo
          let deleteButton = '<button' +
            ' id=' + '\"' + 'btn_delete_' + odontologo.id + '\"' +
            ' type="button" onclick="deleteBy('+odontologo.id+')" class="btn btn-danger btn_delete">' +
            '&times' +
            '</button>';

          // Por cada odontólogo creamos un botón que muestra el id y que al hacerle clic invocará
          // a la función de java script findBy que se encargará de buscar el odontólogo que queremos
          // modificar y mostrar los datos del mismo en un formulario.
          let updateButton = '<button' +
            ' id=' + '\"' + 'btn_id_' + odontologo.id + '\"' +
            ' type="button" onclick="findBy('+odontologo.id+')" class="btn btn-info btn_id">' +
            odontologo.id +
            '</button>';

          // Armamos cada columna de la fila
          // como primer columna pondremos el boton modificar
          // luego los datos del odontólogo
          // como ultima columna el boton eliminar
          odontologoRow.innerHTML = '<td>' + updateButton + '</td>' +
            '<td class=\"td_apellido\">' + odontologo.apellido.toUpperCase() + '</td>' +
            '<td class=\"td_nombre\">' + odontologo.nombre.toUpperCase() + '</td>' +
            '<td class=\"td_matricula\">' + odontologo.matricula + '</td>' +
            '<td>' + deleteButton + '</td>';
      };

    })

  })

  (function(){
    let pathname = window.location.pathname;
    if (pathname == "/get_all_odontologos.html") {
      document.querySelector(".nav .nav-item a:last").addClass("active");
    }
  })


  })