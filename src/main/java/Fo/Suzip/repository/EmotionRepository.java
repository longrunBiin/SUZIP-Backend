package Fo.Suzip.repository;

import Fo.Suzip.domain.Diary;
import Fo.Suzip.domain.DiaryEmotion;
import Fo.Suzip.domain.Emotions;
import Fo.Suzip.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface EmotionRepository extends JpaRepository<DiaryEmotion, Long> {

    Optional<DiaryEmotion> findByEmotion(Emotions emotion);

    @Query("select d.emotion from Diary d where d.id = :diaryId")
    Optional<Emotions> findEmotionById(Long diaryId);

    @Query("select d from Diary d where d.id = :id and d.emotion = :emotions")
    Page<Diary> findHappyByMember(Long id, PageRequest pageRequest, Emotions emotions);

    @Query("select d from DiaryEmotion d where d.emotion = :emotion")
    Optional<DiaryEmotion> findInfoByEmotions(Emotions emotion);
}
