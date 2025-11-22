package tools.challenge.api.core.error;

import tools.challenge.core.error.Error;

public class MethodNotAllowedApiError extends Error {

    private MethodNotAllowedApiError(final String message) {
        super(message);
    }

    public static MethodNotAllowedApiError error() {
        return new MethodNotAllowedApiError("Método de requisição não aceita.");
    }

    public static String typeError() {
        return Error.typeErrorClass(MethodNotAllowedApiError.class);
    }
}
