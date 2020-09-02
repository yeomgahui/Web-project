package com.cartrapido.main.web;

import com.cartrapido.main.config.auth.dto.SessionUser;
import com.cartrapido.main.service.*;
import com.cartrapido.main.web.dto.OrderNumDTO;
import com.cartrapido.main.web.dto.OrderNumHistoryDTO;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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


    @GetMapping("/listHistory")
    public String listHistory(Model model, HttpSession session) {
        SessionUser user = (SessionUser) session.getAttribute("user");
        String userEmail = user.getEmail();
        List<OrderNumHistoryDTO> orderNumHistoryDTOList = orderNumHistoryService.getOrderNumHistoryList();

        if(orderNumHistoryDTOList.size()==0)
            return "/clientWebBody/clientList/noList";
        else
            model.addAttribute("orderNumList", orderNumHistoryDTOList);
        return "/clientWebBody/clientList/listHistory";

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

}
