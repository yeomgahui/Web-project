package com.cartrapido.main.web;

import com.cartrapido.main.config.auth.dto.SessionUser;
import com.cartrapido.main.service.OrderNumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@AllArgsConstructor
public class PayController {

    @Autowired
    private OrderNumService orderNumService;

    @GetMapping("/payment/kakaoPay/{orderNum}/{payTot}")
    public String kakaoPay(@PathVariable("payTot") int payTot,
                           @PathVariable("orderNum") Long orderNum,
                           Model model, HttpSession session) {
        SessionUser user = (SessionUser) session.getAttribute("user");
        String userEmail = user.getEmail();
        String userName = user.getName();
        System.out.print(userName);

        model.addAttribute("orderNum",orderNum);
        model.addAttribute("payTot",payTot);
        model.addAttribute("userEmail",userEmail);
        model.addAttribute("userName",userName);

        return "/payment/kakaoPay";
    }

    @GetMapping("/payment/cardPay/{orderNum}/{payTot}")
    public String cardPay( @PathVariable("payTot") int payTot,
                           @PathVariable("orderNum") Long orderNum,
                           Model model, HttpSession session) {
        SessionUser user = (SessionUser) session.getAttribute("user");
        String userEmail = user.getEmail();
        String userName = user.getName();
        System.out.print(userName);

        model.addAttribute("orderNum",orderNum);
        model.addAttribute("payTot",payTot);
        model.addAttribute("userEmail",userEmail);
        model.addAttribute("userName",userName);

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
    public void updatePay(@RequestParam Long orderNum) {
        System.out.println("-----------updatePay 컨트롤러 -------------------------");
        orderNumService.updatePay(orderNum);
    }
}
