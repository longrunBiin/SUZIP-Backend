package Fo.Suzip.service.contentService;

import Fo.Suzip.domain.MemberItem;
import Fo.Suzip.web.dto.contentDTO.ContentRequestDTO;
import Fo.Suzip.web.dto.scrapDTO.ScrapRequestDTO;

public interface ContentCommandService {
    MemberItem addScrapContent(String userName, ScrapRequestDTO.scrapContentsRequestDto request);

    void deleteScrapContent(String userName, Long id);
}
