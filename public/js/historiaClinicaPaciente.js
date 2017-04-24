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
    console.log(patient)
});
