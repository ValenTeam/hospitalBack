/**
 * Created by felipeplazas on 4/23/17.
 */
$(document).ready(function () {
    var patient = localStorage.getItem("patient");
    patient = JSON.parse(patient);
    $("#antecedentesPaciente").append(patient.antecedentes);
    $("#patientName").append(patient.name + " " + patient.apellido);
    $("#patiendEmail").append(patient.email);
    $("#patientAddress").append(patient.address);
    $("#patientId").append(patient.cedula);
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

            chart.draw(data, options);
            chart2.draw(data2, options);
            chart3.draw(data3, options);
        });
    }
});
