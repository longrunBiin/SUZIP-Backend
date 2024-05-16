package Fo.Suzip.service.contentService;

import Fo.Suzip.domain.Diary;
import Fo.Suzip.domain.MemberItem;
import Fo.Suzip.domain.MemberRecommendedItem;
import Fo.Suzip.domain.contentItem.Book;
import Fo.Suzip.domain.contentItem.ContentItem;
import Fo.Suzip.domain.contentItem.Movie;
import Fo.Suzip.web.dto.contentDTO.ContentRequestDTO;
import Fo.Suzip.web.dto.diaryDTO.DiaryResponseDTO;
import Fo.Suzip.web.dto.scrapDTO.ScrapRequestDTO;
import org.springframework.data.domain.Page;

public interface ContentCommandService {
    MemberItem addScrapContent(String userName, ScrapRequestDTO.scrapContentsRequestDto request);

    void deleteScrapContent(String userName, Long id);

    void addContent(String userName, DiaryResponseDTO.EmotionResponseDto emotionResponse, Diary diary);

    MemberRecommendedItem addRecommendedContent(String userName, ContentItem item);
}
