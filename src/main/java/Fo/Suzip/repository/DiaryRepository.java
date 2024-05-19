package Fo.Suzip.repository;

import Fo.Suzip.domain.Diary;
import Fo.Suzip.domain.DiaryEmotion;
import Fo.Suzip.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface DiaryRepository extends JpaRepository<Diary, Long> {
    Optional<Diary> findByIdAndMember(Long diaryId, Member member);



    @Query("select d from Member m join m.diaryList d where m.id = :id " +
            "and lower(d.title) LIKE lower(concat('%', :title, '%')) order by d.date desc")
    Diary findAllByMemberAndTitle(Long id, String title);
    @Query("select d from Diary d where d.member.id = :id order by d.date desc")
    Page<Diary> findAllByMember(@Param("id") Long id, PageRequest pageRequest);

    @Query("select d from Diary d where d.member.id = :id and (lower(d.title) like lower(concat('%', :searchQuery, '%')) or lower(d.content) like lower(concat('%', :searchQuery, '%'))) order by d.date desc")
    Page<Diary> findAllByMemberAndSearchQuery(@Param("id") Long id, @Param("searchQuery") String searchQuery, PageRequest pageRequest);

    @Query("select d from Diary d where d.member.id = :id order by d.date asc")
    Page<Diary> findAllAscByMember(@Param("id") Long id, PageRequest pageRequest);

    @Query("select d from Diary d where d.member.id = :id and (lower(d.title) like lower(concat('%', :searchQuery, '%')) or lower(d.content) like lower(concat('%', :searchQuery, '%'))) order by d.date asc")
    Page<Diary> findAllAscByMemberAndSearchQuery(@Param("id") Long id, @Param("searchQuery") String searchQuery, PageRequest pageRequest);
}


