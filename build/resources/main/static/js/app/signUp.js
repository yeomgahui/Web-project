<<<<<<< HEAD

var markingErrorField = function (response) {
    const errorFields = response.responseJSON.errors;
    if(!errorFields){
        alert(response.response.message);
        return;
    }
    var $field, error;
    for(var i=0, length = errorFields.length; i<length;i++){
        error = errorFields[i];
        $field = $('#'+error['field']);

        if($field && $field.length > 0){
            $field.siblings('.error-message').remove();
            $field.after('<span class="error-message text-muted taxt-small text-danger">'+error.defaultMessage+'</span>');
        }
    }
};


$("#signUpBtn").click(function (){

        if($("#jb-checkbox").is(":checked")){
            $("#role").val("SHOPPER");
        }else{
            $("#role").val("USER");
        }
    var member = {
        name: $('#name').val(),
        password: $('#password').val(),
        email: $('#email').val(),
        address: $("#address").val(),
        role: $("#role").val()
    };

    $.ajax({
        url:$("#signUp").attr('action'),
        method: 'POST',
        data: JSON.stringify(member),
        contentType: "application/json; charset=utf-8",
        success: function (response) {
            window.location.href="/user/loginPage"
        },
        error: function (response) {
            markingErrorField(response);
        }
    });
});



=======
function email_check( email ) {

    var regex=/([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/;
    return (email != '' && email != 'undefined' && regex.test(email));

}

$("#signUpBtn").click(function (){
    var email = $('#inputEmail').val();
    $("#emailDiv").empty();

    if(email == ''){
        $("#emailDiv").text("이메일을 입력해주세요.").css("color","gray").css("font-size","10pt");
    }else if( ! email_check(email)){
        $("#emailDiv").text("잘못된 형식의 이메일 주소입니다.").css("color","gray").css("font-size","10pt");
    }else if($('#inputPassword').val() == ''){
        $("#pwdDiv").text("비밀번호를 입력해주세요.").css("color","gray").css("font-size","10pt");
    }else{
        document.signUp.submit();

        /*//$("#signUp")[0].submit();
        $.ajax({
            type : 'POST',
            url : '/user/verifyemail',
            dataType : 'text',
            contentType :'application/json; charset=utf-8',
            data : "email="+$("#inputEmail")
        }).done(function (data) {
            alert("ddd");
            if(data != "ok"){
                document.signUp.submit();
            }else{
                alert("qqqqq");
                $("#emailDiv").text("이미 있는 계정입니다.").css("color","gray").css("font-size","10pt");
            }
            //window.location.href ='/';

        }).fail(function (request,status, error) {
            alert("code = "+ request.status + " message = " + request.responseText + " error = " + error);
        });*/
    }
});


>>>>>>> 5963872dc99b808d636acd298534b25b18e6f280
