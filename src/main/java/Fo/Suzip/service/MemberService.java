package Fo.Suzip.service;

import Fo.Suzip.apiPayload.code.status.ErrorStatus;
import Fo.Suzip.apiPayload.exception.handler.MemberHandler;
import Fo.Suzip.aws.s3.AmazonS3Manager;
import Fo.Suzip.domain.Member;
import Fo.Suzip.domain.RefreshToken;
import Fo.Suzip.domain.Uuid;
import Fo.Suzip.jwt.JwtUtil;
import Fo.Suzip.repository.MemberRepository;
import Fo.Suzip.repository.RefreshTokenRepository;
import Fo.Suzip.repository.UuidRepository;
import Fo.Suzip.web.dto.memberDTO.MemberRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.UUID;

//@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final UuidRepository uuidRepository;
    private final AmazonS3Manager s3Manager;

    public Member findMemberById(String userName) {
        return memberRepository.findMemberByUserName(userName)
                .orElseThrow(() -> new MemberHandler(ErrorStatus._MEMBER_NOT_FOUND));
    }

    public Optional<Member> findByEmail(String email, String provider) {
        return memberRepository.findByEmailAndProvider(email, provider);
    }

    @Transactional
    public Member updateMemberById(MemberRequestDTO.updateMemberInfoDto request, String userName, MultipartFile file) {
        Member member = memberRepository.findMemberByUserName(userName)
                .orElseThrow(() -> new MemberHandler(ErrorStatus._MEMBER_NOT_FOUND));

        String uuid = UUID.randomUUID().toString();
        Uuid savedUuid = uuidRepository.save(Uuid.builder()
                .uuid(uuid).build());
        String url = null;
        if (file != null) {
            url = s3Manager.uploadFile(s3Manager.generateDiaryKeyName(savedUuid), file);
        }

        member.updateName(request.getName());
        member.updateProfileImage(url);
//        log.info("[fix] 멤버 정보를 수정했습니다.");
        return member;
    }
    @Transactional
    public void deleteUser(String userName) {
        Member member = memberRepository.findMemberByUserName(userName)
                .orElseThrow(()-> new MemberHandler(ErrorStatus._MEMBER_NOT_FOUND));

        memberRepository.delete(member);
    }
}
