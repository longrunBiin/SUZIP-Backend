package F0.Suzip.web.controller;

import F0.Suzip.apiPayload.ApiResponse;
import F0.Suzip.converter.TempConverter;
import F0.Suzip.web.dto.TempResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/temp")
public class TestController {

    @GetMapping("/test")
    public String test() {
        return "success";
    }

    @GetMapping("/api")
    public ApiResponse<TempResponse.TempTestDTO> testAPI(){

        return ApiResponse.onSuccess(TempConverter.toTempTestDTO());
    }
}
