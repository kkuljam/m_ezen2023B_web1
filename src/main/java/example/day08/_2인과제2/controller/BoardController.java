package example.day08._2인과제2.controller;

import example.day08._2인과제2.BoardDto;
import example.day08._2인과제2.model.BoardDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.util.List;
@Controller
public class BoardController {
    @Autowired
    BoardDao boardDao;

    //1. 저장
    public boolean create(BoardDto boardDto){
        boolean result=boardDao.create(boardDto);
        return result;
    }

    //2. 전체 호출
    public List<BoardDto> read(){
        List<BoardDto> result=boardDao.read();
        return result;
    }
    //3. 수정
    public boolean update(BoardDto boardDto){
        boolean result=boardDao.update(boardDto);
        return result;
    }
    //4. 삭제
    public boolean delete(int bno){
        boolean result=boardDao.delete(bno);
        return result;
    }

}
