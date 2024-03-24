package Fo.Suzip.apiPayload.exception.handler;

import Fo.Suzip.apiPayload.code.ErrorCode;
import Fo.Suzip.apiPayload.exception.GeneralException;

public class MemberHandler extends GeneralException {

    public MemberHandler(ErrorCode errorCode) {
        super(errorCode);
    }
}
