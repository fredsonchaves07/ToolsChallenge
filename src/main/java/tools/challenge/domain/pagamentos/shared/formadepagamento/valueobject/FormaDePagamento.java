package tools.challenge.domain.pagamentos.shared.formadepagamento.valueobject;

import tools.challenge.core.validator.Validator;
import tools.challenge.core.valueobject.ValueObject;
import tools.challenge.domain.pagamentos.shared.formadepagamento.error.FormaDePagamentoError;

public record FormaDePagamento(TipoFormaDePagamento tipoFormaDePagamento,
                               int quantidadeParcelas) implements ValueObject {

    public static FormaDePagamento criaFormaDePagamento(
            final TipoFormaDePagamento tipoFormaDePagamento, final int quantidadeParcelas) {
        return new FormaDePagamento(tipoFormaDePagamento, quantidadeParcelas).validate();
    }

    @Override
    public FormaDePagamento validate() {
        Validator.create(FormaDePagamentoError::erro)
                .rule(this::tipoFormaDePagamentoEInvalido,
                        "Forma de pagamento deve ser do tipo AVISTA, " +
                                "PARCELADO LOJA ou PARCELADO EMISSOR.")
                .rule(this::quantidadeDeParcelasEMenorIgualZero,
                        "Forma de pagamento deve possuir quantidade de parcelas maior que 0.")
                .validate();
        return this;
    }

    private boolean tipoFormaDePagamentoEInvalido() {
        return tipoFormaDePagamento == null;
    }

    private boolean quantidadeDeParcelasEMenorIgualZero() {
        return quantidadeParcelas <= 0;
    }
}
