package tools.challenge.domain.pagamentos.shared;

import org.junit.jupiter.api.Test;
import tools.challenge.domain.pagamentos.shared.codigoautorizacao.CodigoAutorizacao;
import tools.challenge.domain.pagamentos.shared.codigoautorizacao.CodigoNSU;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CodigoAutorizacaoTest {

    @Test
    void deveSerPossivelGerarCodigoNSU() {
        final CodigoNSU codigoNsu = CodigoNSU.of();
        assertNotNull(codigoNsu);
        assertNotNull(codigoNsu.valor());
        assertEquals(10, codigoNsu.valor().length());
    }

    @Test
    void deveSerPossivelGerarCodigoAutorizacao() {
        final CodigoAutorizacao codigoAutorizacao = CodigoAutorizacao.of();
        assertNotNull(codigoAutorizacao);
        assertNotNull(codigoAutorizacao.valor());
        assertEquals(9, codigoAutorizacao.valor().length());
    }
}
