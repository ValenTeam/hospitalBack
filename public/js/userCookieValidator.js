/**
 * Created by felipeplazas on 4/25/17.
 */
$(document).ready(function()    {
    var token = JSON.parse(window.localStorage.getItem('user'));
    if (token == null || token.expireTimeStamp < new Date().getTime()){
        window.location.href = "/pages/login.html";
    }
    else{
        $("#pageBody").show();
    }

    $("#logOutButton").click(function () {
        logOut()
    });

    function logOut() {
        localStorage.removeItem('user');
        localStorage.removeItem('patient');
        window.location.href = "/pages/login.html";
    }
});