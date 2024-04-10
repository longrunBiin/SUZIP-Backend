package Fo.Suzip.service.DiaryService;

import Fo.Suzip.domain.Diary;
import Fo.Suzip.web.dto.diaryDTO.DiaryDTO;
import java.util.List;

import Fo.Suzip.web.dto.diaryDTO.DiaryRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true) // 읽기 전용 트랜잭션 설정
public interface DiaryService {

    Diary addDiary(DiaryRequestDTO.CreateRequestDTO request); //일기 생성
    Diary updateDiary(Long diaryId, DiaryRequestDTO.UpdateRequestDTO request); //일기 수정

    Diary deleteDiary(Long diaryId); //일기 삭제
    
    Diary searchDiary(String title, String content); // 일기 검색



    List<DiaryDTO> getAllDiaries();

    Diary getDiary(Long diaryId, String userName);

    Page<Diary> getDiaryList(String userName, Integer page);
}
