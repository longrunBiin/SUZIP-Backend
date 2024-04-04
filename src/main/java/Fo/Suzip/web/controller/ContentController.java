package Fo.Suzip.web.controller;

import Fo.Suzip.apiPayload.ApiResponse;
import Fo.Suzip.converter.ContentConverter;
import Fo.Suzip.domain.contentItem.Book;
import Fo.Suzip.domain.contentItem.Movie;
import Fo.Suzip.domain.contentItem.Music;
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

    @GetMapping("/movies/{movie-id}")
    public ApiResponse<ContentResponseDTO.findMovieResponseDTO> findMovie(@PathVariable("movie-id") Long movieId) {

        Movie movie = contentQueryService.findMovie(movieId);

        return ApiResponse.onSuccess(ContentConverter.tofindMovieResponseDTO(movie));
    }

    @GetMapping("/musics/{music-id}")
    public ApiResponse<ContentResponseDTO.findMusicResponseDTO> findMusic(@PathVariable("music-id") Long musicId) {

        Music music = contentQueryService.findMusic(musicId);

        return ApiResponse.onSuccess(ContentConverter.tofindMusicResponseDTO(music));
    }
}
