package example.day03.webMvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
//스프링이 해당 클래스가 컨트롤이라는걸 알려야한다
@RestController// 스프링컨테이너[스프링관리하는메모리공간]에 빈(객체) 등록
                // IOC 제어역전 기법: 개발자가 객체 관리X, 스프링이 대신
public class TodoController {
    @Autowired //스트링 컨테이너의 빈 찾아서 주입// 전제조건 : 빈 등록 되었을 때 // 클래스가 @Controller
    private TodoDao todoDao;

    @PostMapping("/todo/post.do")
    public  boolean doPost(TodoDto todoDto){
        return todoDao.doPost(todoDto);
    }
    @GetMapping("/todo/get.do")
    public ArrayList<TodoDto> doGet(){
        return todoDao.doGet();
    }
    //4. 할일 상태 수정
    @PutMapping("/todo/put.do")
    public boolean doPut(TodoDto todoDto){
        return todoDao.doPut(todoDto);
    }
    //5. 할일 삭제
    @DeleteMapping("/todo/delete.do")
    public boolean doDelete(int id){
        return todoDao.doDelete(id);
    }

}
