package Fo.Suzip.converter;

import Fo.Suzip.domain.Member;
import Fo.Suzip.domain.MemberItem;
import Fo.Suzip.domain.contentItem.Book;
import Fo.Suzip.domain.contentItem.ContentItem;
import Fo.Suzip.domain.contentItem.Movie;
import Fo.Suzip.domain.contentItem.Music;
import Fo.Suzip.web.dto.contentDTO.ContentResponseDTO;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class ContentConverter {

    public static ContentResponseDTO.scrapContentsResponseDto toScrapContentsResponseDto(MemberItem memberItem){
        return ContentResponseDTO.scrapContentsResponseDto.builder()
                .contentId(memberItem.getId())
                .createdAt(LocalDateTime.now())
                .build();
    }

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
}
