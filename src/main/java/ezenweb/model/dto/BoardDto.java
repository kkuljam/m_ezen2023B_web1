package ezenweb.model.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Getter@Setter@ToString
public class BoardDto {
    long bno ;          //번호
    String btitle;      //제목
    String bcontent;    //내용
    String bfile;       //첨부파일
    long bview;         //조회수
    String bdate;       //작성일
    long mno;           //작성자 번호
    long bcno;          //카테고리

    MultipartFile uploadfile; //실제 첨부파일 [DB처리x, 서버에 저장]

}
