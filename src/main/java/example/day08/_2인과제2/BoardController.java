package example.day08._2인과제2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
@Controller
public class BoardController {
    // ============================ 데이터 Rest =============================== //
    @Autowired
    BoardDao boardDao;
    // 1. 저장
    @PostMapping("/board/create")
    @ResponseBody
    public boolean create(BoardDto boardDto){               System.out.println("BoardController.create");System.out.println("boardDto = " + boardDto);
        boolean result =  boardDao.create( boardDto );      System.out.println("result = " + result);
        return result;
    }
    // 2. 전체 호출
    @GetMapping("/board/read")
    @ResponseBody
    public List<BoardDto> read( ){                          System.out.println("BoardController.read");
        List<BoardDto> result = boardDao.read();            System.out.println("result = " + result);
        return result;
    }
    // 3. 수정
    @PostMapping("/board/update")
    @ResponseBody
    public boolean update(BoardDto boardDto) {              System.out.println("BoardController.update");System.out.println("boardDto = " + boardDto);

        //1. 패스워드 검증 요청
        boolean result=boardDao.confirmPw(boardDto.getBno(),boardDto.getBpw());
        if(result) {
            //2. 수정요청
            result = boardDao.update(boardDto);
            System.out.println("result = " + result);
        }
        return result;
    }
    // 4. 삭제
    @GetMapping("/board/delete/{bno}/{bpw}")
    @ResponseBody
    public boolean delete( @PathVariable int bno ,@PathVariable String bpw) {                      System.out.println("BoardController.delete");System.out.println("bno = " + bno);

        //1. 패스워드 검증 요청
        boolean result=boardDao.confirmPw(bno,bpw);
        if(result) {
            //2. 수정요청
            result = boardDao.delete(bno);
            System.out.println("result = " + result);
        }
        //2. 삭제요청
        return result;
    }

    // ============================ view Rest =============================== //
    // 1. HTML ( STATIC ) : http://localhost/day08Board.html / REACT
    // 2. 머스테치( templates ) 컨트롤의 반환 ( model )

}
