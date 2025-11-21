package tools.challenge.domain.pagamentos.transacao.usecases.pagamento;

import tools.challenge.core.usecases.ValueInput;
import tools.challenge.domain.pagamentos.transacao.entities.TransacaoID;

public record TransacaoIdInput(String id) implements ValueInput {

    public static TransacaoIdInput criaInput(final TransacaoID transacaoId) {
        return new TransacaoIdInput(transacaoId.toString());
    }

    public TransacaoID toTransacaoId() {
        return TransacaoID.of(id);
    }
}
