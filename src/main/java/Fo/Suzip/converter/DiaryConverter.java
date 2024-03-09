package Fo.Suzip.converter;

import Fo.Suzip.domain.Diary;
import Fo.Suzip.web.dto.DiaryResponseDTO;

public class DiaryConverter {

    public static DiaryResponseDTO.CreateResponseDTO createResponseDTO(Diary diary){
        return DiaryResponseDTO;

    }
}
