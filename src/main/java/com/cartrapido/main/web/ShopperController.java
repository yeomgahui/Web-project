package com.cartrapido.main.web;

import com.cartrapido.main.service.OrderNumService;
import com.cartrapido.main.service.OrderSheetService;
import com.cartrapido.main.web.dto.OrderNumDTO;
import com.cartrapido.main.web.dto.OrderSheetDTO;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@AllArgsConstructor
public class ShopperController {
    @Autowired
    OrderSheetService orderSheetService;
    @Autowired
    OrderNumService orderNumService;

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

        List<OrderNumDTO> orderNumDTOList = orderNumService.getOrderNumList();
        System.out.println(orderNumDTOList.get(0).getOrderNum());
        if(orderNumDTOList.size()!=0){
            model.addAttribute("orderNumList", orderNumDTOList);
        }
        return "/shopperWebBody/orderSheetList";
    }

    @PostMapping("/shopper/viewOrderSheet")
    public String viewOrderSheet(@RequestParam Long orderNum, Model model){
        List<OrderSheetDTO> orderSheetList =
                orderSheetService.getOrderSheetList(orderNum);

        for(OrderSheetDTO dto:orderSheetList){
            System.out.println("view 상품 = "+dto.getProductName());
        }

        model.addAttribute("orderSheetList",orderSheetList);

        return "/shopperWebBody/viewOrderSheet";
    }

}
