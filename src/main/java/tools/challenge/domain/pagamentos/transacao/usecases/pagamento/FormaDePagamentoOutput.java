package tools.challenge.domain.pagamentos.transacao.usecases.pagamento;

import tools.challenge.domain.pagamentos.shared.formadepagamento.valueobject.FormaDePagamento;

public record FormaDePagamentoOutput(String tipo, int parcelas) {

    public static FormaDePagamentoOutput criaOutput(final FormaDePagamento formaDePagamento) {
        return new FormaDePagamentoOutput(formaDePagamento.tipoFormaDePagamento().toString(), formaDePagamento.quantidadeParcelas());
    }
}
