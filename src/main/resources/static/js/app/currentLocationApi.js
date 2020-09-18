var start;
var des;
var allDistance = [];
var distance;


var onSuccess = function(position) {
    alert('Latitude: '          + position.coords.latitude          + '\n' +
        'Longitude: '         + position.coords.longitude         );


    start = {
        latitude : position.coords.latitude,
        longitude :  position.coords.longitude
    };

    ajax(start);




};//onsuccess

function ajax(start) {

    console.log(JSON.stringify(start));

    $.ajax({
        url: "/currentLocation",
        data: JSON.stringify(start),
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
            clientLatlng(data);


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
//
//
// var loc = {
//
//     market : marketNameMatch.get(market),
//     lat : hashMap2.get(minKey).latitude,
//     lng : hashMap2.get(minKey).longitude
// }
//
// locs.push({
//     market : loc.market,
//     lat : loc.lat,
//     lng : loc.lng
// });


function clientLatlng(data) {

    // console.log("start" +JSON.stringify(start));
    //
    //
    //
    //
    // console.log("1 : " + data[0].latitude);

    for (var i = 0; i < data.length; i++) {

        des = {
            latitude: data[i].latitude,
            longitude: data[i].longitude
        }


        distance = computeDistance(start, des);

        allDistance.push({
            orderNum: data[i].orderNum,
            distance: distance.toFixed(1)
        })


        if(allDistance.length === data.length){


            distancetoCotroll(allDistance);


        }



    }




}


function distancetoCotroll(allDistance) {

    $.ajax({
        url: "/allDistance",
        data: JSON.stringify(allDistance),
        contentType: 'application/json; charset=UTF-8',
        dataType : "json",
        type: "POST"
    }).done(function(json) {
        // alert("done");

    }).fail(function(error) {


    });


}







function computeDistance(startCoords, destCoords) {
    var startLatRads = degreesToRadians(startCoords.latitude);
    var startLongRads = degreesToRadians(startCoords.longitude);
    var destLatRads = degreesToRadians(destCoords.latitude);
    var destLongRads = degreesToRadians(destCoords.longitude);

    var Radius = 6371; //지구의 반경(km)
    var distance = Math.acos(Math.sin(startLatRads) * Math.sin(destLatRads) +
        Math.cos(startLatRads) * Math.cos(destLatRads) *
        Math.cos(startLongRads - destLongRads)) * Radius;


    return distance;
}


function degreesToRadians(degrees) {
    radians = (degrees * Math.PI) / 180;
    return radians;
}


