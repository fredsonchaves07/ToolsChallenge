package tools.challenge.domain.pagamentos.shared.cartaodecredito.error;

import tools.challenge.core.error.Error;

public final class CartaoDeCreditoError extends Error {

    private CartaoDeCreditoError(final String message) {
        super(message);
    }

    public static CartaoDeCreditoError erro(final String message) {
        return new CartaoDeCreditoError(message);
    }

    public static String typeError() {
        return Error.typeErrorClass(CartaoDeCreditoError.class);
    }
}
