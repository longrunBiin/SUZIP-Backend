package Fo.Suzip.converter;

import Fo.Suzip.domain.MemberItem;
import Fo.Suzip.web.dto.scrapDTO.ScrapResponseDTO;

import java.time.LocalDateTime;

public class ScrapConverter {
    public static ScrapResponseDTO.scrapContentsResponseDto toScrapContentsResponseDto(MemberItem memberItem){
        return ScrapResponseDTO.scrapContentsResponseDto.builder()
                .contentId(memberItem.getContentItem().getId())
                .createdAt(LocalDateTime.now())
                //.contentType(String.valueOf(memberItem.getContentItem()))
                .build();
    }

    public static ScrapResponseDTO.DeleteContentResponseDto toDeleteContentResponseDto() {
        return ScrapResponseDTO.DeleteContentResponseDto.builder()
                .message("컨텐츠가 삭제되었습니다")
                .build();
    }
}
