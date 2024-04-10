package Fo.Suzip.web.controller;

import Fo.Suzip.apiPayload.ApiResponse;
import Fo.Suzip.converter.ContentConverter;
import Fo.Suzip.converter.ScrapConverter;
import Fo.Suzip.domain.MemberItem;
import Fo.Suzip.service.contentService.ContentCommandService;
import Fo.Suzip.web.dto.contentDTO.ContentRequestDTO;
import Fo.Suzip.web.dto.contentDTO.ContentResponseDTO;
import Fo.Suzip.web.dto.scrapDTO.ScrapRequestDTO;
import Fo.Suzip.web.dto.scrapDTO.ScrapResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/scrap")
@RequiredArgsConstructor
public class ScrapController {

    private final ContentCommandService contentCommandService;

    @PostMapping("/")
    public ApiResponse<ScrapResponseDTO.scrapContentsResponseDto> scrapContent(@RequestBody @Valid ScrapRequestDTO.scrapContentsRequestDto request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        MemberItem memberItem = contentCommandService.addScrapContent(userName, request);

        return ApiResponse.onSuccess(ScrapConverter.toScrapContentsResponseDto(memberItem));
    }

    @DeleteMapping("/{content-id}")
    public ApiResponse<ScrapResponseDTO.DeleteContentResponseDto> deleteScrapContent(@PathVariable("content-id") Long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        contentCommandService.deleteScrapContent(userName, id);
        return ApiResponse.onSuccess(ScrapConverter.toDeleteContentResponseDto());
    }
}
