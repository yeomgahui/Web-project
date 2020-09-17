$('.cancleBtn').on("click",function () {
    var id = $(this).data('id');
    $.ajax({
        method: 'POST',
        url: '/admin/deleteBlackList',
        data: 'id='+id,
    }).done(function () {
        window.location.href="/admin/blackList"

    }).fail(function (error) {
        alert(JSON.stringify(error));
    });
});

$('.blackBtn').on("click",function () {
    $('#shopper').val($(this).data('shopper'));
    $('#id').val($(this).data('id'));

});

$('#cofirmBtn').click(function () {
    $.ajax({
        method: 'POST',
        url: '/admin/blackMember',
        data: "id="+$('#id').val()+"&shopper="+$('#shopper').val()
    }).done(function () {
        window.location.href="/admin/blackList"
        $("#checkModal").modal("hide");

    }).fail(function (error) {
        alert(JSON.stringify(error));
    });

});