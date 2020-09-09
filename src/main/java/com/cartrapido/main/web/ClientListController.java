package com.cartrapido.main.web;

import com.cartrapido.main.config.auth.dto.SessionUser;
import com.cartrapido.main.domain.entity.OrderNumHistory;
import com.cartrapido.main.service.*;
import com.cartrapido.main.web.dto.OrderNumDTO;
import com.cartrapido.main.web.dto.OrderNumHistoryDTO;
import com.cartrapido.main.web.dto.WishItemDTO;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@AllArgsConstructor
public class ClientListController {

    @Autowired
    private OrderSheetService orderSheetService;

    @Autowired
    private OrderNumService orderNumService;

    @Autowired
    private OrderNumHistoryService orderNumHistoryService;

    @Autowired
    private WishItemService wishItemService;

    @GetMapping("/listHistory")
    public String listHistory(Model model, HttpSession session,
                              @PageableDefault(size=3, direction = Sort.Direction.ASC) Pageable pageable) {
        SessionUser user = (SessionUser) session.getAttribute("user");
        String userEmail = user.getEmail();
        Page<OrderNumHistory> orderNumHistories = orderNumHistoryService.getMyOrderNumListPage(userEmail,pageable);

        int startPage = Math.max(0, orderNumHistories.getPageable().getPageNumber()-2);
        int endPage = Math.min(orderNumHistories.getPageable().getPageNumber()+2, orderNumHistories.getTotalPages()-1);
        int endEndPage = orderNumHistories.getTotalPages();
        System.out.println(endEndPage+"endEndPage-------------------------------------------");


        if(orderNumHistories==null)
            return "/clientWebBody/clientList/noList";
        else {
            model.addAttribute("orderNumList", orderNumHistories);
            model.addAttribute("startPage",startPage);
            model.addAttribute("endPage",endPage);
            model.addAttribute("endEndPage",endEndPage);
            return "/clientWebBody/clientList/listHistory";
        }

    }

    @GetMapping("/myOrderList")
    public String myOrderList(Model model, HttpSession session) {
        SessionUser user = (SessionUser) session.getAttribute("user");
        String userEmail = user.getEmail();
        List<OrderNumDTO> orderNumDTOList = orderNumService.getPaidOrder(userEmail, "true");

        if(orderNumDTOList.size()==0)
            return "/clientWebBody/clientList/noList";
        else
            model.addAttribute("orderNumList", orderNumDTOList);
        return "/clientWebBody/clientList/myOrderList";

    }

    @GetMapping("/toPayList")
    public String toPayList(Model model, HttpSession session) {
        SessionUser user = (SessionUser) session.getAttribute("user");
        String userEmail = user.getEmail();
        List<OrderNumDTO> orderNumDTOList = orderNumService.getPaidOrder(userEmail, "false");

        if(orderNumDTOList.size()==0)
            return "/clientWebBody/clientList/noList";
        else
            model.addAttribute("orderNumList", orderNumDTOList);
        return "/clientWebBody/clientList/toPayList";

    }

    @GetMapping("/wishItems")
    public String wishItems(Model model, HttpSession session) {
        SessionUser user = (SessionUser) session.getAttribute("user");
        String userEmail = user.getEmail();
        List<WishItemDTO> wishItemDTOList = wishItemService.findByEmail(userEmail);
        if(wishItemDTOList.size()==0){
            return "/clientWebBody/wish/noWishItem";
        }else {
            model.addAttribute("wishItemDTOList",wishItemDTOList);
            return "/clientWebBody/wish/wishItemList";
        }
    }

}
