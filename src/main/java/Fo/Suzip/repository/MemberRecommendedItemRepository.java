package Fo.Suzip.repository;

import Fo.Suzip.domain.Diary;
import Fo.Suzip.domain.Member;
import Fo.Suzip.domain.MemberItem;
import Fo.Suzip.domain.MemberRecommendedItem;
import Fo.Suzip.domain.contentItem.Book;
import Fo.Suzip.domain.contentItem.ContentItem;
import Fo.Suzip.domain.contentItem.Movie;
import Fo.Suzip.domain.contentItem.Music;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface MemberRecommendedItemRepository extends JpaRepository<MemberRecommendedItem, Long> {

    @Modifying
    @Transactional
    @Query("delete from MemberRecommendedItem m where m.contentItem.diary = :diary")
    void deleteByDiary(Diary diary);
}
