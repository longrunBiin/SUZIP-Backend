package Fo.Suzip.repository;

import Fo.Suzip.domain.Member;
import Fo.Suzip.domain.MemberItem;
import Fo.Suzip.domain.MemberRecommendedItem;
import Fo.Suzip.domain.contentItem.Book;
import Fo.Suzip.domain.contentItem.ContentItem;
import Fo.Suzip.domain.contentItem.Movie;
import Fo.Suzip.domain.contentItem.Music;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MemberRecommendedItemRepository extends JpaRepository<MemberRecommendedItem, Long> {

}
