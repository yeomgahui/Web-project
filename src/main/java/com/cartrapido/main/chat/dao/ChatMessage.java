package com.cartrapido.main.chat.dao;

import com.cartrapido.main.chat.dto.MessageType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.UUID;


@Getter
@NoArgsConstructor
@Entity
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private MessageType messageType;

    private String roomId;

    private String sender;

    private String message;

    @Builder
    public ChatMessage(String roomId,String sender, String message, MessageType messageType){
        this.roomId = roomId;
        this.sender = sender;
        this.message = message;
        this.messageType = messageType;
    }
}
