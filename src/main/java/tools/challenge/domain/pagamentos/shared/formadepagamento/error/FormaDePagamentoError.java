package tools.challenge.domain.pagamentos.shared.formadepagamento.error;

import tools.challenge.core.error.Error;

public final class FormaDePagamentoError extends Error {

    private FormaDePagamentoError(final String message) {
        super(message);
    }

    public static FormaDePagamentoError erro(final String message) {
        return new FormaDePagamentoError(message);
    }

    public static String typeError() {
        return Error.typeErrorClass(FormaDePagamentoError.class);
    }
}
