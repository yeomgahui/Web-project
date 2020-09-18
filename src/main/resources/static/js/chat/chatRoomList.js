var stompClient = null;
var roomInfo = null;
var address = null;
var messageArea = null;
var messageElement = null;

function enterRoom(roomId) {
    $("#messageArea").empty();
    //채팅방 내역 뿌려준다.

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
        $.ajax({
            type: 'GET',
            url: '/chat/findHistory',
            data: "roomId="+ roomId,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8'
        }).done(function (data) {
            //alert(JSON.stringify(data.list));
            $.each(data.list, function(index,items){
                messageArea = document.querySelector('#messageArea');

                messageElement = document.createElement('li');

                /*보낸 사람 이름*/
                var chatAvatar = document.createElement('div');
                chatAvatar.classList.add('chat-avatar');
                var avatarNameDiv = document.createElement('div');
                avatarNameDiv.classList.add('chat-name');
                var tempId = items.sender;
                var idSplit = tempId.split('@');
                var avatarName = document.createTextNode(idSplit[0]);
                avatarNameDiv.appendChild(avatarName);
                chatAvatar.appendChild(avatarNameDiv);

                /*메시지 추가*/
                var chatMessageDiv = document.createElement('div');
                chatMessageDiv.classList.add('chat-text');
                var chatMessage = document.createTextNode(items.message);
                chatMessageDiv.appendChild(chatMessage);

                if(items.sender == roomInfo.sender){
                    messageElement.classList.add('chat-left');
                    messageElement.appendChild(chatAvatar);
                    messageElement.appendChild(chatMessageDiv);
                }else{
                    messageElement.classList.add('chat-right');
                    messageElement.appendChild(chatMessageDiv);
                    messageElement.appendChild(chatAvatar);
                }

                messageArea.appendChild(messageElement);
                var chatContent = document.querySelector('.chat-content');
                chatContent.scrollTop = chatContent.scrollHeight;

            });

        });

        address = "#room"+roomId;
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
    //alert(roomInfo.reciever);
    $(".selected-user .name").text(roomInfo.reciever);

    if(document.querySelector(address).value == '0'){
        document.querySelector(address).value = '1';
        stompClient.subscribe("/sub/chat/room/"+roomInfo.roomId, onMessageReceived);
    }else{
    }

}
function onError(error) {

}

function onMessageReceived(message){
    var recv = JSON.parse(message.body);
    var messageArea = document.querySelector('#messageArea');

    var messageElement = document.createElement('li');
    var chatAvatar = document.createElement('div');
    chatAvatar.classList.add('chat-avatar');
    var avatarNameDiv = document.createElement('div');
    avatarNameDiv.classList.add('chat-name');
    var tempId = recv.sender;
    var idSplit = tempId.split('@');
    var avatarName = document.createTextNode(idSplit[0]);
    avatarNameDiv.appendChild(avatarName);
    chatAvatar.appendChild(avatarNameDiv);


    /*메시지 추가*/
    var chatMessageDiv = document.createElement('div');
    chatMessageDiv.classList.add('chat-text');
    var chatMessage = document.createTextNode(recv.message);
    chatMessageDiv.appendChild(chatMessage);


    if(recv.sender == roomInfo.sender){
        //나
        messageElement.classList.add('chat-left');
        messageElement.appendChild(chatAvatar);
        messageElement.appendChild(chatMessageDiv);

    }else{
        //상대
        messageElement.classList.add('chat-right');
        messageElement.appendChild(chatMessageDiv);
        messageElement.appendChild(chatAvatar);
    }
    messageArea.appendChild(messageElement);
    var chatContent = document.querySelector('.chat-content');
    chatContent.scrollTop = chatContent.scrollHeight;

}

function sendMessage() {

    var messageContent = document.querySelector('#message').value.trim();

    if(messageContent && stompClient){
        var chatMessage ={
            roomId : roomInfo.roomId,
            sender : roomInfo.sender,
            message: messageContent,
            type:'CHAT'
        };
        stompClient.send('/pub/chat/message',{},JSON.stringify(chatMessage));
        document.querySelector('#message').value ='';
    }
}