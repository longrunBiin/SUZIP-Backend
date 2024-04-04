package Fo.Suzip.repository;

import Fo.Suzip.domain.contentItem.Book;
import Fo.Suzip.domain.contentItem.ContentItem;
import Fo.Suzip.domain.contentItem.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ContentRepository extends JpaRepository<ContentItem, Long> {
    @Query("select b from Book b where b.id = :id")
    Optional<Book> findBookById(@Param("id") Long id);

    @Query("select m from Movie m where m.id = :id")
    Optional<Movie> findMovieById(@Param("id") Long id);
}
