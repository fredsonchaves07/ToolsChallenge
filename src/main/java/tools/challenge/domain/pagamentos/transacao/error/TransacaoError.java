package tools.challenge.domain.pagamentos.transacao.error;

import tools.challenge.core.error.Error;

public final class TransacaoError extends Error {

    private TransacaoError(final String message) {
        super(message);
    }

    public static TransacaoError erro(final String message) {
        return new TransacaoError(message);
    }

    public static String typeError() {
        return Error.typeErrorClass(TransacaoError.class);
    }
}
