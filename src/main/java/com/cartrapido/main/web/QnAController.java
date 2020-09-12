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
import org.springframework.data.web.SortDefault;
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
    public String qna(Model model) {
        model.addAttribute("qnaDTO",null);
        return "/clientWebBody/qnaWrite";
    }

    @PostMapping("/qnaWrite")
    public @ResponseBody void qnaWrite(@RequestBody @Valid QnADTO qnaDTO) {

        qnaService.qnaWrite(qnaDTO);
    }

    @GetMapping("/qnaList")
    public String qnaList(Model model,
                          @PageableDefault(size=5)@SortDefault.SortDefaults({
                                  @SortDefault(sort = "ref", direction = Sort.Direction.DESC),
                                  @SortDefault(sort = "lev", direction = Sort.Direction.DESC)
                          })Pageable pageable) {

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
    public String qnaView(Model model, HttpSession session, @RequestParam int seq) {

        SessionUser user = (SessionUser) session.getAttribute("user");
        String userEmail = user.getEmail();
        QnADTO qnaDTO = qnaService.qnaView(seq);
        model.addAttribute("qnaDTO",qnaDTO);
        model.addAttribute("userEmail",userEmail);
        return "/clientWebBody/qnaView";
    }

    @GetMapping("/qnaUpdateView")
    public String qnaUpdateView(Model model, @RequestParam int seq) {

        QnADTO qnaDTO = qnaService.qnaView(seq);
        model.addAttribute("qnaDTO",qnaDTO);

        return "/clientWebBody/qnaUpdate";  }

    @PostMapping("/qnaUpdate")
    public @ResponseBody void qnaUpdate(@RequestBody @Valid QnADTO qnaDTO) {
        qnaService.qnaUpdate(qnaDTO);
    }

    @PostMapping("/qna/qnaDelete")
    public @ResponseBody void qnaDelete(@RequestParam(name="seq") int seq) {
        qnaService.qnaDelete(seq);
    }

    @PostMapping("/qna/qnaDeleteAll")
    public @ResponseBody void qnaDelete(HttpSession session) {
        SessionUser user = (SessionUser) session.getAttribute("user");
        String userEmail = user.getEmail();

        qnaService.qnaDeleteAll(userEmail);
    }
}
