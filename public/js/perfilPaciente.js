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

});
