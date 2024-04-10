package Fo.Suzip.service.contentService;

import Fo.Suzip.domain.MemberItem;
import Fo.Suzip.web.dto.contentDTO.ContentRequestDTO;

public interface ContentCommandService {
    MemberItem addScrapContent(String userName, ContentRequestDTO.scrapContentsRequestDto request);
}
