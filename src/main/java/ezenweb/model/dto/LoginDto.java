package ezenweb.model.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter@Setter@ToString
public class LoginDto {
    private int no;
    private String id;
    private String pw;

}
