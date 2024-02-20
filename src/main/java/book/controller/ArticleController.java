package book.controller;

import book.dao.ArticleDao;
import book.dto.ArticleForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@Slf4j // 자바에서 간단한 로그 처리
// 1. 스프링 컨테이너 (메모리 저장소)에 빈(객체/힙) 등록
// 2. 스프링이 해당 클래스를 사용할 수 있다.
// 3. 모든 클라이언트 요청은 컨트롤러로 들어온다.
public class ArticleController {

    @Autowired // 스프링 컨테이너에 등록된 빈 주입한다
    ArticleDao articleDao;

    //1. 글쓰기 페이지 반환
    @GetMapping("/articles/new")
    public String newArticleForm(){
        return "articles/new"; //확장자 빼고 resources/templates부터 경로 작성
    }
    //2. 글쓰기 처리
        //1. <form action="/articles/create" method="post">
        //2. 입력태그 속성의 name과 DTO 필드의 필드명 일치하면 자동 연결/대입된다.
        //3. public 생성자 필요
    @PostMapping("/articles/create")
    public String createArticle(ArticleForm form){
        //soutm: 메소드명 출력
        System.out.println("ArticleController.createArticle");
        //soutp: 메소드 매개변수 출력
        System.out.println("form = " + form);

        //디버그
        log.debug(form.toString());
        //경고
        log.warn(form.toString());
        //에러
        log.error(form.toString());
        //정보
        log.info(form.toString());

        ArticleForm saved=articleDao.createArticle(form);

        return "redirect:/articles/"+saved.getId();
    }
    // 개별조회
    @GetMapping("/articles/{id}")
    public String show(@PathVariable Long id, Model model){
        System.out.println("id = " + id);
        ArticleForm form=articleDao.show(id);
        System.out.println("form = " + form);
        model.addAttribute("article",form);
        return "articles/show";
    }

    //전체조회
    @GetMapping("/articles")
    public String index(Model model){
        //1. dao 에게 요청해서 데이터 가져온다
        List<ArticleForm>result=articleDao.index();
        //2. 뷰템플릿(머스테치)에게 전달할 값model준다.
        System.out.println(result);
        model.addAttribute("articleList",result);
        //3. 175 뷰 페이지 설정
        return "articles/index";
    }

    //수정 1단계 기존 데이터 불러오기
    @GetMapping("/articles/{id}/edit") //GetMapping 이유: <a> 이용해서 호출할 예정
    public String edit(@PathVariable long id, Model model){
        System.out.println("id = " + id);
        //1. 다오한테 요청 응답 받기
        ArticleForm form=articleDao.findById(id);
        //2. 응답 결과 뷰 템플릿에게 보낼 준비 model
        model.addAttribute("article",form);
        //3. 뷰 페이지 설정
        return "articles/edit";
    }

    //수정 2단계: 수정 데이터 받아오기
    @PostMapping("/article/update")
    public String update(ArticleForm form){
        //form 입력데이터를 Dto  매개변수로 받을때
            // 1. form 입력상자의 name과 Dto 의 필드명 동일
            //  2. Dto의 필드 값을 저장할 생성자 필요
        System.out.println("form = " + form);
        ArticleForm updated=articleDao.update(form);
            //3. 수정 터리된 상세페이지로 이동
        return "redirect:/articles/"+updated.getId();
    }

    //삭제 요청
    @GetMapping("/articles/{id}/delete")
    public String delete(@PathVariable long id){
        System.out.println(id);
        //1. 삭제할 대상
        //2. Dao 삭제 요청하고 응답받기
        boolean result=articleDao.delete(id);
        //3. 결과 페이지로 리다이렉트 하기
        return "redirect:/articles";
    }
}
/*

    @ 어노테이션
        1. 표준 어노테이션 : 자바에서 기본적으로 제공
            @Override:  메소드 재정의
            등등
        2. 메타 어노테이션 : p64.
            - 소스코드에 추가해서 사용하는 메타 데이터
            - 메타 데이터 : 구조화된 데이터
            - 컴파일 또는 실행했을 때 코드를 어떻게 처리 해야 할지 알려주는 정보
            @SpringBootApplication
                - 1. 내장 서버(톰캣) 지원
                - 2. 스프링 MVC 패턴 내장
                    view : resources
                    controller : @Controller , @RestController
                        service : @Service
                    model(Dao) : @Repository
                    entity(do table) : @Entity

                - 2. 컴포넌트(module) 스캔: MVC 클래스 스캔
            @Controller
            @GetMapping
 */