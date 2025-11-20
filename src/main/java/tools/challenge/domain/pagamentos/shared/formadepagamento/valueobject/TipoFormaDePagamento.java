package tools.challenge.domain.pagamentos.shared.formadepagamento.valueobject;

import tools.challenge.core.error.Error;
import tools.challenge.domain.pagamentos.shared.formadepagamento.error.FormaDePagamentoError;

public enum TipoFormaDePagamento {

    AVISTA("AVISTA"),

    PARCELADO_LOJA("PARCELADO LOJA"),

    PARCELADO_EMISSOR("PARCELADO EMISSOR");

    private final String tipo;

    TipoFormaDePagamento(final String tipo) {
        this.tipo = tipo;
    }

    public static TipoFormaDePagamento fromString(String tipoTelefone) {
        try {
            return TipoFormaDePagamento.valueOf(tipoTelefone.toUpperCase());
        } catch (Exception exception) {
            throw error();
        }
    }

    private static Error error() {
        return FormaDePagamentoError.erro("Tipo de forma de pagamento deve ser AVISTA, PARCELADO LOJA ou PARCELADO EMISSOR.");
    }

    @Override
    public String toString() {
        return tipo;
    }
}
