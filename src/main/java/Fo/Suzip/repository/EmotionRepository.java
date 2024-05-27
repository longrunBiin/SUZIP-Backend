package Fo.Suzip.repository;

import Fo.Suzip.domain.Diary;
import Fo.Suzip.domain.DiaryEmotion;
import Fo.Suzip.domain.Emotions;
import Fo.Suzip.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface EmotionRepository extends JpaRepository<DiaryEmotion, Long> {

    Optional<DiaryEmotion> findByEmotion(Emotions emotion);

    @Query("select d.emotion from Diary d where d.id = :diaryId")
    Optional<Emotions> findEmotionById(Long diaryId);

    @Query("select d from Diary d where d.member.id = :memberId and d.emotion = :emotion")
    List<Diary> findHappyDiariesByMember(Long memberId, Emotions emotion);


    @Query("select d from DiaryEmotion d where d.emotion = :emotion")
    Optional<DiaryEmotion> findInfoByEmotions(Emotions emotion);

    @Query("SELECT d FROM Diary d WHERE d.member.userName = :userName AND d.date BETWEEN :startDate AND :endDate")
    List<Diary> findByMemberAndDateBetween(@Param("userName") String userName, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}

