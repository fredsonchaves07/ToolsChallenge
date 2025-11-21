package tools.challenge.domain.pagamentos.transacao.usecases.input;

import tools.challenge.domain.pagamentos.shared.descricaooperacao.valueobject.DescricaoOperacao;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record DescricaoOperacaoTransacaoInput(BigDecimal valor, String estabelecimento, LocalDateTime dataHora) {

    public static DescricaoOperacaoTransacaoInput criaInput(
            final BigDecimal valor, final String estabelecimento, final LocalDateTime dataHora) {
        return new DescricaoOperacaoTransacaoInput(valor, estabelecimento, dataHora);
    }

    public DescricaoOperacao toAggregate() {
        return DescricaoOperacao.criaDescricaoOperacao(estabelecimento, valor, dataHora);
    }
}
