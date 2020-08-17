package com.cartrapido.main.chat.dto;

import com.cartrapido.main.chat.dao.ChatRoom;
import lombok.Getter;

@Getter
public class ChatRoomResponseDTO {
    private String roomId;
    private String roomName;
    private String shopperId;
    private String clientId;

    public ChatRoomResponseDTO(ChatRoom entity){
        this.roomId = entity.getRoomId();
        this.roomName = entity.getRoomName();
        this.shopperId = entity.getShopperId();
        this.clientId = entity.getClientId();
    }

}
