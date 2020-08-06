package com.insta.cart.web;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class IndexController {



    @GetMapping("/index") //@RequestMapping(method=RequestMethod.GET)
    public String index() {
        return "/ogani/index";
    }

    @GetMapping("/blog") //@RequestMapping(method=RequestMethod.GET)
    public String blog() {
        return "/ogani/blog";
    }

    @GetMapping("/blog-details") //@RequestMapping(method=RequestMethod.GET)
    public String blogDetails() {
        return "/ogani/blog-details";
    }


    @GetMapping("/checkout") //@RequestMapping(method=RequestMethod.GET)
    public String checkout() {
        return "/ogani/checkout";
    }

    @GetMapping("/contact") //@RequestMapping(method=RequestMethod.GET)
    public String contact() {
        return "/ogani/contact";
    }

    @GetMapping("/main") //@RequestMapping(method=RequestMethod.GET)
    public String main() {
        return "/ogani/main";
    }

    @GetMapping("/shop-details") //@RequestMapping(method=RequestMethod.GET)
    public String shopDetail() {
        return "/ogani/shop-details";
    }

    @GetMapping("/shop-grid") //@RequestMapping(method=RequestMethod.GET)
    public String shopGrid() {
        return "/ogani/shop-grid";
    }

    @GetMapping("/shoping-cart") //@RequestMapping(method=RequestMethod.GET)
    public String shopingCart() {
        return "/ogani/shoping-cart";
    }

    @GetMapping("/productSearch") //@RequestMapping(method=RequestMethod.GET)
    public String productSearch() {
        return "/ogani/productSearch";
    }
}
