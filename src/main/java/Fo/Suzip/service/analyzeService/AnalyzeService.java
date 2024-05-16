package Fo.Suzip.service.analyzeService;

import Fo.Suzip.web.dto.diaryDTO.DiaryRequestDTO;
import Fo.Suzip.web.dto.diaryDTO.DiaryResponseDTO;
import Fo.Suzip.web.dto.emotionDTO.EmotionResponseDto;
import org.springframework.stereotype.Service;


public interface AnalyzeService {


    DiaryResponseDTO.EmotionResponseDto analyzeDiary(DiaryRequestDTO.CreateRequestDTO request);

}
