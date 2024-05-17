package Fo.Suzip.service.DiaryService;

import Fo.Suzip.apiPayload.code.status.ErrorStatus;
import Fo.Suzip.apiPayload.exception.handler.ContentHandler;
import Fo.Suzip.apiPayload.exception.handler.DiaryHandler;
import Fo.Suzip.apiPayload.exception.handler.EmotionHandler;
import Fo.Suzip.apiPayload.exception.handler.MemberHandler;
import Fo.Suzip.aws.s3.AmazonS3Manager;
import Fo.Suzip.converter.DiaryConverter;
import Fo.Suzip.domain.*;
import Fo.Suzip.domain.contentItem.Book;
import Fo.Suzip.domain.contentItem.Movie;
import Fo.Suzip.domain.contentItem.Music;
import Fo.Suzip.repository.*;
import Fo.Suzip.web.dto.diaryDTO.DiaryDTO;
import Fo.Suzip.web.dto.diaryDTO.DiaryRequestDTO;
import Fo.Suzip.web.dto.diaryDTO.DiaryResponseDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DiaryServiceImpl implements DiaryService{

    private final DiaryRepository diaryRepository;
    private final MemberRepository memberRepository;
    private final UuidRepository uuidRepository;
    private final EmotionRepository emotionRepository;
    private final ContentRepository contentRepository;
    private final AmazonS3Manager s3Manager;



    @Transactional
    public Diary addDiary(DiaryRequestDTO.CreateRequestDTO request, String userName, MultipartFile file, DiaryResponseDTO.EmotionResponseDto emotionResponse) {
        Member member = memberRepository.findMemberByUserName(userName)
                .orElseThrow(() -> new MemberHandler(ErrorStatus._MEMBER_NOT_FOUND));
        String uuid = UUID.randomUUID().toString();
        Uuid savedUuid = uuidRepository.save(Uuid.builder()
                .uuid(uuid).build());

        String url = null;
        if (file != null) {
            url = s3Manager.uploadFile(s3Manager.generateDiaryKeyName(savedUuid), file);
        }

        String response = emotionResponse.getEmotion();
        Emotions diaryEmotion = switch (response) {
            case "불안" -> Emotions.ANXIETY;
            case "슬픔" -> Emotions.SADNESS;
            case "기쁨" -> Emotions.HAPPY;
            case "분노" -> Emotions.ANGER;
            case "상처" -> Emotions.HURT;
            default -> null;
        };

        String sentence = emotionResponse.getSentence();

        DiaryEmotion emotion = emotionRepository.findByEmotion(diaryEmotion)
                .orElseThrow(() -> new EmotionHandler(ErrorStatus._EMOTION_NOT_FOUND));

        Diary newDiary = DiaryConverter.toDiary(member, request, url, emotion, sentence);

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
        return diary;
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
    public Page<Diary> getDiaryList(String userName, Integer page, String sortOrder, String searchQuery) {
        Member member = memberRepository.findMemberByUserName(userName)
                .orElseThrow(() -> new MemberHandler(ErrorStatus._MEMBER_NOT_FOUND));

        PageRequest pageRequest = PageRequest.of(page, 5);

        if (sortOrder.equals("최신순")) {
            if (searchQuery != null && !searchQuery.isEmpty()) {
                return diaryRepository.findAllByMemberAndSearchQuery(member.getId(), searchQuery, pageRequest);
            } else {
                return diaryRepository.findAllByMember(member.getId(), pageRequest);
            }
        }else{
            if (searchQuery != null && !searchQuery.isEmpty()) {
                return diaryRepository.findAllAscByMemberAndSearchQuery(member.getId(), searchQuery, pageRequest);
            } else {
                return diaryRepository.findAllAscByMember(member.getId(), pageRequest);
            }
        }
    }



    @Override
    public Diary searchDiaries(String userName, String title, String content, String tag, Integer page) {
        Member member = memberRepository.findMemberByUserName(userName)
                .orElseThrow(() -> new MemberHandler(ErrorStatus._MEMBER_NOT_FOUND));

//        PageRequest pageRequest = PageRequest.of(page, 5);
        return diaryRepository.findAllByMemberAndTitle(member.getId(), title);
    }

    @Override
    public DiaryResponseDTO.EmotionResponseDto getAnalyzeResult(String userName, Diary diary, String emotion) {
        Book bookByDiaryId = contentRepository.findBookByDiaryId(diary.getId())
                .orElseThrow(() -> new ContentHandler(ErrorStatus._BOOK_NOT_FOUND));
        Movie movieByDiaryId = contentRepository.findMovieByDiaryId(diary.getId())
                .orElseThrow(() -> new ContentHandler(ErrorStatus._MOVIE_NOT_FOUND));
        Music musicByDiaryId = contentRepository.findMusicByDiaryId(diary.getId())
                .orElseThrow(() -> new ContentHandler(ErrorStatus._MUSIC_NOT_FOUND));

        DiaryResponseDTO.BookDto bookDto = DiaryConverter.toBookDto(bookByDiaryId);
        DiaryResponseDTO.MovieDto movieDto = DiaryConverter.toMovieDto(movieByDiaryId);
        DiaryResponseDTO.MusicDto musicDto = DiaryConverter.toMusicDto(musicByDiaryId);

        DiaryResponseDTO.RecommendationsDto recommendationsDto = DiaryConverter.toRecommendationsDto(musicDto, bookDto, movieDto);


        return DiaryConverter.toEmotionResponseDto(recommendationsDto, diary.getSentence(), emotion);
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
