/**
 * Created by felipeplazas on 4/23/17.
 */
$(document).ready(function () {
    var patient = localStorage.getItem("paciente");
    patient = JSON.parse(patient);
    $("#antecedentesPaciente").append(patient.antecedentes);
    $("#patientName").append(patient.name + " " + patient.apellido);
    $("#patiendEmail").append(patient.email);
    $("#patientAddress").append(patient.address);
    $("#patientId").append(patient.cedula);
    $("#leftTittle").append(patient.name + " " + patient.apellido);
    var token = JSON.parse(window.localStorage.getItem('user'));

    var settings = {
        "async": true,
        "crossDomain": true,
        "url": "/medicion/" + patient.id,
        "method": "GET",
        "headers": {
            "x-auth-token": token.token,
            "cache-control": "no-cache"
        }
    }

    function updateConcejos() {
        var table3 = $('#consejosTable').DataTable();
        table3.clear();
        patient.historiaClinica.consejos.forEach(function (consejo) {
            var date = new Date(consejo.fecha);
            table3.row.add([consejo.mensaje, date.toLocaleString()]).draw();
        });
    }

    updateConcejos();

    $.ajax(settings).done(function (data) {
        var table2 = $('#medicionesTable').DataTable();
        data.forEach(function (medicion) {
            var date = new Date(medicion.openTimestamp);
            table2.row.add([date.toLocaleString(), medicion.valorMedicion, medicion.colorMedicion, medicion.tipoMedicion]).draw();
        });
        table2.draw();
    });

    $("#sendConcejoButton").click(function () {
        if ($("#concejoTxt").val() == undefined || $("#concejoTxt").val() == '') {
            swal(
                'Oops...',
                'Debes escribir un concejo para poderlo enviar!',
                'error'
            )
        }
        else {
            sendConcejo($("#concejoTxt").val());
        }
    });

    function sendConcejo(concejoTxt) {
        var body = {
            "mensaje": concejoTxt,
            "fecha": new Date().getTime()
        }
        var settings = {
            "async": true,
            "crossDomain": true,
            "data": JSON.stringify(body),
            "url": "/sendConcejo/" + patient.id + "/" + token.userId,
            "method": "POST",
            "headers": {
                "x-auth-token": token.token,
                "content-type": "application/json",
                "cache-control": "no-cache"
            }
        };
        $.ajax(settings)
            .done(function (response) {
                patient.historiaClinica.consejos.push(body);
                $("#concejoTxt").val("");
                updateConcejos();
                swal(
                    'Buen trabajo!',
                    'El concejo fue enviado exitosamente: \n' +
                    response.mensaje,
                    'success'
                )
            })
            .fail(function (xhr, status, error) {
                swal(
                    'Oops...',
                    'ERROR: '+xhr.responseJSON.message,
                    'error'
                )
            });
    }

    google.charts.load('current', {'packages': ['corechart']});
    google.charts.setOnLoadCallback(drawChart);

    function drawChart() {
        var settings = {
            "async": true,
            "crossDomain": true,
            "url": "/medicion/" + patient.id,
            "method": "GET",
            "headers": {
                "x-auth-token": token.token,
                "cache-control": "no-cache"
            }
        }

        $.ajax(settings).done(function (response) {
            var heartRate = [];
            var pressureArray = [];
            var stressArray = [];
            heartRate.push(['Date', 'Frecuencia']);
            stressArray.push(['Date', 'Estrés']);
            pressureArray.push(['Date', 'Presión']);
            response.forEach(function (medicion) {
                var date = new Date(medicion.openTimestamp);
                if (medicion.tipoMedicion == 'ESTRES')
                    stressArray.push([date.toLocaleString(), medicion.valorMedicion]);
                else if (medicion.tipoMedicion == 'PRESION')
                    pressureArray.push([date.toLocaleString(), medicion.valorMedicion]);
                else
                    heartRate.push([date.toLocaleString(), medicion.valorMedicion]);
            });
            var data = google.visualization.arrayToDataTable(heartRate);
            var data2 = google.visualization.arrayToDataTable(pressureArray);
            var data3 = google.visualization.arrayToDataTable(stressArray);
            var options = {
                chartArea: {left: "10%", top: "10%", width: "90%", height: "80%"},
                curveType: 'function',
                legend: {position: 'bottom'}
            };

            var chart = new google.visualization.LineChart(document.getElementById('curve_chart'));
            var chart2 = new google.visualization.LineChart(document.getElementById('curve_chart2'));
            var chart3 = new google.visualization.LineChart(document.getElementById('curve_chart3'));

            $("#loadingSpinner").hide();
            $("#loadingSpinner2").hide();
            $("#loadingSpinner3 ").hide();
            chart.draw(data, options);
            chart2.draw(data2, options);
            chart3.draw(data3, options);
        });
    }

    Pusher.logToConsole = false;

    var pusher = new Pusher('9000a2bfc63c687333a0', {
        encrypted: true
    });

    var channel = pusher.subscribe(token.userId);
    channel.bind('my-event', function(data) {
        console.log(data);
        swal({
            title: 'ALERTA',
            text: "Tu paciente "+ data.name +" "+data.apellido+" ha generado una alerta roja",
            type: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#2ECC71',
            cancelButtonColor: '#F22613',
            confirmButtonText: 'Ver perfil paciente',
            cancelButtonText: 'Ignorar'
        }).then(function () {
            var txt = JSON.stringify(data);
            window.localStorage.setItem("paciente", txt);
            window.location.href ="/pages/historiaClinica.html";
        })
    });

    var settings = {
        "async": true,
        "crossDomain": true,
        "url": "/medicos/id/"+token.userId,
        "method": "GET",
        "headers": {
            "x-auth-token": token.token,
            "cache-control": "no-cache"
        }
    }

    $.ajax(settings).done(function (response) {
        if (response.especialidad != null){
            $("#marcapasosEx").show();
            var settings = {
                "async": true,
                "crossDomain": true,
                "url": "/marcapasos/"+patient.marcapasos,
                "method": "GET",
                "headers": {
                    "x-auth-token": token.token
                }
            }

            $.ajax(settings).done(function (response) {
                $("#amplitud").val(response.amplitud);
                $("#duracion").val(response.duracion);
                $("#sens").val(response.sensibilidad);
            });
        }
    });


    $('#marcapasosForm').submit(function() {
        $("#loadingSpinner4").show();
        var data = {};
        data.amplitud = $("#amplitud").val();
        data.duracion = $("#duracion").val();
        data.sensibilidad = $("#sens").val();
        var settings = {
            "async": true,
            "crossDomain": true,
            "data":JSON.stringify(data),
            "url": "/marcapasos/"+patient.marcapasos,
            "method": "PUT",
            "headers": {
                "content-type": "application/json",
                "x-auth-token": token.token,
                "cache-control": "no-cache"
            }
        }
        $.ajax(settings).done(function (response) {
            swal(
                'Buen trabajo!',
                'El marcapasos fue actualizado exitosamente',
                'success'
            )
            $("#loadingSpinner4").hide();
        });
    });


});
