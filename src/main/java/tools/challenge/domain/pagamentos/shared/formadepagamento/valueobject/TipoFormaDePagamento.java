package tools.challenge.domain.pagamentos.shared.formadepagamento.valueobject;

public enum TipoFormaDePagamento {

    AVISTA("AVISTA"),

    PARCELADO_LOJA("PARCELADO LOJA"),

    PARCELADO_EMISSOR("PARCELADO_EMISSOR");

    private final String tipo;

    TipoFormaDePagamento(final String tipo) {
        this.tipo = tipo;
    }
}
