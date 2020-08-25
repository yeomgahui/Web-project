package com.cartrapido.main.chat.dao;

import com.cartrapido.main.domain.entity.TimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Getter
@NoArgsConstructor
@Entity
public class ChatRoom extends TimeEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roomId;

    private String roomName;

    private String shopperId;

    private String clientId;

    @Builder
    public ChatRoom(String roomName,String shopperId, String clientId){
        this.roomId = UUID.randomUUID().toString();
        this.roomName = roomName;
        this.shopperId = shopperId;
        this.clientId = clientId;
    }
}