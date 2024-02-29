package ezenweb.controller;

import ezenweb.Service.EmailService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@RequestMapping("/auth")
public class AuthController {
    // 세션 객체 호출
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private EmailService emailService;

    //1. email 인증 요청
    @GetMapping("/email/req")
    public boolean getEmailReq(String email){
        System.out.println("AuthController.getEmailReq");
        //1. 난수 이용한 6자리 인증코드 발급
            //1. 난수 객체 생성
        Random random =new Random();
            //2. 6번
        String ecode="";
        for(int i=1;i<=6;i++){
            //3. 난수 생성해서 변수에 누적 문자로 연결하기
            ecode+=random.nextInt(10); //0~10
        }
        System.out.println(ecode);
        //2. HTTP 세션에 특정시간만큼 발급된 인증 코드 보관
            //1. 세션에 속성 추가 해서 발급된 인증코드 대입하기
        request.getSession().setAttribute("ecode",ecode);
            //2. 세션에 생명주기
        request.getSession().setMaxInactiveInterval(10);
        //3. 발급된 인증코드를 인증할 이메일로 전송
        emailService.send(email,"EZEN WEB 인증코드","인증코드:"+ecode);
        return true;
    }

    //2. email 인증 확인
    @GetMapping("/email/check")
    public boolean getEmailCheck(@RequestParam String ecodeinput){
        System.out.println("AuthController.getEmailCheck");

        //1. HTTP 세션에 보관했던 인증코드를 꺼내서
            //1. 세션 속성 호출
        Object object=request.getSession().getAttribute("ecode");
        if(object !=null){
            String ecode=(String) object;
            if(ecode.equals(ecodeinput)){

            }
        }
        String ecode=(String) request.getSession().getAttribute("ecode");

        //2. 입력받은 인증코드와 생성된 인증코드 일치여부
        return true;
    }
}
