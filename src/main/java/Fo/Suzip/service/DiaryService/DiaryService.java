package Fo.Suzip.service.DiaryService;

import Fo.Suzip.domain.Diary;
import Fo.Suzip.repository.DiaryRepository;
import Fo.Suzip.web.dto.DiaryDTO;
import java.util.List;

import Fo.Suzip.web.dto.DiaryRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@Service
@Transactional(readOnly = true) // 읽기 전용 트랜잭션 설정
public interface DiaryService {

    Diary addDiary(DiaryRequestDTO.CreateRequestDTO request);
    Diary updateDiary(Long diaryId, DiaryRequestDTO.UpdateRequestDTO request);

    void deleteDiary(Long diaryId);

    DiaryDTO getDiaryById(Long diaryId);

    List<DiaryDTO> getAllDiaries();

    List<DiaryDTO> searchDiaries(String title, String content, String tag);

}
