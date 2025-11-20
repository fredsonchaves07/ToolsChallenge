package tools.challenge.domain.pagamentos.transacao.usecases.pagamento;

import tools.challenge.domain.pagamentos.shared.descricaooperacao.valueobject.DescricaoOperacaoTransacao;

import java.math.BigDecimal;

public record DescricaoOperacaoTransacaoOutput(BigDecimal valor,
                                               String estabelecimento,
                                               String dataHora,
                                               String nsu,
                                               String codigoAutorizacao,
                                               String status) {

    public static DescricaoOperacaoTransacaoOutput criaOutput(final DescricaoOperacaoTransacao descricaoOperacaoTransacao) {
        return new DescricaoOperacaoTransacaoOutput(
                descricaoOperacaoTransacao.valor(),
                descricaoOperacaoTransacao.estabelecimento(),
                descricaoOperacaoTransacao.dataHora(),
                descricaoOperacaoTransacao.nsu().toString(),
                descricaoOperacaoTransacao.codigoAutorizacao().toString(),
                descricaoOperacaoTransacao.status().toString());
    }
}
