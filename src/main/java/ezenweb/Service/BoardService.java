package ezenweb.Service;

import ezenweb.model.dao.BoardDao;
import ezenweb.model.dto.BoardDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardService {
    @Autowired
    private BoardDao boardDao;
    @Autowired
    private FileService fileService;
    //1. 글쓰기 처리 /board/write.do    POST
    public long doPostBoardWrite(BoardDto boardDto){
        System.out.println("BoardController.doPostBoardWrite");

        //1. 첨부 파일 처리
        if(!boardDto.getUploadfile().isEmpty()){// 첨부파일이 존재하면
            String fileName = fileService.fileUpload(boardDto.getUploadfile());
            if(fileName !=null){ //업로드 성공했으면
                boardDto.setBfile(fileName);
            }else {return -1;}
        }
        //2. DB처리
        return boardDao.doPostBoardWrite(boardDto);
    }

    //2. 전체 글 보기 /board.do

    //3. 개별글 출력 /board/view.do
    public BoardDto doGetBoardView(int bno){
        System.out.println("BoardService :doGetBoardView");
        return boardDao.doGetBoardView(bno);
    }

    //4. 글 수정 처리 /board/update.do

    //5. 글 삭제 처리 /board/delete.do
}
