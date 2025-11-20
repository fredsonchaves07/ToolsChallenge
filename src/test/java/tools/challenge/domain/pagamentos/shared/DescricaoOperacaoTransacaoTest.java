package tools.challenge.domain.pagamentos.shared;

import org.junit.jupiter.api.Test;
import tools.challenge.domain.pagamentos.shared.descricaooperacao.valueobject.DescricaoOperacao;
import tools.challenge.domain.pagamentos.shared.descricaooperacao.valueobject.DescricaoOperacaoTransacao;
import tools.challenge.domain.pagamentos.transacao.entities.TransacaoStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static tools.challenge.factories.ObjectValueFactoryTest.criaDescricaoOperacao;

public class DescricaoOperacaoTransacaoTest {

    @Test
    void deveSerPossivelCriarUmaDescricaoDeOperacaoDeTransacao() {
        final DescricaoOperacao descricaoOperacao = criaDescricaoOperacao();
        final TransacaoStatus status = TransacaoStatus.AUTORIZADO;
        final DescricaoOperacaoTransacao descricaoOperacaoTransacao = DescricaoOperacaoTransacao
                .of(descricaoOperacao, status);
        assertNotNull(descricaoOperacaoTransacao);
        assertNotNull(descricaoOperacaoTransacao.nsu());
        assertNotNull(descricaoOperacaoTransacao.codigoAutorizacao());
        assertEquals(descricaoOperacao.estabelecimento(), descricaoOperacaoTransacao.estabelecimento());
        assertEquals(descricaoOperacao.valor(), descricaoOperacaoTransacao.valor());
        assertEquals(descricaoOperacao.dataHoraFormatada(), descricaoOperacaoTransacao.dataHora());
    }
}
