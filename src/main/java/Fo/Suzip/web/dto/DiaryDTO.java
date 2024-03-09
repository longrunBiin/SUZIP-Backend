package Fo.Suzip.web.dto;

import lombok.Getter;

import java.time.LocalDateTime;

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
