package com.cartrapido.main.web;

import com.cartrapido.main.chat.dto.ChatRoomSaveRequestDTO;
import com.cartrapido.main.chat.service.ChatRoomService;
import com.cartrapido.main.config.auth.dto.SessionUser;
import com.cartrapido.main.service.OrderNumService;
import com.cartrapido.main.service.OrderSheetService;
import com.cartrapido.main.web.dto.OrderNumDTO;
import com.cartrapido.main.web.dto.OrderSheetDTO;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@AllArgsConstructor
public class ShopperController {
    @Autowired
    OrderSheetService orderSheetService;
    @Autowired
    OrderNumService orderNumService;

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

        List<OrderNumDTO> orderNumDTOList = orderNumService.shopperGetPaidOrder("true");
        for(int i = 0 ; i<orderNumDTOList.size();i++){
            if(orderNumDTOList.get(i).getShopper()!=null){
                orderNumDTOList.remove(i);
            }
        }

        if(orderNumDTOList.size()!=0){
            model.addAttribute("orderNumList", orderNumDTOList);
        }
        return "/shopperWebBody/orderSheetList";
    }

    @GetMapping("/myOrderSheets")
    public String myOrderSheets(Model model, HttpSession session ) {
        SessionUser user = (SessionUser) session.getAttribute("user");
        String shopperEmail = user.getEmail();

        List<OrderNumDTO> orderNumDTOList = orderNumService.getMyOrderNumListShopper(shopperEmail);
        if(orderNumDTOList.size()!=0){
            model.addAttribute("orderNumList", orderNumDTOList);
        }
        return "/shopperWebBody/myOrderSheets";
    }

    @GetMapping("/viewOrderSheet/{orderNum}")
    public String viewOrderSheet(@PathVariable("orderNum") Long orderNum, Model model){
        OrderNumDTO orderNumDTO = orderNumService.getOrderNum(orderNum);
        int productTot = orderNumDTO.getProductTot();
        int deliveryCost = orderNumDTO.getDeliveryCost();

        List<OrderSheetDTO> orderSheetList =
                orderSheetService.getOrderSheetList(orderNum);

        for(OrderSheetDTO dto:orderSheetList){
            System.out.println("view 상품 = "+dto.getProductName());
        }
        model.addAttribute("productTot", productTot);
        model.addAttribute("deliveryCost", deliveryCost);
        model.addAttribute("orderNumList", orderSheetList);
        model.addAttribute("orderSize", orderSheetList.size());
        return "/shopperWebBody/viewOrderSheet";
    }

    @PostMapping("/acceptOrder")
    @ResponseBody
    public void acceptOrder(@RequestParam Long orderNum, Model model, HttpSession session ){
        System.out.println(orderNum+"번 주문을 수락합니다.");
        SessionUser user = (SessionUser) session.getAttribute("user");
        String shopperEmail = user.getEmail();
        System.out.println(shopperEmail+" 쇼퍼의 이메일 ");
        String clientId = orderNumService.acceptOrder(orderNum,shopperEmail);

        //채팅방 생성
        String roomName = orderNum.toString();
        ChatRoomSaveRequestDTO requestDTO = ChatRoomSaveRequestDTO.builder()
                .roomname(roomName)
                .shopperId(shopperEmail)
                .clientId(clientId).build();

        chatRoomService.save(requestDTO);

    }

}
