package com.cartrapido.main.chat.controller;

import com.cartrapido.main.chat.dto.ChatMessageDTO;
import com.cartrapido.main.chat.dto.MessageType;
import com.cartrapido.main.chat.service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class ChatController {
    private  final SimpMessageSendingOperations messagingTemplate;
    private final ChatMessageService chatMessageService;


    @MessageMapping("/chat/message")
    public void message(ChatMessageDTO message){
        chatMessageService.save(message);

        /*if(MessageType.JOIN.equals(message.getMessageType()))
            message.setMessage(message.getSender()+"님이 입장하였습니다.");*/

        messagingTemplate.convertAndSend("/sub/chat/room/"+message.getRoomId(),message);
    }
}
