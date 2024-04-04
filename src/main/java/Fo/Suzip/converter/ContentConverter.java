package Fo.Suzip.converter;

import Fo.Suzip.domain.contentItem.Book;
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
}
