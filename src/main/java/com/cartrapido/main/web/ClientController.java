package com.cartrapido.main.web;

import com.cartrapido.main.config.auth.dto.SessionUser;
import com.cartrapido.main.domain.entity.OrderNum;
import com.cartrapido.main.domain.entity.Product;
import com.cartrapido.main.service.*;
import com.cartrapido.main.web.dto.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;


@Controller
@AllArgsConstructor
public class ClientController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CartService cartService;

    @Autowired
    private OrderSheetService orderSheetService;

    @Autowired
    private OrderNumService orderNumService;

    @Autowired
    private OrderNumHistoryService orderNumHistoryService;

    @Autowired
    private WishItemService wishItemService;
    //페이징 적용
    //마트별로(where store) 상품 보여줌 //
    @GetMapping("/clientMart/{mart}")
    public String mart(@PathVariable("mart") String mart, Model model,
                       @PageableDefault(size=16, sort = "productId", direction = Sort.Direction.ASC)Pageable pageable) {
        List<ProductDTO> productList = productService.getStoreProductList(mart, pageable);
        Page<Product> products = productService.pagingStoreProduct(mart, pageable);
        List<String> categoryList = productService.getCategoryList(mart);

        int startPage = Math.max(0, products.getPageable().getPageNumber()-2);
        int endPage = Math.min(products.getPageable().getPageNumber()+2, products.getTotalPages()-1);
        int endEndPage = products.getTotalPages()-1;

        model.addAttribute("productList",productList);
        model.addAttribute("categoryList",categoryList);
        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);
        model.addAttribute("endEndPage",endEndPage);
        model.addAttribute("mart",mart);
        model.addAttribute("products",products);
        model.addAttribute("category","ALL");

        return "/clientWebBody/mart";
    }

    //페이징 적용
    //마트별로(where store), 카테고리별로(where category) 상품보여줌
    @GetMapping("/clientMart/{mart}/{category}")
    public String category( @PathVariable("mart") String mart, @PathVariable("category") String category, Model model,
                            @PageableDefault(size=30, direction = Sort.Direction.ASC)Pageable pageable) {

        List<ProductDTO> productList = productService.getCategoryProductList(mart, category, pageable);
        Page<Product> products = productService.pagingCategoryProduct(mart, category, pageable);

        int startPage = Math.max(0, products.getPageable().getPageNumber()-2);
        int endPage = Math.min(products.getPageable().getPageNumber()+2, products.getTotalPages()-1);
        int endEndPage = products.getTotalPages()-1;

        List<String> categoryList = productService.getCategoryList(mart);

        model.addAttribute("productList",productList);
        model.addAttribute("categoryList",categoryList);
        model.addAttribute("category",category);
        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);
        model.addAttribute("endEndPage",endEndPage);
        model.addAttribute("mart",mart);
        model.addAttribute("products",products);

        return "/clientWebBody/category";
    }

    @PostMapping("/clientMart/putInCart")
    @ResponseBody
    public void putInCart(@RequestParam Long productId, HttpSession session,
                          @RequestParam(required=true,defaultValue="1") Integer amount) {

        ProductDTO productDTO = productService.getProductInfo(productId);
        SessionUser user = (SessionUser) session.getAttribute("user");
        String userEmail = user.getEmail();
        System.out.println(productDTO.getStore()+" 상품을 카트에 넣는다");
        CartDTO cartDTO = new CartDTO(userEmail,productId,amount,
                productDTO.getProductName(), productDTO.getProductPrice(),
                productDTO.getImage(), productDTO.getStore());

        if(cartService.checkCart(productId, userEmail)==true) cartService.saveCart(cartDTO);
    }

    @PostMapping("/deleteInCart")
    @ResponseBody
    public void deleteInCart(@RequestParam Long cartId) {
        cartService.deleteCart(cartId);
    }

    @PostMapping("/deleteOrder")
    @ResponseBody
    public void deleteOrder(@RequestParam Long orderNum) {
        orderNumService.deleteOrder(orderNum);
    }

    @PostMapping("/finishOrder")
    @ResponseBody
    public void finishOrder(@RequestParam Long orderNum) {
        System.out.println("===============finishOrderfinishOrderfinishOrderfinishOrderfinishOrder=========");
        OrderNumDTO orderNumDTO = orderNumService.getOrderNum(orderNum);
        OrderNumHistoryDTO orderNumHistoryDTO = new OrderNumHistoryDTO(
                orderNum, orderNum, orderNumDTO.getUserEmail(), orderNumDTO.getShopper(),
                orderNumDTO.getDeliveryCost(), orderNumDTO.getProductTot(), orderNumDTO.getPay(),
                orderNumDTO.getAddress(), orderNumDTO.getDetailAddress(),
                orderNumDTO.getAgree(), orderNumDTO.getRequest());

        orderNumHistoryService.saveOrderNum(orderNumHistoryDTO);
        orderNumService.deleteOrder(orderNum);
    }

    @PostMapping("/amountPlus")
    @ResponseBody
    public void amountPlus(@RequestParam Long cartId) {
        cartService.amountPlus(cartId);
    }

    @PostMapping("/amountMinus")
    @ResponseBody
    public void amountMinus(@RequestParam Long cartId) {
        cartService.amountPlus(cartId);
    }


    @PostMapping("/clientWeb/pushOrder")
    @ResponseBody /*@ModelAttribute List<CartDTO> cartList*/
    public String pushOrder(@RequestParam (value = "chbox[]") List<Long> checkArr,
                          @RequestParam (value = "amountArr[]") List<Integer> amountArr,
                          @RequestParam int productTot,
                          @RequestParam int deliveryCost
                          ) {
    ) {

        CartDTO cartDTO = cartService.getCartIdInfo(checkArr.get(0));
        String userEmail = cartDTO.getUserEmail();
        OrderNumDTO orderNumDTO = new OrderNumDTO(
                userEmail,null,deliveryCost, productTot, "false"
        );
        //OrderNum 저장
        OrderNum orderNum = orderNumService.saveOrderNum(orderNumDTO);
        Long orderNum1 = orderNum.getOrderNum();
        //OrderSheet 저장
        for(int i = 0 ; i <checkArr.size() ;i++){
            cartDTO = cartService.getCartIdInfo(checkArr.get(i));
            OrderSheetDTO orderSheetDTO = new OrderSheetDTO(
                    orderNum1, orderNumDTO.getUserEmail(),
                    cartDTO.getProductId(), amountArr.get(i)
            );
            orderSheetDTO.setOtherInfo(cartDTO.getProductName(), cartDTO.getProductPrice(), cartDTO.getStore(),cartDTO.getImage());
            orderSheetService.saveOrderSheet(orderSheetDTO);
            cartService.deleteCart(checkArr.get(i));
        }

        return orderNum1+"";
    }

    @GetMapping("/clientLayout")
    public String clientLayout() {

        return "/clientWeb/clientLayout";
    }

    @GetMapping("/clientMypage")
    public String clientMypage() {

        return "/clientWebBody/myPage";
    }

    @GetMapping("/myOrderList")
    public String myOrderList(Model model, HttpSession session) {
        SessionUser user = (SessionUser) session.getAttribute("user");
        String userEmail = user.getEmail();
        List<OrderNumDTO> orderNumDTOList = orderNumService.getPaidOrder(userEmail, 1);

        if(orderNumDTOList.size()==0)
            return "/payment/noList";
        else
            model.addAttribute("orderNumList", orderNumDTOList);
        return "/clientWebBody/myOrderList";

    }

    @GetMapping("/toPayList")
    public String toPayList(Model model, HttpSession session) {
        SessionUser user = (SessionUser) session.getAttribute("user");
        String userEmail = user.getEmail();
        List<OrderNumDTO> orderNumDTOList = orderNumService.getPaidOrder(userEmail, 0);

        if(orderNumDTOList.size()==0)
            return "/payment/noList";
        else
            model.addAttribute("orderNumList", orderNumDTOList);
        return "/clientWebBody/toPayList";

    }


    @GetMapping("/shoppingCart")
    public String shoppingCart(HttpSession session, Model model) {
        SessionUser user = (SessionUser) session.getAttribute("user");
        String userEmail = user.getEmail();
        List<CartDTO> cartList = cartService.getCartList(userEmail);

        if(cartList.size()!=0) {
            for (CartDTO cartDTO : cartList) {
                System.out.println("카트 아이디 " + cartDTO.getCartId());
                System.out.println("카트 주인 " + cartDTO.getUserEmail());
                System.out.println("들어갈 상품 번호 " + cartDTO.getProductId());
                System.out.println("들어갈 상품 번호 " + cartDTO.getImage());
            }
            Long firstCartId = cartList.get(0).getCartId();
            Long lastCartId = cartList.get(cartList.size() - 1).getCartId();
            model.addAttribute("firstCartId", firstCartId);
            model.addAttribute("lastCartId", lastCartId);
            model.addAttribute("cartList", cartList);
        }else{
            model.addAttribute("firstCartId", 0);
            model.addAttribute("lastCartId", 0);
            model.addAttribute("cartList", cartList);
        }

        return "/clientWebBody/shoppingCart";
    }

    @GetMapping("/clientWeb/viewOrderSheet/{orderNum}")
    public String viewOrderSheet(@PathVariable("orderNum") Long orderNum, Model model){
        OrderNumDTO orderNumDTO = orderNumService.getOrderNum(orderNum);
        int productTot = orderNumDTO.getProductTot();
        int deliveryCost = orderNumDTO.getDeliveryCost();

        List<OrderSheetDTO> orderSheetList =
                orderSheetService.getOrderSheetList(orderNum);

        for(OrderSheetDTO dto:orderSheetList){
            System.out.println("view 상품 = "+dto.getProductName());
        }
        model.addAttribute("productTot", orderNum);
        model.addAttribute("productTot", productTot);
        model.addAttribute("deliveryCost", deliveryCost);
        model.addAttribute("orderNumDTO", orderNumDTO);
        model.addAttribute("orderNumList", orderSheetList);
        model.addAttribute("orderSize", orderSheetList.size());

        return "/clientWebBody/clientView/viewMyOrder";
    }


    @GetMapping("/clientChatting")
    public String clientChatting() {
        return "/clientWebBody/clientChatting.html";
    }

    @GetMapping("/payComplete")
    public String payComplete() {
        return "/payment/payComplete";
    }

    @PostMapping("/saveAddress")
    @ResponseBody
    public void saveAddress(@RequestBody @Valid OrderExtraInfoDTO OrderExtraInfoDTO) {
        orderNumService.saveAddress(OrderExtraInfoDTO);
    }

    @PostMapping("/putInWishList")
    @ResponseBody
    public void putInWishList(@RequestBody @Valid WishItemDTO wishItemDTO, HttpSession session) {
        System.out.println("-----------putInWishList "+wishItemDTO.getProductName());
        SessionUser user = (SessionUser) session.getAttribute("user");
        wishItemDTO.setEmail(user.getEmail());
        //위시리스트 중복체크
        if(wishItemService.checkWishList(wishItemDTO.getProductId(), user.getEmail())==true)
            wishItemService.saveWishItem(wishItemDTO);

        return "/payment/kakaoPay";
    }


    @GetMapping("/myFavorites")
    public String myFavorites() {
        return "/clientWebBody/myFavorites";
    }



}
