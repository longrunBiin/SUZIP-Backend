package Fo.Suzip.apiPayload.exception.handler;

import Fo.Suzip.apiPayload.code.ErrorCode;
import Fo.Suzip.apiPayload.exception.GeneralException;

public class EmotionHandler extends GeneralException {

    public EmotionHandler(ErrorCode errorCode) {
        super(errorCode);
    }
}
