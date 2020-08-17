package com.cartrapido.main.chat.dto;

import com.cartrapido.main.chat.dao.ChatRoom;
import lombok.Getter;

@Getter
public class ChatRoomListResponsDto {

    private String roomId;
    private String roomName;

    public ChatRoomListResponsDto(ChatRoom entity){
        this.roomId = entity.getRoomId();
        this.roomName = entity.getRoomName();
    }

}
