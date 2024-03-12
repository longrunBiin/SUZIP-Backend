package Fo.Suzip.web.controller;

import Fo.Suzip.apiPayload.ApiResponse;
import Fo.Suzip.converter.DiaryConverter;
import Fo.Suzip.domain.Diary;
import Fo.Suzip.web.dto.DiaryRequestDTO;
import Fo.Suzip.web.dto.DiaryResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import Fo.Suzip.service.DiaryService.DiaryService;
import Fo.Suzip.web.dto.DiaryDTO;

@RestController
@RequestMapping("/api")
//@RequiredArgsConstructor
public class DiaryController {

    private final DiaryService diaryService;

    @Autowired
    public DiaryController(DiaryService diaryService){
        this.diaryService = diaryService;
    }

    // 일기 작성
    @PostMapping(value = "/diary")
    public ApiResponse<DiaryResponseDTO.CreateResponseDTO> addDiary(@RequestBody DiaryRequestDTO.CreateRequestDTO request)
    {
        Diary diary = diaryService.addDiary(request);
        return ApiResponse.onSuccess(DiaryConverter.toCreateResultDTO(diary));
    }

    // 일기 수정
    @PatchMapping("/diary/{diary-id}")
    public ApiResponse<DiaryResponseDTO.UpdateResponseDTO> updateDiary(@PathVariable("diary-id") Long diaryId, @RequestBody DiaryRequestDTO.UpdateRequestDTO request)
    {
        Diary updatedDiary = diaryService.updateDiary(diaryId, request);
        DiaryResponseDTO.UpdateResponseDTO responseDTO = DiaryConverter.toUpdateResponseDTO(updatedDiary);
        return ApiResponse.onSuccess(responseDTO);
    }


    // 일기 수정
//    @PatchMapping("/diary/{diary-id}")
//    public ResponseEntity<DiaryDTO> updateDiary(@PathVariable("diary-id") Long diaryId, @RequestBody DiaryDTO diaryDto) {
//        DiaryDTO updatedDiary = diaryService.updateDiary(diaryId, diaryDto);
//        return new ResponseEntity<>(updatedDiary, HttpStatus.OK);
//    }

    // 일기 삭제
    @DeleteMapping("/diary/{diary-id}")
    public ResponseEntity<Void> deleteDiary(@PathVariable("diary-id") Long diaryId) {
        diaryService.deleteDiary(diaryId);
        return ResponseEntity.noContent().build();
    }

    // 일기 1개 조회
    @GetMapping("/diary/{diary-id}")
    public ResponseEntity<DiaryDTO> getDiaryById(@PathVariable("diary-id") Long diaryId) {
        DiaryDTO diaryDto = diaryService.getDiaryById(diaryId);
        return ResponseEntity.ok(diaryDto);
    }

    // 전체 일기 조회
    @GetMapping("/diary")
    public ResponseEntity<Object> getAllDiaries() {
        List<DiaryDTO> diaries = diaryService.getAllDiaries();
        return ResponseEntity.ok(diaries);
    }

    // 제목, 내용, 태그로 일기 검색
    @GetMapping("/diary/search")
    public ResponseEntity<Object> searchDiaries(@RequestParam(required = false) String title,
                                                @RequestParam(required = false) String content,
                                                @RequestParam(required = false) String tag) {
        List<DiaryDTO> diaries = diaryService.searchDiaries(title, content, tag);
        return ResponseEntity.ok(diaries);
    }
}

