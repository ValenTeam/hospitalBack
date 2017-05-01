$(document).ready(function() {

    var listaPacientes;
    var token = JSON.parse(window.localStorage.getItem('user'));
    var table = $('#tablaPacientes').DataTable({
        select:         true
    });
    var settings = {
        "async": true,
        "crossDomain": true,
        "url": "/medicos/"+token.userId+"/pacientes",
        "method": "GET",
        "headers": {
            "x-auth-token": token.token,
            "cache-control": "no-cache"
        }
    }

    $.ajax(settings).done(function (data) {
        listaPacientes = data;
        for(var i=0; i<data.length; i++) {
            var paciente = data[i];
            var state;
            if (paciente.estado == "ROJO")
                state = "danger";
            else if (paciente.estado == "AMARILLO")
                state = "warning";
            else if (paciente.estado == "VERDE")
                state = "success";
            table.row.add([paciente.cedula, paciente.name+" "+paciente.apellido, paciente.marcapasosActual]).draw().nodes().to$().addClass(state);
            table.draw();
        }
    });

    $('#tablaPacientes tbody').on('click', 'tr', function () {
        var data = table.row( this ).index()
        var txt = JSON.stringify(listaPacientes[data]);
        window.localStorage.setItem("paciente", txt);
        window.location.href ="/pages/historiaClinica.html";s
    } );

    // Enable pusher logging - don't include this in production
    Pusher.logToConsole = true;

    var pusher = new Pusher('9000a2bfc63c687333a0', {
        encrypted: true
    });

    var channel = pusher.subscribe(token.userId);
    channel.bind('my-event', function(data) {
        console.log(data);
        swal({
            title: 'ALERTA',
            text: "Un paciente ha generado una alerta roja",
            type: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#2ECC71',
            cancelButtonColor: '#F22613',
            confirmButtonText: 'Ver perfil paciente',
            cancelButtonText: 'Ignorar'
        }).then(function () {
            for(var i=0; i < listaPacientes.length; i++){
                if (listaPacientes[i].id == data.patient){
                    var txt = JSON.stringify(listaPacientes[i]);
                    window.localStorage.setItem("paciente", txt);
                    window.location.href ="/pages/historiaClinica.html";
                }
            }
        })
    });

});

