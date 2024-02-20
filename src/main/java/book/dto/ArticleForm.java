package book.dto;

import lombok.*;

//입력폼 전용 DTO
    //관례적으로 모든 객체 필드는 직접 접근 권장하지 않는다. private, 안정성보장, 캡슐화 특징, setter,getter, 생성자
    //필드는 private , 생성자 : 빈, 풀 , 메소드 : toString(), setter,getter
@AllArgsConstructor // 컴파일시 모든 필드 생성자를 자동으로 만들어준다. [룸북]
@NoArgsConstructor// 컴파일시 기본 생성자를 자동으로 만들어준다.
@ToString // 컴파일시 toString() 자동으로 만들어준다.
@Getter@Setter // 컴파일시 setter/getter 메소드를 자동으로 만들어준다.

public class ArticleForm {
    //1. 필드
    private Long id;
    private String title; // 입력받은 제목 필드
    private String content; // 입력받은 내용 필드

}
