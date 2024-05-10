package Fo.Suzip.service;

import Fo.Suzip.apiPayload.code.status.ErrorStatus;
import Fo.Suzip.apiPayload.exception.handler.MemberHandler;
import Fo.Suzip.apiPayload.exception.handler.TokenHandler;
import Fo.Suzip.domain.Member;
import Fo.Suzip.domain.RefreshToken;
import Fo.Suzip.repository.MemberRepository;
import Fo.Suzip.repository.RefreshTokenRepository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RefreshTokenService {


    private final RefreshTokenRepository repository;

    @Transactional
    public void saveTokenInfo(String userName, String refreshToken, String accessToken) {
        Optional<RefreshToken> existingToken = repository.findByUsername(userName);
        if (existingToken.isPresent()) {
            // 기존 토큰이 존재하면 정보를 업데이트합니다.
            RefreshToken token = existingToken.get();
            token.updateAccessToken(accessToken);  // 엑세스 토큰 업데이트
            token.updateRefreshToken(refreshToken);  // 리프레시 토큰 업데이트
            repository.save(token);  // 변경 사항을 저장합니다.
            System.out.println("Token updated");
        } else {
            // 새 토큰을 생성하고 저장합니다.
            RefreshToken token = RefreshToken.builder()
                    .username(userName)
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();
            repository.save(token);
            System.out.println("Token saved");
        }
    }

    @Transactional
    public boolean removeRefreshToken(String accessToken) {
        RefreshToken token = repository.findByAccessToken(accessToken)
                .orElseThrow(IllegalArgumentException::new);

        repository.delete(token);

        return true;
    }

    public RefreshToken getAccessToken(Member member) {

        return repository.findByUsername(member.getUserName())
                .orElseThrow(()->new TokenHandler(ErrorStatus._TOKEN_UNSUPPORTED));
    }
}
