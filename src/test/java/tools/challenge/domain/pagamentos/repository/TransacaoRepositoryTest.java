package tools.challenge.domain.pagamentos.repository;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tools.challenge.domain.pagamentos.transacao.entities.Transacao;
import tools.challenge.domain.pagamentos.transacao.entities.TransacaoID;
import tools.challenge.domain.pagamentos.transacao.repository.TransacaoRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static tools.challenge.factories.ObjectFactoryTest.criaTransacaoDeEstorno;
import static tools.challenge.factories.ObjectFactoryTest.criaTransacaoDePagamento;

@QuarkusTest
public class TransacaoRepositoryTest {

    @Inject
    private TransacaoRepository repository;

    @BeforeEach
    void tearDown() {
        repository.deleteAll();
    }

    @Test
    void deveSerPossivelCadastrarTransacaoDePagamento() {
        final Transacao transacao = criaTransacaoDePagamento();
        assertDoesNotThrow(() -> repository.save(transacao));
        assertEquals(Long.valueOf(1), repository.count());
    }

    @Test
    void deveSerPossivelExcluirTransacaoDeEstorno() {
        final Transacao transacao = criaTransacaoDeEstorno();
        assertDoesNotThrow(() -> repository.save(transacao));
        assertEquals(Long.valueOf(1), repository.count());
    }

    @Test
    void deveSerPossivelConsultarUmaTransacaoDePagamentoPorId() {
        final Transacao novaTransacao = criaTransacaoDePagamento();
        repository.save(novaTransacao);
        final Transacao transacao = repository.findById(novaTransacao.id()).orElseThrow();
        assertNotNull(transacao.id());
        assertEquals(novaTransacao.tipoTransacao(), transacao.tipoTransacao());
        assertEquals(novaTransacao.descricaoOperacao(), transacao.descricaoOperacao());
        assertEquals(novaTransacao.formaDePagamento(), transacao.formaDePagamento());
        assertEquals(novaTransacao.cartaoDeCredito(), transacao.cartaoDeCredito());
        assertEquals(novaTransacao.createdAt(), transacao.createdAt());
        assertEquals(novaTransacao.updatedAt(), transacao.updatedAt());
    }

    @Test
    void deveSerPossivelConsultarUmaTransacaoDeEstornoPorId() {
        final Transacao novaTransacao = criaTransacaoDeEstorno();
        repository.save(novaTransacao);
        final Transacao transacao = repository.findById(novaTransacao.id()).orElseThrow();
        assertNotNull(transacao.id());
        assertEquals(novaTransacao.tipoTransacao(), transacao.tipoTransacao());
        assertEquals(novaTransacao.descricaoOperacao(), transacao.descricaoOperacao());
        assertEquals(novaTransacao.formaDePagamento(), transacao.formaDePagamento());
        assertEquals(novaTransacao.cartaoDeCredito(), transacao.cartaoDeCredito());
        assertEquals(novaTransacao.createdAt(), transacao.createdAt());
        assertEquals(novaTransacao.updatedAt(), transacao.updatedAt());
    }

    @Test
    void deveSerPossivelConsultarTodosTransacaoDePagamento() {
        repository.save(criaTransacaoDePagamento());
        repository.save(criaTransacaoDePagamento());
        repository.save(criaTransacaoDePagamento());
        final List<Transacao> pagamentos = repository.transacoesPagamento();
        assertFalse(pagamentos.isEmpty());
        assertEquals(3, repository.count());
    }

    @Test
    void deveSerPossivelConsultarTodosTransacaoDeEstorno() {
        repository.save(criaTransacaoDeEstorno());
        repository.save(criaTransacaoDeEstorno());
        repository.save(criaTransacaoDeEstorno());
        final List<Transacao> estornos = repository.transacoesEstorno();
        assertFalse(estornos.isEmpty());
        assertEquals(3, repository.count());
    }

    @Test
    void deveSerPossivelDeletarUmaTransacaoDePagamento() {
        final Transacao transacaoDePagamento = criaTransacaoDePagamento();
        repository.save(transacaoDePagamento);
        repository.delete(transacaoDePagamento);
        assertTrue(repository.findAll().isEmpty());
        assertEquals(0, repository.count());
    }

    @Test
    void deveSerPossivelDeletarUmaTransacaoDeEstorno() {
        final Transacao transacaoDeEstorno = criaTransacaoDeEstorno();
        repository.save(transacaoDeEstorno);
        repository.delete(transacaoDeEstorno);
        assertTrue(repository.findAll().isEmpty());
        assertEquals(0, repository.count());
    }

    @Test
    void deveSerPossivelDeletarTodasTransacoes() {
        repository.save(criaTransacaoDePagamento());
        repository.save(criaTransacaoDeEstorno());
        repository.deleteAll();
        assertTrue(repository.findAll().isEmpty());
        assertEquals(0, repository.count());
    }

    @Test
    void deveSerPossivelConsultarUmaTransacaoVaziaSeForFornecidoUmIdNaoExistente() {
        final Transacao novaTransacao = criaTransacaoDeEstorno();
        repository.save(novaTransacao);
        assertTrue(repository.findById(TransacaoID.of()).isEmpty());
    }

    @Test
    void deveSerPossivelConsultarUmaTransacaoVaziaSeForFornecidoUmIdNulo() {
        final Transacao novaTransacao = criaTransacaoDePagamento();
        repository.save(novaTransacao);
        assertTrue(repository.findById(null).isEmpty());
    }
}
