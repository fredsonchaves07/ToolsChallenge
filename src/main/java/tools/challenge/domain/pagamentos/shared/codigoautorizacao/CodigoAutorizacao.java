package tools.challenge.domain.pagamentos.shared.codigoautorizacao;

import tools.challenge.core.utils.CodigoGenerator;
import tools.challenge.core.valueobject.ValueObject;

public class CodigoAutorizacao implements ValueObject {

    private final String valor;

    private static final int LENGTH = 9;

    private CodigoAutorizacao(final String valor) {
        this.valor = valor;
    }

    public static CodigoAutorizacao of(final String valor) {
        return new CodigoAutorizacao(valor);
    }

    public static CodigoAutorizacao of() {
        return new CodigoAutorizacao(CodigoGenerator.generateNumericCode(LENGTH));
    }

    public String valor() {
        return valor;
    }

    @Override
    public String toString() {
        return valor;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        final CodigoAutorizacao that = (CodigoAutorizacao) o;
        return valor.equals(that.valor);
    }

    @Override
    public int hashCode() {
        return valor.hashCode();
    }
}
