package Fo.Suzip.web.controller;

import Fo.Suzip.apiPayload.ApiResponse;
import Fo.Suzip.converter.ContentConverter;
import Fo.Suzip.converter.ScrapConverter;
import Fo.Suzip.domain.MemberItem;
import Fo.Suzip.domain.contentItem.Book;
import Fo.Suzip.domain.contentItem.Movie;
import Fo.Suzip.domain.contentItem.Music;
import Fo.Suzip.service.contentService.ContentCommandService;
import Fo.Suzip.service.contentService.ContentQueryService;
import Fo.Suzip.web.dto.contentDTO.ContentRequestDTO;
import Fo.Suzip.web.dto.contentDTO.ContentResponseDTO;
import Fo.Suzip.web.dto.scrapDTO.ScrapRequestDTO;
import Fo.Suzip.web.dto.scrapDTO.ScrapResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/scrap")
@RequiredArgsConstructor
public class ScrapController {

    private final ContentCommandService contentCommandService;
    private final ContentQueryService contentQueryService;

    @PostMapping("/")
    @Operation(summary = "추천받은 컨텐츠 저장API",description = "추천받은 컨텐츠를 저장합니다. 콘텐츠 아이디를 주세요")
    public ApiResponse<ScrapResponseDTO.scrapContentsResponseDto> scrapContent(@RequestBody @Valid ScrapRequestDTO.scrapContentsRequestDto request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        MemberItem memberItem = contentCommandService.addScrapContent(userName, request);

        return ApiResponse.onSuccess(ScrapConverter.toScrapContentsResponseDto(memberItem));
    }

    @DeleteMapping("/{content-id}")
    @Operation(summary = "저장한 컨텐츠 삭제API",description = "저장한 컨텐츠를 삭제합니다. 콘텐츠 아이디를 주세요")
    public ApiResponse<ScrapResponseDTO.DeleteContentResponseDto> deleteScrapContent(@PathVariable("content-id") Long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        contentCommandService.deleteScrapContent(userName, id);
        return ApiResponse.onSuccess(ScrapConverter.toDeleteContentResponseDto());
    }


    @GetMapping("/books")
    @Operation(summary = "저장한 책 전체 조회 API",description = "저장한 모든 책을 조회합니다. 페이지번호를 queryString으로 주세요")
    public ApiResponse<ContentResponseDTO.findAllBookListDTO> findAllBooks(@RequestParam(name = "page") Integer page){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        Page<Book> bookList = contentQueryService.getMemberBookList(userName, page);

        return ApiResponse.onSuccess(ContentConverter.toFindAllBookResultListDTO(bookList));
    }

    @GetMapping("/movies")
    @Operation(summary = "저장한 영화 전체 조회 API",description = "저장한 모든 영화를 조회합니다. 페이지번호를 queryString으로 주세요")
    public ApiResponse<ContentResponseDTO.findAllMovieListDTO> findAllMovies(@RequestParam(name = "page") Integer page){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        Page<Movie> movieList = contentQueryService.getMemberMovieList(userName, page);

        return ApiResponse.onSuccess(ContentConverter.toFindAllMovieResultListDTO(movieList));
    }

    @GetMapping("/musics")
    @Operation(summary = "저장한 음악 전체 조회 API",description = "저장한 모든 음악을 조회합니다. 페이지번호를 queryString으로 주세요")
    public ApiResponse<ContentResponseDTO.findAllMusicListDTO> findAllMusics(@RequestParam(name = "page") Integer page){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        Page<Music> musicList = contentQueryService.getMemberMusicList(userName, page);

        return ApiResponse.onSuccess(ContentConverter.toFindAllMusicResultListDTO(musicList));
    }


}
