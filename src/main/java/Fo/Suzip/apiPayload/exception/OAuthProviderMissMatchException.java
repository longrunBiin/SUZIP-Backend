package Fo.Suzip.apiPayload.exception;

import javax.security.sasl.AuthenticationException;

public class OAuthProviderMissMatchException extends RuntimeException {

    public OAuthProviderMissMatchException(String message) {
        super(message);
    }
}
