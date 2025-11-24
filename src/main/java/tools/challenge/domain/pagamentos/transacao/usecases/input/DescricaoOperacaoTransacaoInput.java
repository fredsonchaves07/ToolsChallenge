package tools.challenge.domain.pagamentos.transacao.usecases.input;

import tools.challenge.domain.pagamentos.shared.descricaooperacao.valueobject.DescricaoOperacao;
import tools.challenge.domain.pagamentos.shared.descricaooperacao.valueobject.DescricaoOperacaoTransacao;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record DescricaoOperacaoTransacaoInput(BigDecimal valor, String estabelecimento, String dataHora) {

    public static DescricaoOperacaoTransacaoInput criaInput(
            final BigDecimal valor, final String estabelecimento, final LocalDateTime dataHora) {
        if (dataHora == null) return new DescricaoOperacaoTransacaoInput(valor, estabelecimento, null);
        return new DescricaoOperacaoTransacaoInput(valor, estabelecimento, dataHora.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")).toString());
    }

    public DescricaoOperacao toAggregate() {
        if (dataHora == null) return
                DescricaoOperacao.criaDescricaoOperacao(estabelecimento, valor, null);
        return DescricaoOperacao.criaDescricaoOperacaoComFormatoDataHoraString(estabelecimento, valor, dataHora);
    }

    public static DescricaoOperacaoTransacaoInput from(final DescricaoOperacaoTransacao descricaoOperacaoTransacao) {
        return DescricaoOperacaoTransacaoInput.criaInput(
                descricaoOperacaoTransacao.valor(),
                descricaoOperacaoTransacao.estabelecimento(),
                descricaoOperacaoTransacao.descricaoOperacao().dataHora());
    }
}
