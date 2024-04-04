package Fo.Suzip.web.controller;

import Fo.Suzip.apiPayload.ApiResponse;
import Fo.Suzip.converter.ContentConverter;
import Fo.Suzip.domain.contentItem.Book;
import Fo.Suzip.service.contentService.ContentQueryService;
import Fo.Suzip.web.dto.contentDTO.ContentResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/content")
@RequiredArgsConstructor
public class ContentController {

    private final ContentQueryService contentQueryService;
    @GetMapping("/books/{book-id}")
    public ApiResponse<ContentResponseDTO.findBookResponseDTO> findBook(@PathVariable("book-id") Long bookId) {

        Book book = contentQueryService.findBook(bookId);

        return ApiResponse.onSuccess(ContentConverter.toFindBookResponseDTO(book));
    }
}
