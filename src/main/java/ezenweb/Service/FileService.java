package ezenweb.Service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URLEncoder;
import java.util.UUID;
@Service // 해당 클래스를 스프링 컨테이너(저장소)에 빈(객체) 등록
public class FileService {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;
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
    public void fileDownload(String bfile){
        System.out.println("bfile = " + bfile);

        //1. 다운로드 할 파일의 경로와 파일명 연결해서 해당 파일 찾기
        String downloadPath=uploadPath+bfile;
        System.out.println("downloadPath = " + downloadPath);

        //2. 해당 파일을 객체(기능을 쓰려고)로 가져오기 [File 클래스]
        File file = new File(downloadPath);
        System.out.println("file = " + file);

        //3. exists() : 해당 경로에 파일이 있다/ 없다
        if(file.exists()){
            System.out.println("첨부파일 있다");
            //HTTP가 파일 전송하는 방법 : 파일을 바이트 전송
            try {

                //HTTP로 응답시 응답방법(다운로드 모양)에 대한 정보를 추가
                    //기본값은 url은 한글지원 안한다
                    //url에 한글 지원하기 위해서는 URLEncoder.encode(url정보,"utf-8")
                    // 첨부파일 다운로드 형식 : 브라우저마다 형식이 다르다 (커스텀 불가능)
                response.setHeader("Content-Disposition","attachment;filename="
                        + URLEncoder.encode(bfile.split("_")[1],"utf-8"));

                //1. 해당파일을 바이트로 불러온다
                    //1-1. 파일 입력스트림(바이트가 다니는 통로) 객체 생성
                BufferedInputStream fin = new BufferedInputStream(new FileInputStream(file));
                    //1-2 바이트 배열(고정길이) vs 리스트(가변길이)
                        //1. 파일의 사이즈/크기/용량(파일의 크기만큼 바이트 배열 선언하기 위해)
                        long fileSize=file.length();
                        //2. 해당 파일의 사이즈 만큼 바이트 배열 선언
                        byte[]bytes=new byte[(int) fileSize]; // 배열의 길이는 int 형
                    //1-2 입력(불러오기) 메소드
                        // 바이트 하나씩 읽어오면서 바이트배열 저장 => 바이트 배열 필요하다
                    fin.read(bytes); // -입력스트림객체.read(바이트배열) 하나씩 바이트를 읽어와서 해당 바이트 배열에 저장 해주는 함수

                    //1-3 확인용
                    System.out.println("bytes = " + bytes);
                //new FileInputStream(file);

                //2. 불러온 바이트를 HTTP response 이용한 바이트로 응답한다.
                    //2-1 HTTP 응답스트림 객체 생성
                BufferedOutputStream fout = new BufferedOutputStream(response.getOutputStream());
                    //2-2 응답스트림.write(내보내가할 바이트배열) : 내보내기 할 바이트 배열 준비 상태이면 내보내기
                fout.write(bytes);
            }catch (Exception e){
                System.out.println(e);
            }
        }
    }

    //3. 파일 삭제 [게시물 삭제시 만약에 첨부파일 있으면 첨부파일도 같이 삭제, 게시물 수정시 첨부파일 변경하면 기존 첨부파일 삭제]
    public boolean fileDelete(String bfile){

        //1. 경로와 파일을 합쳐서 파일 위치 찾기
        String filePath=uploadPath+bfile;
        //2. File클래스의 메소드 활용
            //.exists() : 해당 파일의 존재 여부
            // .length() : 해당 파일의 크기/용량(바이트 단위)
        File file = new File(filePath);
        if(file.exists()){
           return file.delete();
        }
        return false;
    }
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