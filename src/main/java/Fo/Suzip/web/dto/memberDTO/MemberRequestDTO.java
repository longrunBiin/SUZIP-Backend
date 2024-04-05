package Fo.Suzip.web.dto.memberDTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

public class MemberRequestDTO {

    @Getter
    @Valid
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class JoinDto{
        @NotBlank
        String email;

        @NotBlank
        String provider;

        @NotBlank
        String role;
    }


    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class updateMemberInfoDto{
        @NotBlank
        String name;

        String profileImage;
    }
}
