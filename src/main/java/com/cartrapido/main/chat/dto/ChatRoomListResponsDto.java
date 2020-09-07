package com.cartrapido.main.chat.dto;

import com.cartrapido.main.chat.dao.ChatRoom;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class ChatRoomListResponsDto {

    private String roomId;
    private String roomName;
    private LocalDate modifiedDate;

    public ChatRoomListResponsDto(ChatRoom entity){
        this.roomId = entity.getRoomId();
        this.roomName = entity.getRoomName();
        this.modifiedDate = entity.getModifiedDate();
    }

}
