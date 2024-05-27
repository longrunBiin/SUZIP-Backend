package Fo.Suzip.web.controller;

import Fo.Suzip.apiPayload.ApiResponse;
import Fo.Suzip.converter.DiaryConverter;
import Fo.Suzip.converter.EmotionConverter;
import Fo.Suzip.domain.Diary;
import Fo.Suzip.domain.DiaryEmotion;
import Fo.Suzip.service.emotionService.EmotionQueryService;
import Fo.Suzip.web.dto.diaryDTO.DiaryResponseDTO;
import Fo.Suzip.web.dto.emotionDTO.EmotionResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/emotions")
@RequiredArgsConstructor
public class EmotionController {

    private final EmotionQueryService emotionService;

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

    @GetMapping("/happy")//감정 정보 조회
    @Operation(summary = "행복 수집 API",description = "작성한 일기 중 감정이 HAPPY인 것을 조회합니다.")
    public ApiResponse<DiaryResponseDTO.SearchResponseDTO> getAllDiaries(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        Diary diary = emotionService.getHappyDiary(userName);

        return ApiResponse.onSuccess(DiaryConverter.toSearchResponseDTO(diary));
    }

    @GetMapping("/months")
    @Operation(summary = "한달 감정 정보 조회 API", description = "한달간의 감정 정보를 조회합니다.")
    public ApiResponse<List<EmotionResponseDto.findMonthEmotionResponseDto>> getMonthEmotions(@RequestParam(name = "year") Integer year,
                                                                                              @RequestParam(name = "month") Integer month) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        List<EmotionResponseDto.findMonthEmotionResponseDto> emotions = emotionService.findMonthEmotion(userName, year, month);

        return ApiResponse.onSuccess(emotions);
    }
}
