package Fo.Suzip.repository;

import Fo.Suzip.domain.Diary;
import Fo.Suzip.domain.DiaryEmotion;
import Fo.Suzip.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DiaryEmotionRepository extends JpaRepository<DiaryEmotion, Long> {

}
