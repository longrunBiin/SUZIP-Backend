package Fo.Suzip.service.emotionService;

import Fo.Suzip.apiPayload.code.status.ErrorStatus;
import Fo.Suzip.apiPayload.exception.handler.EmotionHandler;
import Fo.Suzip.converter.EmotionConverter;
import Fo.Suzip.domain.Diary;
import Fo.Suzip.domain.DiaryEmotion;
import Fo.Suzip.domain.Emotions;
import Fo.Suzip.repository.DiaryRepository;
import Fo.Suzip.repository.EmotionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EmotionServiceImpl implements EmotionService{

    private final EmotionRepository emotionRepository;
    private final DiaryRepository diaryRepository;

    @Override
    public DiaryEmotion findEmotion(String emotion) {

        Emotions emotions = EmotionConverter.toEmotions(emotion);

        return emotionRepository.findByEmotion(emotions)
                .orElseThrow(() -> new EmotionHandler(ErrorStatus._EMOTION_NOT_FOUND));
    }

    @Override
    public DiaryEmotion findDiaryEmotion(Long diaryId) {
        return diaryRepository.findEmotionById(diaryId)
                .orElseThrow(() -> new EmotionHandler(ErrorStatus._EMOTION_NOT_FOUND));
    }
}
