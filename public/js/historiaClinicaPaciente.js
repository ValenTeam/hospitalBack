/**
 * Created by felipeplazas on 4/23/17.
 */
$(document).ready(function()    {
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
        "url": "/medicion/"+patient.id,
        "method": "GET",
        "headers": {
            "x-auth-token": token.token,
            "cache-control": "no-cache"
        }
    }

    function updateConcejos() {
        var table3 = $('#consejosTable').DataTable();
        table3.clear();
        patient.historiaClinica.consejos.forEach(function(consejo){
            var date = new Date(consejo.fecha);
            table3.row.add([consejo.mensaje, date.toLocaleString()]).draw();
        });
    }

    updateConcejos();

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
        }
    });

    function sendConcejo(concejoTxt) {
        var body = {
            "mensaje":concejoTxt,
            "fecha":new Date().getTime()
        }
        var settings = {
            "async": true,
            "crossDomain": true,
            "data":JSON.stringify(body),
            "url": "/sendConcejo/"+patient.id+"/"+token.userId,
            "method": "POST",
            "headers": {
                "content-type": "application/json",
                "cache-control": "no-cache"
            }
        };
        $.ajax(settings).done(function (response) {
            patient.historiaClinica.consejos.push(body);
            $("#concejoTxt").val("");
            updateConcejos();
            swal(
                'Buen trabajo!',
                'El concejo fue enviado exitosamente: \n'+
                response.mensaje,
                'success'
            )
        });
    }

    google.charts.load('current', {'packages':['corechart']});
    google.charts.setOnLoadCallback(drawChart);

    function drawChart() {
        var settings = {
            "async": true,
            "crossDomain": true,
            "url": "http://localhost:9000/medicion/58e7dacda85b7428acd03abb",
            "method": "GET",
            "headers": {
                "x-auth-token": token.token,
                "cache-control": "no-cache"
            }
        }

        $.ajax(settings).done(function (response) {
            var array = [];
            array.push(['Date', 'Value']);
            response.forEach(function (medicion) {
                var date = new Date(medicion.openTimestamp);
                array.push([date.toLocaleString(), medicion.valorMedicion]);
            });
            console.log(response);
            var data = google.visualization.arrayToDataTable(array);
            var options = {
                curveType: 'function',
                legend: { position: 'bottom' }
            };

            var chart = new google.visualization.LineChart(document.getElementById('curve_chart'));
            var chart2 = new google.visualization.LineChart(document.getElementById('curve_chart2'));

            chart.draw(data, options);
            chart2.draw(data, options);
        });
    }
});
