package tools.challenge.api.core.controller;

import static io.netty.handler.codec.http.HttpResponseStatus.*;

public interface ApiStatusCode {

    static int internalServerError() {
        return INTERNAL_SERVER_ERROR.code();
    }

    static int badRequestError() {
        return BAD_REQUEST.code();
    }

    static int ok() {
        return OK.code();
    }

    static int unsuportedMediaTypeError() {
        return UNSUPPORTED_MEDIA_TYPE.code();
    }

    static int notFoundError() {
        return NOT_FOUND.code();
    }

    static int methodNotAllowedError() {
        return METHOD_NOT_ALLOWED.code();
    }
}
