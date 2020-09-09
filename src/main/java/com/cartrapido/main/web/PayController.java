package com.cartrapido.main.web;

import com.cartrapido.main.config.auth.dto.SessionUser;
import com.cartrapido.main.service.MemberService;
import com.cartrapido.main.service.OrderNumService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@AllArgsConstructor
public class PayController {

    @Autowired
    private OrderNumService orderNumService;
    @Autowired
    private MemberService memberService;

    @GetMapping("/payment/kakaoPay/{orderNum}/{payTot}/{point}")
    public String kakaoPay(@PathVariable("payTot") int payTot,
                           @PathVariable("orderNum") Long orderNum,
                           @PathVariable("point") int point,
                           Model model, HttpSession session) {
        SessionUser user = (SessionUser) session.getAttribute("user");
        String userEmail = user.getEmail();
        String userName = user.getName();

        model.addAttribute("orderNum",orderNum);
        model.addAttribute("payTot",payTot);
        model.addAttribute("userEmail",userEmail);
        model.addAttribute("userName",userName);
        model.addAttribute("point",point);

        return "/payment/kakaoPay";
    }

    @GetMapping("/payment/cardPay/{orderNum}/{payTot}/{point}")
    public String cardPay( @PathVariable("payTot") int payTot,
                           @PathVariable("orderNum") Long orderNum,
                           @PathVariable("point") int point,
                           Model model, HttpSession session) {
        SessionUser user = (SessionUser) session.getAttribute("user");
        String userEmail = user.getEmail();
        String userName = user.getName();
        System.out.print(userName);

        model.addAttribute("orderNum",orderNum);
        model.addAttribute("payTot",payTot);
        model.addAttribute("userEmail",userEmail);
        model.addAttribute("userName",userName);
        model.addAttribute("point",point);

        return "/payment/cardPay";
    }


    @GetMapping("/payComplete")
    public String payComplete() {
        return "/payment/payComplete";
    }

    @GetMapping("/payFail")
    public String payFail() {
        return "/payment/payFail";
    }

    @PostMapping("/updatePay")
    @ResponseBody
    public void updatePay(@RequestParam Long orderNum, @RequestParam int point,
                          HttpSession session) {
        System.out.println("===========updatePay=====point==========="+point+"=================");
        SessionUser user = (SessionUser) session.getAttribute("user");
        String userEmail = user.getEmail();
        //pay현황 true로 변경
        orderNumService.updatePay(orderNum);
        //point 차감
        memberService.usePoint(userEmail,point);
        //point session 업데이트
        user.setPoint(user.getPoint()-point);
        session.setAttribute("user",user);
    }
}
