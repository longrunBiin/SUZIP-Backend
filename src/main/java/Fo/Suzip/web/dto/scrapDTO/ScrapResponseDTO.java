package Fo.Suzip.web.dto.scrapDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class ScrapResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class scrapContentsResponseDto {  //콘탠츠 저장 응답 DTO
        private Long contentId; // 책 아이디
        private LocalDateTime createdAt;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DeleteContentResponseDto {
        private String message;
    }
}
