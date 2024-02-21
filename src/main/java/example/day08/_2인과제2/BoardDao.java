package example.day08._2인과제2;

import example.day08._2인과제2.BoardDto;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Component
public class BoardDao {

    private Connection conn;          private PreparedStatement ps;          private ResultSet rs;

    public  BoardDao(){
        try {
            // 1. MYSQL 회사의 JDBC 관련된 (Driver)객체를 JVM 에 로딩한다/불러오기
            Class.forName("com.mysql.cj.jdbc.Driver");
            // 2. 연동된 결과의 객체를 Connection 인터페이스에 대입한다
            conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/springweb",
                    "root",
                    "1234"
            );
            System.out.println("DB 연동 성공");
        } catch (Exception e) {
            System.out.println("DB 연동 실패" + e);
        }
    }

    // 1. 저장
    public boolean create(BoardDto boardDto){  System.out.println("BoardDao.create");       System.out.println("boardDto = " + boardDto);
        try{
            String sql = "insert into board( bcontent , bwriter , bpw )values(?,?,?)";
            ps = conn.prepareStatement(sql);
            ps.setString( 1 , boardDto.getBcontent() );
            ps.setString( 2 , boardDto.getBwriter() );
            ps.setString( 3 , boardDto.getBpw() );
            ps.executeUpdate();   return true;
        }catch (Exception e ){  System.out.println("e = " + e);  }  return false;
    }
    // 2. 전체 호출
    public List<BoardDto> read( ){ System.out.println("BoardDao.read");
        List<BoardDto> list = new ArrayList<>();
        try{
            String sql = "select * from board";
            ps = conn.prepareStatement(sql);         rs = ps.executeQuery();
            while ( rs.next() ){
                list.add( new BoardDto( rs.getInt( 1 ) , rs.getString(2) ,
                        rs.getString(3) , rs.getString(4) ));
            }
        }catch (Exception e ){  System.out.println("e = " + e);  }  return list;
    }
    // 3. 수정
    public boolean update(BoardDto boardDto) {  System.out.println("BoardDao.update");System.out.println("boardDto = " + boardDto);
        try{
            String sql = "update board set bcontent = ? where bno = ? ";
            ps = conn.prepareStatement(sql);
            ps.setString( 1 , boardDto.getBcontent() );
            ps.setInt( 2 , boardDto.getBno()  );
            ps.executeUpdate();    return true;
        }catch (Exception e ){  System.out.println("e = " + e);  }  return false;
    }
    // 4. 삭제
    public boolean delete( int bno ) {   System.out.println("BoardDao.delete");  System.out.println("bno = " + bno);
        try{
            String sql = "delete from board where bno = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt( 1 , bno );
            ps.executeUpdate();    return true;
        }catch (Exception e ){  System.out.println("e = " + e);  }return false;
    }

    //5. 게시물 번호에 따른 패스워드 검증
    public boolean confirmPw(int bno, String pw){
        try{
            String sql = "select * from board where bno = ? and bpw=?";
            ps = conn.prepareStatement(sql);
            ps.setInt( 1 , bno );
            ps.setString( 2 , pw );
            rs=ps.executeQuery();
            if(rs.next()){
                return true;
            }
        }catch (Exception e ){  System.out.println("e = " + e);  }
        return false;
    }
}
