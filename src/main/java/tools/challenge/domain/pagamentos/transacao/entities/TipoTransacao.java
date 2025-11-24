package tools.challenge.domain.pagamentos.transacao.entities;

public enum TipoTransacao {

    PAGAMENTO("PAGAMENTO"),

    ESTORNO("ESTORNO");

    private final String tipo;

    TipoTransacao(final String tipo) {
        this.tipo = tipo;
    }
}
