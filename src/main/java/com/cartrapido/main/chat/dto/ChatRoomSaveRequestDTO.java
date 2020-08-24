package com.cartrapido.main.chat.dto;

import com.cartrapido.main.chat.dao.ChatRoom;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

//채팅방 저장할때
@Getter
@NoArgsConstructor
public class ChatRoomSaveRequestDTO {
    private String roomName;
    private String shopperId;
    private String clientId;


    @Builder
    public ChatRoomSaveRequestDTO(String roomname, String shopperId, String clientId){
        this.roomName = roomname;
        this.shopperId = shopperId;
        this.clientId = clientId;
    }

    public ChatRoom toEntity(){
        return ChatRoom.builder()
                .roomName(roomName)
                .shopperId(shopperId)
                .clientId(clientId)
                .build();
    }

}
