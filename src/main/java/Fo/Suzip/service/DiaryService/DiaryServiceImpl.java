package Fo.Suzip.service.DiaryService;

import Fo.Suzip.apiPayload.code.status.ErrorStatus;
import Fo.Suzip.apiPayload.exception.handler.DiaryHandler;
import Fo.Suzip.apiPayload.exception.handler.EmotionHandler;
import Fo.Suzip.apiPayload.exception.handler.MemberHandler;
import Fo.Suzip.aws.s3.AmazonS3Manager;
import Fo.Suzip.converter.DiaryConverter;
import Fo.Suzip.domain.DiaryEmotion;
import Fo.Suzip.domain.Member;
import Fo.Suzip.domain.Uuid;
import Fo.Suzip.repository.EmotionRepository;
import Fo.Suzip.repository.MemberRepository;
import Fo.Suzip.repository.UuidRepository;
import Fo.Suzip.web.dto.diaryDTO.DiaryDTO;
import Fo.Suzip.domain.Diary;
import Fo.Suzip.repository.DiaryRepository;
import Fo.Suzip.web.dto.diaryDTO.DiaryRequestDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DiaryServiceImpl implements DiaryService{

    private final DiaryRepository diaryRepository;
    private final MemberRepository memberRepository;
    private final UuidRepository uuidRepository;
    private final EmotionRepository emotionRepository;
    private final AmazonS3Manager s3Manager;



    @Transactional
    public Diary addDiary(DiaryRequestDTO.CreateRequestDTO request, String userName, MultipartFile file) {
        Member member = memberRepository.findMemberByUserName(userName)
                .orElseThrow(() -> new MemberHandler(ErrorStatus._MEMBER_NOT_FOUND));
        String uuid = UUID.randomUUID().toString();
        Uuid savedUuid = uuidRepository.save(Uuid.builder()
                .uuid(uuid).build());

        String url = null;
        if (file != null) {
            url = s3Manager.uploadFile(s3Manager.generateDiaryKeyName(savedUuid), file);
        }


        DiaryEmotion emotion = emotionRepository.findByEmotion(request.getEmotions())
                .orElseThrow(() -> new EmotionHandler(ErrorStatus._EMOTION_NOT_FOUND));

        Diary newDiary = DiaryConverter.toDiary(member, request, url, emotion);

        return diaryRepository.save(newDiary);
    }

    @Transactional
    @Override
    public Diary updateDiary(Long diaryId, DiaryRequestDTO.UpdateRequestDTO request, MultipartFile file) {
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new EntityNotFoundException("Diary not found with id " + diaryId));
        String uuid = UUID.randomUUID().toString();
        Uuid savedUuid = uuidRepository.save(Uuid.builder()
                .uuid(uuid).build());
        String url = null;
        if (file != null) {
            url = s3Manager.uploadFile(s3Manager.generateDiaryKeyName(savedUuid), file);
        }
        diary.updateDiary(request, url);
        return diaryRepository.save(diary);
    }

    @Transactional
    @Override
    public Diary deleteDiary(Long diaryId) {
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new EntityNotFoundException("Diary not found with id " + diaryId));

        diaryRepository.delete(diary);

        return null;
    }

    @Override
    public Diary getDiary(Long diaryId, String userName) {
        Member member = memberRepository.findMemberByUserName(userName)
                .orElseThrow(() -> new MemberHandler(ErrorStatus._MEMBER_NOT_FOUND));

        return diaryRepository.findByIdAndMember(diaryId, member)
                .orElseThrow(() -> new DiaryHandler(ErrorStatus._DIARY_NOT_FOUND));
    }

    @Override
    public Page<Diary> getDiaryList(String userName, Integer page) {
        Member member = memberRepository.findMemberByUserName(userName)
                .orElseThrow(() -> new MemberHandler(ErrorStatus._MEMBER_NOT_FOUND));

        PageRequest pageRequest = PageRequest.of(page, 5);
        return diaryRepository.findAllByMember(member.getId(), pageRequest);
    }

    @Override
    public Page<Diary> searchDiaries(String userName, String title, String content, String tag, Integer page) {
        Member member = memberRepository.findMemberByUserName(userName)
                .orElseThrow(() -> new MemberHandler(ErrorStatus._MEMBER_NOT_FOUND));

        PageRequest pageRequest = PageRequest.of(page, 5);
        return diaryRepository.findAllByMemberAndTitle(member.getId(), pageRequest, title);
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
