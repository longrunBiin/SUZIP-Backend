package Fo.Suzip.converter;

import Fo.Suzip.domain.contentItem.Book;
import Fo.Suzip.domain.contentItem.Movie;
import Fo.Suzip.web.dto.contentDTO.ContentResponseDTO;

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
}
