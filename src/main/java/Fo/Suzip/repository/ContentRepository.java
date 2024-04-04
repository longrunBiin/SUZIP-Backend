package Fo.Suzip.repository;

import Fo.Suzip.domain.contentItem.Book;
import Fo.Suzip.domain.contentItem.ContentItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ContentRepository extends JpaRepository<ContentItem, Long> {
    @Query("select b from Book b where b.id = :id")
    Optional<Book> findBookById(@Param("id") Long id);
}
