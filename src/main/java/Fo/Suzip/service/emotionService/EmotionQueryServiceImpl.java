package Fo.Suzip.service.emotionService;

import Fo.Suzip.apiPayload.code.status.ErrorStatus;
import Fo.Suzip.apiPayload.exception.handler.DiaryHandler;
import Fo.Suzip.apiPayload.exception.handler.EmotionHandler;
import Fo.Suzip.apiPayload.exception.handler.MemberHandler;
import Fo.Suzip.converter.EmotionConverter;
import Fo.Suzip.domain.Diary;
import Fo.Suzip.domain.DiaryEmotion;
import Fo.Suzip.domain.Emotions;
import Fo.Suzip.domain.Member;
import Fo.Suzip.repository.DiaryRepository;
import Fo.Suzip.repository.EmotionRepository;
import Fo.Suzip.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EmotionQueryServiceImpl implements EmotionQueryService {

    private final EmotionRepository emotionRepository;
    private final DiaryRepository diaryRepository;
    private final MemberRepository memberRepository;

    @Override
    public DiaryEmotion findEmotion(String emotion) {

        Emotions emotions = EmotionConverter.toEmotions(emotion);

        return emotionRepository.findByEmotion(emotions)
                .orElseThrow(() -> new EmotionHandler(ErrorStatus._EMOTION_NOT_FOUND));
    }

    @Override
    public DiaryEmotion findDiaryEmotion(Long diaryId) {
        return emotionRepository.findEmotionById(diaryId)
                .orElseThrow(() -> new EmotionHandler(ErrorStatus._EMOTION_NOT_FOUND));
    }

    @Override
    public Page<Diary> getHappyDiary(String userName, Integer page) {
        Member member = memberRepository.findMemberByUserName(userName)
                .orElseThrow(() -> new MemberHandler(ErrorStatus._MEMBER_NOT_FOUND));

        PageRequest pageRequest = PageRequest.of(page, 10);
        return emotionRepository.findHappyByMember(member.getId(), pageRequest, Emotions.HAPPY);
    }
}
