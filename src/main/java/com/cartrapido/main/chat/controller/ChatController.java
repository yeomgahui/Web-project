package com.cartrapido.main.chat.controller;

import com.cartrapido.main.chat.dto.ChatMessage;
import com.cartrapido.main.chat.dto.MessageType;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class ChatController {
    private  final SimpMessageSendingOperations messagingTemplate;

    @MessageMapping("/chat/message")
    public void message(ChatMessage message){
        if(MessageType.JOIN.equals(message.getMessageType()))
            message.setMessage(message.getSender()+"님이 입장하였습니다.");
        System.out.println("message 내용 = "+message.getMessage());
        messagingTemplate.convertAndSend("/sub/chat/room/"+message.getRoomId(),message);
    }
}
