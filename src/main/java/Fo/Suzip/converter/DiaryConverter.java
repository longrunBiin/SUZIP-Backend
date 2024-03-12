package Fo.Suzip.converter;

import Fo.Suzip.domain.Diary;
import Fo.Suzip.domain.Member;
import Fo.Suzip.web.dto.DiaryDTO;
import Fo.Suzip.web.dto.DiaryRequestDTO;
import Fo.Suzip.web.dto.DiaryResponseDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class DiaryConverter {


    public static DiaryResponseDTO.CreateResponseDTO toCreateResultDTO(Diary diary) {
        return DiaryResponseDTO.CreateResponseDTO.builder()
                .diaryId(diary.getId())
                .title(diary.getTitle())
                .content(diary.getContent())
                .memberId(diary.getMember().getId())
                .date(LocalDate.now())
                .build();
    }

    public static Diary toDiary(Member member, DiaryRequestDTO.CreateRequestDTO request) {
        return Diary.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .member(member)
                .build();
    }

    public static DiaryResponseDTO.UpdateResponseDTO toUpdateResponseDTO(Diary diary) {
        return DiaryResponseDTO.UpdateResponseDTO.builder()
                .diaryId(diary.getId())
                .title(diary.getTitle())
                .content(diary.getContent())
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
