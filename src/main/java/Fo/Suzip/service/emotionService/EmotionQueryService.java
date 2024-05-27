package Fo.Suzip.service.emotionService;

import Fo.Suzip.domain.Diary;
import Fo.Suzip.domain.DiaryEmotion;
import Fo.Suzip.web.dto.emotionDTO.EmotionResponseDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface EmotionQueryService {
    DiaryEmotion findEmotion(String emotion);

    DiaryEmotion findDiaryEmotion(Long diaryId);

    Diary getHappyDiary(String userName);

    List<EmotionResponseDto.findMonthEmotionResponseDto> findMonthEmotion(String userName, Integer year, Integer month);
}
