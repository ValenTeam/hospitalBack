$(document).ready(function()    {
    var source = "localhost:9000";
    $.get("http://"+source+"/pacientes",function(data, status) {
        console.log(status);
        data.forEach(function(paciente) {
        $('#tablaPacientes tbody').append('<tr class="success"><td>'+paciente.cedula+'</td><td>'+paciente.name+'</td><td>'+paciente.apellido+'</td><td>'+paciente.edad+'</td><td>'+paciente.marcapasosActual+'</td><td>'+paciente.presionActual+'</td><td>'+paciente.estresActual+'</td><td>'+paciente.frecuenciaActual+'</td></tr>');
        });
    });
});
