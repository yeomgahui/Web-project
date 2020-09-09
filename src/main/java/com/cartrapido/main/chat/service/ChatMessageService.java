package com.cartrapido.main.chat.service;


import com.cartrapido.main.chat.dao.ChatMessage;
import com.cartrapido.main.chat.dao.ChatMessageRepository;
import com.cartrapido.main.chat.dto.ChatMessageDTO;
import com.cartrapido.main.chat.dto.ChatRoomResponseDTO;
import com.cartrapido.main.chat.dto.MessageListDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ChatMessageService {
    private final ChatMessageRepository chatMessageRepository;

    //메시지 저장
    @Transactional
    public void save(ChatMessageDTO chatMessageDTO){
        System.out.println("룸 id= " + chatMessageDTO.getRoomId());
        chatMessageRepository.save(chatMessageDTO.toEntity());
        System.out.println("메시지 저장 완료");
    }

    @Transactional
    public List<MessageListDTO> findByRoomId(String roomId){

        return chatMessageRepository.findByRoomId(roomId).stream().map(MessageListDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteMessages(String roomId){

        chatMessageRepository.deleteByRoomId(roomId);
    }


}
