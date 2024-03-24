package Fo.Suzip.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class MemberResponseDTO {

    @Builder
    @Getter
    @AllArgsConstructor
    public static class JoinResultDto{
        Long memberId;
        String grantType;
        String accessToken;
        String refreshToken;
        Long accessTokenExpiresIn;
    }
}
