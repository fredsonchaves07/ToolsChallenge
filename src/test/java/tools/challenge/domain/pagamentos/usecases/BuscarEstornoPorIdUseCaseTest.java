package tools.challenge.domain.pagamentos.usecases;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tools.challenge.core.either.Either;
import tools.challenge.core.error.Error;
import tools.challenge.core.usecases.EmptyValueOutput;
import tools.challenge.core.usecases.ValueOutput;
import tools.challenge.domain.pagamentos.transacao.entities.Transacao;
import tools.challenge.domain.pagamentos.transacao.entities.TransacaoID;
import tools.challenge.domain.pagamentos.transacao.entities.TransacaoStatus;
import tools.challenge.domain.pagamentos.transacao.repository.TransacaoRepository;
import tools.challenge.domain.pagamentos.transacao.usecases.estorno.BuscarEstornoPorIdUseCase;
import tools.challenge.domain.pagamentos.transacao.usecases.input.TransacaoIdInput;
import tools.challenge.domain.pagamentos.transacao.usecases.output.TransacaoOutput;

import static org.junit.jupiter.api.Assertions.*;
import static tools.challenge.factories.ObjectFactoryTest.criaTransacaoDeEstorno;

@QuarkusTest
public class BuscarEstornoPorIdUseCaseTest {

    @Inject
    TransacaoRepository repository;

    @Inject
    BuscarEstornoPorIdUseCase useCase;

    private Transacao transacao;

    @BeforeEach
    void setUp() {
        transacao = criaTransacaoDeEstorno();
        repository.save(transacao);
    }

    @Test
    void deveSerPossivelBuscarUmEstornoPorIdDaTransacao() {
        final TransacaoIdInput input = TransacaoIdInput.criaInput(transacao.id());
        final Either<Error, ValueOutput> output = useCase.execute(input);
        assertNotNull(output);
        assertTrue(output.isSuccess());
        assertNotNull(((TransacaoOutput) output.getSuccess()).id());
        assertNotNull(((TransacaoOutput) output.getSuccess()).descricaoOperacao().nsu());
        assertEquals(transacao.descricaoOperacao().valor(), ((TransacaoOutput) output.getSuccess()).descricaoOperacao().valor());
        assertEquals(transacao.descricaoOperacao().dataHora(), ((TransacaoOutput) output.getSuccess()).descricaoOperacao().dataHora());
        assertEquals(transacao.descricaoOperacao().estabelecimento(), ((TransacaoOutput) output.getSuccess()).descricaoOperacao().estabelecimento());
        assertEquals(TransacaoStatus.NEGADO.toString(), ((TransacaoOutput) output.getSuccess()).descricaoOperacao().status());
        assertEquals(transacao.formaDePagamento().tipoFormaDePagamento().toString(), ((TransacaoOutput) output.getSuccess()).formaPagamento().tipo());
        assertEquals(transacao.formaDePagamento().quantidadeParcelas(), ((TransacaoOutput) output.getSuccess()).formaPagamento().parcelas());
    }

    @Test
    void deveSerPossivelBuscarUmEstornoVazioSeForInformadoUmIdNaoExistenteNaBase() {
        final TransacaoIdInput input = TransacaoIdInput.criaInput(TransacaoID.of());
        final Either<Error, ValueOutput> output = useCase.execute(input);
        assertNotNull(output);
        assertTrue(output.isSuccess());
        assertInstanceOf(EmptyValueOutput.class, output.getSuccess());
    }
}
