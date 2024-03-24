package Fo.Suzip.service;

import Fo.Suzip.apiPayload.code.status.ErrorStatus;
import Fo.Suzip.apiPayload.exception.handler.MemberHandler;
import Fo.Suzip.converter.MemberConverter;
import Fo.Suzip.domain.Member;
import Fo.Suzip.jwt.JwtUtil;
import Fo.Suzip.jwt.RefreshToken;
import Fo.Suzip.repository.MemberRepository;
import Fo.Suzip.repository.RefreshTokenRepository;
import Fo.Suzip.web.dto.GeneratedToken;
import Fo.Suzip.web.dto.MemberRequestDTO;
import Fo.Suzip.web.dto.MemberResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtUtil jwtUtil;

    public Optional<Member> findByEmail(String email) {
        return memberRepository.findByEmail(email);

    }

    @Transactional
    public Member signup(MemberRequestDTO.JoinDto request) {
        Member member = memberRepository.findByEmail(request.getEmail())
                .orElseGet(() -> this.joinMember(request));

        log.info("[login] 계정을 찾았습니다. " + member);

        //토큰 발급
        GeneratedToken tokenDto = jwtUtil.generateToken(request.getEmail(), request.getRole());

        if(refreshTokenRepository.existsById(member.getId().toString())){
            RefreshToken refreshToken = refreshTokenRepository.findById(member.getId().toString())
                    .orElseThrow(()-> new MemberHandler(ErrorStatus._INVALID_REFRESH_TOKEN));
            refreshToken.updateAccessToken(tokenDto.getRefreshToken());
        }
        else {
            RefreshToken refreshToken = RefreshToken.builder()
                    .id(member.getId().toString())
                    .refreshToken(tokenDto.getRefreshToken())
                    .build();
            refreshTokenRepository.save(refreshToken);
        }

        return member;
    }

    @Transactional
    public Member joinMember(MemberRequestDTO.JoinDto request){
        Member newMem = MemberConverter.toMember(request);

        return memberRepository.save(newMem);
    }

}
