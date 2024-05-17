package Fo.Suzip.converter;

import Fo.Suzip.domain.Diary;
import Fo.Suzip.domain.Member;
import Fo.Suzip.domain.MemberItem;
import Fo.Suzip.domain.MemberRecommendedItem;
import Fo.Suzip.domain.contentItem.Book;
import Fo.Suzip.domain.contentItem.ContentItem;
import Fo.Suzip.domain.contentItem.Movie;
import Fo.Suzip.domain.contentItem.Music;
import Fo.Suzip.web.dto.contentDTO.ContentResponseDTO;
import Fo.Suzip.web.dto.diaryDTO.DiaryResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ContentConverter {



    public static Book toBook(DiaryResponseDTO.BookDto bookDto, Diary diary){
        return Book.builder()
                .name(bookDto.getName())
                .image(bookDto.getImage())
                .genre(bookDto.getGenre())
                .diary(diary)
                .dType("book")
                .author(bookDto.getAuthor())
                .build();
    }

    public static Music toMusic(DiaryResponseDTO.MusicDto musicDto, Diary diary){
        return Music.builder()
                .name(musicDto.getName())
                .image(musicDto.getImage())
                .diary(diary)
                .dType("music")
                .artist(musicDto.getArtist())
                .build();
    }

    public static Movie toMovie(DiaryResponseDTO.MovieDto movieDto, Diary diary){
        String substring = null;
        if (movieDto.getContent().length() > 500)
            substring = movieDto.getContent().substring(0, 500);
        return Movie.builder()
                .content(substring)
                .name(movieDto.getName())
                .image(movieDto.getImage())
                .genre(movieDto.getGenre())
                .diary(diary)
                .dType("movie")
                .director(movieDto.getDirector())
                .build();
    }

//    public static ContentItem toContentItem(DiaryResponseDTO.EmotionResponseDto emotionResponse, Diary diary){
//        DiaryResponseDTO.MovieDto movie = emotionResponse.getRecommendations().getMovie();
//        DiaryResponseDTO.BookDto book = emotionResponse.getRecommendations().getBook();
//        DiaryResponseDTO.MusicDto music = emotionResponse.getRecommendations().getMusic();
//
//        Movie newMovie = toMovie(movie, diary);
//        Music newMusic = toMusic(music, diary);
//        Book newBook = toBook((book), diary);
//    }


    public static MemberItem toMemberItem(Member member, ContentItem item) {
        return MemberItem.builder()
                .member(member)
                .contentItem(item)
                .build();
    }

    public static ContentResponseDTO.findBookResponseDTO toFindBookResponseDTO(Book book) {

        return ContentResponseDTO.findBookResponseDTO.builder()
                .BookId(book.getId())
                .name(book.getName())
                .content(book.getContent())
                .image(book.getImage())
                .genre(book.getGenre())
                .author(book.getAuthor())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static ContentResponseDTO.findMovieResponseDTO tofindMovieResponseDTO(Movie movie) {

        return ContentResponseDTO.findMovieResponseDTO.builder()
                .movieId(movie.getId())
                .name(movie.getName())
                .content(movie.getContent())
                .image(movie.getImage())
                .genre(movie.getGenre())
                .director(movie.getDirector())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static ContentResponseDTO.findMusicResponseDTO tofindMusicResponseDTO(Music music) {

        return ContentResponseDTO.findMusicResponseDTO.builder()
                .musicId(music.getId())
                .name(music.getName())
                .content(music.getContent())
                .image(music.getImage())
                .genre(music.getGenre())
                .artist(music.getArtist())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static ContentResponseDTO.findAllBookListDTO toFindAllBookResultListDTO(Page<Book> bookList) {
        List<ContentResponseDTO.findBookResponseDTO> bookResponseDTOList = bookList.getContent().stream()
                .map(ContentConverter::toFindBookResponseDTO)
                .collect(Collectors.toList());

        return ContentResponseDTO.findAllBookListDTO.builder()
                .isLast(bookList.isLast())
                .isFirst(bookList.isFirst())
                .totalPage(bookList.getTotalPages())
                .totalElements(bookList.getTotalElements())
                .listSize(bookResponseDTOList.size())
                .bookList(bookResponseDTOList)
                .build();
    }

    public static ContentResponseDTO.findAllMovieListDTO toFindAllMovieResultListDTO(Page<Movie> movieList) {
        List<ContentResponseDTO.findMovieResponseDTO> movieResponseDTOList = movieList.getContent().stream()
                .map(ContentConverter::tofindMovieResponseDTO)
                .collect(Collectors.toList());

        return ContentResponseDTO.findAllMovieListDTO.builder()
                .isLast(movieList.isLast())
                .isFirst(movieList.isFirst())
                .totalPage(movieList.getTotalPages())
                .totalElements(movieList.getTotalElements())
                .listSize(movieResponseDTOList.size())
                .movieList(movieResponseDTOList)
                .build();
    }

    public static ContentResponseDTO.findAllMusicListDTO toFindAllMusicResultListDTO(Page<Music> musicList) {
        List<ContentResponseDTO.findMusicResponseDTO> musicResponseDTOList = musicList.getContent().stream()
                .map(ContentConverter::tofindMusicResponseDTO)
                .collect(Collectors.toList());

        return ContentResponseDTO.findAllMusicListDTO.builder()
                .isLast(musicList.isLast())
                .isFirst(musicList.isFirst())
                .totalPage(musicList.getTotalPages())
                .totalElements(musicList.getTotalElements())
                .listSize(musicResponseDTOList.size())
                .musicList(musicResponseDTOList)
                .build();
    }

    public static MemberRecommendedItem toRecommendedItem(Member member, ContentItem item) {

        return MemberRecommendedItem.builder()
                .member(member)
                .contentItem(item)
                .build();
    }
}
