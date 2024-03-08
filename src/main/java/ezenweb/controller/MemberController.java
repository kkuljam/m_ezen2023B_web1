package ezenweb.controller;

import ezenweb.Service.MemberService;
import ezenweb.model.dao.MemberDao;
import ezenweb.model.dto.LoginDto;
import ezenweb.model.dto.MemberDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Controller
public class MemberController {

    // 1단계 V----C 사이의 HTTP 통신 방식 설계
    // 2단계 Controller mapping 함수 선언 하고 통신 체크 (API Tester)
    // 3단계 Controller request 매개변수 매핑
        //------Dto, Server-----

    //
    @Autowired
    private HttpServletRequest request;
    @Autowired
   private MemberDao memberDao;
    @Autowired
    private MemberService memberService;

    // 1. 회원가입 처리 요청
    @PostMapping("/member/signup")
    @ResponseBody
    public boolean signup(MemberDto memberDto){
        System.out.println("memberDto = " + memberDto);

        return memberService.doPostSignup(memberDto);
    }


    // 2. 로그인 처리 요청
    @PostMapping("/member/login")
    @ResponseBody
    public boolean login(LoginDto loginDto){
        System.out.println("MemberController.login");
        System.out.println("loginDto = " + loginDto);
        //---

        boolean result= memberDao.doPostLogin(loginDto);
        if(result){
            request.getSession().setAttribute("loginDto",loginDto.getId());
        }
        //로그인 성공시
        /*
        세션 저장소 : 톰켓서버에 브라우저 마다 메모리 할당
        세션 객체 타입 : Object ( 여러가지 타입들을 저장하려고)
        1. 요청 객체 호출 : HttpServletRequest
        2. 세션 객체 호출 : .getSession()
        3. 세션 데이터 저장 : .setAttribute("세션명" , 데이터)     --  자동형 변환(자->부)
        -. 세션 데이터 호출 : .getAttribute("세션명")             -- 강제형 변환 (부->자) /캐스팅
        -http 세션 초기화 : invalidate()
         */
        return result;
    }

    //=======로그인 여부 확인 요청
    // 2-2 ============= 로그인 여부 확인 요청 / 세션 호출  ================
    @GetMapping("/member/login/check")
    @ResponseBody
    public String doGetLoginCheck(){
        // * 로그인 여부 확인 = 세션이 있다 없다 확인   // 1-> http 요청 객체 호출 , 2->http세션 객체 호출 -> 3.http 세션 데이터 호출
        // null은 형변환이 불가능하기 때문에 유효성검사.
        String loginDto = null;
        Object sessionObj = request.getSession().getAttribute("loginDto");
        if( sessionObj != null ){     loginDto = (String) sessionObj;    }
        // 만약에 로그인했으면(세션에 데이터가 있으면) 강제형변환을 통해 데이터 호출 아니면 0
        return loginDto;
    } // f end

    //========== 로그아웃
    @GetMapping("member/logout")
    @ResponseBody
    public boolean doGetLogout(){
        // 1. 로그인 관련 세션 호출
            //1. 모든 세션 초기화 (모든 세션의 속성이 초기화 -> 로그인세션 외 다른 세션도 고려
        request.getSession().invalidate(); // 현재 요청 보낸 브라우저의 모든 세션 초기화
            //2. 특정 세션속성 초기화 => 동일한 세션속성명에 null 대입한다
        //request.getSession().setAttribute("loginDto", null);

        return true;
    }
    //3========= 회원정보 요청 ( 로그인된 회원 요청)==========
    @GetMapping("/member/login/info")
    @ResponseBody
    public MemberDto doGetLoginInfo (String id){
        return memberService.doGetLoginInfo(id);
    }

    @GetMapping("/member/find/idcheck")
    @ResponseBody
    public boolean doGetFindIdCheck(@RequestParam String id){
        return memberService.doGetFindIdCheck(id);
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
