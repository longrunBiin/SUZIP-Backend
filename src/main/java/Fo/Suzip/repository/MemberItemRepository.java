package Fo.Suzip.repository;

import Fo.Suzip.domain.Member;
import Fo.Suzip.domain.MemberItem;
import Fo.Suzip.domain.contentItem.Book;
import Fo.Suzip.domain.contentItem.ContentItem;
import Fo.Suzip.domain.contentItem.Movie;
import Fo.Suzip.domain.contentItem.Music;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemberItemRepository extends JpaRepository<MemberItem, Long> {

    void deleteByContentItemAndMember(ContentItem content, Member member);


    @Query("select b from Member m join m.memberItemList mi " +
            "join mi.contentItem b " +
            "where m.id = :memberId and mi.contentItem.dType = :dtype")
    Page<Book> findAllBookByMember(Long memberId, PageRequest pageRequest, String dtype);

    @Query("select mo from Member m join m.memberItemList mi " +
            " join mi.contentItem mo " +
            "where m.id = :memberId and mi.contentItem.dType = :dtype")
    Page<Movie> findAllMovieByMember(Long memberId, PageRequest pageRequest, String dtype);

    @Query("select mu from Member m join m.memberItemList mi " +
            " join mi.contentItem mu " +
            "where m.id = :memberId and mi.contentItem.dType = :dtype")
    Page<Music> findAllMusicByMember(Long memberId, PageRequest pageRequest, String dtype);

    List<MemberItem> findAllByMember(Member member);
    Optional<MemberItem> findByMemberAndContentItem_Id(Member member, Long contentItemId);
}
