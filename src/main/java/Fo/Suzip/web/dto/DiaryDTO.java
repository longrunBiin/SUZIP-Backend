package Fo.Suzip.web.dto;

import io.swagger.v3.oas.annotations.info.Info;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class DiaryDTO {
    private Long id;
    private Long memberId;
    private String title;
    private String content;
    private String image;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


}
