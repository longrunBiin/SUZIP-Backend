package Fo.Suzip.service.DiaryService;

import Fo.Suzip.converter.DiaryConverter;
import Fo.Suzip.web.dto.DiaryDTO;
import Fo.Suzip.domain.Diary;
import Fo.Suzip.repository.DiaryRepository;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
//@RequiredArgsConstructor
@Transactional
public class DiaryServiceImpl implements DiaryService{

    private final DiaryRepository diaryRepository;
    private final DiaryConverter diaryConverter;

    public DiaryServiceImpl(DiaryRepository diaryRepository, DiaryConverter diaryConverter) {
        this.diaryRepository = diaryRepository;
        this.diaryConverter = diaryConverter;
    }

    @Override
    public DiaryDTO createDiary(DiaryDTO diaryDto) {
        // DTO를 Entity로 변환하고 저장 후, 다시 DTO로 변환하여 반환
        Diary diary = diaryConverter.entityToDiary(diaryRTO);
        Diary savedDiary = diaryRepository.save(diary);
        return diaryConverter.diaryToCreateResponseDTO(savedDiary);
    }

    @Override
    public DiaryDTO updateDiary(Long diaryId, DiaryDTO diaryDto) {
        // ID로 일기를 찾고, 내용을 업데이트 한 후 저장
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new RuntimeException("Diary not found"));

        diary = diaryRepository.save(diary);
        return diaryConverter.diaryToUpdateResponseDTO(diary);
    }

    @Override
    public void deleteDiary(Long diaryId) {
        diaryRepository.deleteById(diaryId);
    }

    @Override
    public DiaryDTO getDiaryById(Long diaryId) {
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new RuntimeException("Diary not found"));
        return diaryConverter.diaryToCreateResponseDTO(diary);
    }

    @Override
    public List<DiaryDTO> getAllDiaries() {
        List<Diary> diaries = diaryRepository.findALL();
        return diaries.stream()
                .map(diaryConverter::diaryToCreateResponseDTO)
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

    }

    private Diary convertToEntity(DiaryDTO diaryDto) {

    }
}
