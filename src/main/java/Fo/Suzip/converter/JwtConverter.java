package Fo.Suzip.converter;

import Fo.Suzip.web.dto.authDTO.GeneratedToken;

public class JwtConverter {

    public static GeneratedToken toRefreshDto(String newAccessToken, String username) {
        return GeneratedToken.builder()
                .accessToken(newAccessToken)
                .userName(username)
                .build();
    }
}
