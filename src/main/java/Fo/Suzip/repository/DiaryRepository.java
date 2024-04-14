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

    @Query("select d from Member m join m.diaryList d where m.id = :id")
    Page<Diary> findAllByMember(Long id, PageRequest pageRequest);

    @Query("select d from Member m join m.diaryList d where m.id = :id " +
            "and lower(d.title) LIKE lower(concat('%', :title, '%'))")
    Page<Diary> findAllByMemberAndTitle(Long id, PageRequest pageRequest, String title);

    @Query("select d.diaryEmotion from Diary d where d.id = :diaryId")
    Optional<DiaryEmotion> findEmotionById(Long diaryId);
}
