package Fo.Suzip.service.contentService;

import Fo.Suzip.apiPayload.code.status.ErrorStatus;
import Fo.Suzip.apiPayload.exception.handler.ContentHandler;
import Fo.Suzip.domain.contentItem.Book;
import Fo.Suzip.domain.contentItem.ContentItem;
import Fo.Suzip.domain.contentItem.Movie;
import Fo.Suzip.repository.ContentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ContentQueryServiceImpl implements ContentQueryService{
    private final ContentRepository contentRepository;
    @Override
    public Book findBook(Long bookId) {

        return contentRepository.findBookById(bookId)
                .orElseThrow(() -> new ContentHandler(ErrorStatus._BOOK_NOT_FOUND));
    }

    @Override
    public Movie findMovie(Long movieId) {
        return contentRepository.findMovieById(movieId)
                .orElseThrow(() -> new ContentHandler(ErrorStatus._BOOK_NOT_FOUND));
    }
}
