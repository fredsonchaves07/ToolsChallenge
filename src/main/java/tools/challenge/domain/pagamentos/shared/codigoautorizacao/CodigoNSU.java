package tools.challenge.domain.pagamentos.shared.codigoautorizacao;

import tools.challenge.core.utils.CodigoGenerator;
import tools.challenge.core.valueobject.ValueObject;

public class CodigoNSU implements ValueObject {

    private final String valor;

    private static final int LENGTH = 10;

    private CodigoNSU(final String valor) {
        this.valor = valor;
    }

    public static CodigoNSU of() {
        return new CodigoNSU(CodigoGenerator.generateNumericCode(LENGTH));
    }

    public String valor() {
        return valor;
    }
}
