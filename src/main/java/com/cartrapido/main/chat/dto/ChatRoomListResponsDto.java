package com.cartrapido.main.chat.dto;

import com.cartrapido.main.chat.dao.ChatRoom;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ChatRoomListResponsDto {

    private String roomId;
    private String roomName;
    private LocalDateTime modifiedDate;

    public ChatRoomListResponsDto(ChatRoom entity){
        this.roomId = entity.getRoomId();
        this.roomName = entity.getRoomName();
        this.modifiedDate = entity.getModifiedDate();
    }

}
