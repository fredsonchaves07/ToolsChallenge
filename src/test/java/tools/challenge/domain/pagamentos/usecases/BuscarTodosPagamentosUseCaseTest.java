package tools.challenge.domain.pagamentos.usecases;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tools.challenge.core.either.Either;
import tools.challenge.core.error.Error;
import tools.challenge.domain.pagamentos.transacao.repository.TransacaoRepository;
import tools.challenge.domain.pagamentos.transacao.usecases.output.ListTransacaoOutput;
import tools.challenge.domain.pagamentos.transacao.usecases.pagamento.BuscarTodosPagamentosUseCase;

import static org.junit.jupiter.api.Assertions.*;
import static tools.challenge.factories.ObjectFactoryTest.criaTransacaoDePagamento;

@QuarkusTest
public class BuscarTodosPagamentosUseCaseTest {

    @Inject
    TransacaoRepository repository;

    @Inject
    BuscarTodosPagamentosUseCase useCase;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
    }

    @Test
    void deveSerPossivelBuscarTodosPagamentos() {
        repository.save(criaTransacaoDePagamento());
        repository.save(criaTransacaoDePagamento());
        repository.save(criaTransacaoDePagamento());
        final Either<Error, ListTransacaoOutput> output = useCase.execute();
        assertNotNull(output);
        assertNotNull(output.getSuccess());
        assertFalse(output.getSuccess().transacoes().isEmpty());
        assertEquals(3, output.getSuccess().transacoes().size());
    }

    @Test
    void deveSerPossivelBuscarTodosPagamentosVazioSeNaoHouverPagamentos() {
        final Either<Error, ListTransacaoOutput> output = useCase.execute();
        assertNotNull(output);
        assertNotNull(output.getSuccess());
        assertTrue(output.getSuccess().transacoes().isEmpty());
        assertEquals(0, output.getSuccess().transacoes().size());
    }
}
