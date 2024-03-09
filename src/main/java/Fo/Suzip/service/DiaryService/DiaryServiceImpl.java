package Fo.Suzip.service.DiaryService;

import Fo.Suzip.web.dto.DiaryDTO;
import Fo.Suzip.domain.Diary;
import Fo.Suzip.repository.DiaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DiaryServiceImpl implements DiaryService{

    private final DiaryRepository diaryRepository;

    @Override
    public DiaryDTO createDiary(DiaryDTO diaryDto) {
        // DTO를 Entity로 변환하고 저장 후, 다시 DTO로 변환하여 반환
        Diary diary = diaryRepository.save(convertToEntity(diaryDto));
        return convertToDto(diary);
    }

    @Override
    public DiaryDTO updateDiary(Long diaryId, DiaryDTO diaryDto) {
        // ID로 일기를 찾고, 내용을 업데이트 한 후 저장
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new RuntimeException("Diary not found"));
        // diary의 내용을 diaryDto의 값으로 업데이트 하는 로직 필요
        diary = diaryRepository.save(diary);
        return convertToDto(diary);
    }

    @Override
    public void deleteDiary(Long diaryId) {
        diaryRepository.deleteById(diaryId);
    }

    @Override
    public DiaryDTO getDiaryById(Long diaryId) {
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new RuntimeException("Diary not found"));
        return convertToDto(diary);
    }

    @Override
    public List<DiaryDTO> getAllDiaries() {
        return diaryRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<DiaryDTO> searchDiaries(String title, String content, String tag) {
        // 검색 로직 구현, 예시는 단순화되어 있습니다.
        return diaryRepository.findByTitleContainingOrContentContaining(title, content).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private DiaryDTO convertToDto(Diary diary) {
        // Diary 엔티티를 DiaryDTO로 변환하는 메서드를 구현
    }

    private Diary convertToEntity(DiaryDTO diaryDto) {
        // DiaryDTO를 Diary 엔티티로 변환하는 메서드를 구현
    }
}
