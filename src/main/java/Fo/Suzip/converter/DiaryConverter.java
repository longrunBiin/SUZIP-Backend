package Fo.Suzip.converter;

import Fo.Suzip.domain.Diary;
import Fo.Suzip.domain.DiaryEmotion;
import Fo.Suzip.domain.Member;
import Fo.Suzip.web.dto.contentDTO.ContentResponseDTO;
import Fo.Suzip.web.dto.diaryDTO.DiaryDTO;
import Fo.Suzip.web.dto.diaryDTO.DiaryRequestDTO;
import Fo.Suzip.web.dto.diaryDTO.DiaryResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DiaryConverter {


    public static DiaryResponseDTO.CreateResponseDTO toCreateResultDTO(Diary diary) {
        return DiaryResponseDTO.CreateResponseDTO.builder()
                .diaryId(diary.getId())
                .title(diary.getTitle())
                .content(diary.getContent())
                .memberId(diary.getMember().getId())
                .createdAt(LocalDate.now())
                .updatedAt(LocalDate.now())
                .imageUrl(diary.getImage())
                .emotions(diary.getDiaryEmotion())
                .build();
    }

    public static Diary toDiary(Member member, DiaryRequestDTO.CreateRequestDTO request, String imageUrl, DiaryEmotion emotion) {
        return Diary.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .member(member)
                .image(imageUrl)
                .diaryEmotion(emotion)
                .build();
    }

    public static DiaryResponseDTO.UpdateResponseDTO toUpdateResponseDTO(Diary diary) {
        return DiaryResponseDTO.UpdateResponseDTO.builder()
                .diaryId(diary.getId())
                .title(diary.getTitle())
                .content(diary.getContent())
                .updatedAt(LocalDate.now())
                .build();

    }

    public static DiaryResponseDTO.SearchResponseDTO toSearchResponseDTO(Diary diary) {
        String emotion = diary.getDiaryEmotion().toString();
        String color = diary.getDiaryEmotion().getColor();

        return DiaryResponseDTO.SearchResponseDTO.builder()
                .title(diary.getTitle())
                .content(diary.getContent())
                .emotion(emotion)
                .color(color)
                .image(diary.getImage())
                .build();
    }

    public static DiaryResponseDTO.findAllDiaryResponseDto toFindAllDiaryResultListDTO(Page<Diary> diaries) {
        List<DiaryResponseDTO.SearchResponseDTO> searchResponseDTOS = diaries.getContent().stream()
                .map(DiaryConverter::toSearchResponseDTO)
                .collect(Collectors.toList());

        return DiaryResponseDTO.findAllDiaryResponseDto.builder()
                .isLast(diaries.isLast())
                .isFirst(diaries.isFirst())
                .totalPage(diaries.getTotalPages())
                .totalElements(diaries.getTotalElements())
                .listSize(searchResponseDTOS.size())
                .diaryList(searchResponseDTOS)
                .build();
    }


    public DiaryDTO entityToDto(Diary diary) {
        return DiaryDTO.builder()
                .title(diary.getTitle())
                .content(diary.getContent())
                .build();
    }

    public DiaryResponseDTO.CreateResponseDTO diaryToCreateResponseDTO(Diary diary) {
        return DiaryResponseDTO.CreateResponseDTO.builder()
                .title(diary.getTitle())
                .content(diary.getContent())
                .build();
    }

    public DiaryResponseDTO.UpdateResponseDTO diaryToUpdateResponseDTO(Diary diary) {
        return DiaryResponseDTO.UpdateResponseDTO.builder()
                .diaryId(diary.getId())
                .title(diary.getTitle())
                .content(diary.getContent())

                .build();
    }

//    public DiaryResponseDTO.SearchResponseDTO diaryToSearchResponseDTO(Diary diary) {
//        return DiaryResponseDTO.SearchResponseDTO.builder()
//                .title(diary.getTitle())
//                .emotion(diary.getEmotion())
//
//                .build();
//    }

}
