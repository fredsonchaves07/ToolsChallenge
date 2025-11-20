package tools.challenge.domain.pagamentos.shared.descricaooperacao.valueobject;

import tools.challenge.core.valueobject.ValueObject;
import tools.challenge.domain.pagamentos.shared.codigoautorizacao.CodigoAutorizacao;
import tools.challenge.domain.pagamentos.shared.codigoautorizacao.CodigoNSU;
import tools.challenge.domain.pagamentos.transacao.entities.TransacaoStatus;

import java.math.BigDecimal;

public final class DescricaoOperacaoTransacao implements ValueObject {

    final CodigoNSU codigoNsu;

    final CodigoAutorizacao codigoAutorizacao;

    final TransacaoStatus status;

    final DescricaoOperacao descricaoOperacao;

    private DescricaoOperacaoTransacao(
            final CodigoNSU nsu, final CodigoAutorizacao codigoAutorizacao,
            final TransacaoStatus status, final DescricaoOperacao descricaoOperacao) {
        this.descricaoOperacao = descricaoOperacao;
        this.codigoNsu = nsu;
        this.codigoAutorizacao = codigoAutorizacao;
        this.status = status;
    }

    public static DescricaoOperacaoTransacao of(final DescricaoOperacao descricaoOperacao, final TransacaoStatus status) {
        if (descricaoOperacao == null) return null;
        return new DescricaoOperacaoTransacao(CodigoNSU.of(), CodigoAutorizacao.of(), status, descricaoOperacao);
    }

    public DescricaoOperacao descricaoOperacao() {
        return descricaoOperacao;
    }

    public String estabelecimento() {
        return descricaoOperacao.estabelecimento();
    }

    public BigDecimal valor() {
        return descricaoOperacao.valor();
    }

    public String dataHora() {
        return descricaoOperacao.dataHoraFormatada();
    }

    public CodigoNSU nsu() {
        return codigoNsu;
    }

    public CodigoAutorizacao codigoAutorizacao() {
        return codigoAutorizacao;
    }

    public TransacaoStatus status() {
        return status;
    }
}
