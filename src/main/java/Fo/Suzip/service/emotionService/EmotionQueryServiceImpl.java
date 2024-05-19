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
import Fo.Suzip.web.dto.emotionDTO.EmotionResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;

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
        Emotions emotions = emotionRepository.findEmotionById(diaryId)
                .orElseThrow(() -> new EmotionHandler(ErrorStatus._EMOTION_NOT_FOUND));

        return emotionRepository.findInfoByEmotions(emotions)
                .orElseThrow(() -> new EmotionHandler(ErrorStatus._EMOTION_NOT_FOUND));
    }

    @Override
    public Page<Diary> getHappyDiary(String userName, Integer page) {
        Member member = memberRepository.findMemberByUserName(userName)
                .orElseThrow(() -> new MemberHandler(ErrorStatus._MEMBER_NOT_FOUND));

        PageRequest pageRequest = PageRequest.of(page, 10);
        return emotionRepository.findHappyByMember(member.getId(), pageRequest, Emotions.HAPPY);
    }

    @Override
    public List<EmotionResponseDto.findMonthEmotionResponseDto> findMonthEmotion(String userName, Integer year, Integer month) {
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = YearMonth.of(year, month).atEndOfMonth();

        List<Diary> diaries = emotionRepository.findByMemberAndDateBetween(userName, startDate, endDate);

        return diaries.stream()
                .map(diary -> EmotionResponseDto.findMonthEmotionResponseDto.builder()
                        .emotion(diary.getEmotion().toString())
                        .date(diary.getDate())
                        .build())
                .collect(Collectors.toList());
    }
}
