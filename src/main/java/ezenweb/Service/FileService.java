package ezenweb.Service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;
@Service // 해당 클래스를 스프링 컨테이너(저장소)에 빈(객체) 등록
public class FileService {
    //controller : 중계자 역할(HTTP 매핑, 데이터 유효성 검사 ) 등등
    //Service : controller  <-- Service (비지니로직)
    // 어디에(PATH) 누구를 (파일객체)
    String uploadPath="C:\\Users\\504\\Desktop\\m_ezen2023B_web1\\build\\resources\\main\\static\\img\\";

    // 1. 업로드 메소드
    public String fileUpload(MultipartFile multipartFile){
        //* 업로드할 경로 설정
        String uploadPath="C:\\Users\\504\\Desktop\\m_ezen2023B_web1\\build\\resources\\main\\static\\img\\";

        String uuid= UUID.randomUUID().toString();
        String filename = uuid+"_"+multipartFile.getOriginalFilename().replace("_","-");
        File file= new File(uploadPath+filename);
        System.out.println("file = " + file);
        System.out.println("file.exists() = " + file.exists());
        //2.
        try {
            multipartFile.transferTo(file);
        }catch (Exception e){
            System.out.println("e = " + e);
            return null;
        }
        return filename;
    }
    // 2. 다운로드 메소드
}
/*
   MultipartFile 첨부파일= memberDto.getImg();
        System.out.println(첨부파일); // 첨부파일이 들어있는 객체 주소
        System.out.println(첨부파일.getSize()); // 첨부파일 용량
        System.out.println(첨부파일.getContentType());  // img/png : 파일 확장자
        System.out.println(첨부파일.getOriginalFilename()); //logo.png 첨부파일 이름
        System.out.println(첨부파일.getName()); //img : form input name

        // 서버에 업로드 했을 때 설계
        //1. 여러 클라이언트[다수]가 동일한 파일명으로 서버[1]명에게 업로드 했을 때[식별 깨짐]
        // 식별 이름: 1.(아이디어) 날짜 조합+ 데이터 2.uuid
        //2. 클라이언트 화면 표시
        // 업로드 경로 아파치 톰켓(static)

        //* 업로드할 경로 설정
        String uploadPath="C:\\Users\\504\\Desktop\\m_ezen2023B_web1\\build\\resources\\main\\static\\img\\";

        // * 파일이름 조합하기 : 새로운 식별이름과 실제 파일 이름
        // 식별키와 실제 이름 구분: 왜? 나중에 쪼개서 구분하려고
        // 혹시나 파일 이름이 _가 있을 경우 기준이 깨짐
        String uuid= UUID.randomUUID().toString();
        System.out.println("uuid = " + uuid);
        String filename = uuid+"_"+memberDto.getImg().getOriginalFilename().replace("_","-");
        memberDto.setUuidFile(filename);
        //1. 첨부파일 업로드 하기 [업로드란 : 클라이언트의 바이트 (대용량/파일)을 서버로 복사
        //1. 첨부파일을 저장할 경로
        //File 클래스 : 파일 관련된 메소드 제공
        //new File(파일경로);
        File file= new File(uploadPath+filename);
        System.out.println("file = " + file);
        System.out.println("file.exists() = " + file.exists());
        //2.
        try {
            memberDto.getImg().transferTo(file);
        }catch (Exception e){
            System.out.println("e = " + e);
        }
 */