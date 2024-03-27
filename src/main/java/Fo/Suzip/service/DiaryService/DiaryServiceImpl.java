package Fo.Suzip.service.DiaryService;

import Fo.Suzip.apiPayload.ApiResponse;
import Fo.Suzip.apiPayload.code.status.SuccessStatus;
import Fo.Suzip.converter.DiaryConverter;
import Fo.Suzip.domain.Member;
import Fo.Suzip.repository.MemberRepository;
import Fo.Suzip.web.dto.DiaryDTO;
import Fo.Suzip.domain.Diary;
import Fo.Suzip.repository.DiaryRepository;
import Fo.Suzip.web.dto.DiaryRequestDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class DiaryServiceImpl implements DiaryService{

    private final DiaryRepository diaryRepository;
    private final MemberRepository memberRepository;
    private final DiaryConverter diaryConverter;

    @Autowired
    public DiaryServiceImpl(DiaryRepository diaryRepository, MemberRepository memberRepository, DiaryConverter diaryConverter) {
        this.diaryRepository = diaryRepository;
        this.memberRepository = memberRepository;
        this.diaryConverter = diaryConverter;
    }

    @Transactional(readOnly = false)
    public Diary addDiary(DiaryRequestDTO.CreateRequestDTO request) {
        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new RuntimeException("Member not found"));

        Diary newDiary = DiaryConverter.toDiary(member, request);

        return diaryRepository.save(newDiary);
    }

    @Transactional(readOnly = false)
    @Override
    public Diary updateDiary(Long diaryId, DiaryRequestDTO.UpdateRequestDTO request) {
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new EntityNotFoundException("Diary not found with id " + diaryId));

        diary.setTitle(request.getTitle());
        diary.setContent(request.getContent());

        return diaryRepository.save(diary);
    }

    @Transactional(readOnly = false)
    @Override
    public Diary deleteDiary(Long diaryId) {
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new EntityNotFoundException("Diary not found with id " + diaryId));

        diaryRepository.delete(diary);

        return null;
    }

    @Transactional(readOnly = false)
    @Override
    public Diary searchDiary(String title, String content) {
        return null;
    }



    @Override
    public DiaryDTO getDiaryById(Long diaryId) {
        return null;
    }

    @Override
    public List<DiaryDTO> getAllDiaries() {
        return null;
    }


//    @Override
//    public DiaryDTO updateDiary(Long diaryId, DiaryDTO diaryDto) {
//        // ID로 일기를 찾고, 내용을 업데이트 한 후 저장
//        Diary diary = diaryRepository.findById(diaryId)
//                .orElseThrow(() -> new RuntimeException("Diary not found"));
//
//        diary = diaryRepository.save(diary);
//        return diaryConverter.diaryToUpdateResponseDTO(diary);
//    }
//
//    @Override
//    public void deleteDiary(Long diaryId) {
//        diaryRepository.deleteById(diaryId);
//    }
//
//    @Override
//    public DiaryDTO getDiaryById(Long diaryId) {
//        Diary diary = diaryRepository.findById(diaryId)
//                .orElseThrow(() -> new RuntimeException("Diary not found"));
//        return diaryConverter.diaryToCreateResponseDTO(diary);
//    }
//
//    @Override
//    public List<DiaryDTO> getAllDiaries() {
//        List<Diary> diaries = diaryRepository.findALL();
//        return diaries.stream()
//                .map(diaryConverter::diaryToCreateResponseDTO)
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public List<DiaryDTO> searchDiaries(String title, String content, String tag) {
//        // 검색 로직 구현
//        return diaryRepository.findByTitleContainingOrContentContaining(title, content).stream()
//                .map(this::convertToDto)
//                .collect(Collectors.toList());
//    }
//
//    private DiaryDTO convertToDto(Diary diary) {
//
//    }
//
//    private Diary convertToEntity(DiaryDTO diaryDto) {
//
//    }
}
