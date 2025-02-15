package Fo.Suzip.web.dto.diaryDTO;

import Fo.Suzip.domain.Emotions;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
//@AllArgsConstructor
public class DiaryRequestDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateRequestDTO {  //일기 생성 요청 DTO
        private String title; //일기 제목
        private String content; //일기 내용
        private Emotions emotions;
        private LocalDate date;//작성할 일기의 날짜
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateRequestDTO { //일기 수정 요청 DTO
        //private Long diaryId; // 일기 ID
        private String title; // 일기 제목
        private String content; // 일기 내용
        private String previewSrc;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SearchRequestDTO { //일기 검색 요청 DTO
        private String title; // 검색할 제목
        private String emotion; // 검색할 감정
        private LocalDate startDate; // 검색할 시작 날짜
        private LocalDate endDate; // 검색할 종료 날짜
    }
}
