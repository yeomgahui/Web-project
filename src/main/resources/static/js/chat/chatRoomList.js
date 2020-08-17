var stompClient = null;
var roomInfo = null;
function enterRoom(roomId) {
    //채팅방 정보 db에서 가져온다...
    $.ajax({
        type: 'GET',
        url: '/chat/room/enter/'+roomId,
        dataType: 'json',
        contentType:'application/json; charset=utf-8',
        //data: JSON.stringify(data)
    }).done(function(data) {
        roomInfo ={
            roomId : data.roomId,
            roomName : data.roomName,
            sender : data.sender,
            reciever : data.reciever
        };
        connect();
    }).fail(function (error) {
        alert(JSON.stringify(error));
    });

    //로직
    //기본 페이지 안보이게 한다.
    $("#chatdefaultImage").addClass("hidden");
    $("#chatSuperContainer").removeClass("hidden");

    //필요한 것 , 방id, 상대방 emile, 내 email
    //그다음..?

}
function connect(){
    var socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);

    stompClient.connect({},onConnected, onError);

   /* stompClient.connect({},function () {
        alert("여기여기");
    });*/
}

function onConnected() {
    $(".selected-user .name").text(roomInfo.reciever);

    stompClient.subscribe("/sub/chat/room/"+roomInfo.roomId, onMessageReceived);
}
function onError(error) {
    alert("error");
}

function onMessageReceived(message){
    var recv = JSON.parse(message.body);
    var messageArea = document.querySelector('#messageArea');

    var messageElement = document.createElement('li');



    if(recv.sender == roomInfo.sender){
        alert('내가보낸거');
        messageElement.classList.add('chat-left');

    }else{
        messageElement.classList.add('chat-right');
    }

    messageArea.appendChild(messageElement);
    messageArea.scrollTop = messageArea.scrollHeight;
}

function sendMessage() {
    var messageContent = document.querySelector('#message').value.trim();

    if(messageContent && stompClient){
        var chatMessage ={
            roomId : roomInfo.roomId,
            sender : roomInfo.reciever,
            message: messageContent,
            type:'CHAT'
        };
        stompClient.send('/pub/chat/message',{},JSON.stringify(chatMessage));
        document.querySelector('#message').value ='';
    }
}