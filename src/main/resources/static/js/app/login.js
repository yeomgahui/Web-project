function email_check( email ) {

    var regex=/([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/;
    return (email != '' && email != 'undefined' && regex.test(email));

}

$("#loginBtn").click(function (){
    var email = $('#inputEmail').val();
    $("#emailDiv").empty();

    if(email == ''){
        $("#emailDiv").text("이메일을 입력해주세요.").css("color","gray").css("font-size","10pt");
    }else if( ! email_check(email)){
        $("#emailDiv").text("잘못된 형식의 이메일 주소입니다.").css("color","gray").css("font-size","10pt");
    }else if($('#inputPassword').val() == ''){
        $("#pwdDiv").text("비밀번호를 입력해주세요.").css("color","gray").css("font-size","10pt");
    }else{
        document.LoginForm.submit();
    }
});