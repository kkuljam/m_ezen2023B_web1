package ezenweb.model.dao;

import ezenweb.model.dto.BoardDto;
import org.springframework.stereotype.Component;

import java.sql.Statement;

@Component
public class BoardDao extends Dao{
    //1. 글쓰기 처리 /board/write.do    POST [글쓰기 성공했을 때 자동 생성된 글번호 반환, 실패시 0]
    public long doPostBoardWrite(BoardDto boardDto){
        System.out.println("BoardController.doPostBoardWrite");
        System.out.println("boardDto = " + boardDto);
        try {
            String sql="insert into board(btitle,bcontent,bfile,mno,bcno)values(?,?,?,?,?)";
            ps= conn.prepareStatement(sql , Statement.RETURN_GENERATED_KEYS);
            ps.setString(1,boardDto.getBtitle());
            ps.setString(2,boardDto.getBcontent());
            ps.setString(3,boardDto.getBfile());
            ps.setLong(4,boardDto.getMno());
            ps.setLong(5,boardDto.getBcno());

            int count =ps.executeUpdate();
            if(count==1){
                rs=ps.getGeneratedKeys();
                if(rs.next()){
                    return rs.getLong(1);
                }
            }
        }catch (Exception e){
            System.out.println("e = " + e);
        }
        return 0;
    }

    //2. 전체 글 보기 /board.do

    //3. 개별글 출력 /board/view.do
    public BoardDto doGetBoardView(int bno){
        System.out.println("BoardService.doGetBoardView");
        BoardDto boardDto=null;
        try {
            String sql="select*from board where bno=? ";
            ps=conn.prepareStatement(sql);
            ps.setLong(1,bno);

            rs= ps.executeQuery();
            if(rs.next()){
                System.out.println("dao 찾아짐");
                return new BoardDto(
                        rs.getLong(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getLong(5),
                        rs.getString(6),
                        rs.getLong(7),
                        rs.getLong(8),
                        null
                );
            }
        }catch (Exception e){
            System.out.println("e = " + e);
        }
        return null;
    }

    //4. 글 수정 처리 /board/update.do

    //5. 글 삭제 처리 /board/delete.do


}
