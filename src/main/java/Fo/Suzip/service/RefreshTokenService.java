package Fo.Suzip.service;

import Fo.Suzip.apiPayload.code.status.ErrorStatus;
import Fo.Suzip.apiPayload.exception.handler.MemberHandler;
import Fo.Suzip.apiPayload.exception.handler.TokenHandler;
import Fo.Suzip.domain.Member;
import Fo.Suzip.domain.RefreshToken;
import Fo.Suzip.repository.MemberRepository;
import Fo.Suzip.repository.RefreshTokenRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {


    private final RefreshTokenRepository repository;

    @Transactional
    public void saveTokenInfo(String userName, String refreshToken, String accessToken) {
        RefreshToken token = RefreshToken.builder()
                .username(userName)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();

        if (repository.findByUsername(userName).isPresent()){
            token.updateToken(token);
        }
        else
            repository.save(token);
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
