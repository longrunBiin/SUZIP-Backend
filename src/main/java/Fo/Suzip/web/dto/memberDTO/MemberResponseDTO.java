package Fo.Suzip.web.dto.memberDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
        LocalDateTime createdAt;
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

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class updateMemberResultDto{
        Long memberId;
        String userName;
        LocalDateTime updatedAt;
    }
}
