package Fo.Suzip.converter;

import Fo.Suzip.domain.Diary;
import Fo.Suzip.web.dto.DiaryRequestDTO;
import Fo.Suzip.web.dto.DiaryResponseDTO;

public class DiaryConverter {

    public Diary entityToDiary(DiaryRequestDTO.CreateRequestDTO createDTO){
        Diary diary = new Diary();
        diary.setTitle(createDTO.getTitle());
        diary.setContent(createDTO.getContent());

        return diary;
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

    public DiaryResponseDTO.SearchResponseDTO diaryToSearchResponseDTO(Diary diary) {
        return DiaryResponseDTO.SearchResponseDTO.builder()
                .title(diary.getTitle())
                .emotion(diary.getEmotion())

                .build();
    }

}
