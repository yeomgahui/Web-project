package com.cartrapido.main.chat.dto;

import com.cartrapido.main.chat.dao.ChatMessage;
import com.cartrapido.main.chat.dao.ChatRoom;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ChatMessageDTO {

    private MessageType messageType;
    private String roomId;
    private String sender;
    private String message;

    public ChatMessage toEntity(){
        return ChatMessage.builder()
                .roomId(roomId)
                .messageType(messageType)
                .sender(sender)
                .message(message)
                .build();
    }
}
