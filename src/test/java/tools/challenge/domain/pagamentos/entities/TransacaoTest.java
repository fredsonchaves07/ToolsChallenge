//package tools.challenge.domain.pagamentos.entities;
//
//
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.AssertionsKt.assertNotNull;
//
//public class TransacaoTest {
//
//    @Test
//    void deveSerPossivelCriarUmaTransacao() {
//        final TransacaoStatus status = TransacaoStatus.AUTORIZADO;
//        final CartaoDeCredito cartaoDeCredito = criaCartaoDeCredito();
//        final DescricaoOperacao descricaoOperacao = criaDescricaoOperacao();
//        final FormaDePagamento formaDePagamento = criaFormaDePagamento();
//        final Transacao transacao = Transacao.criaTransacao()
//                .cartaoDeCredito(cartaoDeCredito)
//                .descricaoOperacao(descricaoOperacao)
//                .status(status)
//                .formaDePagamento(formaDePagamento);
//        assertNotNull(transacao);
//        assertNotNull(transacao.id());
//        assertEquals(status, transacao.status());
//        assertEquals(cartaoDeCredito, transacao.cartaoDeCredito());
//        assertEquals(descricaoOperacao, transacao.descricaoOperacao());
//        assertEquals(formaDePagamento, transacao.formaDePagamento());
//    }
//}
