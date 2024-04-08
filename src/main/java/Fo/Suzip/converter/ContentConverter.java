package Fo.Suzip.converter;

import Fo.Suzip.domain.contentItem.Book;
import Fo.Suzip.domain.contentItem.Movie;
import Fo.Suzip.domain.contentItem.Music;
import Fo.Suzip.web.dto.contentDTO.ContentResponseDTO;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class ContentConverter {

    public static ContentResponseDTO.findBookResponseDTO toFindBookResponseDTO(Book book) {

        return ContentResponseDTO.findBookResponseDTO.builder()
                .BookId(book.getId())
                .name(book.getName())
                .content(book.getContent())
                .image(book.getImage())
                .genre(book.getGenre())
                .author(book.getAuthor())
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
                .build();
    }

    public static ContentResponseDTO.findAllBookListDTO toFindAllResultListDTO(Page<Book> ingredientList) {
        List<ContentResponseDTO.findBookResponseDTO> bookResponseDTOList = ingredientList.getContent().stream()
                .map(ContentConverter::toFindBookResponseDTO)
                .collect(Collectors.toList());

        return ContentResponseDTO.findAllBookListDTO.builder()
                .isLast(ingredientList.isLast())
                .isFirst(ingredientList.isFirst())
                .totalPage(ingredientList.getTotalPages())
                .totalElements(ingredientList.getTotalElements())
                .listSize(bookResponseDTOList.size())
                .bookList(bookResponseDTOList)
                .build();
    }
}
