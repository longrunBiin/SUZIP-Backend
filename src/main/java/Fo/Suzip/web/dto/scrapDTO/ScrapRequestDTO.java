package Fo.Suzip.web.dto.scrapDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class ScrapRequestDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class scrapContentsRequestDto {  //스크랩 요청 dto
        private Long contentId; // 콘텐츠 아이디
        private LocalDateTime createdAt;
    }
}
