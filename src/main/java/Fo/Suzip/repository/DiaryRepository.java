package Fo.Suzip.repository;

import Fo.Suzip.domain.Diary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiaryRepository extends JpaRepository<Diary, Long> {
}
