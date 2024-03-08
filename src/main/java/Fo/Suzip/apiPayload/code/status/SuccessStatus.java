package Fo.Suzip.apiPayload.code.status;

import Fo.Suzip.apiPayload.code.BaseCode;
import Fo.Suzip.apiPayload.code.ReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SuccessStatus implements BaseCode {

    // 가장 일반적인 응답
    _OK(HttpStatus.OK, "COMMON2001", "성공"),
    _CREATED(HttpStatus.CREATED, "COMMON2002", "새로 리소스 생성"),


    // 멤버 관련 응답
    _MEMBER_LOGIN_SUCCESS(HttpStatus.OK, "MEMBER2001", "로그인 성공");
    // ~~~ 관련 응답 ....


    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ReasonDTO getReason() {
        return ReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(true)
                .build();
    }

    @Override
    public ReasonDTO getReasonHttpStatus() {
        return ReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(true)
                .httpStatus(httpStatus)
                .build()
                ;
    }
}
