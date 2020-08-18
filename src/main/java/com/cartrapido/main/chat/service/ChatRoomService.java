package com.cartrapido.main.chat.service;

import com.cartrapido.main.chat.dao.ChatRoom;
import com.cartrapido.main.chat.dao.ChatRoomRepository;
import com.cartrapido.main.chat.dto.ChatRoomListResponsDto;
import com.cartrapido.main.chat.dto.ChatRoomResponseDTO;
import com.cartrapido.main.chat.dto.ChatRoomSaveRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ChatRoomService {
    private final HttpServletRequest httpServletRequest;
    private final ChatRoomRepository chatRoomRepository;

    //채팅방 저장
    @Transactional
    public String save(ChatRoomSaveRequestDTO requestDTO){

        return chatRoomRepository.save(requestDTO.toEntity()).getRoomId();
    }
    //채팅방 ROOM 띄우기
    @Transactional
    public List<ChatRoomListResponsDto> findAllChatRoom(String email){
        System.out.println("findAllCHatRoom 진입");

        if(httpServletRequest.isUserInRole("ROLE_SHOPPER")){

            return chatRoomRepository.findAllByShopperId(email).stream().map(ChatRoomListResponsDto::new)
                    .collect(Collectors.toList());
        }else {
            return chatRoomRepository.findAllByClientId(email).stream().map(ChatRoomListResponsDto::new)
                    .collect(Collectors.toList());
        }
    }

    @Transactional
    public List<ChatRoomResponseDTO> findChatRoom(String roomId){

        return chatRoomRepository.findByRoomId(roomId).stream().map(ChatRoomResponseDTO::new)
                .collect(Collectors.toList());
    }

}
