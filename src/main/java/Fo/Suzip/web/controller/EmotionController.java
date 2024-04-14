package Fo.Suzip.web.controller;

import Fo.Suzip.apiPayload.ApiResponse;
import Fo.Suzip.converter.EmotionConverter;
import Fo.Suzip.domain.DiaryEmotion;
import Fo.Suzip.repository.EmotionRepository;
import Fo.Suzip.service.DiaryService.DiaryService;
import Fo.Suzip.service.emotionService.EmotionService;
import Fo.Suzip.web.dto.emotionDTO.EmotionResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/emotions")
@RequiredArgsConstructor
public class EmotionController {

    private final EmotionService emotionService;

    @GetMapping("/")//감정 정보 조회
    @Operation(summary = "감정 정보 조회 API",description = "저장되어있는 감정을 조회합니다. " +
            "HAPPY, ANGER, SADNESS, CONFUSION, HURT, ANXIETY 위 6개 중에서 대문자로 입력해주세요")
    public ApiResponse<EmotionResponseDto.findEmotionResponseDto> findEmotion(@RequestParam("emotion") String emotion) {

        DiaryEmotion findEmotion = emotionService.findEmotion(emotion);

        return ApiResponse.onSuccess(EmotionConverter.toFindEmotionResponseDto(findEmotion));
    }

    @GetMapping("/{diary-id}")//감정 정보 조회
    @Operation(summary = "일기 감정 조회 API",description = "작성한 일기의 감정을 조회합니다. ")
    public ApiResponse<EmotionResponseDto.findEmotionResponseDto> findDiaryEmotion(@PathVariable("diary-id") Long diaryId) {

        DiaryEmotion emotion = emotionService.findDiaryEmotion(diaryId);
        return ApiResponse.onSuccess(EmotionConverter.toFindEmotionResponseDto(emotion));
    }
}
