package Fo.Suzip.web.dto.contentDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

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
    }
}

