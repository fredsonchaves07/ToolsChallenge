package tools.challenge.domain.pagamentos.transacao.usecases.pagamento;

import tools.challenge.core.usecases.ValueOutput;
import tools.challenge.domain.pagamentos.transacao.entities.Transacao;

public record TransacaoOutput(
        String id,
        String numeroCartao,
        DescricaoOperacaoTransacaoOutput descricaoOperacao,
        FormaDePagamentoOutput formaPagamento) implements ValueOutput {

    public static TransacaoOutput criaInput(final Transacao transacao) {
        final FormaDePagamentoOutput formaDePagamentoOutput = FormaDePagamentoOutput
                .criaOutput(transacao.formaDePagamento());
        final DescricaoOperacaoTransacaoOutput descricaoOperacaoTransacaoOutput = DescricaoOperacaoTransacaoOutput
                .criaOutput(transacao.descricaoOperacao());
        return new TransacaoOutput(transacao.id().toString(),
                transacao.cartaoDeCredito().numero(),
                descricaoOperacaoTransacaoOutput,
                formaDePagamentoOutput);
    }
}
