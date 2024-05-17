package Fo.Suzip.web.controller;

import Fo.Suzip.apiPayload.ApiResponse;
import Fo.Suzip.apiPayload.code.status.ErrorStatus;
import Fo.Suzip.apiPayload.exception.GeneralException;
import Fo.Suzip.aws.s3.AmazonS3Manager;
import Fo.Suzip.converter.ContentConverter;
import Fo.Suzip.converter.DiaryConverter;
import Fo.Suzip.domain.Diary;
import Fo.Suzip.domain.Uuid;
import Fo.Suzip.domain.contentItem.Book;
import Fo.Suzip.repository.UuidRepository;
import Fo.Suzip.service.analyzeService.AnalyzeService;
import Fo.Suzip.service.contentService.ContentCommandService;
import Fo.Suzip.web.dto.diaryDTO.DiaryRequestDTO;
import Fo.Suzip.web.dto.diaryDTO.DiaryResponseDTO;
import Fo.Suzip.web.dto.emotionDTO.EmotionResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import Fo.Suzip.service.DiaryService.DiaryService;
import Fo.Suzip.web.dto.diaryDTO.DiaryDTO;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class DiaryController {

    private final DiaryService diaryService;
    private final AnalyzeService analyzeService;
    private final ContentCommandService contentCommandService;

    // 일기 작성
    @PostMapping(value = "/diary", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "일기 작성 API",description = "일기를 작성합니다." +
            "감정은 HAPPY, ANGER, SADNESS,  HURT, ANXIETY 위 5개 중에서 대문자로 입력해주세요")
    public ApiResponse<DiaryResponseDTO.CreateResponseDTO> addDiary(@RequestPart("request") DiaryRequestDTO.CreateRequestDTO request,
                                                                    @RequestPart(value = "file", required = false) MultipartFile file,
                                                                    @RequestHeader("Authorization") String accessToken){
        if (accessToken != null && accessToken.startsWith("Bearer ")) {
            accessToken = accessToken.substring(7);
        }

        System.out.println("accessToken = " + accessToken);

        if(accessToken != null) {
            // 현재 토큰을 사용중인 유저 고유 id 조회
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();

            DiaryResponseDTO.EmotionResponseDto emotionResponse = analyzeService.analyzeDiary(request);
            Diary diary = diaryService.addDiary(request, userName, file, emotionResponse);
            contentCommandService.addContent(userName, emotionResponse, diary);
            DiaryResponseDTO.EmotionResponseDto analyzeResult = diaryService.getAnalyzeResult(userName, diary, emotionResponse.getEmotion());

            return ApiResponse.onSuccess(DiaryConverter.toCreateResultDTO(diary, analyzeResult));
        } else
            return ApiResponse.onFailure(ErrorStatus._DIARY_ADD_FAILED.getCode(), ErrorStatus._DIARY_ADD_FAILED.getMessage(), null);
    }

    // 일기 수정
    @PatchMapping(value = "/diary/{diary-id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "일기 수정 API",description = "저장한 일기를 수정합니다. 일기 아이디와 수정할 내용을 주세요")
    public ApiResponse<DiaryResponseDTO.UpdateResponseDTO> updateDiary(@PathVariable("diary-id") Long diaryId,
                                                                       @RequestPart(value = "file", required = false) MultipartFile file,
                                                                       @RequestPart("request")  DiaryRequestDTO.UpdateRequestDTO request)
    {
        Diary updatedDiary = diaryService.updateDiary(diaryId, request, file);
        return ApiResponse.onSuccess(DiaryConverter.toUpdateResponseDTO(updatedDiary));
    }

    // 일기 삭제
    @DeleteMapping("/diary/{diary-id}")
    @Operation(summary = "일기 삭제 API",description = "저장한 일기를 삭제합니다. 일기 아이디를 주세요")
    public ApiResponse<DiaryResponseDTO.DeleteResponseDTO> deleteDiary(@PathVariable("diary-id") Long diaryId) {

        try {
            diaryService.deleteDiary(diaryId);
            return ApiResponse.onSuccess(null); // 일기 삭제
        } catch (EntityNotFoundException e) {
            return ApiResponse.onFailure("404", "Diary Not Found", null); // 일기를 찾을 수 없음
        } catch (Exception e) {
            return ApiResponse.onFailure("500", "Internal Server Error", null); // 내부 서버 오류
        }
    }

    // 일기 1개 조회
    @GetMapping("/diary/{diary-id}")
    @Operation(summary = "일기 단건 조회 API",description = "작성한 일기를 조회합니다. 일기 아이디를 주세요")
    public ApiResponse<DiaryResponseDTO.SearchResponseDTO> searchDiary(@PathVariable("diary-id") Long diaryId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

         Diary diary = diaryService.getDiary(diaryId, userName);

        return ApiResponse.onSuccess(DiaryConverter.toSearchResponseDTO(diary));
    }


    // 전체 일기 조회
    @GetMapping("/diary")
    @Operation(summary = "일기 전체 조회 API", description = "작성한 모든 일기를 조회합니다.")
    public ApiResponse<DiaryResponseDTO.findAllDiaryResponseDto> getAllDiaries(
            @RequestParam(name = "page") Integer page,
            @RequestParam(name = "sortOrder") String sortOrder,
            @RequestParam(name = "searchQuery", required = false) String searchQuery) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        Page<Diary> diaries = diaryService.getDiaryList(userName, page, sortOrder, searchQuery);

        return ApiResponse.onSuccess(DiaryConverter.toFindAllDiaryResultListDTO(diaries));
    }


    // 제목, 내용, 태그로 일기 검색
    @GetMapping("/diary/search")
    @Operation(summary = "일기 제목 검색 API",description = "제목으로 일기를 검색합니다. 검색할 제목과 페이지번호를 주세요")
    public ApiResponse<DiaryResponseDTO.SearchResponseDTO> searchDiaries(
            @RequestParam(required = false) String title, @RequestParam(required = false) String content,
            @RequestParam(required = false) String tag, @RequestParam(name = "page") Integer page) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        Diary diaries = diaryService.searchDiaries(userName, title, content, tag, page);
        return ApiResponse.onSuccess(DiaryConverter.toSearchResponseDTO(diaries));
    }

    @GetMapping("/analyze/{diary-id}")
    @Operation(summary = "일기 분석페이지 조회 API",description = "제목으로 일기를 검색합니다. 검색할 제목과 페이지번호를 주세요")
    public ApiResponse<DiaryResponseDTO.CreateResponseDTO> showAnalyzePage (@PathVariable("diary-id") Long diaryId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        Diary diary = diaryService.getDiary(diaryId, userName);
        DiaryResponseDTO.EmotionResponseDto emotionResponse = diaryService.getAnalyzeResult(userName, diary, diary.getEmotion().toString());
        return ApiResponse.onSuccess(DiaryConverter.toCreateResultDTO(diary, emotionResponse));
    }
}

