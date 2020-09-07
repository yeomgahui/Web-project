package com.cartrapido.main.web;


import com.cartrapido.main.chat.dto.ChatRoomListResponsDto;
import com.cartrapido.main.chat.dto.ChatRoomResponseDTO;
import com.cartrapido.main.chat.dto.ChatRoomSaveRequestDTO;
import com.cartrapido.main.chat.dto.MessageListDTO;
import com.cartrapido.main.chat.service.ChatMessageService;
import com.cartrapido.main.chat.service.ChatRoomService;
import com.cartrapido.main.config.auth.dto.SessionUser;
import com.cartrapido.main.domain.entity.Member;
import com.cartrapido.main.service.MemberService;
import com.cartrapido.main.web.dto.MemberRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/chat")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;
    private final ChatMessageService chatMessageService;

    private final HttpServletRequest request;


    //채팅ROOM lIST화면
    @GetMapping("/shopperChatting")
    public String rooms(Model model,HttpSession session){
        System.out.println("rooms 입장");
        SessionUser user = (SessionUser) session.getAttribute("user");
        String email = user.getEmail();

        model.addAttribute("chatRoom",chatRoomService.findAllChatRoom(email));

        return "/shopperWebBody/chatRoomList.html";
    }

    @GetMapping("/clientChatting")
    public String chatRoomUser(Model model,HttpSession session){
        SessionUser user = (SessionUser) session.getAttribute("user");
        String email = user.getEmail();

        model.addAttribute("chatRoom",chatRoomService.findAllChatRoom(email));

        return "/clientWebBody/clientChatting.html";
    }

    @GetMapping("/room/enter/{roomId}")
    public @ResponseBody Object roomDetail( @PathVariable String roomId){

        ChatRoomResponseDTO chatRoom =  chatRoomService.findChatRoom(roomId).get(0);


        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("roomId", chatRoom.getRoomId());
        map.put("roomName", chatRoom.getRoomName());
        if(request.isUserInRole("ROLE_SHOPPER")){
            map.put("sender", chatRoom.getShopperId());
            map.put("reciever", chatRoom.getClientId());
        }else{
            map.put("sender", chatRoom.getClientId());
            map.put("reciever", chatRoom.getShopperId());
        }
        return map;
    }

    @GetMapping("/findHistory")
    public @ResponseBody ModelAndView histories(Model model,@RequestParam String roomId){

        List<MessageListDTO> history = chatMessageService.findByRoomId(roomId);

        ModelAndView mv = new ModelAndView("jsonView");
        mv.addObject("list",history);

        return mv;
    }

}
