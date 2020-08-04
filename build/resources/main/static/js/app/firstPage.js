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
        });
    }
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
        $("#zipCodeDiv").text("주소를 입력해주세요.").css("color","red").css("font-size","14pt");
    }else{
        window.location.href ="/user/signUpPage?address="+$("#autocomplete").val();
        //document.zipcodeForm.submit();
    }
});