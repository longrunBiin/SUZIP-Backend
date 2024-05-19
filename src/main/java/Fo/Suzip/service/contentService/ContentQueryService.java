package Fo.Suzip.service.contentService;

import Fo.Suzip.domain.contentItem.Book;
import Fo.Suzip.domain.contentItem.ContentItem;
import Fo.Suzip.domain.contentItem.Movie;
import Fo.Suzip.domain.contentItem.Music;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Map;


public interface ContentQueryService {
    Book findBook(Long bookId);

    Movie findMovie(Long movieId);

    Music findMusic(Long musicId);

    Page<Book> getBookList(String userName, Integer page);

    Page<Movie> getMovieList(String userName, Integer page);

    Page<Music> getMusicList(String userName, Integer page);

    Page<Book> getMemberBookList(String userName, Integer page);

    Page<Movie> getMemberMovieList(String userName, Integer page);

    Page<Music> getMemberMusicList(String userName, Integer page);

}

