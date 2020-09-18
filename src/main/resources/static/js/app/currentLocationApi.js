
var onSuccess = function(position) {
    alert('Latitude: '          + position.coords.latitude          + '\n' +
        'Longitude: '         + position.coords.longitude         );


    var des = {
        latitude : position.coords.latitude,
        longitude :  position.coords.longitude
    };

    ajax(des);




};//onsuccess

function ajax(des) {

    console.log(JSON.stringify(des));

    $.ajax({
        url: "/currentLocation",
        data: JSON.stringify(des),
        contentType: 'application/json; charset=UTF-8',
        dataType : "json",
        type: "POST"
    }).done(function(json) {
        alert("done");

    }).fail(function(error) {


    });



    $.ajax({
        type : "GET",
        url : "clientLatlng",
        success : function(data) {
            console.log(data);

        },
        error : function(error) {
            console.log("error");
        },
    });



}




// onError Callback receives a PositionError object
//
function onError(error) {
    alert('code: '    + error.code    + '\n' +
        'message: ' + error.message + '\n');
}





navigator.geolocation.getCurrentPosition(onSuccess, onError);