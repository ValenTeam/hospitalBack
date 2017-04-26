$(document).ready(function() {

    var source = "localhost:9000";
    var listaPacientes;
    var table = $('#tablaPacientes').DataTable({
        select:         true
    });
    $.get("http://"+source+"/pacientes",function(data, status) {
        console.log(status);
        listaPacientes = data;
        for(var i=0; i<data.length; i++) {
            var paciente = data[i];
            if (paciente.estado == "ROJO") {

                table.row.add([paciente.cedula, paciente.name, paciente.apellido, paciente.fechaNacimiento, paciente.marcapasosActual, paciente.presionActual, paciente.estresActual, paciente.frecuenciaActual]).draw().nodes().to$().addClass('danger');

            } else if (paciente.estado == "AMARILLO") {

                table.row.add([paciente.cedula, paciente.name, paciente.apellido, paciente.fechaNacimiento, paciente.marcapasosActual, paciente.presionActual, paciente.estresActual, paciente.frecuenciaActual]).draw().nodes().to$().addClass('warning');
            }
            else if (paciente.estado == "VERDE") {
                table.row.add([paciente.cedula, paciente.name, paciente.apellido, paciente.fechaNacimiento, paciente.marcapasosActual, paciente.presionActual, paciente.estresActual, paciente.frecuenciaActual]).draw().nodes().to$().addClass('success');
            }
            table.draw();
        }

        $('#tablaPacientes tbody').on('click', 'tr', function () {
            var data = table.row( this ).index()
            //alert( 'You clicked on '+data+'\'s row' + listaPacientes[data].name );
            var txt = JSON.stringify(listaPacientes[data]);
            window.localStorage.setItem("paciente", txt);
            window.location.href ="/pages/historiaClinica.html";s
        } );
    });

});

