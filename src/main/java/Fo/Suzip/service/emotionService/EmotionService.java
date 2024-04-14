package Fo.Suzip.service.emotionService;

import Fo.Suzip.domain.DiaryEmotion;

public interface EmotionService {
    DiaryEmotion findEmotion(String emotion);

    DiaryEmotion findDiaryEmotion(Long diaryId);
}
