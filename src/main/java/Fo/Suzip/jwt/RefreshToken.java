package Fo.Suzip.jwt;

import jakarta.persistence.*;
import lombok.*;


import java.io.Serializable;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@RedisHash(value = "jwtToken", timeToLive = 60 * 60 * 24 * 14)
public class RefreshToken implements Serializable {

    @Id
    private String id;

    private String accessToken;

    private String refreshToken;


    public void updateAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

}