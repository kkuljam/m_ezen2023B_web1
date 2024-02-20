package book.dao;

import book.dto.ArticleForm;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component // 스프링 컨테이너에 해당 클래스를 빈(객체) 등록
public class ArticleDao {
    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;

    public ArticleDao() { // 생성자 : 객체 생성시 초기화 담당
        // - 객체 생성시 db연동
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
    public ArticleForm createArticle(ArticleForm form){
        System.out.println("ArticleDao.createArticle");
        System.out.println("form = " + form);
        //성공시 반환할 Dto
        ArticleForm saved=new ArticleForm();
        try {
            String sql="insert into article(title,content) values(?,?)";
            //ps=conn.prepareStatement(sql);
            //1. SQL 기재 할때 자동으로 생성된 키 호출 선언
            //2.
            ps=conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, form.getTitle());
            ps.setString(2, form.getContent());

            int count=ps.executeUpdate();
            rs=ps.getGeneratedKeys();

            if(rs.next()){
                System.out.println(" 방금 자동으로 생성된 pk(id)= " +rs.getLong(1));
                Long id= rs.getLong(1);
                saved.setId(id);
                saved.setTitle(form.getTitle());
                saved.setContent(form.getContent());
                return saved;
            }
        }catch (Exception e){
            System.out.println(e);
        }
       return saved;
    };
    //-----------------------------------
    //2. 개별 글 조회 : 매개변수 : 조회할 게시글 번호(id) 반환: 조회한 게시글 정보 1개
    public ArticleForm show(Long id){
        try {
            String sql="select * from article where id=?";
            ps=conn.prepareStatement(sql);
            ps.setLong(1,id);

            rs=ps.executeQuery();
            if(rs.next()){
                ArticleForm form=new ArticleForm(
                        rs.getLong(1),
                        rs.getString(2),
                        rs.getString(3)
                );
                return form;
            }
        }catch (Exception e){
            System.out.println("e = " + e);
        }
        return null;
    }
    //3. 전체글 조회 : 매개변수 : X , 리턴 : ArrayList
    public List<ArticleForm>index(){
        List<ArticleForm>list=new ArrayList<>();
        try {
            String sql="select * from article";
            ps= conn.prepareStatement(sql);
            rs= ps.executeQuery();
            while (rs.next()){
                ArticleForm form=new ArticleForm(
                        rs.getLong(1),
                        rs.getString(2),
                        rs.getString(3)
                );
                list.add(form);
            }
        }catch (Exception e){

        }
        return list;
    }

    public ArticleForm findById(Long id){
        try {
            String sql="select*from article where id=?";
            ps= conn.prepareCall(sql);
            ps.setLong(1,id);
            rs= ps.executeQuery();
            if(rs.next()){
                return new ArticleForm(
                    rs.getLong(1),
                    rs.getString(2),
                    rs.getString(3)
                );
            }
        }catch (Exception e){
            System.out.println(e);
        }
        return null;
    }

    //5. 수정처리, 매개변수 : 수정할 pk, 수정할 값들 , 리턴 : dto
    public ArticleForm update (ArticleForm form){
        try {
            String sql="update article set title = ? , content = ? where id = ?";
            ps= conn.prepareCall(sql);
            ps.setString(1, form.getTitle());
            ps.setString(2, form.getContent());
            ps.setLong(3,form.getId());

            int count=ps.executeUpdate();
            if(count==1){
                return form;
            }
        }catch (Exception e){
            System.out.println("e = " + e);
        }
        return null;
    }

    //6. 삭제 처리, 매개변수: 삭제할 id, 리턴: F/T
    public boolean delete(Long id){
        try {
            String sql="delete from article where id=?";
            ps=conn.prepareStatement(sql);
            ps.setLong(1,id);
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
