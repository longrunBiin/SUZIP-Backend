package Fo.Suzip.repository;

import Fo.Suzip.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {

    // accessToken으로 RefreshToken을 찾아온다.
    Optional<RefreshToken> findByAccessToken(String accessToken);

}