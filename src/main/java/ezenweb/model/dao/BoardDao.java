package ezenweb.model.dao;

import ezenweb.model.dto.BoardDto;
import org.springframework.stereotype.Component;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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

    public List<BoardDto> doGetBoardViewList( int startRow , int pageBoardSize , int bcno  , String key , String keyword  ){ System.out.println("BoardDao.doGetBoardViewList");

        System.out.println("startRow = " + startRow + ", pageBoardSize = " + pageBoardSize + ", bcno = " + bcno + ", key = " + key + ", keyword = " + keyword);

        BoardDto boardDto = null;   List<BoardDto> list = new ArrayList<>();
        try{  // String sql = "select * from board";
            // SQL 앞부분
            String sql = "select * from board b inner join member m " +
                    " on b.mno = m.no ";

            // --- SQL 가운데부분 [ 조건에 따라 where 절 추가 ]
            // ==================== 1.만약에 카테고리 조건이 있으면 where 추가.
            if( bcno > 0 ){ sql += " where bcno = "+bcno ; }

            // ==================== 2.만약에 검색 있을때.
            if(  !keyword.isEmpty() ){    System.out.println("★검색 키워드가 존재.");
                if( bcno > 0 ){ sql += " and "; }   // 카테고리가 있을때. and로 연결
                else{ sql += " where "; }           // 카테고리 없을때 where 로 연결
                sql += keyword+" like '%"+keyword+"%' ";
            }

            // SQL 뒷부분
            sql += " order by b.bdate desc " +
                    " limit ? , ?";

            ps =conn.prepareStatement(sql);
            ps.setInt( 1 , startRow );
            ps.setInt( 2 , pageBoardSize );

            rs = ps.executeQuery();
            while ( rs.next() ){
                boardDto = new BoardDto( rs.getLong( "bno" ) , rs.getString( "btitle" ) ,
                        rs.getString( "bcontent" ) , rs.getString( "bfile" ) ,
                        rs.getLong("bview") , rs.getString("bdate") ,
                        rs.getLong("mno") , rs.getLong("bcno") , null ,
                        rs.getString("id") , rs.getString("img") );
                list.add( boardDto );
            }
        }catch (Exception e ){ System.out.println("e = " + e);  }
        return list;
    }
    //2-2 전체 게시글 수 호출
    public int getBoardSize(int bcno, String key, String keyword){
        System.out.println("bcno = " + bcno + ", key = " + key + ", keyword = " + keyword);
        try {
            String sql="select count(*) from board b inner join member m on b.mno=m.no ";
            //============= 만약에 카테고리 조건이 있으면 where 추가
            if(bcno >0){
                sql+=" where b.bcno="+bcno;
            }
            //========= 만약 검색 있을 때
            if(! keyword.isEmpty()){
                System.out.println("검색 키워드가 존재");
                if(bcno>0){sql+=" and "; //카테고리가 있을 때 and 연결
                }else {sql+=" where ";} // 카테고리가 없을 때 where 연결
                sql+=key+" like '%"+keyword+"%' ";
            }
            ps= conn.prepareStatement(sql);
            rs=ps.executeQuery();
            if(rs.next()){
                return rs.getInt(1);
            }
        }catch (Exception e){
            System.out.println("e = " + e);
        }
        return 0;
    }

    // 3. 개별 글 출력 호출
    public BoardDto doGetBoardView(int bno ) { System.out.println("BoardDao.doGetBoardView");
        BoardDto boardDto = null;
        try{
            String sql="update board set bview=bview+1 where bno=?";
            ps= conn.prepareStatement(sql);
            ps.setInt(1, bno);
            ps.executeUpdate();

            sql ="select * from board b inner join member m on b.mno = m.no where b.bno = ? ";
            ps =conn.prepareStatement(sql);
            ps.setLong( 1 , bno );       rs = ps.executeQuery();
            if( rs.next() ){
                boardDto = new BoardDto( rs.getLong( "bno" ) , rs.getString( "btitle" ) ,
                        rs.getString( "bcontent" ) , rs.getString( "bfile" ) ,
                        rs.getLong("bview") , rs.getString("bdate") ,
                        rs.getLong("mno") , rs.getLong("bcno") , null ,
                        rs.getString("id") , rs.getString("img") );
            }
        }catch (Exception e ){  System.out.println("e = " + e);   }
        return boardDto;
    }

    //4. 글 수정 처리 /board/update.do
    public boolean doUpdateBoard( BoardDto boardDto){
        System.out.println("BoardDao.doUpdateBoard");
        try {
            String sql="update board set btitle=? , bcontent=?, bcno=? where bno=?";
            ps=conn.prepareStatement(sql);
            ps.setString(1,boardDto.getBtitle());
            ps.setString(2,boardDto.getBcontent());
            ps.setLong(3,boardDto.getBcno());
            ps.setLong(4,boardDto.getBno());
            int count=ps.executeUpdate();
            if(count==1){
                return true;
            }
        }catch (Exception e){
            System.out.println("e = " + e);
        }
        return false;
    }
    //5. 글 삭제 처리 /board/delete.do
    public boolean doDeleteBoard( int bno){
        try {
            String sql="delete from board where bno="+bno;
            ps= conn.prepareStatement(sql);
            int count=ps.executeUpdate();
            if(count==1){
                return true;
            }
        }catch (Exception e){
            System.out.println("e = " + e);
        }
        return false;
    }

}
