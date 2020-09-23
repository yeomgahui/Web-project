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
import java.util.*;

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
        return "/shopperWebBody/firstPage.html";
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
        List<Double> distanceList = new ArrayList<>();
        List<OrderNumDTO> orderNumDTOListReturn = new ArrayList<>();
        List<OrderNumDTO> orderNumDTOList = orderNumService.shopperGetPaidOrder(1);

        for(int i = 0 ; i<orderNumDTOList.size();i++){//중복값의 거리 중 최솟값 계산해서 orderNumDTOList에 넣어주기
            List<OrderSheetDTO> orderSheetList = orderSheetService.getOrderSheetList(orderNumDTOList.get(i).getOrderNum());
            for(int j = 0 ; j<orderSheetList.size();j++){

                distanceList.add(orderSheetList.get(j).getDistance());

            }

            Collections.sort(distanceList);
            orderNumDTOList.get(i).setDistance(distanceList.get(0));//계산한 최솟값 setter로 넣어줌
            distanceList.clear();//이전 값 지워주기

            if(orderNumDTOList.get(i).getShopper()==null){//쇼퍼와 매칭전이면,
                orderNumDTOListReturn.add(orderNumDTOList.get(i));
            }

        }
        orderNumDTOListReturn.sort(Comparator.comparing(OrderNumDTO::getDistance));

        for(int j = 0 ; j<orderNumDTOListReturn.size();j++){
            System.out.println("sort 값 : " + orderNumDTOListReturn.get(j).getOrderNum());//최솟값 확인
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




    @RequestMapping(value = "clientLatlng")
    @ResponseBody
    public List<OrderSheetDTO> clientLatlng() {

        List<OrderNumDTO> orderNumDTOList = orderNumService.shopperGetPaidOrder(1);
        System.out.println(orderNumDTOList);
        List<OrderSheetDTO> orderSheetListReturn = new ArrayList<>();


        for(int i = 0 ; i<orderNumDTOList.size();i++){
            List<OrderSheetDTO> orderSheetList =
                    orderSheetService.getOrderSheetList(orderNumDTOList.get(i).getOrderNum());


            for(int j = 0 ; j < orderSheetList.size();j++){
                orderSheetListReturn.add(orderSheetList.get(j));

            }

       }



        return orderSheetListReturn;
    }



    @PostMapping("/allDistance")
    @ResponseBody
    public void allDistance(@RequestBody List<Map> allDistance) {
        Long orderId;
        Double distance;

        for (int i = 0; i < allDistance.size(); i++) {


            orderId = Long.valueOf(String.valueOf(allDistance.get(i).get("orderId")));
            distance = Double.valueOf(String.valueOf(allDistance.get(i).get("distance")));


            orderSheetService.saveDistance(orderId, distance);

        }
    }












}
