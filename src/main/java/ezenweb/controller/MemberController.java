package ezenweb.controller;

import ezenweb.model.dto.LoginDto;
import ezenweb.model.dto.MemberDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MemberController {

    // 1단계 V----C 사이의 HTTP 통신 방식 설계
    // 2단계 Controller mapping 함수 선언 하고 통신 체크 (API Tester)
    // 3단계 Controller request 매개변수 매핑
        //------Dto, Server-----

    //

    // 1. 회원가입 처리 요청
    @PostMapping("/member/signup")
    @ResponseBody
    public boolean signup(MemberDto memberDto){
        System.out.println("MemberController.signup");
        System.out.println("memberDto = " + memberDto);
        boolean result=true;
        return result;
    }

    // 2. 로그인 처리 요청
    @PostMapping("/member/login")
    @ResponseBody
    public boolean login(LoginDto loginDto){
        System.out.println("MemberController.login");
        System.out.println("loginDto = " + loginDto);
        //---
        boolean result=true;
        return result;
    }

    //3. 회원가입 페이지 요청
    @GetMapping("/member/signup")
    public String viewSignup(){
        System.out.println("MemberController.viewSignup");
        return "ezenweb/signup";
    }

    //4. 로그인 페이지 요청
    @GetMapping("member/login")
    public String viewLogin(){
        System.out.println("MemberController.viewLogin");
        return "ezenweb/login";
    }

    //5. 회원수정
    @GetMapping("/member/update")
    public String viewUpdate(){
        System.out.println("MemberController.edit");
        return "ezenweb/update";
    }

    @PostMapping("/member/update")
    @ResponseBody
    public boolean update(MemberDto memberDto){
        System.out.println("MemberController.update");
        return true;
    }

    //6. 회원삭제
    @GetMapping("/member/delete")
    public String delete(){
        System.out.println("MemberController.delete");
        return "redirect:/member/signup";
    }
}
