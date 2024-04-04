package Fo.Suzip.service.contentService;

import Fo.Suzip.domain.contentItem.Book;
import Fo.Suzip.domain.contentItem.ContentItem;
import Fo.Suzip.domain.contentItem.Movie;
import org.springframework.stereotype.Service;


public interface ContentQueryService {
    Book findBook(Long bookId);

    Movie findMovie(Long movieId);
}
