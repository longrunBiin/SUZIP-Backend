package Fo.Suzip.web.dto.contentDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class ContentResponseDTO {



    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class findBookResponseDTO {  //독서 조회 응답 DTO
        private Long BookId; // 책 아이디
        private String name;// 책 이름
        private String content;// 책 내용
        private String image;// 책 표지 사진
        private String genre;// 책 장르
        private String author;//작가
        private LocalDateTime createdAt;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class findMovieResponseDTO {  //독서 조회 응답 DTO
        private Long movieId; // 영화 아이디
        private String name;// 영화 이름
        private String content;// 영화 내용
        private String image;// 영화 표지 사진
        private String genre;// 영화 장르
        private String director;//감독
        private LocalDateTime createdAt;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class findMusicResponseDTO {  //독서 조회 응답 DTO
        private Long musicId; // 음악 아이디
        private String name;// 음악 이름
        private String content;// 음악 내용
        private String image;// 음악 표지 사진
        private String genre;// 음악 장르
        private String artist;//가수
        private LocalDateTime createdAt;
    }

    @Schema(description = "책 조회 리스트 정보DTO")
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class findAllBookListDTO{
        @Schema(description = "게시글 리스트")
        List<ContentResponseDTO.findBookResponseDTO> bookList;
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

    @Schema(description = "영화 조회 리스트 정보DTO")
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class findAllMovieListDTO{
        @Schema(description = "게시글 리스트")
        List<ContentResponseDTO.findMovieResponseDTO> movieList;
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

    @Schema(description = "음악 조회 리스트 정보DTO")
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class findAllMusicListDTO{
        @Schema(description = "게시글 리스트")
        List<ContentResponseDTO.findMusicResponseDTO > musicList;
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

