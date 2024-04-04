package Fo.Suzip.service.contentService;

import Fo.Suzip.domain.contentItem.Book;
import Fo.Suzip.domain.contentItem.ContentItem;
import org.springframework.stereotype.Service;


public interface ContentQueryService {
    Book findBook(Long bookId);
}
