package tools.challenge.domain.pagamentos.transacao.entities;

import tools.challenge.core.entity.Identifier;

public final class TransacaoID extends Identifier {

    private TransacaoID() {
        super();
    }

    private TransacaoID(final String value) {
        super(value);
    }

    public static TransacaoID of() {
        return new TransacaoID();
    }

    public static TransacaoID of(final String value) {
        return new TransacaoID(value);
    }
}
