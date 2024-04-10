package Fo.Suzip.web.dto.diaryDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

public class DiaryResponseDTO {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateResponseDTO {  //일기 생성 응답 DTO
        private Long diaryId; //일기 ID
        private String title; //일기 제목
        private String content; //일기 내용
        private Long memberId; // 이용자 ID
        private LocalDate createdAt;
        private LocalDate updatedAt;

    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateResponseDTO { //일기 수정 응답 DTO
        private Long diaryId; // 일기 ID
        private String title; // 일기 제목
        private String content; // 일기 내용
        private LocalDate updatedAt; // 갱신할 일기 날짜
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SearchResponseDTO { //일기 검색 응답 DTO
        private String title; // 검색된 제목
        private String emotion; // 검색된 감정
        private String color;
        private String content; // 내용
        private String image; // 사진

    }
    public static class DeleteResponseDTO { //일기 삭제 응답 DTO

    }

}
