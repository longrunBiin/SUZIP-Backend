package Fo.Suzip.apiPayload.exception.handler;

import Fo.Suzip.apiPayload.code.ErrorCode;
import Fo.Suzip.apiPayload.exception.GeneralException;

public class TempHandler extends GeneralException {

    public TempHandler(ErrorCode errorCode) {
        super(errorCode);
    }
}
