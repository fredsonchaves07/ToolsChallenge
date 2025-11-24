package tools.challenge.domain.pagamentos.transacao.usecases.input;

import tools.challenge.core.usecases.ValueInput;
import tools.challenge.domain.pagamentos.transacao.entities.Transacao;

public record TransacaoInput(
        String numeroCartao,
        DescricaoOperacaoTransacaoInput descricaoOperacao,
        FormaDePagamentoInput formaPagamento) implements ValueInput {

    public static TransacaoInput criaInput(final String numeroCartao,
                                           final DescricaoOperacaoTransacaoInput descricaoOperacaoTransacaoInput,
                                           final FormaDePagamentoInput formaDePagamentoInput) {
        return new TransacaoInput(numeroCartao, descricaoOperacaoTransacaoInput, formaDePagamentoInput);
    }

    public static TransacaoInput from(final Transacao transacao) {
        final DescricaoOperacaoTransacaoInput descricaoOperacaoTransacaoInput = DescricaoOperacaoTransacaoInput
                .from(transacao.descricaoOperacao());
        final FormaDePagamentoInput formaDePagamentoInput = FormaDePagamentoInput
                .from(transacao.formaDePagamento());
        return TransacaoInput
                .criaInput(transacao.cartaoDeCredito().toString(), descricaoOperacaoTransacaoInput, formaDePagamentoInput);
    }
}
