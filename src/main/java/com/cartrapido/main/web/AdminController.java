package com.cartrapido.main.web;

import com.cartrapido.main.domain.entity.Member;
import com.cartrapido.main.memberControl.dto.BlackListResponseDTO;
import com.cartrapido.main.memberControl.service.BlackListService;
import com.cartrapido.main.service.MemberService;
import com.cartrapido.main.service.OrderNumHistoryService;
import com.cartrapido.main.service.OrderNumService;
import com.cartrapido.main.service.ProductService;
import com.cartrapido.main.web.dto.MemberListDTO;
import com.cartrapido.main.web.dto.SalesOfMonthDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Controller
public class AdminController {
    private final BlackListService blackListService;
    private final MemberService memberService;
    private final ProductService productService;
    private final OrderNumHistoryService orderNumHistoryService;
    private final OrderNumService orderNumService;

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

}
