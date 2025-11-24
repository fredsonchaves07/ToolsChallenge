package tools.challenge.domain.pagamentos.shared.codigoautorizacao;

import tools.challenge.core.utils.CodigoGenerator;
import tools.challenge.core.valueobject.ValueObject;

import java.util.Objects;

public class CodigoNSU implements ValueObject {

    private final String valor;

    private static final int LENGTH = 10;

    private CodigoNSU(final String valor) {
        this.valor = valor;
    }

    public static CodigoNSU of(final String valor) {
        return new CodigoNSU(valor);
    }

    public static CodigoNSU of() {
        return new CodigoNSU(CodigoGenerator.generateNumericCode(LENGTH));
    }

    public String valor() {
        return valor;
    }

    @Override
    public String toString() {
        return valor;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        final CodigoNSU codigoNSU = (CodigoNSU) o;
        return Objects.equals(valor, codigoNSU.valor);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(valor);
    }
}
