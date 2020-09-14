package com.cartrapido.main.web;

import com.cartrapido.main.config.auth.dto.SessionUser;
import com.cartrapido.main.domain.entity.Member;
import com.cartrapido.main.domain.entity.QnA;
import com.cartrapido.main.memberControl.dto.BlackListResponseDTO;
import com.cartrapido.main.memberControl.service.BlackListService;
import com.cartrapido.main.service.*;
import com.cartrapido.main.web.dto.MemberListDTO;

import com.cartrapido.main.web.dto.QnADTO;

import com.cartrapido.main.web.dto.OrderSheetDTO;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

import java.util.*;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Controller
public class AdminController {
    private final BlackListService blackListService;
    private final MemberService memberService;
    private final ProductService productService;
    private final OrderNumHistoryService orderNumHistoryService;
    private final OrderNumService orderNumService;
    private final OrderSheetService orderSheetService;

    @Autowired
    private QnAService qnaService;

    //어드민 페이지 테스트
    @GetMapping("/adminTest")
    public String dispAdmin(Model model){
        // /admin/blackList.html 붙이기
        model.addAttribute("template","/admin/adminMain.html");
        return "/admin/adminSidebar.html";
    }
    //회원관리
    @GetMapping("/adminTest/blackList")
    public String blackList(Model model){
        List<BlackListResponseDTO> blackList = blackListService.findAllDesc();

        model.addAttribute("blackList",blackList);

        // /admin/blackList.html 붙이기
        model.addAttribute("js","/js/admin/blackList.js");
        model.addAttribute("template","/admin/blackList.html");
        return "/admin/adminSidebar.html";
    }
    //블랙리스트 삭제
    @PostMapping("/adminTest/deleteBlackList")
    public @ResponseBody void deleteBlackList(@RequestParam(name="id") Long id){
        blackListService.delete(id);
    }

    //회원 정지 시키기
    @PostMapping("/adminTest/blackMember")
    public @ResponseBody void blackMember(@RequestParam(name="id") Long id, @RequestParam(name="shopper") String shopper){
        //회원 로그인 못하게 막기
        memberService.enableSet(shopper,false);
        //리스트에서 삭제
        blackListService.delete(id);

    }
    //차트
    @GetMapping("/adminTest/dashboard")
    public String dashboard(Model model){
        //총 상품 갯수
        model.addAttribute("productCount",productService.getTotalNumProduct());
        Long numHistory = orderNumHistoryService.getTotalNumHistory();
        Long numOrderRun = orderNumService.getTotalNumOrder();
        //총 판매량
        model.addAttribute("salesCount",numHistory+numOrderRun);
        //총 수익
        model.addAttribute("revenueSum",orderNumHistoryService.getTotalRevenue());
        //총인원 수
        model.addAttribute("totalMember",memberService.getTotalMember());
        //shopper와 client수
        model.addAttribute("NumOfCS",memberService.getChaqueMember());
        //오늘 주문량
        model.addAttribute("todaySales", orderNumHistoryService.getTodaySales());

        model.addAttribute("js","/js/admin/dashboard.js");
        model.addAttribute("template","/admin/dashboard.html");
        return "/admin/adminSidebar.html";
    }
    @PostMapping("/adminTest/chartData")
    public @ResponseBody ModelAndView chartData(){
        System.out.println("data가져오기");

        ModelAndView mv = new ModelAndView("jsonView");
        mv.addObject("list",orderNumHistoryService.salesOfMonth());
        return mv;
    }


    //회원 검색 페이지
    @GetMapping("/adminTest/findMemberPage")
    public String findMemberPage(Model model){
        model.addAttribute("js","/js/admin/findMemberPage.js");
        model.addAttribute("template","/admin/findMemberPage.html");
        return "/admin/adminSidebar.html";
    }

    @PostMapping("/adminTest/findMember/{page}")
    public @ResponseBody ModelAndView findMember(@PathVariable int page, @RequestParam(name="user") String user, @RequestParam(name="searchOption") String searchOption){

        //회원 조회
       Page<Member> memberList=  memberService.getMemberList(PageRequest.of(page-1,2),user,searchOption);

       List<MemberListDTO> searchMembers = memberList.stream().map(MemberListDTO::new).collect(Collectors.toList());
       ModelAndView mv = new ModelAndView("jsonView");
        mv.addObject("list",searchMembers);
        mv.addObject("pageNum",memberList.getTotalPages());

        return mv;
    }
    @PostMapping("/adminTest/deblock")
    public  @ResponseBody void deblock(@RequestParam(name="user") String user){
        memberService.enableSet(user,true);

    }

    //Q&A 관리창 보기
    @GetMapping("/adminTest/adminQnaList")
    public String adminQnaList(Model model,
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
        model.addAttribute("template","/admin/adminQnaList.html");
        return "/admin/adminSidebar.html";
    }

    //Q&A 답변
    @GetMapping("/adminTest/qnaAnswer")
    public String qnaAnswer(Model model,@RequestParam int seq) {

        QnADTO qnaDTO = qnaService.qnaView(seq);
        model.addAttribute("qnaDTO",qnaDTO);
        model.addAttribute("template","/admin/adminQnaAnswer.html");
        return "/admin/adminSidebar.html";
    }

    //답변 DB 등록
    @PostMapping("/adminTest/qnaAnswerWrite")
    public @ResponseBody void qnaAnswerWrite(@RequestBody @Valid QnADTO qnaDTO) {
        QnADTO qnaAnswerDTO = new QnADTO();
        qnaAnswerDTO.setName("관리자");
        qnaAnswerDTO.setEmail("admin@wm.com");
        qnaAnswerDTO.setTitle(qnaDTO.getTitle());
        qnaAnswerDTO.setContent(qnaDTO.getContent());
        qnaAnswerDTO.setRef(qnaDTO.getSeq());
        qnaAnswerDTO.setLev(1);
        qnaService.qnaAnswerWrite(qnaAnswerDTO);
    }

    //Q&A search
    @PostMapping("/adminTest/searchQna")
    public @ResponseBody ModelAndView searchQna(@RequestParam(name="searchValue") String searchValue,
                                                @RequestParam(name="searchOption") String searchOption,
                                                @PageableDefault(size=5)@SortDefault.SortDefaults({
                                                        @SortDefault(sort = "ref", direction = Sort.Direction.DESC),
                                                        @SortDefault(sort = "lev", direction = Sort.Direction.DESC)})Pageable pageable) {

        List<QnADTO> qnaList = qnaService.qnaSearchList(pageable, searchValue, searchOption);
        Page<QnA> pageQnA = qnaService.pagingQnaSearchList(pageable, searchValue, searchOption);

        int startPage = Math.max(0, pageQnA.getPageable().getPageNumber() - 2);
        int endPage = Math.min(pageQnA.getPageable().getPageNumber() + 2, pageQnA.getTotalPages() - 1);
        int endEndPage = pageQnA.getTotalPages() - 1;

        ModelAndView mov = new ModelAndView("jsonView");

        mov.addObject("qnaList", qnaList);
        mov.addObject("startPage", startPage);
        mov.addObject("endPage", endPage);
        mov.addObject("endEndPage", endEndPage);

        return mov;
    }

    @PostMapping("/adminTest/storeRank")
    @ResponseBody
    public ModelAndView storeRank(Model model){
        System.out.println("============storeRank========================");
        String[] storeList = {"emart","lottemart","homeplus","cu","gs25","ministop"};

        List<OrderSheetDTO> orderSheetDTOS= new ArrayList<>();

        ModelAndView mv = new ModelAndView("jsonView");

        for(String data : storeList){
            OrderSheetDTO orderSheetDTO = new OrderSheetDTO();
            orderSheetDTO.setStore(data);
            orderSheetDTO.setAmount(orderSheetService.storeRank(data));
            orderSheetDTOS.add(orderSheetDTO);
        }

        Collections.sort(orderSheetDTOS, new Comparator<OrderSheetDTO>() {
            @Override
            public int compare(OrderSheetDTO s1, OrderSheetDTO s2) {
                if (s1.getAmount() < s2.getAmount()) {
                    return 1;
                } else if (s1.getAmount() > s2.getAmount()) {
                    return -1;
                }
                return 0;
            }
        });

        model.addAttribute("list", orderSheetDTOS);
        mv.addObject("list",orderSheetDTOS);
        return mv;

    }

}
