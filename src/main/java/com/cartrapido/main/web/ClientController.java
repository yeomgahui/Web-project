package com.cartrapido.main.web;

import com.cartrapido.main.chat.service.ChatMessageService;
import com.cartrapido.main.chat.service.ChatRoomService;
import com.cartrapido.main.memberControl.dto.BlackListSaveDTO;
import com.cartrapido.main.memberControl.service.BlackListService;
import com.cartrapido.main.config.auth.dto.SessionUser;
import com.cartrapido.main.domain.entity.OrderNum;
import com.cartrapido.main.domain.entity.Product;
import com.cartrapido.main.domain.repository.WishItemRepository;
import com.cartrapido.main.service.*;
import com.cartrapido.main.web.dto.*;
import lombok.AllArgsConstructor;
import org.openqa.selenium.support.FindAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.batch.BatchAutoConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.lang.reflect.Array;
import java.util.*;


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

    private BlackListService blackListService;

    private MemberService memberService;

    private ChatRoomService chatRoomService;
    private ChatMessageService chatMessageService;

    /*    @GetMapping("/clientMain")
        public String clientWeb() {

            return "/clientWebBody/clientMain";
        }*/
    @Autowired
    private OrderNumHistoryService orderNumHistoryService;

    @Autowired
    private WishItemService wishItemService;

    //페이징 적용
    //마트별로(where store) 상품 보여줌
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

    //장바구니에 넣기
    @PostMapping("/clientMart/putInCart")
    @ResponseBody
    public void putInCart(@RequestParam Long productId,HttpSession session,
                          @RequestParam(required=true,defaultValue="1") Integer amount) {

        ProductDTO productDTO = productService.getProductInfo(productId);
        SessionUser user = (SessionUser) session.getAttribute("user");



        String userEmail = user.getEmail();
        System.out.println(productDTO.getStore()+" 상품을 카트에 넣는다");
        CartDTO cartDTO = CartDTO.builder()
                .userEmail(userEmail)
                .productId(productId)
                .amount(amount)
                .build();
        if(cartService.checkCart(productId, userEmail)==true) cartService.saveCart(cartDTO);
    }

    @PostMapping("/clientLatlng")
    @ResponseBody
    public String clientLatlng(@RequestBody HashMap clientLatlng, HttpSession session){

        System.out.println("clientLatlng : "+ clientLatlng);

        session.setAttribute("clientLatlng", clientLatlng);

        return "/clientLatlng";

    }

    @PostMapping("/marketLatlng")
    @ResponseBody
    public String marketLatlng(@RequestBody List<Map> marketLatlng, HttpSession session){

        System.out.println("marketLatlng : "+ marketLatlng.get(0));

        session.setAttribute("market", marketLatlng);

        return "/marketLatlng";

    }


    //위시리스트 상품 삭제
    @PostMapping("/deleteWishItem")
    @ResponseBody
    public void deleteWishItem(@RequestParam Long wiSequence) {
        wishItemService.deleteWishItem(wiSequence);
    }

    //카트에서 상품 삭제
    @PostMapping("/deleteInCart")
    @ResponseBody
    public void deleteInCart(@RequestParam Long cartId) {
        cartService.deleteCart(cartId);
    }

    //주문서 삭제
    @PostMapping("/deleteOrder")
    @ResponseBody
    public void deleteOrder(@RequestParam Long orderNum) {
        orderNumService.deleteOrder(orderNum);
    }

    //거래 끝내기
    @PostMapping("/finishOrder")
    @ResponseBody
    public void finishOrder(@RequestParam Long orderNum, @RequestParam int score, HttpSession session) {
        System.out.println("===============finishOrder=========");
        SessionUser user = (SessionUser) session.getAttribute("user");
        String userEmail = user.getEmail();

        OrderNumDTO orderNumDTO = orderNumService.getOrderNum(orderNum);
        OrderNumHistoryDTO orderNumHistoryDTO =
                new OrderNumHistoryDTO(
                        orderNum, orderNum, orderNumDTO.getUserEmail(), orderNumDTO.getShopper(),
                        orderNumDTO.getDeliveryCost(), orderNumDTO.getProductTot(), null,
                        orderNumDTO.getAddress(), orderNumDTO.getDetailAddress(),
                        orderNumDTO.getAgree(), orderNumDTO.getRequest(),orderNumDTO.getCreatedDate());


        orderNumHistoryService.saveOrderNum(orderNumHistoryDTO);
        orderNumService.deleteOrder(orderNum);
        //채팅방 지우기 0908 추가 gahui
        String roomId = chatRoomService.deleteChatRoom(orderNum);
        chatMessageService.deleteMessages(roomId);
        //쇼퍼에게 점수주기
        memberService.updateScore(orderNumDTO.getShopper(),score);
        //결제액의 1퍼센트 포인트 적립
        int savePoint = (orderNumDTO.getProductTot()+orderNumDTO.getDeliveryCost())/100;
        memberService.updatePoint(userEmail,savePoint);

        user.setPoint(user.getPoint()+savePoint);
        session.setAttribute("user",user);

    }

    //주문서 발주하기
    @PostMapping("/clientWeb/pushOrder")
    @ResponseBody /*@ModelAttribute List<CartDTO> cartList*/
    public String pushOrder(@RequestParam (value = "chbox[]") List<Long> checkArr,
                            @RequestParam (value = "amountArr[]") List<Integer> amountArr,
                            @RequestParam int productTot,
                            @RequestParam int deliveryCost,
                            HttpSession session
    ) {
        SessionUser user = (SessionUser) session.getAttribute("user");
        String userEmail = user.getEmail();
        System.out.println("pushOrder---------------------"+userEmail);
        OrderNumDTO orderNumDTO = OrderNumDTO.builder()
                .userEmail(userEmail)
                .shopper(null)
                .deliveryCost(deliveryCost)
                .productTot(productTot)
                .build();

        //OrderNum 저장
        OrderNum orderNum = orderNumService.saveOrderNum(orderNumDTO);
        Long orderNum1 = orderNum.getOrderNum();
        //OrderSheet 저장
        for(int i = 0 ; i <checkArr.size() ;i++){
            CartDTO cartDTO= cartService.getCartIdInfo(checkArr.get(i));
            String store = productService.getProductInfo(cartDTO.getProductId()).getStore();
            OrderSheetDTO orderSheetDTO =  OrderSheetDTO.builder()
                    .orderNum(orderNum1)
                    .userEmail(userEmail)
                    .productId(cartDTO.getProductId())
                    .amount(amountArr.get(i))
                    .store(store)
                    .build();
            System.out.println("pushOrder---------------------"+userEmail);

            orderSheetService.saveOrderSheet(orderSheetDTO);
            cartService.deleteCart(checkArr.get(i));
        }

        return orderNum1+"";
    }

    @GetMapping("/clientLayout")
    public String clientLayout() {

        return "/clientWeb/clientLayout";
    }

    //마이페이지 화면
    @GetMapping("/clientMypage")
    public String clientMypage() {

        return "/clientWebBody/myPage";
    }

    //쇼핑카트 화면
    @GetMapping("/shoppingCart")
    public String shoppingCart(HttpSession session, Model model) {
        SessionUser user = (SessionUser) session.getAttribute("user");
        String userEmail = user.getEmail();
        List<CartDTO> cartList = cartService.getCartList(userEmail);
        for(CartDTO cartDTO: cartList){
            ProductDTO productDTO = productService.getProductInfo(cartDTO.getProductId());
            cartDTO.setOtherInfo(
                    productDTO.getProductName(),productDTO.getProductPrice(),
                    productDTO.getStore(),productDTO.getImage()
            );
            System.out.println("shoppingCart----------------------------"+cartDTO.getAmount());
        }
        if(cartList.size()!=0) {
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

    //결제할 주문서들
    @GetMapping("/clientWeb/payMyOrder/{orderNum}")
    public String payMyOrder(@PathVariable("orderNum") Long orderNum, Model model){
        OrderNumDTO orderNumDTO = orderNumService.getOrderNum(orderNum);
        int productTot = orderNumDTO.getProductTot();
        int deliveryCost = orderNumDTO.getDeliveryCost();

        List<OrderSheetDTO> orderSheetList =
                orderSheetService.getOrderSheetList(orderNum);

        for(OrderSheetDTO dto:orderSheetList){
            System.out.println("view 상품 = "+dto.getProductName());
        }
        System.out.println("======payMyOrder getRequest================= "+orderNumDTO.getRequest());

        model.addAttribute("orderNumDTO", orderNumDTO);
        model.addAttribute("orderNumList", orderSheetList);
        model.addAttribute("orderSize", orderSheetList.size());

        return "/clientWebBody/clientView/payMyOrder";
    }

    //과거주문서 내용보기
    @GetMapping("/clientWeb/viewHistoryOrder/{orderNum}")
    public String viewHistoryOrder(@PathVariable("orderNum") Long orderNum, Model model){
        OrderNumHistoryDTO orderNumDTO = orderNumHistoryService.findByOriOrderNum(orderNum);

        int productTot = orderNumDTO.getProductTot();
        int deliveryCost = orderNumDTO.getDeliveryCost();

        List<OrderSheetDTO> orderSheetList =
                orderSheetService.getOrderSheetList(orderNum);

        model.addAttribute("orderNumDTO", orderNumDTO);
        model.addAttribute("orderNumList", orderSheetList);
        model.addAttribute("orderSize", orderSheetList.size());

        return "/clientWebBody/clientView/viewMyHistoryOrder";
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

    //채팅
    @GetMapping("/clientChatting")
    public String clientChatting() {
        return "/clientWebBody/clientChatting.html";
    }

    //주문서 기타정보 저장하기
    @PostMapping("/saveAddress")
    @ResponseBody
    public void saveAddress(@RequestBody @Valid OrderExtraInfoDTO OrderExtraInfoDTO) {
        orderNumService.saveAddress(OrderExtraInfoDTO);
    }

    //위시리스트에 담기
    @PostMapping("/putInWishList")
    @ResponseBody
    public void putInWishList(@RequestBody @Valid WishItemDTO wishItemDTO, HttpSession session) {
        System.out.println("-----------putInWishList "+wishItemDTO.getProductName());
        SessionUser user = (SessionUser) session.getAttribute("user");
        wishItemDTO.setEmail(user.getEmail());

        //위시리스트 중복체크
        if(wishItemService.checkWishList(wishItemDTO.getProductId(), user.getEmail())==true)
            wishItemService.saveWishItem(wishItemDTO);

        System.out.println("-----------putInWishList "+wishItemDTO.getProductId());
        productService.updateWishPoint(wishItemDTO.getProductId());

    }

    //회원 신고하기
    @PostMapping("/blackListSave")
    public @ResponseBody void blackListSave(@RequestBody BlackListSaveDTO blackListSaveDTO){
        blackListService.save(blackListSaveDTO);
    }

    //회원 신고하기
    @PostMapping("/getShopperScore")
    @ResponseBody
    public double getShopperScore(@RequestParam String shopper){
        System.out.println("============getShopperScore===============================");
        double score = memberService.getShopperScore(shopper);
        System.out.println(score+"===========================================");
        return score;
    }

    //product search
    @PostMapping("/searchProduct/{page}")
    public @ResponseBody
    ModelAndView searchProduct(@PathVariable int page,
                               @RequestParam(name="searchValue") String searchValue,
                               @RequestParam(name="store") String store,
                               @RequestParam(name="category") String category,
                               @PageableDefault(size=30, direction = Sort.Direction.ASC)Pageable pageable) {

        System.out.println(searchValue);
        System.out.println(store);
        System.out.println(category);

        List<ProductDTO> productList = productService.productSearchList(PageRequest.of(page-1,30), searchValue, store, category);
        Page<Product> pageProduct = productService.pagingProductSearchList(pageable, searchValue, store, category);

        int startPage = Math.max(0, pageProduct.getPageable().getPageNumber() - 2);
        int endPage = Math.min(pageProduct.getPageable().getPageNumber() + 2, pageProduct.getTotalPages() - 1);
        int endEndPage = pageProduct.getTotalPages() - 1;

        for(ProductDTO productDTO :productList){
            System.out.println(store+category+productDTO.getProductName());
        }

        ModelAndView mov = new ModelAndView("jsonView");

        mov.addObject("productList", productList);
        mov.addObject("mart", store);
        mov.addObject("startPage", startPage);
        mov.addObject("endPage", endPage);
        mov.addObject("endEndPage", endEndPage);

        return mov;
    }

    @PostMapping("/clientMart/putInWishList")
    @ResponseBody
    public void putInWishList(@RequestParam Long productId, HttpSession session) {
        ProductDTO productDTO = productService.getProductInfo(productId);

        System.out.println("-----------putInWishList "+productDTO.getProductName());

        SessionUser user = (SessionUser) session.getAttribute("user");
        WishItemDTO wishItemDTO = new WishItemDTO();
        wishItemDTO.setEmail(user.getEmail());
        wishItemDTO.setProductId(productId);

        //위시리스트 중복체크
        if(wishItemService.checkWishList(wishItemDTO.getProductId(), user.getEmail())==true)
            wishItemService.saveWishItem(wishItemDTO);

        System.out.println("-----------putInWishList "+wishItemDTO.getProductId());
        productService.updateWishPoint(wishItemDTO.getProductId());

    }

}
