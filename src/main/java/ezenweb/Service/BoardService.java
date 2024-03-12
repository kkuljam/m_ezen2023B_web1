package ezenweb.Service;

import ezenweb.model.dao.BoardDao;
import ezenweb.model.dto.BoardDto;
import ezenweb.model.dto.BoardPageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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

    // 2. 전체 글 출력 호출
    public BoardPageDto doGetBoardViewList( int page, int pageBoardSize,int bcno, String key, String keyword ){   System.out.println("BoardService.doGetBoardViewList");

        // 페이지처리시 사용할 SQL 구문 : limit 시작레코드번호(0부터) , 출력개수

        // 1. 페이지당 게시물을 출력할 개수          [ 출력개수 ]
        //int pageBoardSize = 5;

        // 2. 페이지당 게시물을 출력할 시작 레코드번호. [ 시작레코드번호(0부터) ]
        int startRow = ( page-1 ) * pageBoardSize;
        //3. 총 페이지수
            //1. 전체 게시글 수
        int totalBoardSize=boardDao.getBoardSize(bcno,key,keyword);
            //2. 총 페이지 수 계산
        int totalPage=totalBoardSize % pageBoardSize == 0 ?
                        totalBoardSize / pageBoardSize :
                        totalBoardSize / pageBoardSize +1 ;
        //4. 게시글 정보 요청
        List<BoardDto>list=boardDao.doGetBoardViewList( startRow , pageBoardSize,bcno,key,keyword);

        //5. 페이징 버튼 개수
            // 1. 페이지 버튼 최대 개수
        int btnSize = 5;
            //2. 페이지 버튼 시작번호
        //((페이지 그룹 - 1) * 한 화면에 보여질 페이지 개수) + 1
        int startBtn = (page-1)/btnSize*btnSize+1;
        //3. 페이지 끝번호
        int endBtn = startBtn+btnSize-1;
            //
        if(endBtn>=totalPage)endBtn=totalPage;
        //pageDto 구성
      BoardPageDto boardPageDto = BoardPageDto.builder()
                .page(page)
                .totalBoardSize(totalBoardSize)
                .totalPage(totalPage)
                .list(list)
                .startBtn(startBtn)
                .endBtn(endBtn)
                .build();
        return boardPageDto;
    }
    //3. 개별글 출력 /board/view.do
    public BoardDto doGetBoardView(int bno){
        System.out.println("BoardService :doGetBoardView");
        return boardDao.doGetBoardView(bno);
    }

    //4. 글 수정 처리 /board/update.do
    public boolean doUpdateBoard(BoardDto boardDto){
        System.out.println("BoardService.doUpdateBoard");
        //1. 기존 첨부파일명 구하고
        String bfile = boardDao.doGetBoardView((int)boardDto.getBno()).getBfile();
        // - 새로운 첨부파일이 있다. 없다.
        if(!boardDto.getUploadfile().isEmpty()){// 수정시 새로운 첨부파일이 있으면
            //새로운  첨부파일을 업로드하고 기존 첨부파일 삭제
            String fileName =fileService.fileUpload(boardDto.getUploadfile());
            if(fileName !=null){
                boardDto.setBfile(fileName); // 새로운첨부파일의 이름 Dto 대입
                // 기존 첨부파일 삭제
                    //2. 기존 첨부파일 삭제
                fileService.fileDelete(bfile);
            }else {
                return false; // 업로드 실패
            }
        }else {
            boardDto.setBfile(bfile); // 새로운 첨부파일이 없으면 기존 첨부파일명 그대로 대입
        }
        return boardDao.doUpdateBoard(boardDto);
    }

    //5. 글 삭제 처리 /board/delete.do
    public boolean doDeleteBoard( int bno){
        System.out.println("BoardService.doDeleteBoard");

        // -레코드 삭제 하기전에 삭제할 첨부파일명을 임시로 꺼내둔다.
        String bfile= boardDao.doGetBoardView(bno).getBfile();

        //1. DAO 처리
        boolean result=boardDao.doDeleteBoard(bno);

        //2. Dao 처리 성공시 첨부파일도 삭제
         if(result){
             // 기존에 첨부파일이 있었으면
             System.out.println("bfile = " + bfile);
             fileService.fileDelete(bfile);

         }
        return result;
    }
    //6 게시물 작성자 인증
    public boolean boardWriterAuth(long bno, String mid){
        return boardDao.boardWriterAuth(bno,mid);
    }

    // 7. 댓글 등록
    public boolean postReplyWrite( Map< String , String > map ){    System.out.println("BoardController.postReplyWrite");
        return boardDao.postReplyWrite(map);
    }
    // 8. 댓글 출력
    public List< Map< String , Object > > getReplyDo( int bno ){    System.out.println("BoardController.getReplyDo");
        return boardDao.getReplyDo( bno );
    }


}
