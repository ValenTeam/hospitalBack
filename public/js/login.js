/**
 * Created by felipeplazas on 4/25/17.
 */
$(document).ready(function()    {
    var host = "http://localhost:9000";
    $("#loginButton").click( function() {
        if (!verifyInputs()) return;
        var body = {
            "email":$("#emailField").val(),
            "role":$('input[name=optradio]:checked', '#loginForm').val(),
            "password":$("#passwordField").val()
        };
        var settings = {
            "async": true,
            "crossDomain": true,
            "url": host+"/login",
            "method": "POST",
            "data":JSON.stringify(body),
            "headers": {
                "content-type": "application/json",
                "cache-control": "no-cache"
            }
        }

        $.ajax(settings)
            .done(function (response) {
                console.log(response);
                window.localStorage.setItem('user', JSON.stringify(response));
                // Cookies.set('user', response);
                window.location.href = "/pages/index.html";
            })
            .fail(function (xhr, status, error) {
                swal(
                    'Oops...',
                    'ERROR: '+xhr.responseJSON.message,
                    'error'
                )
            });
    });


    function verifyInputs() {
        var email = $("#emailField").val();
        if (email == undefined || email == '' || email.indexOf("@") == -1 || email.indexOf(".co") == -1){
            swal(
                'Oops...',
                'Debes escribir un correo válido',
                'error'
            )
            return false;
        }
        var password = $("#passwordField").val();
        if (password == undefined || password == '' || password.length < 5){
            swal(
                'Oops...',
                'Debes escribir una contraseña válida de una longitud de al menos 5 caracteres.',
                'error'
            )
            return false;
        }
        return true;
    }
});
