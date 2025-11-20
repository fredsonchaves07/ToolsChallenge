package tools.challenge.domain.pagamentos.entities;


import org.junit.jupiter.api.Test;
import tools.challenge.domain.pagamentos.shared.cartaodecredito.valueobject.CartaoDeCredito;
import tools.challenge.domain.pagamentos.shared.descricaooperacao.valueobject.DescricaoOperacao;
import tools.challenge.domain.pagamentos.shared.formadepagamento.valueobject.FormaDePagamento;
import tools.challenge.domain.pagamentos.transacao.entities.Transacao;
import tools.challenge.domain.pagamentos.transacao.entities.TransacaoStatus;
import tools.challenge.domain.pagamentos.transacao.error.TransacaoError;

import static org.junit.jupiter.api.Assertions.*;
import static tools.challenge.factories.ObjectValueFactoryTest.*;

public class TransacaoTest {

    @Test
    void deveSerPossivelCriarUmaTransacaoDePagamento() {
        final CartaoDeCredito cartaoDeCredito = criaCartaoDeCredito();
        final DescricaoOperacao descricaoOperacao = criaDescricaoOperacao();
        final FormaDePagamento formaDePagamento = criaFormaDePagamentoAVista();
        final Transacao transacao = Transacao
                .criaTransacaoDePagamento(descricaoOperacao, cartaoDeCredito, formaDePagamento);
        assertNotNull(transacao);
        assertNotNull(transacao.id());
        assertNotNull(transacao.descricaoOperacao());
        assertNotNull(transacao.descricaoOperacao().nsu());
        assertNotNull(transacao.descricaoOperacao().codigoAutorizacao());
        assertEquals(cartaoDeCredito, transacao.cartaoDeCredito());
        assertEquals(formaDePagamento, transacao.formaDePagamento());
        assertEquals(descricaoOperacao.estabelecimento(), transacao.descricaoOperacao().estabelecimento());
        assertEquals(descricaoOperacao.valor(), transacao.descricaoOperacao().valor());
        assertEquals(descricaoOperacao.dataHoraFormatada(), transacao.descricaoOperacao().dataHora());
        assertEquals(TransacaoStatus.AUTORIZADO, transacao.descricaoOperacao().status());
        assertNotNull(transacao.createdAt());
        assertNotNull(transacao.updatedAt());
    }

    @Test
    void deveSerPossivelCriarUmaTransacaoDeEstorno() {
        final CartaoDeCredito cartaoDeCredito = criaCartaoDeCredito();
        final DescricaoOperacao descricaoOperacao = criaDescricaoOperacao();
        final FormaDePagamento formaDePagamento = criaFormaDePagamentoAVista();
        final Transacao transacao = Transacao
                .criaTransacaoDeEstorno(descricaoOperacao, cartaoDeCredito, formaDePagamento);
        assertNotNull(transacao);
        assertNotNull(transacao.id());
        assertNotNull(transacao.descricaoOperacao());
        assertNotNull(transacao.descricaoOperacao().nsu());
        assertNotNull(transacao.descricaoOperacao().codigoAutorizacao());
        assertEquals(cartaoDeCredito, transacao.cartaoDeCredito());
        assertEquals(formaDePagamento, transacao.formaDePagamento());
        assertEquals(descricaoOperacao.estabelecimento(), transacao.descricaoOperacao().estabelecimento());
        assertEquals(descricaoOperacao.valor(), transacao.descricaoOperacao().valor());
        assertEquals(descricaoOperacao.dataHoraFormatada(), transacao.descricaoOperacao().dataHora());
        assertEquals(TransacaoStatus.NEGADO, transacao.descricaoOperacao().status());
        assertNotNull(transacao.createdAt());
        assertNotNull(transacao.updatedAt());
    }

    @Test
    void naoDeveSerPossivelCadastrarUmaTransacaoDePagamentoComDescricaoOperacaoNulo() {
        final CartaoDeCredito cartaoDeCredito = criaCartaoDeCredito();
        final FormaDePagamento formaDePagamento = criaFormaDePagamentoAVista();
        final String mensagemDeErroEsperada = "Transação deve possuir a descrição da operação.";
        final String typeError = TransacaoError.typeError();
        final TransacaoError transacaoError = assertThrows(
                TransacaoError.class,
                () -> Transacao.criaTransacaoDePagamento(null, cartaoDeCredito, formaDePagamento));
        assertEquals(typeError, transacaoError.getTypeError());
        assertEquals(mensagemDeErroEsperada, transacaoError.getMessage());
    }

    @Test
    void naoDeveSerPossivelCadastrarUmaTransacaoDePagamentoComCartaoDeCreditoNulo() {
        final DescricaoOperacao descricaoOperacao = criaDescricaoOperacao();
        final FormaDePagamento formaDePagamento = criaFormaDePagamentoAVista();
        final String mensagemDeErroEsperada = "Transação deve possuir o número do cartão de crédito.";
        final String typeError = TransacaoError.typeError();
        final TransacaoError transacaoError = assertThrows(
                TransacaoError.class,
                () -> Transacao.criaTransacaoDePagamento(descricaoOperacao, null, formaDePagamento));
        assertEquals(typeError, transacaoError.getTypeError());
        assertEquals(mensagemDeErroEsperada, transacaoError.getMessage());
    }

    @Test
    void naoDeveSerPossivelCadastrarUmaTransacaoDePagamentoComCartaoDeFormaDePagamentoNulo() {
        final DescricaoOperacao descricaoOperacao = criaDescricaoOperacao();
        final CartaoDeCredito cartaoDeCredito = criaCartaoDeCredito();
        final String mensagemDeErroEsperada = "Transação deve possuir a forma de pagamento.";
        final String typeError = TransacaoError.typeError();
        final TransacaoError transacaoError = assertThrows(
                TransacaoError.class,
                () -> Transacao.criaTransacaoDePagamento(descricaoOperacao, cartaoDeCredito, null));
        assertEquals(typeError, transacaoError.getTypeError());
        assertEquals(mensagemDeErroEsperada, transacaoError.getMessage());
    }
}
