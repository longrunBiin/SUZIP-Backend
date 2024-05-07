package Fo.Suzip.web.controller;

import Fo.Suzip.apiPayload.ApiResponse;
import Fo.Suzip.apiPayload.code.status.SuccessStatus;
import Fo.Suzip.converter.MemberConverter;
import Fo.Suzip.domain.Member;
import Fo.Suzip.service.MemberService;
import Fo.Suzip.web.dto.memberDTO.MemberRequestDTO;
import Fo.Suzip.web.dto.memberDTO.MemberResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "마이페이지 API",description = "사용자 정보를 확인합니다")
    @GetMapping(value = "/")
    public ApiResponse<MemberResponseDTO.getMemberResultDto> findOne() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        Member member = memberService.findMemberById(userName);
        return ApiResponse.onSuccess(MemberConverter.getMemberResult(member));
    }

    @Operation(summary = "유저 정보 변경 API",description = "사용자 정보 변경합니다. 이름이랑 프로필 사진 변경가능")
    @PatchMapping(value = "/", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ApiResponse<MemberResponseDTO.updateMemberResultDto> fixMember(
            @RequestPart("request") MemberRequestDTO.updateMemberInfoDto request,
            @RequestPart(value = "file", required = false) MultipartFile file) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        Member member = memberService.updateMemberById(request, userName, file);
        return ApiResponse.onSuccess(MemberConverter.updateMemberResult(member));
    }

    @Operation(summary = "유저 탈퇴 API",description = "사용자가 탈퇴합니다. 데이터베이스에서 관련 정보 전부 삭제")
    @DeleteMapping("/")
    public ApiResponse<?> deleteUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        memberService.deleteUser(userName);
        return ApiResponse.onSuccess(SuccessStatus._OK);
    }
}
