// alert("distanceAPI");


//rendering 속도향상을 위해 hashmap.js 파일을 여기로 옮김
    HashMap = function(){
        this.hashmap = new Object();
    };

    HashMap.prototype = {
        put : function(key, value){
            this.hashmap[key] = value;
        },
        get : function(key){
            return this.hashmap[key];
        },
        containsKey : function(key){
            return key in this.hashmap;
        },
        containsValue : function(value){

            for(var prop in this.hashmap){
                if(this.hashmap[prop] == value) return true;
            }
            return false;
        },
        isEmpty : function(key){
            return (this.size() == 0);
        },
        clear : function(){
            for(var prop in this.hashmap){
            delete this.hashmap[prop];
            }
        },
        remove : function(key){
            delete this.hashmap[key];
        },
        keys : function(){
            var keys = new Array();
            for(var prop in this.hashmap){
                keys.push(prop);
            }
            return keys;
        },
        values : function(){
            var values = new Array();
            for(var prop in this.hashmap){
                values.push(this.hashmap[prop]);
            }
            return values;
        },
        size : function(){
            var count = 0;
            for (var prop in this.hashmap) {
                count++;
            }
            return count;
        }
    };



//google place api


        var map;
        // var infowindow;

        var request;
        var service;
        // var markers = [];
        var distance;
        var hashmap = new HashMap();
        var hashMap2 = new HashMap();
        var marketNameMatch = new HashMap();
        let locs = [];









    function initialize() {


        request2 = {
            address: $("#address").text()


            //$("#address-test").text(); //세션 주소

        };


        geocoder = new google.maps.Geocoder();

        /*service.textSearch(request, callback);*/
        geocoder.geocode(request2, latlngFromAdress);


    }


    function init2(latlng) {


        var center = new google.maps.LatLng(latlng.latitude, latlng.longitude);
        map = new google.maps.Map(document.getElementById('map'), {
            center: center,
            zoom: 13

        });

        var markets = ['홈플러스','이마트','롯데마트','CU','GS25','MINISTOP'];
        var marketsNameEdit = ['homeplus','emart','lottemart','cu','gs25','ministop'];


        // alert(markets.length);


        for (var k = 0; k < markets.length; k++) {
            let market = markets[k];
            let marketEdit = marketsNameEdit[k];

            marketNameMatch.put(market,marketEdit);


            // alert(market);

            request = { //center로 부터 ~radius 떨어짐
                location: center,
                radius: 1000,
                /*types: ['supermarket']*/
                /*rankby : google.maps.places.RankBy.DISTANCE,*/
                query: [market]


            };

            // alert("2:" + request.query);



            // infowindow = new google.maps.InfoWindow();

            service = new google.maps.places.PlacesService(map);


            //latlng을 받아와야 하기 때문에 callback 함수를 내부함수로 생성
            service.textSearch(request, function (results, status) {








                if (status == google.maps.places.PlacesServiceStatus.OK) {
                    for (var i = 0; i < results.length; i++) {
                        const regexr = [/24/, /조이마트/, /노브랜드/, /하이/];//제외
                        // const regexr2 = /조이마트/;
                        // const martName = /이마트/;


                        if (regexr[0].test(results[i].name) || regexr[1].test(results[i].name) || regexr[2].test(results[i].name) || regexr[3].test(results[i].name)) {
                            const idx = results.indexOf(results[i].name)
                            results.splice(idx, 1)


                        } else {





                            distance = computeDistance(results[i].geometry.location, latlng);

                            hashmap.put(results[i].name, distance);

                            var latlngStart = {


                                latitude: results[i].geometry.location.lat(),
                                longitude: results[i].geometry.location.lng()
                            }

                            hashMap2.put(results[i].name, latlngStart);//검색용 해쉬맵








                        }//else


                    }//for


                    // get object keys array

                    // alert(Math.min.apply(null, hashmap.values())); //가장 가까운 값 반환


                    var min;
                    var minVal;
                    var minKey;



                    min = hashmap.values()[2];


                    // alert("size : "+hashmap.size());


                    for (var j = 0; j < hashmap.size(); j++) {


                        if (hashmap.values()[j] < min) {
                            min = hashmap.values()[j];
                            minVal = min;
                            minKey = hashmap.keys()[j];




                        }


                    }//for


                    // alert("제일 가까운 마트는 " + minKey + "으로 " + minVal.toFixed(1) + "km 떨어져 있습니다!");

                    $("#" + market + "_minKey").text(minKey);
                    $("#" + market + "_minVal").text(minVal.toFixed(1) + "km");





                    var loc = {

                        locMarket: minKey,
                        market : marketNameMatch.get(market),
                        lat : hashMap2.get(minKey).latitude,
                        lng : hashMap2.get(minKey).longitude
                    }

                    locs.push({
                        locMarket : loc.locMarket,
                        market : loc.market,
                        lat : loc.lat,
                        lng : loc.lng
                    });



                    //locs값이 다 채워지면 컨트롤러로 보냄
                    if(locs.length === markets.length){


                        ajax(locs);


                    }




                    hashmap.clear();//데이터가 쌓이지 않게 지워줌


                }//if


            });//내부함수


        }//for



    }//init




    function ajax(locs) {

        $.ajax({
            url: "/marketLatlng",
            data: JSON.stringify(locs),
            contentType: 'application/json; charset=UTF-8',
            dataType : "json",
            type: "POST"
        }).done(function(json) {
            alert("done");

        }).fail(function(error) {

        });
    }













    function latlngFromAdress(results, status) {//변하지 않는 original 위치

        if (status == google.maps.GeocoderStatus.OK)  //Geocoding이 성공적이라면,

        {

            // alert(results.length + "개의 결과를 찾았습니다.");

            var latlng = {

                latitude: results[0].geometry.location.lat(),
                longitude: results[0].geometry.location.lng()
            }


            init2(latlng); //매개변수로 전달!


        }


    }


    function computeDistance(startCoords, destCoords) {
        var startLatRads = degreesToRadians(startCoords.lat());
        var startLongRads = degreesToRadians(startCoords.lng());
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


    google.maps.event.addDomListener(window, 'load', initialize);
