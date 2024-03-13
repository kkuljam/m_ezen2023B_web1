package ezenweb.controller;

import ezenweb.Service.MemberService;
import ezenweb.Service.ProductService;
import ezenweb.model.dto.ProductDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private MemberService memberService;

    //1. 등록 서비스
    @PostMapping("/register.do")
    @ResponseBody
    public boolean postProductRegister(ProductDto productDto){
        System.out.println("ProductController.postProductRegister");

        //1. 등록자 세션 처리
        Object object = request.getSession().getAttribute("loginDto");
        if( object == null )return false;
        productDto.setMno( memberService.doGetLoginInfo( (String)object ).getNo() );
        // productDto.setMno( 1 ); // 1번(없으면 본인pc기준으로 아무거나) 회원 으로 테스트;
        return productService.postProductRegister( productDto );
    }

    //2. 제품 출력 (지도에 출력할) 요청
    @GetMapping("/list.do")
    @ResponseBody
    public List<ProductDto> getProductList(){
        return productService.getProductList();
    }


    //3. 해당 제품의 찜하기 등록 // 언제 실행: 로그인 했고 찜하기 버튼 클릭 , 매개변수: pno, 리턴: boolean
    @PostMapping("/plike.do")
    @ResponseBody
    public boolean getPlikeWrite(int pno){
        System.out.println("pno = " + pno);
        Object object=request.getSession().getAttribute("loginDto");
        if(object==null)return false;
        int mno=memberService.doGetLoginInfo((String) object).getNo();
        System.out.println("mno = " + mno);
        return productService.getPlikeWrite(pno,mno);
    }
    //4. 해당 제품의 찜하기 상태 출력 // 언제 실행:로그인 했고 찜하기 버튼 클릭 출력, 매개변수: pno, 리턴: boolean(등록 있다/없다)
    @GetMapping("/plike.do")
    @ResponseBody
    public boolean getPlikeView(int pno){
        int mno=1;
        return productService.getPlikeView(pno,mno);
    }
    //5. 해당 제품의 찜하기 취소/삭제 // 언제 실행: 로그인 했고 찜하기 버튼 클릭,매개변수: pno, 리턴: boolean
    @DeleteMapping("/plike.do")
    @ResponseBody
    public boolean getPlikeDelete(int pno){
        int mno=1;
        return productService.getPlikeDelete(pno,mno);
    }
    //=============================
    //1. 등록페이지/화면/뷰 요청
    @GetMapping("/register")
    public String productRegister(){
        return "ezenweb/product/register";
    }

    //1. 등록페이지/화면/뷰 요청
    @GetMapping("/list")
    public String productList(){
        return "ezenweb/product/list";
    }

}
