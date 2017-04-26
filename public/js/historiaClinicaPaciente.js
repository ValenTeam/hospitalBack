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


    var settings = {
        "async": true,
        "crossDomain": true,
        "url": "http://localhost:9000/medicion/"+patient.id,
        "method": "GET",
        "headers": {
            "x-auth-token": "8m8d443kvru74uu7fe",
            "cache-control": "no-cache",
            "postman-token": "49680c9d-db45-01b0-1afa-83ec700409d3"
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
            "url": "http://localhost:9000/sendConcejo/58e7dacda85b7428acd03abb/58e71a16a85b741ba5bb5a2c",
            "method": "POST",
            "headers": {
                "content-type": "application/json",
                "cache-control": "no-cache",
                "postman-token": "29b39ae6-18e2-cfc7-7339-41267f5eaa94"
            }
        }

        $.ajax(settings).done(function (response) {
            swal(
                'Buen trabajo!',
                'El concejo fue enviado exitosamente',
                'success'
            )
        });
    }
});
