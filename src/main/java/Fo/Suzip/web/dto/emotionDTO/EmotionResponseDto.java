package Fo.Suzip.web.dto.emotionDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

public class EmotionResponseDto {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class findMonthEmotionResponseDto {
        private String emotion;
        private LocalDate date;
        private Long diaryId;
    }


    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class findEmotionResponseDto {
        private String emotion;
        private String color;
        private String content;
    }


}
