/**
 * Created by felipeplazas on 4/23/17.
 */
$(document).ready(function()    {
    var host = "http://localhost:9000";
    var patient = localStorage.getItem("paciente");
    patient = JSON.parse(patient);
    $( "#antecedentesPaciente" ).append( patient.antecedentes );
    $( "#patientName" ).append( patient.name + " " + patient.apellido );
    $( "#patiendEmail" ).append( patient.email );
    $( "#patientAddress" ).append( patient.address );
    $( "#patientId" ).append( patient.cedula );
    $( "#leftTittle" ).append( patient.name + " " + patient.apellido );
    var token = JSON.parse(window.localStorage.getItem('user'));

    var settings = {
        "async": true,
        "crossDomain": true,
        "url": host+"/medicion/"+patient.id,
        "method": "GET",
        "headers": {
            "x-auth-token": token.token,
            "cache-control": "no-cache"
        }
    }

    $.ajax(settings).done(function (data) {
        var table2 = $('#medicionesTable').DataTable();
        data.forEach(function(medicion){
            var date = new Date(medicion.openTimestamp);
            table2.row.add([date.toLocaleString(), medicion.valorMedicion, medicion.colorMedicion, medicion.tipoMedicion]).draw();
        });
        table2.draw();
    });

    $("#sendConcejoButton").click( function() {
        if ($("#concejoTxt").val() == undefined || $("#concejoTxt").val() == ''){
                swal(
                    'Oops...',
                    'Debes escribir un concejo para poderlo enviar!',
                    'error'
                )
        }
        else{
            sendConcejo( $("#concejoTxt").val() );
            $("#concejoTxt").val("");
        }
    });

    function sendConcejo(concejoTxt) {
        var body = {
            "msg":concejoTxt
        }
        var settings = {
            "async": true,
            "crossDomain": true,
            "data":JSON.stringify(body),
            "url": host+"/sendConcejo/"+patient.id+"/"+token.userId,
            "method": "POST",
            "headers": {
                "content-type": "application/json",
                "cache-control": "no-cache"
            }
        };
        $.ajax(settings).done(function (response) {
            swal(
                'Buen trabajo!',
                'El concejo fue enviado exitosamente: \n'+
                response.mensaje,
                'success'
            )
        });
    }
});
