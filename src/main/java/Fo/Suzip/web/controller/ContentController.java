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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contents")
@RequiredArgsConstructor
public class ContentController {

    private final ContentQueryService contentQueryService;

    @GetMapping("/books/{book-id}")
    @Operation(summary = "책 단건 조회 API",description = "책 아이디를 받아서 해당하는 책의 상세정보를 줍니다.")
    public ApiResponse<ContentResponseDTO.findBookResponseDTO> findBook(@PathVariable("book-id") Long bookId) {

        Book book = contentQueryService.findBook(bookId);

        return ApiResponse.onSuccess(ContentConverter.toFindBookResponseDTO(book));
    }

    @GetMapping("/movies/{movie-id}")
    @Operation(summary = "영화 단건 조회 API",description = "영화 아이디를 받아서 해당하는 영화의 상세정보를 줍니다.")
    public ApiResponse<ContentResponseDTO.findMovieResponseDTO> findMovie(@PathVariable("movie-id") Long movieId) {

        Movie movie = contentQueryService.findMovie(movieId);

        return ApiResponse.onSuccess(ContentConverter.tofindMovieResponseDTO(movie));
    }

    @GetMapping("/musics/{music-id}")
    @Operation(summary = "음악 단건 조회 API",description = "음악 아이디를 받아서 해당하는 음악의 상세정보를 줍니다.")
    public ApiResponse<ContentResponseDTO.findMusicResponseDTO> findMusic(@PathVariable("music-id") Long musicId) {

        Music music = contentQueryService.findMusic(musicId);

        return ApiResponse.onSuccess(ContentConverter.tofindMusicResponseDTO(music));
    }

    @GetMapping("/books")
    @Operation(summary = "추천받은 책 전체 조회 API",description = "추천받은 모든 책을 조회합니다. 페이지번호를 queryString으로 주세요")
    public ApiResponse<ContentResponseDTO.findAllBookListDTO> findAllBooks(@RequestParam(name = "page") Integer page){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        Page<Book> bookList = contentQueryService.getBookList(userName, page);

        return ApiResponse.onSuccess(ContentConverter.toFindAllBookResultListDTO(bookList));
    }

    @GetMapping("/movies")
    @Operation(summary = "추천받은 영화 전체 조회 API",description = "추천받은 모든 영화를 조회합니다. 페이지번호를 queryString으로 주세요")
    public ApiResponse<ContentResponseDTO.findAllMovieListDTO> findAllMovies(@RequestParam(name = "page") Integer page){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        Page<Movie> movieList = contentQueryService.getMovieList(userName, page);

        return ApiResponse.onSuccess(ContentConverter.toFindAllMovieResultListDTO(movieList));
    }

    @GetMapping("/musics")
    @Operation(summary = "추천받은 음악 전체 조회 API",description = "추천받은 모든 음악을 조회합니다. 페이지번호를 queryString으로 주세요")
    public ApiResponse<ContentResponseDTO.findAllMusicListDTO> findAllMusics(@RequestParam(name = "page") Integer page){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        Page<Music> musicList = contentQueryService.getMusicList(userName, page);

        return ApiResponse.onSuccess(ContentConverter.toFindAllMusicResultListDTO(musicList));
    }




}
