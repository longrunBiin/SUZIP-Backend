package Fo.Suzip.web.dto.authDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GeneratedToken {
    private String accessToken;
    private String refreshToken;
    private String userName;
}