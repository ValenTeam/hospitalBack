
$(document).ready(function()    {
    var source = "localhost:9000";
    var table = $('#tablaPacientes').DataTable();

    $.get("http://"+source+"/pacientes",function(data, status) {
        console.log(status);
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


    });

});

