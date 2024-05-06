package Fo.Suzip.web.dto.diaryDTO;

import Fo.Suzip.domain.DiaryEmotion;
import Fo.Suzip.domain.Emotions;
import Fo.Suzip.web.dto.contentDTO.ContentResponseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

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
        private String imageUrl;
        private Emotions emotions;
        private LocalDate date;//작성할 일기의 날짜
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
        private String imageurl;
        private LocalDate date;
        private LocalDate updatedAt; // 갱신할 일기 날짜
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SearchResponseDTO { //일기 검색 응답 DTO
        private Long diaryId; // 일기 ID
        private String title; // 검색된 제목
        private String emotion; // 검색된 감정
        private String color;
        private String content; // 내용
        private String image; // 사진
        private LocalDate date;//작성한 날

    }
    public static class DeleteResponseDTO { //일기 삭제 응답 DTO

    }


    @Schema(description = "일기 조회 리스트 정보DTO")
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class findAllDiaryResponseDto{
        @Schema(description = "일기 리스트")
        List<DiaryResponseDTO.SearchResponseDTO> diaryList;
        @Schema(description = "리스트 사이즈")
        Integer listSize;
        @Schema(description = "전체 페이지 갯수")
        Integer totalPage;
        @Schema(description = "전체 데이터 갯수")
        Long totalElements;
        @Schema(description = "첫 페이지면 true")
        Boolean isFirst;
        @Schema(description = " 마지막 페이지면 true")
        Boolean isLast;
    }
}
