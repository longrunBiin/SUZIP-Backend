package Fo.Suzip.apiPayload.exception.handler;

import Fo.Suzip.apiPayload.code.ErrorCode;
import Fo.Suzip.apiPayload.exception.GeneralException;

public class DiaryHandler extends GeneralException {

    public DiaryHandler(ErrorCode errorCode) {
        super(errorCode);
    }
}
