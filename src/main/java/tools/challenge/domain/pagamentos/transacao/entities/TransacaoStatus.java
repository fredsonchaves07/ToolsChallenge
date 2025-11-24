package tools.challenge.domain.pagamentos.transacao.entities;

public enum TransacaoStatus {

    AUTORIZADO("AUTORIZADO"),

    NEGADO("NEGADO");

    private final String tipo;

    TransacaoStatus(final String tipo) {
        this.tipo = tipo;
    }
}
