package tools.challenge.api.core.error;

import tools.challenge.core.error.Error;

public class NotFoundApiError extends Error {

    private NotFoundApiError(final String message) {
        super(message);
    }

    public static NotFoundApiError error() {
        return new NotFoundApiError("Não foi encontrado a rota ou endpoint da requisição solicitada.");
    }

    public static String typeError() {
        return Error.typeErrorClass(NotFoundApiError.class);
    }
}
