package tools.challenge.api.controller.estornos;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tools.challenge.core.usecases.EmptyValueOutput;
import tools.challenge.domain.pagamentos.transacao.entities.Transacao;
import tools.challenge.domain.pagamentos.transacao.entities.TransacaoID;
import tools.challenge.domain.pagamentos.transacao.entities.TransacaoStatus;
import tools.challenge.domain.pagamentos.transacao.repository.TransacaoRepository;
import tools.challenge.domain.pagamentos.transacao.usecases.input.TransacaoInput;
import tools.challenge.domain.pagamentos.transacao.usecases.output.TransacaoOutput;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.wildfly.common.Assert.assertNotNull;
import static tools.challenge.factories.ApiFactoryTest.responseDeCriacaoDeEstorno;
import static tools.challenge.factories.ObjectFactoryTest.criaTransacaoDeEstorno;

@QuarkusTest
public class BuscarEstornoPorIdControllerTest {

    @Inject
    private TransacaoRepository repository;

    @BeforeEach
    void tearDown() {
        repository.deleteAll();
    }

    @Test
    void deveSerPossivelBuscarUmEstornoPorIdDaTransacao() {
        final Transacao transacao = criaTransacaoDeEstorno();
        responseDeCriacaoDeEstorno(TransacaoInput.from(transacao));
        final Transacao transacaoPersistido = repository.findAll().getFirst();
        final String path = "/estornos/" + transacaoPersistido.id().toString();
        final TransacaoOutput output = given()
                .when()
                .get(path)
                .then()
                .extract()
                .response()
                .as(TransacaoOutput.class);
        assertNotNull(output);
        assertNotNull(output.descricaoOperacao().nsu());
        assertNotNull(output.descricaoOperacao().codigoAutorizacao());
        assertEquals(transacaoPersistido.id().toString(), output.id());
        assertEquals(transacao.descricaoOperacao().valor(), output.descricaoOperacao().valor());
        assertEquals(transacao.descricaoOperacao().dataHora(), output.descricaoOperacao().dataHora());
        assertEquals(transacao.descricaoOperacao().estabelecimento(), output.descricaoOperacao().estabelecimento());
        assertEquals(TransacaoStatus.NEGADO.toString(), output.descricaoOperacao().status());
        assertEquals(transacao.formaDePagamento().tipoFormaDePagamento().toString(), output.formaPagamento().tipo());
        assertEquals(transacao.formaDePagamento().quantidadeParcelas(), output.formaPagamento().parcelas());

    }

    @Test
    void deveSerPossivelBuscarTodosEstornoVazio() {
        final String path = "/estornos/" + TransacaoID.of();
        final EmptyValueOutput output = given()
                .when()
                .get(path)
                .then()
                .extract()
                .response()
                .as(EmptyValueOutput.class);
        assertEquals("", output.toString());
    }
}
