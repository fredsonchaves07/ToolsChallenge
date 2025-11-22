package tools.challenge.domain.pagamentos.transacao.usecases.input;

import tools.challenge.domain.pagamentos.shared.formadepagamento.valueobject.FormaDePagamento;
import tools.challenge.domain.pagamentos.shared.formadepagamento.valueobject.TipoFormaDePagamento;

public record FormaDePagamentoInput(String tipoFormaPagamento, int parcelas) {

    public static FormaDePagamentoInput criaInput(String tipoFormaPagamento, int parcelas) {
        return new FormaDePagamentoInput(tipoFormaPagamento, parcelas);
    }

    public FormaDePagamento toAggregate() {
        return FormaDePagamento.criaFormaDePagamento(TipoFormaDePagamento.fromString(tipoFormaPagamento), parcelas);
    }

    public static FormaDePagamentoInput from(final FormaDePagamento formaDePagamento) {
        return FormaDePagamentoInput.criaInput(
                formaDePagamento.tipoFormaDePagamento().toString(), formaDePagamento.quantidadeParcelas());
    }
}
