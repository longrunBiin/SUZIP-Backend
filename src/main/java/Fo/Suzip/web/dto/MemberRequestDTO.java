package Fo.Suzip.web.dto;

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


}
