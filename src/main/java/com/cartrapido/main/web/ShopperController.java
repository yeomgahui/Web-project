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
        model.addAttribute("orderNumList", orderNumDTOList);
        return "/shopperWebBody/orderSheetList";
    }

    @PostMapping("/viewOrderSheet")
    public String viewOrderSheet(@RequestParam Long orderNum, Model model){
        System.out.println("view 컨트롤러에 들어온다"+orderNum);
        List<OrderSheetDTO> orderSheetList =
                orderSheetService.getOrderSheetList(orderNum);
        model.addAttribute("orderSheetList",orderSheetList);

        return "/shopperWebBody/viewOrderSheet";
    }

}
