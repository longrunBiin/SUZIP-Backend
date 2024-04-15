package Fo.Suzip.converter;

import Fo.Suzip.domain.DiaryEmotion;
import Fo.Suzip.domain.Emotions;
import Fo.Suzip.web.dto.emotionDTO.EmotionResponseDto;
import org.springframework.stereotype.Component;

@Component
public class EmotionConverter {

    public static Emotions toEmotions(String emotion) {
        return switch (emotion) {
            case "HAPPY" -> Emotions.HAPPY;
            case "ANGER" -> Emotions.ANGER;
            case "SADNESS" -> Emotions.SADNESS;
            case "CONFUSION" -> Emotions.CONFUSION;
            case "HURT" -> Emotions.HURT;
            case "ANXIETY" -> Emotions.ANXIETY;
            default -> null;
        };
    }

    public static EmotionResponseDto.findEmotionResponseDto toFindEmotionResponseDto(DiaryEmotion findEmotion) {

        return EmotionResponseDto.findEmotionResponseDto.builder()
                .emotion(findEmotion.toString())
                .color(findEmotion.getColor())
                .content(findEmotion.getContent())
                .build();
    }
}
