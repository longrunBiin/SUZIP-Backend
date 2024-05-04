package Fo.Suzip.apiPayload.exception.handler;

import Fo.Suzip.apiPayload.code.ErrorCode;
import Fo.Suzip.apiPayload.exception.GeneralException;

public class TokenHandler extends GeneralException {

    public TokenHandler(ErrorCode errorCode) {
        super(errorCode);
    }
}
