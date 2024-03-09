package Fo.Suzip.service.DiaryService;

import Fo.Suzip.repository.DiaryRepository;
import Fo.Suzip.web.dto.DiaryDTO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@Service
@Transactional(readOnly = true) // 읽기 전용 트랜잭션 설정
public interface DiaryService {

    //private final DiaryRepository diaryRepository;

    DiaryDTO createDiary(DiaryDTO diaryDto);

    DiaryDTO updateDiary(Long diaryId, DiaryDTO diaryDto);

    void deleteDiary(Long diaryId);

    DiaryDTO getDiaryById(Long diaryId);

    List<DiaryDTO> getAllDiaries();

    List<DiaryDTO> searchDiaries(String title, String content, String tag);
}
