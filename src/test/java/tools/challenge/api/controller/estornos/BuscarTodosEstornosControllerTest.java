package tools.challenge.api.controller.estornos;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import tools.challenge.domain.pagamentos.transacao.usecases.input.TransacaoInput;
import tools.challenge.domain.pagamentos.transacao.usecases.output.ListTransacaoOutput;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.wildfly.common.Assert.assertNotNull;
import static tools.challenge.factories.ApiFactoryTest.responseDeCriacaoDeEstorno;
import static tools.challenge.factories.ApiFactoryTest.responseDeCriacaoDePagamento;
import static tools.challenge.factories.ObjectFactoryTest.criaTransacaoDeEstorno;
import static tools.challenge.factories.ObjectFactoryTest.criaTransacaoDePagamento;

@QuarkusTest
public class BuscarTodosEstornosControllerTest {

    @Test
    void deveSerPossivelBuscarEstornos() {
        responseDeCriacaoDeEstorno(TransacaoInput.from(criaTransacaoDeEstorno()));
        responseDeCriacaoDeEstorno(TransacaoInput.from(criaTransacaoDeEstorno()));
        responseDeCriacaoDeEstorno(TransacaoInput.from(criaTransacaoDeEstorno()));
        final ListTransacaoOutput output = given()
                .when()
                .get("/estornos")
                .then()
                .extract()
                .response()
                .as(ListTransacaoOutput.class);
        assertNotNull(output);
        assertEquals(3, output.transacoes().size());
    }

    @Test
    void deveSerPossivelBuscarTodosEstornosVazio() {
        final ListTransacaoOutput output = given()
                .when()
                .get("/estornos")
                .then()
                .extract()
                .response()
                .as(ListTransacaoOutput.class);
        assertNotNull(output);
        assertTrue(output.transacoes().isEmpty());
    }
}
