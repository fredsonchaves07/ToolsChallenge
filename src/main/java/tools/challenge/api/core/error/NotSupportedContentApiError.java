package tools.challenge.api.core.error;

import tools.challenge.core.error.Error;

public class NotSupportedContentApiError extends Error {

    private NotSupportedContentApiError(final String message) {
        super(message);
    }

    public static NotSupportedContentApiError error() {
        return new NotSupportedContentApiError("O tipo de conteúdo não é suportado.");
    }

    public static String typeError() {
        return Error.typeErrorClass(NotSupportedContentApiError.class);
    }
}
