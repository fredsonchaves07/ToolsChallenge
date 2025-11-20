package tools.challenge.domain.pagamentos.shared.descricaooperacao.error;

import tools.challenge.core.error.Error;

public final class DescricaoOperacaoError extends Error {

    private DescricaoOperacaoError(final String message) {
        super(message);
    }

    public static DescricaoOperacaoError erro(final String message) {
        return new DescricaoOperacaoError(message);
    }

    public static String typeError() {
        return Error.typeErrorClass(DescricaoOperacaoError.class);
    }
}
