package Fo.Suzip.service.emotionService;

import Fo.Suzip.domain.Diary;
import Fo.Suzip.domain.DiaryEmotion;
import Fo.Suzip.web.dto.emotionDTO.EmotionResponseDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface EmotionQueryService {
    DiaryEmotion findEmotion(String emotion);

    DiaryEmotion findDiaryEmotion(Long diaryId);

    Page<Diary> getHappyDiary(String userName, Integer page);

    List<EmotionResponseDto.findMonthEmotionResponseDto> findMonthEmotion(String userName, Integer year, Integer month);
}
