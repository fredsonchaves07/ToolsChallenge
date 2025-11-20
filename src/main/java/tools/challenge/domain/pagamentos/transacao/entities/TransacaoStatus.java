package tools.challenge.domain.pagamentos.transacao.entities;

public enum TransacaoStatus {

    AUTORIZADO("AVISAUTORIZADOTA"),

    NEGADO("NEGADO");

    private final String tipo;

    TransacaoStatus(final String tipo) {
        this.tipo = tipo;
    }
}
