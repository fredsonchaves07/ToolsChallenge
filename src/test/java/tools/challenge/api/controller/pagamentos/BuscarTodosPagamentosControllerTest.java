package tools.challenge.api.controller.pagamentos;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import tools.challenge.domain.pagamentos.transacao.usecases.input.TransacaoInput;
import tools.challenge.domain.pagamentos.transacao.usecases.output.ListTransacaoOutput;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.wildfly.common.Assert.assertNotNull;
import static tools.challenge.factories.ApiFactoryTest.responseDeCriacaoDePagamento;
import static tools.challenge.factories.ObjectFactoryTest.criaTransacaoDePagamento;

@QuarkusTest
public class BuscarTodosPagamentosControllerTest {

    @Test
    void deveSerPossivelBuscarPagamentos() {
        responseDeCriacaoDePagamento(TransacaoInput.from(criaTransacaoDePagamento()));
        responseDeCriacaoDePagamento(TransacaoInput.from(criaTransacaoDePagamento()));
        responseDeCriacaoDePagamento(TransacaoInput.from(criaTransacaoDePagamento()));
        final ListTransacaoOutput output = given()
                .when()
                .get("/pagamentos")
                .then()
                .extract()
                .response()
                .as(ListTransacaoOutput.class);
        assertNotNull(output);
        assertEquals(3, output.transacoes().size());
    }

    @Test
    void deveSerPossivelBuscarTodosPagamentosVazio() {
        final ListTransacaoOutput output = given()
                .when()
                .get("/pagamentos")
                .then()
                .extract()
                .response()
                .as(ListTransacaoOutput.class);
        assertNotNull(output);
        assertTrue(output.transacoes().isEmpty());
    }
}
