/**
 * Created by felipeplazas on 4/25/17.
 */
$(document).ready(function()    {
    // = JSON.parse(Cookies.get('user'));
    var token = JSON.parse(window.localStorage.getItem('user'));
    if (token == null || token.expireTimeStamp < new Date().getMilliseconds()){
        window.location.href = "/pages/login.html";
    }
    else{
        $("#pageBody").show();
    }
});