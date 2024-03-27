package Fo.Suzip.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

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

    @Builder
    @Getter
    @AllArgsConstructor
    public static class getMemberResultDto{
        Long memberId;
        String name;
        String userName;
        String userRole;
        String email;
        String profileImage;
        String provider;
        LocalDateTime createdAt;
    }
}
