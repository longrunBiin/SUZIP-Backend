package Fo.Suzip.web.controller;

import Fo.Suzip.apiPayload.ApiResponse;
import Fo.Suzip.converter.ContentConverter;
import Fo.Suzip.domain.MemberItem;
import Fo.Suzip.domain.contentItem.Book;
import Fo.Suzip.domain.contentItem.Movie;
import Fo.Suzip.domain.contentItem.Music;
import Fo.Suzip.service.contentService.ContentCommandService;
import Fo.Suzip.service.contentService.ContentQueryService;
import Fo.Suzip.web.dto.contentDTO.ContentRequestDTO;
import Fo.Suzip.web.dto.contentDTO.ContentResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contents")
@RequiredArgsConstructor
public class ContentController {

    private final ContentQueryService contentQueryService;
    private final ContentCommandService contentCommandService;

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

    @GetMapping("/books")
    public ApiResponse<ContentResponseDTO.findAllBookListDTO> findAllBooks(@RequestParam(name = "page") Integer page){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        Page<Book> bookList = contentQueryService.getBookList(userName, page);

        return ApiResponse.onSuccess(ContentConverter.toFindAllBookResultListDTO(bookList));
    }

    @GetMapping("/movies")
    public ApiResponse<ContentResponseDTO.findAllMovieListDTO> findAllMovies(@RequestParam(name = "page") Integer page){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        Page<Movie> movieList = contentQueryService.getMovieList(userName, page);

        return ApiResponse.onSuccess(ContentConverter.toFindAllMovieResultListDTO(movieList));
    }

    @GetMapping("/musics")
    public ApiResponse<ContentResponseDTO.findAllMusicListDTO> findAllMusics(@RequestParam(name = "page") Integer page){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        Page<Music> musicList = contentQueryService.getMusicList(userName, page);

        return ApiResponse.onSuccess(ContentConverter.toFindAllMusicResultListDTO(musicList));
    }

    @PostMapping("/")
    public ApiResponse<ContentResponseDTO.scrapContentsResponseDto> scrapContent(@RequestBody @Valid ContentRequestDTO.scrapContentsRequestDto request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        MemberItem memberItem = contentCommandService.addScrapContent(userName, request);

        return ApiResponse.onSuccess(ContentConverter.toScrapContentsResponseDto(memberItem));
    }
}
