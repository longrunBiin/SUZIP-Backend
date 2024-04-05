package Fo.Suzip.web.controller;

import Fo.Suzip.apiPayload.ApiResponse;
import Fo.Suzip.converter.MemberConverter;
import Fo.Suzip.domain.Member;
import Fo.Suzip.service.MemberService;
import Fo.Suzip.web.dto.memberDTO.MemberRequestDTO;
import Fo.Suzip.web.dto.memberDTO.MemberResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "마이페이지 API",description = "사용자 정보 받아오기")
    @GetMapping(value = "/")
    public ApiResponse<MemberResponseDTO.getMemberResultDto> findOne() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        Member member = memberService.findMemberById(userName);
        return ApiResponse.onSuccess(MemberConverter.getMemberResult(member));
    }

    @Operation(summary = "유저 정보 변경 API",description = "사용자 정보 변경")
    @PatchMapping(value = "/")
    public ApiResponse<MemberResponseDTO.updateMemberResultDto> fixMember(
            @RequestBody MemberRequestDTO.updateMemberInfoDto request) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        Member member = memberService.updateMemberById(request, userName);
        return ApiResponse.onSuccess(MemberConverter.updateMemberResult(member));
    }
}
