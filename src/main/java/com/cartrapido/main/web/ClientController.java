package com.cartrapido.main.web;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@AllArgsConstructor
public class ClientController {

/*    @GetMapping("/clientMain")
    public String clientWeb() {

        return "/clientWebBody/clientMain";
    }*/

    @GetMapping("/clientLayout")
    public String clientLayout() {

        return "/clientWeb/clientLayout";
    }

    @GetMapping("/clientMypage")
    public String clientMypage() {

        return "/clientWebBody/myPage";
    }

    @GetMapping("/shoppingCart")
    public String shoppingCart() {

        return "/clientWebBody/shoppingCart";
    }

    @GetMapping("/clientChatting")
    public String clientChatting() {

        return "/clientWebBody/clientChatting.html";
    }

}
