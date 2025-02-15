package Fo.Suzip.repository;

import Fo.Suzip.domain.Member;
import Fo.Suzip.domain.contentItem.Book;
import Fo.Suzip.domain.contentItem.ContentItem;
import Fo.Suzip.domain.contentItem.Movie;
import Fo.Suzip.domain.contentItem.Music;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ContentRepository extends JpaRepository<ContentItem, Long> {
    @Query("select b from Book b where b.id = :id")
    Optional<Book> findBookById(@Param("id") Long id);

    @Query("select m from Movie m where m.id = :id")
    Optional<Movie> findMovieById(@Param("id") Long id);

    @Query("select m from Music m where m.id = :id")
    Optional<Music> findMusicById(@Param("id") Long id);

    @Query("select b from Book b where b.diary.id = :id")
    Optional<Book> findBookByDiaryId(@Param("id") Long id);

    @Query("select m from Movie m where m.diary.id = :id")
    Optional<Movie> findMovieByDiaryId(@Param("id") Long id);

    @Query("select m from Music m where m.diary.id = :id")
    Optional<Music> findMusicByDiaryId(@Param("id") Long id);

    @Query("select b from Member m join m.memberReconmmendedItemList mi " +
            "join mi.contentItem b " +
            "where m.id = :memberId and mi.contentItem.dType = :dtype")
    Page<Book> findAllBookByMember(Long memberId, PageRequest pageRequest, String dtype);

    @Query("select mo from Member m join m.memberReconmmendedItemList mi " +
            " join mi.contentItem mo " +
            "where m.id = :memberId and mi.contentItem.dType = :dtype")
    Page<Movie> findAllMovieByMember(Long memberId, PageRequest pageRequest, String dtype);

    @Query("select mu from Member m join m.memberReconmmendedItemList mi " +
            " join mi.contentItem mu " +
            "where m.id = :memberId and mi.contentItem.dType = :dtype")
    Page<Music> findAllMusicByMember(Long memberId, PageRequest pageRequest, String dtype);

    Optional<ContentItem> findContentById(Long contentId);

    @Query("select m from Movie m where m.name = :name and m.content = :content and m.dType = :dType ")
    Optional<ContentItem> findMovieByNameAndContent(String name, String content, String dType);

    @Query("select m from Movie m where m.name = :name and m.content = :content and m.dType = :dType ")
    Optional<ContentItem> findMusicByNameAndContent(String name, String content, String dType);

    @Query("select m from Movie m where m.name = :name and m.content = :content and m.dType = :dType ")
    Optional<ContentItem> findBookByNameAndContent(String name, String content, String dType);
}

