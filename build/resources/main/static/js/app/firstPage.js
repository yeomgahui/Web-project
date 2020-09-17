var placeSearch, autocomplete;

function geolocate() {
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(function(position) {
            var geolocation = {
                lat: position.coords.latitude,
                lng: position.coords.longitude
            };
            var circle = new google.maps.Circle(
                {center: geolocation, radius: position.coords.accuracy});
            autocomplete.setBounds(circle.getBounds());


            ajax(geolocation);
        });
    }
}


function ajax(geolocation) {

    console.log(geolocation);

    $.ajax({
        url: "/clientLatlng",
        data: JSON.stringify(geolocation),
        contentType: 'application/json; charset=UTF-8',
        dataType : "json",
        type: "POST"
    }).done(function(json) {
        alert("done");

    }).fail(function(error) {

    });
}


function initAutocomplete() {
    // Create the autocomplete object, restricting the search predictions to
    // geographical location types.
    autocomplete = new google.maps.places.Autocomplete(
        document.getElementById('autocomplete'), {types: ['geocode']});

    // Avoid paying for data that you don't need by restricting the set of
    // place fields that are returned to just the address components.
    autocomplete.setFields(['address_component']);

}
$("#continueBtn").click(function (){

    if($("#autocomplete").val() ==''){
        $("#zipCodeDiv").text("주소를 입력해주세요.").css("color","gray").css("font-size","10t");
    }else{
        document.zipcodeForm.submit();//form 의 url로 이동
    }
});