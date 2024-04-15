package Fo.Suzip.service.emotionService;

import Fo.Suzip.domain.Diary;
import Fo.Suzip.domain.DiaryEmotion;
import org.springframework.data.domain.Page;

public interface EmotionQueryService {
    DiaryEmotion findEmotion(String emotion);

    DiaryEmotion findDiaryEmotion(Long diaryId);

    Page<Diary> getHappyDiary(String userName, Integer page);
}
