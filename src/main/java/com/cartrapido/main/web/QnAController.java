package com.cartrapido.main.web;

import com.cartrapido.main.config.auth.dto.SessionUser;
import com.cartrapido.main.domain.entity.QnA;
import com.cartrapido.main.service.QnAService;
import com.cartrapido.main.web.dto.QnADTO;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

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

    @GetMapping("/qnaList")
    public String qnaList(Model model,
                          @PageableDefault(size=5, sort = "seq", direction = Sort.Direction.DESC)Pageable pageable) {

        List<QnADTO> qnaList = qnaService.getQnAList(pageable);
        Page<QnA> pageQnA = qnaService.pagingQnAList(pageable);

        int startPage = Math.max(0, pageQnA.getPageable().getPageNumber()-2);
        int endPage = Math.min(pageQnA.getPageable().getPageNumber()+2, pageQnA.getTotalPages()-1);
        int endEndPage = pageQnA.getTotalPages()-1;

        model.addAttribute("qnaList",qnaList);
        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);
        model.addAttribute("endEndPage",endEndPage);

        return "/clientWebBody/qnaList";
    }

    @GetMapping("/qnaMyList")
    public String qnaList(Model model, HttpSession session,
                          @PageableDefault(size=5, sort = "seq", direction = Sort.Direction.DESC)Pageable pageable) {

        SessionUser user = (SessionUser) session.getAttribute("user");
        String userEmail = user.getEmail();
        System.out.println(userEmail);

        List<QnADTO> qnaMyList = qnaService.getMyQnAList(userEmail, pageable);
        Page<QnA> pageMyQnA = qnaService.pagingMyQnAList(userEmail, pageable);

        int startPage = Math.max(0, pageMyQnA.getPageable().getPageNumber()-2);
        int endPage = Math.min(pageMyQnA.getPageable().getPageNumber()+2, pageMyQnA.getTotalPages()-1);
        int endEndPage = pageMyQnA.getTotalPages()-1;

        model.addAttribute("qnaMyList",qnaMyList);
        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);
        model.addAttribute("endEndPage",endEndPage);

        return "/clientWebBody/qnaMyList";
    }
    @GetMapping("/qnaView")
    public String qnaView(Model model, @RequestParam String seq) {
        System.out.println(seq);
        QnADTO qnaDTO = qnaService.qnaView(Integer.parseInt(seq));
        model.addAttribute("qnaDTO",qnaDTO);
        return "/clientWebBody/qnaView";  }
}
