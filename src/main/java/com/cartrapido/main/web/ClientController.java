package com.cartrapido.main.web;

import com.cartrapido.main.service.ProductService;
import com.cartrapido.main.web.dto.ProductDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


@Controller
@AllArgsConstructor
public class ClientController {
    private ProductService productService;
/*    @GetMapping("/clientMain")
    public String clientWeb() {

        return "/clientWebBody/clientMain";
    }*/


    //마트들 상품 보여줌
    @GetMapping("/clientMart")
    public String clientMart(Model model) {
        List<ProductDTO> productList = productService.getProductList();
        model.addAttribute("productList",productList);


        return "/clientWebBody/mart";
    }


    //마트별로(where store) 상품 보여줌 //
    @GetMapping("/clientMart/{mart}")
    public String detail( @PathVariable("mart") String mart, Model model) {
//        List<ProductDTO> productList = productService.getProductList();
        System.out.println(mart);
        List<ProductDTO> productList = productService.getStoreProductList(mart);
        model.addAttribute("productList",productList);
        model.addAttribute("mart",mart);

        return "/clientWebBody/mart";
    }


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
