package com.cartrapido.main.chat.dto;

import com.cartrapido.main.chat.dao.ChatMessage;
import lombok.Getter;

@Getter
public class MessageListDTO {

    private Long id;
    private MessageType messageType;
    private String roomId;
    private String sender;
    private String message;

    public MessageListDTO(ChatMessage entity){
        this.id = entity.getId();
        this.messageType = entity.getMessageType();
        this.roomId = entity.getRoomId();
        this.sender = entity.getSender();
        this.message = entity.getMessage();
    }
}
