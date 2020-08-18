package com.cartrapido.main.web;

import com.cartrapido.main.config.auth.dto.SessionUser;
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
import org.springframework.web.bind.annotation.ResponseBody;
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

    @PostMapping("/shopper/viewOrderSheet")
    @ResponseBody
    public ModelAndView viewOrderSheet(@RequestParam Long orderNum, Model model){
        List<OrderSheetDTO> orderSheetList =
                orderSheetService.getOrderSheetList(orderNum);

        for(OrderSheetDTO dto:orderSheetList){
            System.out.println("view 상품 = "+dto.getProductName());
        }

        ModelAndView mv = new ModelAndView("jsonView");
        mv.addObject("list",orderSheetList);
        return mv;
    }

    @PostMapping("/acceptOrder")
    @ResponseBody
    public void acceptOrder(@RequestParam Long orderNum, Model model, HttpSession session ){
        System.out.println(orderNum+"번 주문을 수락합니다.");
        SessionUser user = (SessionUser) session.getAttribute("user");
        String shopperEmail = user.getEmail();
        System.out.println(shopperEmail+" 쇼퍼의 이메일 ");
        orderNumService.acceptOrder(orderNum,shopperEmail);
    }

}
