package Fo.Suzip.web.dto.authDTO;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SecurityUserDto {
    private String id;
    private String role;
    private String name;
    private String userName;
    private String email;
}
