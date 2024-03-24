package Fo.Suzip.converter;

import Fo.Suzip.domain.Member;
import Fo.Suzip.jwt.JwtUtil;
import Fo.Suzip.web.dto.GeneratedToken;
import Fo.Suzip.web.dto.MemberRequestDTO;
import Fo.Suzip.web.dto.MemberResponseDTO;

import java.util.ArrayList;

public class MemberConverter {

    public static GeneratedToken toJoinResult(Member member, JwtUtil jwtUtil){
        return jwtUtil.generateToken(member.getEmail(), member.getUserRole());
    }

    public static Member toMember(MemberRequestDTO.JoinDto request){


        return Member.builder()
                .email(request.getEmail())
                .userRole(request.getRole())
                .provider(request.getProvider())
                .build();
    }
}
