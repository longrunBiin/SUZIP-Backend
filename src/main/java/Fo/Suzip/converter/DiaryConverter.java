package Fo.Suzip.converter;

import Fo.Suzip.domain.Diary;
import Fo.Suzip.domain.DiaryEmotion;
import Fo.Suzip.domain.Member;
import Fo.Suzip.domain.contentItem.Book;
import Fo.Suzip.domain.contentItem.Movie;
import Fo.Suzip.domain.contentItem.Music;
import Fo.Suzip.web.dto.contentDTO.ContentResponseDTO;
import Fo.Suzip.web.dto.diaryDTO.DiaryDTO;
import Fo.Suzip.web.dto.diaryDTO.DiaryRequestDTO;
import Fo.Suzip.web.dto.diaryDTO.DiaryResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DiaryConverter {

    public static DiaryResponseDTO.EmotionResponseDto toEmotionResponseDto(DiaryResponseDTO.RecommendationsDto recommendationsDto,
                                                                          String sentence, String emotion) {

        switch (emotion){
            case "HAPPY" :{emotion =  "기쁨"; break;}
            case "ANGER" :{emotion =  "분노"; break;}
            case "SADNESS" :{emotion =  "슬픔"; break;}
            case "HURT" :{emotion =  "상처"; break;}
            case "ANXIETY" :{emotion =  "불안"; break;}
        };

        return DiaryResponseDTO.EmotionResponseDto.builder()
                .emotion(emotion)
                .sentence(sentence)
                .recommendations(recommendationsDto)
                .build();
    }

    public static DiaryResponseDTO.RecommendationsDto toRecommendationsDto(DiaryResponseDTO.MusicDto musicDto,
                                                                           DiaryResponseDTO.BookDto bookDto,
                                                                           DiaryResponseDTO.MovieDto movieDto) {
        return DiaryResponseDTO.RecommendationsDto.builder()
                .music(musicDto)
                .movie(movieDto)
                .book(bookDto)
                .build();
    }


    public static DiaryResponseDTO.MusicDto toMusicDto(Music music) {
        return DiaryResponseDTO.MusicDto.builder()
                .musicId(music.getId())
                .name(music.getName())
                .image(music.getImage())
                .artist(music.getArtist())
                .build();
    }

    public static DiaryResponseDTO.MovieDto toMovieDto(Movie movie) {
        return DiaryResponseDTO.MovieDto.builder()
                .movieId(movie.getId())
                .name(movie.getName())
                .image(movie.getImage())
                .genre(movie.getGenre())
                .content(movie.getContent())
                .director(movie.getDirector())
                .build();
    }

    public static DiaryResponseDTO.BookDto toBookDto(Book book) {
        return DiaryResponseDTO.BookDto.builder()
                .bookId(book.getId())
                .name(book.getName())
                .image(book.getImage())
                .genre(book.getGenre())
                .author(book.getAuthor())
                .build();
    }

    public static DiaryResponseDTO.CreateResponseDTO toCreateResultDTO(Diary diary, DiaryResponseDTO.EmotionResponseDto emotionResponseDto) {
        return DiaryResponseDTO.CreateResponseDTO.builder()
                .diaryId(diary.getId())
                .title(diary.getTitle())
                .content(diary.getContent())
                .memberId(diary.getMember().getId())
                .createdAt(LocalDate.now())
                .updatedAt(LocalDate.now())
                .imageUrl(diary.getImage())
                .emotions(diary.getEmotion())
                .date(diary.getDate())
                .emotionResponseDto(emotionResponseDto)
                .build();
    }

    public static Diary toDiary(Member member, DiaryRequestDTO.CreateRequestDTO request, String imageUrl, DiaryEmotion emotion, String sentence) {
        return Diary.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .member(member)
                .image(imageUrl)
                .emotion(emotion.getEmotion())
                .date(request.getDate())
                .sentence(sentence)
                .build();
    }

    public static DiaryResponseDTO.UpdateResponseDTO toUpdateResponseDTO(Diary diary) {
        return DiaryResponseDTO.UpdateResponseDTO.builder()
                .diaryId(diary.getId())
                .title(diary.getTitle())
                .content(diary.getContent())
                .imageurl(diary.getImage())
                .updatedAt(LocalDate.now())
                .date(diary.getDate())
                .build();

    }

    public static DiaryResponseDTO.SearchResponseDTO toSearchResponseDTO(Diary diary) {
        String emotion = diary.getEmotion().toString();
        String color = switch (diary.getEmotion()){
            case HAPPY -> "green";
            case ANGER -> "red";
            case SADNESS -> "blue";
            case CONFUSION -> "yellow";
            case HURT -> "black";
            case ANXIETY -> "purple";
        };

        return DiaryResponseDTO.SearchResponseDTO.builder()
                .diaryId(diary.getId())
                .title(diary.getTitle())
                .content(diary.getContent())
                .emotion(emotion)
                .color(color)
                .image(diary.getImage())
                .date(diary.getDate())
                .build();
    }

    public static DiaryResponseDTO.findAllDiaryResponseDto toFindAllDiaryResultListDTO(Page<Diary> diaries) {
        List<DiaryResponseDTO.SearchResponseDTO> searchResponseDTOS = diaries.getContent().stream()
                .map(DiaryConverter::toSearchResponseDTO)
                .collect(Collectors.toList());

        return DiaryResponseDTO.findAllDiaryResponseDto.builder()
                .isLast(diaries.isLast())
                .isFirst(diaries.isFirst())
                .totalPage(diaries.getTotalPages())
                .totalElements(diaries.getTotalElements())
                .listSize(searchResponseDTOS.size())
                .diaryList(searchResponseDTOS)
                .build();
    }


    public DiaryDTO entityToDto(Diary diary) {
        return DiaryDTO.builder()
                .title(diary.getTitle())
                .content(diary.getContent())
                .build();
    }

    public DiaryResponseDTO.CreateResponseDTO diaryToCreateResponseDTO(Diary diary) {
        return DiaryResponseDTO.CreateResponseDTO.builder()
                .title(diary.getTitle())
                .content(diary.getContent())
                .build();
    }

    public DiaryResponseDTO.UpdateResponseDTO diaryToUpdateResponseDTO(Diary diary) {
        return DiaryResponseDTO.UpdateResponseDTO.builder()
                .diaryId(diary.getId())
                .title(diary.getTitle())
                .content(diary.getContent())

                .build();
    }

//    public DiaryResponseDTO.SearchResponseDTO diaryToSearchResponseDTO(Diary diary) {
//        return DiaryResponseDTO.SearchResponseDTO.builder()
//                .title(diary.getTitle())
//                .emotion(diary.getEmotion())
//
//                .build();
//    }

}
