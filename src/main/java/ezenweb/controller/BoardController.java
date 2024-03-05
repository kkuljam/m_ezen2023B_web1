package ezenweb.controller;

import ezenweb.Service.BoardService;
import ezenweb.Service.MemberService;
import ezenweb.model.dto.BoardDto;
import ezenweb.model.dto.BoardPageDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/board")
public class BoardController {
    @Autowired
    private BoardService boardService;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private MemberService memberService;
    //1. 글쓰기 처리 /board/write.do    POST
    @PostMapping("/write.do")
    @ResponseBody
    public long doPostBoardWrite(BoardDto boardDto){
        System.out.println("BoardController.doPostBoardWrite");
        //1. 현재 로그인된 세션(톰켓 서버 메모리(JVM) 저장소) 호출
        Object object=request.getSession().getAttribute("loginDto");
        if(object==null){return -2;} // 세션이 없다

        //2. 형변환
        String mid=(String) object;

        //3. mid를 mno찾아오기
        long mno=memberService.doGetLoginInfo(mid).getNo();

        //4. 작성자번호 대입
        boardDto.setMno(mno);

        return boardService.doPostBoardWrite(boardDto);
    }

    //2. 전체 글 보기 /board.do
    @GetMapping("/do")
    @ResponseBody
    public BoardPageDto doGetBoardViewList(int page){
        System.out.println("BoardController.doGetBoardView");
        return boardService.doGetBoardViewList(page);
    }

    // 3. 개별 글 출력 호출               /board/view.do         get           게시물번호      dto
    @GetMapping("/view.do")
    @ResponseBody
    public BoardDto doGetBoardView(@RequestParam int bno ) {
        System.out.println("BoardController.doGetBoardView");
        return boardService.doGetBoardView( bno );
    }

    //4. 글 수정 처리 /board/update.do

    //5. 글 삭제 처리 /board/delete.do
    //==================

    //1. 글 쓰기 페이지 이동 /board/write
    @GetMapping("/write")
    public String getBoardWrite(){
        return "ezenweb/board/write";
    }
    //2. 게시판 페이지 이동 /board.do
    @GetMapping("")
    public String getBoard(){
        return "ezenweb/board/board";
    }
    //3. 게시판 상세 페에지 이동 /board/view
    @GetMapping("/view")
    public String getBoardView(int bno){
        System.out.println(" controller : getBoardView ");
        return "ezenweb/board/view";
    }
    //4. 글수정 페이지 이동  /board/update
}
