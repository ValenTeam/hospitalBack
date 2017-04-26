/**
 * Created by felipeplazas on 4/25/17.
 */
$(document).ready(function()    {
    var token = Cookies.get('user');
    if (token == null || token.expireTimeStamp > new Date().getMilliseconds()){
        window.location.href = "/pages/login.html";
    }
    else{
        $("#pageBody").show();
    }
});