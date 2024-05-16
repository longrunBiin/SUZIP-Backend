package Fo.Suzip.service.analyzeService;

import Fo.Suzip.apiPayload.ApiResponse;
import Fo.Suzip.apiPayload.code.status.ErrorStatus;
import Fo.Suzip.web.dto.diaryDTO.DiaryRequestDTO;
import Fo.Suzip.web.dto.diaryDTO.DiaryResponseDTO;
import Fo.Suzip.web.dto.emotionDTO.EmotionResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AnalyzeServiceImpl implements AnalyzeService {


    @Override
    public DiaryResponseDTO.EmotionResponseDto analyzeDiary(DiaryRequestDTO.CreateRequestDTO request) {
        WebClient webClient = WebClient.builder()
                .baseUrl("http://mysuzip.com:5000")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
        String response = null;
        try {
            // FastAPI 서버로 전송할 데이터 객체 생성
            Map<String, String> fastApiRequest = new HashMap<>();

            fastApiRequest.put("sentence", request.getContent());

            response = webClient.post()
                    .uri("/predict/")
                    //.header("Authorization", "Bearer " + accessToken) // 필요 시 헤더에 토큰 추가
                    .body(Mono.just(fastApiRequest), Map.class)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            System.out.println("Emotion Response: " + response);

            ObjectMapper objectMapper = new ObjectMapper();


            // 변환된 DTO 객체를 사용
            return objectMapper.readValue(response, DiaryResponseDTO.EmotionResponseDto.class);

        } catch (WebClientResponseException e) {
            // 예외 처리
            System.err.println("Error occurred while calling FastAPI: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error occurred while processing response: " + e.getMessage());
        }

        return null;
    }
}
