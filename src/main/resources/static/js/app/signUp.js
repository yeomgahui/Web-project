function chkEmail(email) {

    var regExp = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
    if (email != '' && email != 'undefined' && regex.test(email));
}

$("#signUpBtn").click(function (){
    var email = $('#inputEmail').val();
    alert(email);

    if(email == ''){
        alert("아이디 미입력")
        $("emailDiv").text("이메일을 입력해주세요.").css("color","red").css("font-size","14pt");
    }else if( ! chkEmail(email)){
        $("emailDiv").text("잘못된 형식의 이메일 주소입니다.").css("color","red").css("font-size","14pt");
    }else if($('inputPassword').val() == ''){
        $("pwdDiv").text("비밀번호를 입력해주세요.").css("color","red").css("font-size","14pt");
    }else{
        document.signUp.submit();
    }
});


