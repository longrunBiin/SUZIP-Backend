package Fo.Suzip.apiPayload.code.status;

import Fo.Suzip.apiPayload.code.ErrorCode;
import Fo.Suzip.apiPayload.code.ErrorReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements ErrorCode {
    // 가장 일반적인 응답
    _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 에러, 관리자에게 문의 바랍니다."),
    _BAD_REQUEST(HttpStatus.BAD_REQUEST,"COMMON400","잘못된 요청입니다."),
    _UNAUTHORIZED(HttpStatus.UNAUTHORIZED,"COMMON401","인증이 필요합니다."),
    _FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403", "금지된 요청입니다."),
    _NOT_FOUND(HttpStatus.NOT_FOUND, "COMMON404", "요청을 찾을 수 없습니다."),

    // 멤버 관련 응답
    _MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBER4001", "사용자가 없습니다."),
    _NICKNAME_NOT_EXIST(HttpStatus.BAD_REQUEST, "MEMBER4002", "닉네임은 필수 입니다."),
    _MEMBER_ROLE_DOES_NOT_EXISTS(HttpStatus.BAD_REQUEST, "MEMBER4003", "사용자 role을 찾을 수 없습니다."),

    // 토큰 관련 응답
    _INVALID_ACCESS_TOKEN(HttpStatus.BAD_REQUEST, "TOKEN4001", "access token이 만료되었습니다."),
    _INVALID_REFRESH_TOKEN(HttpStatus.BAD_REQUEST, "TOKEN4002", "refresh token이 만료되었습니다."),
    _TOKEN_EXPIRED(HttpStatus.BAD_REQUEST, "TOKEN4003", "만료된 토큰입니다."),
    _TOKEN_UNSUPPORTED(HttpStatus.BAD_REQUEST, "TOKEN400e", "token unsupported."),
    _TOKEN_ILLEGAL_ARGUMENT(HttpStatus.BAD_REQUEST, "TOKEN400e", "token illegal Argument."),

    //암호화 관련 응답
    _ENCRYPTION_FAILED(HttpStatus.BAD_REQUEST, "ENCRYPT4001", "암호화 실패."),
    _DECRYPTION_FAILED(HttpStatus.BAD_REQUEST, "ENCRYPT4002", "복호화 실패."),
    _SECRET_KEY_INVALID(HttpStatus.BAD_REQUEST, "ENCRYPT4003", "시크릿키는 16자이어야 합니다."),

    //컨텐츠 관련 응답
    _BOOK_NOT_FOUND(HttpStatus.NOT_FOUND, "CONTENT4001", "책을 찾을 수 없습니다."),
    _BOOK_NOT_EXIST(HttpStatus.BAD_REQUEST, "CONTENT4002", "책이 존재하지 않습니다."),
    _MOVIE_NOT_FOUND(HttpStatus.NOT_FOUND, "CONTENT4003", "영화를 찾을 수 없습니다."),
    _MOVIE_NOT_EXIST(HttpStatus.BAD_REQUEST, "CONTENT4004", "영화가 존재하지 않습니다."),
    _MUSIC_NOT_FOUND(HttpStatus.NOT_FOUND, "CONTENT4005", "음악을 찾을 수 없습니다."),
    _MUSIC_NOT_EXIST(HttpStatus.BAD_REQUEST, "CONTENT4006", "음악이 존재하지 않습니다."),
    _CONTENT_NOT_FOUND(HttpStatus.NOT_FOUND, "CONTENT4007", "콘텐츠를 찾을 수 없습니다."),
    _CONTENT_NOT_EXIST(HttpStatus.BAD_REQUEST, "CONTENT4008", "콘텐츠를 존재하지 않습니다."),

    //일기 관련 응답
    _DIARY_NOT_FOUND(HttpStatus.NOT_FOUND, "DIARY4001", "일기를 찾을 수 없습니다."),
    _DIARY_NOT_EXIST(HttpStatus.BAD_REQUEST, "DIARY4002", "일기를 존재하지 않습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ErrorReasonDTO getReason() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .build();
    }

    @Override
    public ErrorReasonDTO getReasonHttpStatus() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .httpStatus(httpStatus)
                .build()
                ;
    }
}
