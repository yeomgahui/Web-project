package com.cartrapido.main.web;

import com.cartrapido.main.chat.dto.ChatRoomSaveRequestDTO;
import com.cartrapido.main.chat.service.ChatRoomService;
import com.cartrapido.main.config.auth.dto.SessionUser;
import com.cartrapido.main.service.OrderNumHistoryService;
import com.cartrapido.main.service.OrderNumService;
import com.cartrapido.main.service.OrderSheetService;
import com.cartrapido.main.web.dto.OrderNumDTO;
import com.cartrapido.main.web.dto.OrderNumHistoryDTO;
import com.cartrapido.main.web.dto.OrderSheetDTO;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@AllArgsConstructor
public class ShopperController {
    @Autowired
    OrderSheetService orderSheetService;
    @Autowired
    OrderNumService orderNumService;
    @Autowired
    OrderNumHistoryService orderNumHistoryService;

    ChatRoomService chatRoomService;


    @GetMapping("/shopperWeb")
    public String shopperWeb() {
        return "/shopperWebBody/firstPage.mustache";
    }


    @GetMapping("/changePwd")
    public String changePwd() {
        return "/shopperWebBody/changePwd";
    }

    @GetMapping("/withdrawal")
    public String withdrawal() {
        return "/shopperWebBody/withdrawal";
    }

    @GetMapping("/myPage")
    public String layout() {



        return "/shopperWebBody/myPage";
    }

    @GetMapping("/orderSheetList")
    public String orderSheetList(Model model) {

        List<OrderNumDTO> orderNumDTOList = orderNumService.shopperGetPaidOrder(1);
        List<OrderNumDTO> orderNumDTOListReturn = new ArrayList<>();
        for(int i = 0 ; i<orderNumDTOList.size();i++){
            if(orderNumDTOList.get(i).getShopper()==null){
                orderNumDTOListReturn.add(orderNumDTOList.get(i));
            }
        }
        if(orderNumDTOList.size()!=0){
            model.addAttribute("orderNumList", orderNumDTOListReturn);
        }
        return "/shopperWebBody/shopperList/orderSheetList";
    }

    @GetMapping("/myOrderSheetsHistory")
    public String myOrderSheetsHistory(Model model, HttpSession session ) {
        SessionUser user = (SessionUser) session.getAttribute("user");
        String shopperEmail = user.getEmail();

        List<OrderNumHistoryDTO> orderNumDTOList = orderNumHistoryService.getMyOrderNumListShopper(shopperEmail);
        if(orderNumDTOList.size()!=0){
            model.addAttribute("orderNumList", orderNumDTOList);
        }
        return "/shopperWebBody/shopperList/myOrderSheetsHistory";
    }

    @GetMapping("/myOrderSheets")
    public String myOrderSheets(Model model, HttpSession session ) {
        SessionUser user = (SessionUser) session.getAttribute("user");
        String shopperEmail = user.getEmail();

        List<OrderNumDTO> orderNumDTOList = orderNumService.getMyOrderNumListShopper(shopperEmail);
        if(orderNumDTOList.size()!=0){


            //오름차순으로 넣어주기
            model.addAttribute("orderNumList", orderNumDTOList);
        }
        return "/shopperWebBody/shopperList/myOrderSheets";
    }


    @GetMapping("/viewHistoryOrder/{orderNum}")
    public String viewHistoryOrder(@PathVariable("orderNum") Long orderNum, Model model){
        OrderNumHistoryDTO orderNumDTO = orderNumHistoryService.findByOriOrderNum(orderNum);
        List<OrderSheetDTO> orderSheetList =
                orderSheetService.getOrderSheetList(orderNum);

        model.addAttribute("orderNumDTO", orderNumDTO);
        model.addAttribute("orderNumList", orderSheetList);
        model.addAttribute("orderSize", orderSheetList.size());
        return "/shopperWebBody/shopperView/viewHistoryOrder";
    }

    @GetMapping("/viewOrderSheet/{orderNum}")
    public String viewOrderSheet(@PathVariable("orderNum") Long orderNum, Model model){
        OrderNumDTO orderNumDTO = orderNumService.getOrderNum(orderNum);
        List<OrderSheetDTO> orderSheetList =
                orderSheetService.getOrderSheetList(orderNum);

        model.addAttribute("orderNumDTO", orderNumDTO);
        model.addAttribute("orderNumList", orderSheetList);
        model.addAttribute("orderSize", orderSheetList.size());
        return "/shopperWebBody/shopperView/viewOrderSheet";
    }

    @PostMapping("/acceptOrder")
    @ResponseBody
    public void acceptOrder(@RequestParam Long orderNum, Model model, HttpSession session ){
        SessionUser user = (SessionUser) session.getAttribute("user");
        String shopperEmail = user.getEmail();
        String clientId = orderNumService.acceptOrder(orderNum,shopperEmail);

        //채팅방 생성
        String roomName = orderNum.toString();
        ChatRoomSaveRequestDTO requestDTO = ChatRoomSaveRequestDTO.builder()
                .roomname(roomName)
                .shopperId(shopperEmail)
                .clientId(clientId).build();

        chatRoomService.save(requestDTO);

    }

    @PostMapping("/currentLocation")
    @ResponseBody
    public String currentLocation(@RequestBody HashMap hash,HttpSession session){

        System.out.println("dto : "+ hash);

        session.setAttribute("latlng", hash);

        return "/currentLocation";



    }


}
