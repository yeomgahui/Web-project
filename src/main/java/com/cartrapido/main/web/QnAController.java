package com.cartrapido.main.web;

import com.cartrapido.main.config.auth.dto.SessionUser;
import com.cartrapido.main.service.*;
import com.cartrapido.main.web.dto.OrderNumDTO;
import com.cartrapido.main.web.dto.OrderNumHistoryDTO;
import com.cartrapido.main.web.dto.QnADTO;
import com.cartrapido.main.web.dto.WishItemDTO;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
@AllArgsConstructor
public class QnAController {

    @Autowired
    private QnAService qnaService;

    @GetMapping("/qna")
    public String qna() { return "/clientWebBody/qnaWrite";  }

    @PostMapping("/qnaWrite")
    public @ResponseBody void qnaWrite(@RequestBody @Valid QnADTO qnaDTO) {
        System.out.println("controller");
        qnaService.qnaWrite(qnaDTO);
    }

/*    @PostMapping("/qnaList")
    @ResponseBody
    public ModelAndView qnaList() {

        List<QnADTO> qnaList = qnaService.getQnAList();

        for(QnADTO qnaDTO: qnaList){
            System.out.println(qnaDTO.getTitle());
        }

        ModelAndView mav = new ModelAndView();
        mav.addObject("qnaList", qnaList);
        mav.setViewName("jsonView");
        return mav;
    }*/

    @GetMapping("/qnaList")
    public String qnaList() {
        return "/clientWebBody/qnaList";
    }

}
