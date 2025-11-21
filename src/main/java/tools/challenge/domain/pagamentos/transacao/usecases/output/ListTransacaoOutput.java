package tools.challenge.domain.pagamentos.transacao.usecases.output;

import tools.challenge.core.usecases.ValueOutput;
import tools.challenge.domain.pagamentos.transacao.entities.Transacao;

import java.util.List;

public record ListTransacaoOutput(List<TransacaoOutput> transacoes) implements ValueOutput {

    public static ListTransacaoOutput criaOutput(final List<Transacao> transacoes) {
        return new ListTransacaoOutput(transacoes.stream().map(TransacaoOutput::criaOutput).toList());
    }
}
