package Fo.Suzip.converter;

import Fo.Suzip.domain.Member;
import Fo.Suzip.jwt.JwtUtil;
import Fo.Suzip.service.OAuth2Service.OAuth2Attribute;
import Fo.Suzip.web.dto.authDTO.GeneratedToken;
import Fo.Suzip.web.dto.memberDTO.MemberResponseDTO;
import Fo.Suzip.web.dto.authDTO.SecurityUserDto;

import java.time.LocalDateTime;

public class MemberConverter {

    public static MemberResponseDTO.JoinResultDto toJoinResult(Member member, String accessToken){
        return MemberResponseDTO.JoinResultDto.builder()
                .name(member.getName())
                .email(member.getEmail())
                .accessToken(accessToken)
                .build();
    }

    public static Member toMember(OAuth2Attribute oAuth2Attribute){
        String username = oAuth2Attribute.getProvider() + " " + oAuth2Attribute.getProviderId();

        return Member.builder()
                .userName(username)
                .email(oAuth2Attribute.getEmail())
                .name(oAuth2Attribute.getName())
                .profileImage(oAuth2Attribute.getPicture())
                .userRole("ROLE_USER")
                .provider(oAuth2Attribute.getProvider())
                .build();
    }

    public static SecurityUserDto toSecurityUserDto(JwtUtil jwtUtil, String token) {
        return SecurityUserDto.builder()
                .id(jwtUtil.getUid(token))
//                .email(jwtUtil.getEmail(token))
                .role(jwtUtil.getRole(token))
                .userName(jwtUtil.getUsername(token))
                .name(jwtUtil.getSub(token))
                .build();
    }

    public static SecurityUserDto toSecurityUserDtoByAttribute(OAuth2Attribute oAuth2Attribute) {
        String username = oAuth2Attribute.getProvider() + " " + oAuth2Attribute.getProviderId();
        return SecurityUserDto.builder()
                .id(oAuth2Attribute.getProviderId())
                .userName(username)
                .name(oAuth2Attribute.getName())
                .role("ROLE_USER")
                .email(oAuth2Attribute.getEmail())
                .build();
    }

    public static MemberResponseDTO.getMemberResultDto getMemberResult(Member member) {

        return MemberResponseDTO.getMemberResultDto.builder()
                .memberId(member.getId())
                .provider(member.getProvider())
                .profileImage(member.getProfileImage())
                .userRole(member.getUserRole())
                .userName(member.getUserName())
                .name(member.getName())
                .email(member.getEmail())
                .createdAt(LocalDateTime.now())
                .build();

    }

    public static MemberResponseDTO.updateMemberResultDto updateMemberResult(Member member) {
        return MemberResponseDTO.updateMemberResultDto.builder()
                .memberId(member.getId())
                .name(member.getName())
                .profileImage(member.getProfileImage())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
