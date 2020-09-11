
$(document).ready(function () {

    var shopper = $("#docutChart").data('shopper');
    data = {
        datasets: [{
            backgroundColor: ['#088A4B','#F7FE2E'],
            data: [ shopper, 100-shopper]
        }],
        // 라벨의 이름이 툴팁처럼 마우스가 근처에 오면 나타남
        labels: ['shopper','client']
    };
    var ctx2 = document.getElementById("docutChart");
    var myDoughnutChart = new Chart(ctx2,{
        type: 'doughnut',
        data: data,
        options:{}
    });

    //line
    var ctxL = document.getElementById("lineChart").getContext('2d');
    var myLineChart = new Chart(ctxL, {
        type: 'line',
        data: {
            labels: [ "7월", "8월", "9월", "10월", "11월", "12월"],
            datasets: [{
                label: "월별 주문건",
                data: [65, 59, 80, 81, 56, 55],
                backgroundColor: [
                    'rgba(105, 0, 132, .2)',
                ],
                borderColor: [
                    'rgba(200, 99, 132, .7)',
                ],
                borderWidth: 1
            }]
        },
        options: {
            responsive: true
        }
    });


    //스토어별 판매량

        $.ajax({
            url: '/adminTest/storeRank',
            type: 'POST',
            dataType: 'json'
        }).done(function (data) {
            $.each(data.list, function (index, items) {
                var html ='';
                html+=(index+1)+'.&emsp;<span>'+items.store+'</span>&emsp;<span>'+items.amount+'</span><br>';
                $('#storeRank').append(html);

            });
        })


});

