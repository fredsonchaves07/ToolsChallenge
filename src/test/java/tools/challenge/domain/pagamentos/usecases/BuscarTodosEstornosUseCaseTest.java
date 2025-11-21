package tools.challenge.domain.pagamentos.usecases;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tools.challenge.core.either.Either;
import tools.challenge.core.error.Error;
import tools.challenge.domain.pagamentos.transacao.repository.TransacaoRepository;
import tools.challenge.domain.pagamentos.transacao.usecases.estorno.BuscarTodosEstornosUseCase;
import tools.challenge.domain.pagamentos.transacao.usecases.output.ListTransacaoOutput;

import static org.junit.jupiter.api.Assertions.*;
import static tools.challenge.factories.ObjectFactoryTest.criaTransacaoDeEstorno;

@QuarkusTest
public class BuscarTodosEstornosUseCaseTest {

    @Inject
    TransacaoRepository repository;

    @Inject
    BuscarTodosEstornosUseCase useCase;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
    }

    @Test
    void deveSerPossivelBuscarTodosEstornos() {
        repository.save(criaTransacaoDeEstorno());
        repository.save(criaTransacaoDeEstorno());
        repository.save(criaTransacaoDeEstorno());
        final Either<Error, ListTransacaoOutput> output = useCase.execute();
        assertNotNull(output);
        assertNotNull(output.getSuccess());
        assertFalse(output.getSuccess().transacoes().isEmpty());
        assertEquals(3, output.getSuccess().transacoes().size());
    }

    @Test
    void deveSerPossivelBuscarTodosEstornosVazioSeNaoHouverPagamentos() {
        final Either<Error, ListTransacaoOutput> output = useCase.execute();
        assertNotNull(output);
        assertNotNull(output.getSuccess());
        assertTrue(output.getSuccess().transacoes().isEmpty());
        assertEquals(0, output.getSuccess().transacoes().size());
    }
}
