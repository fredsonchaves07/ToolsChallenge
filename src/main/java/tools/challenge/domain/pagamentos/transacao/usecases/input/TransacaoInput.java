package tools.challenge.domain.pagamentos.transacao.usecases.input;

import tools.challenge.core.usecases.ValueInput;

public record TransacaoInput(
        String numeroCartao,
        DescricaoOperacaoTransacaoInput descricaoOperacao,
        FormaDePagamentoInput formaPagamento) implements ValueInput {

    public static TransacaoInput criaInput(final String numeroCartao,
                                           final DescricaoOperacaoTransacaoInput descricaoOperacaoTransacaoInput,
                                           final FormaDePagamentoInput formaDePagamentoInput) {
        return new TransacaoInput(numeroCartao, descricaoOperacaoTransacaoInput, formaDePagamentoInput);
    }
}
