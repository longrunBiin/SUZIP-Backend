package Fo.Suzip.web.dto.user;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDTO {

    private Long id;
    private String role;
    private String name;
    private String username;
    private String email;
    private String subject;
}