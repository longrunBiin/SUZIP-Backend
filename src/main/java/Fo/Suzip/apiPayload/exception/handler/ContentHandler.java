package Fo.Suzip.apiPayload.exception.handler;

import Fo.Suzip.apiPayload.code.ErrorCode;
import Fo.Suzip.apiPayload.exception.GeneralException;

public class ContentHandler extends GeneralException {

    public ContentHandler(ErrorCode errorCode) {
        super(errorCode);
    }
}
