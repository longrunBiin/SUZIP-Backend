package Fo.Suzip.service;

import Fo.Suzip.apiPayload.code.status.ErrorStatus;
import Fo.Suzip.apiPayload.exception.handler.MemberHandler;
import Fo.Suzip.converter.MemberConverter;
import Fo.Suzip.domain.Member;
import Fo.Suzip.jwt.JwtUtil;
import Fo.Suzip.domain.RefreshToken;
import Fo.Suzip.repository.MemberRepository;
import Fo.Suzip.repository.RefreshTokenRepository;
import Fo.Suzip.web.dto.GeneratedToken;
import Fo.Suzip.web.dto.MemberRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

//@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtUtil jwtUtil;

    public Member findMemberById(String userName) {
        return memberRepository.findMemberByUserName(userName)
                .orElseThrow(() -> new MemberHandler(ErrorStatus._MEMBER_NOT_FOUND));
    }

    public Optional<Member> findByEmail(String email, String provider) {
        return memberRepository.findByEmailAndProvider(email, provider);
    }

    @Transactional
    public Member updateMemberById(MemberRequestDTO.updateMemberInfoDto request, String userName) {
        Member member = memberRepository.findMemberByUserName(userName)
                .orElseThrow(() -> new MemberHandler(ErrorStatus._MEMBER_NOT_FOUND));

        member.updateName(request.getName());
        member.updateProfileImage(request.getProfileImage());
//        log.info("[fix] 멤버 정보를 수정했습니다.");
        return memberRepository.save(member);
    }

}
