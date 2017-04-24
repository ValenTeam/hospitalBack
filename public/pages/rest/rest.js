
$(document).ready(function()    {
    var source = "localhost:9000";
    var table = $('#tablaPacientes').DataTable();

    $.get("http://"+source+"/pacientes",function(data, status) {
        console.log(status);
        for(var i=0; i<data.length; i++) {
            var paciente = data[i];
            if (paciente.estado == "ROJO") {


                table.row.add([paciente.cedula, paciente.name, paciente.apellido, paciente.fechaNacimiento, paciente.marcapasosActual, paciente.presionActual, paciente.estresActual, paciente.frecuenciaActual]).draw().nodes().to$().addClass('danger');
                var allRows = document.getElementsByTagName("tr");
                console.log(allRows)

            } else if (paciente.estado == "AMARILLO") {

                table.row.add([paciente.cedula, paciente.name, paciente.apellido, paciente.fechaNacimiento, paciente.marcapasosActual, paciente.presionActual, paciente.estresActual, paciente.frecuenciaActual]).draw().nodes().to$().addClass('warning');
                //var allRows = document.getElementsByTagName("tr");
                //var lastRow = allRows[i];
                //console.log(i +" "+ paciente.name);
                //lastRow.setAttribute('class', 'warning');
                // $('#tablaPacientes tbody').append('<tr class="warning"><td>' + paciente.cedula + '</td><td>' + paciente.name + '</td><td>' + paciente.apellido + '</td><td>' + paciente.edad + '</td><td>' + paciente.marcapasosActual + '</td><td>' + paciente.presionActual + '</td><td>' + paciente.estresActual + '</td><td>' + paciente.frecuenciaActual + '</td></tr>');
            } else if (paciente.estado == "VERDE") {


                table.row.add([paciente.cedula, paciente.name, paciente.apellido, paciente.fechaNacimiento, paciente.marcapasosActual, paciente.presionActual, paciente.estresActual, paciente.frecuenciaActual]).draw().nodes().to$().addClass('success');
                //var allRows = document.getElementsByTagName("tr");
                //var lastRow = allRows[i];
                //console.log(i +" "+ paciente.name);
                //lastRow.setAttribute('class', 'success');
            }
            table.draw();
        }


    });

});

