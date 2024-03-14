package Fo.Suzip.apiPayload;

import Fo.Suzip.apiPayload.code.BaseCode;
import Fo.Suzip.apiPayload.code.status.SuccessStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

//@Getter
//@AllArgsConstructor
//@JsonPropertyOrder({"isSuccess", "code", "message", "result"})
//public class ApiResponse<T> {
//
//    @JsonProperty("isSuccess")
//    private final Boolean isSuccess;
//    private final String code;
//    private final String message;
//    @JsonInclude(JsonInclude.Include.NON_NULL)
//    private T result;
//
//
//    // 성공한 경우 응답 생성
//
//    public static <T> ApiResponse<T> onSuccess(T result){
//        return new ApiResponse<>(true, SuccessStatus._OK.getCode() , SuccessStatus._OK.getMessage(), result);
//    }
//
//    public static <T> ApiResponse<T> of(BaseCode code, T result){
//            return new ApiResponse<>(true, code.getReasonHttpStatus().getCode() , code.getReasonHttpStatus().getMessage(), result);
//    }
//
//
//    // 실패한 경우 응답 생성
//    public static <T> ApiResponse<T> onFailure(String code, String message, T data){
//        return new ApiResponse<>(false, code, message, data);
//    }

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"isSuccess", "code", "message", "result"})
public class ApiResponse<T> {

    @JsonProperty("isSuccess")
    private final Boolean isSuccess;
    private final String code;
    private final String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T result;


    private final static int SUCCESS = 200;
    private final static int NOT_FOUND = 400;
    private final static int FAILED = 500;
    private final static String SUCCESS_MESSAGE = "SUCCESS";
    private final static String NOT_FOUND_MESSAGE = "NOT FOUND";
    private final static String FAILED_MESSAGE = "서버에서 오류가 발생하였습니다.";
    private final static String INVALID_ACCESS_TOKEN = "Invalid access token.";
    private final static String INVALID_REFRESH_TOKEN = "Invalid refresh token.";
    private final static String NOT_EXPIRED_TOKEN_YET = "Not expired token yet.";

    private final ApiResponseHeader header;
    private final Map<String, T> body;

    public static <T> ApiResponse<T> success(String name, T body) {
        Map<String, T> map = new HashMap<>();
        map.put(name, body);

        return new ApiResponse(new ApiResponseHeader(SUCCESS, SUCCESS_MESSAGE), map);
    }

    public static <T> ApiResponse<T> fail() {
        return new ApiResponse(new ApiResponseHeader(FAILED, FAILED_MESSAGE), null);
    }

    public static <T> ApiResponse<T> invalidAccessToken() {
        return new ApiResponse(new ApiResponseHeader(FAILED, INVALID_ACCESS_TOKEN), null);
    }

    public static <T> ApiResponse<T> invalidRefreshToken() {
        return new ApiResponse(new ApiResponseHeader(FAILED, INVALID_REFRESH_TOKEN), null);
    }

    public static <T> ApiResponse<T> notExpiredTokenYet() {
        return new ApiResponse(new ApiResponseHeader(FAILED, NOT_EXPIRED_TOKEN_YET), null);
    }

    public static <T> ApiResponse<T> onSuccess(T result){
        return new ApiResponse<>(true, SuccessStatus._OK.getCode() , SuccessStatus._OK.getMessage(), result);
    }

    public static <T> ApiResponse<T> of(BaseCode code, T result){
            return new ApiResponse<>(true, code.getReasonHttpStatus().getCode() , code.getReasonHttpStatus().getMessage(), result);
    }


    // 실패한 경우 응답 생성
    public static <T> ApiResponse<T> onFailure(String code, String message, T data){
        return new ApiResponse<>(false, code, message, data);
    }
}
