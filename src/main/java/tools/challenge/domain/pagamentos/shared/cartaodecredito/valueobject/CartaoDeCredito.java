package tools.challenge.domain.pagamentos.shared.cartaodecredito.valueobject;

import tools.challenge.core.validator.Validator;
import tools.challenge.core.valueobject.ValueObject;
import tools.challenge.domain.pagamentos.shared.cartaodecredito.error.CartaoDeCreditoError;

public record CartaoDeCredito(String numero) implements ValueObject {

    public static CartaoDeCredito criaCartaoDeCredito(final String numero) {
        return new CartaoDeCredito(numero).validate();
    }

    @Override
    public CartaoDeCredito validate() {
        Validator.create(CartaoDeCreditoError::erro)
                .rule(this::numeroEInvalido, "Cartão de crédito deve possuir um número de 16 dígitos.")
                .validate();
        return this;
    }

    private boolean numeroEInvalido() {
        return numero == null || numero.isBlank() || !numero.matches("\\d{16}");
    }

    @Override
    public String toString() {
        return numero;
    }
}
