package tools.challenge.factories;

import io.restassured.http.ContentType;
import tools.challenge.api.core.error.ApiError;
import tools.challenge.domain.pagamentos.transacao.usecases.input.TransacaoInput;
import tools.challenge.domain.pagamentos.transacao.usecases.output.TransacaoOutput;

import static io.restassured.RestAssured.given;

public class ApiFactoryTest {

    public static TransacaoOutput responseDeCriacaoDePagamento(final TransacaoInput input) {
        return given()
                .contentType(ContentType.JSON)
                .when()
                .body(input)
                .post("/pagamentos")
                .then()
                .statusCode(200)
                .extract()
                .response()
                .as(TransacaoOutput.class);
    }

    public static TransacaoOutput responseDeCriacaoDeEstorno(final TransacaoInput input) {
        return given()
                .contentType(ContentType.JSON)
                .when()
                .body(input)
                .post("/estornos")
                .then()
                .statusCode(200)
                .extract()
                .response()
                .as(TransacaoOutput.class);
    }

    public static ApiError responseDeCriacaoDePagamentoComErro(final TransacaoInput input) {
        return given()
                .contentType(ContentType.JSON)
                .when()
                .body(input)
                .post("/pagamentos")
                .then()
                .statusCode(400)
                .extract()
                .response()
                .as(ApiError.class);
    }

    public static ApiError responseDeCriacaoDeEstornoComErro(final TransacaoInput input) {
        return given()
                .contentType(ContentType.JSON)
                .when()
                .body(input)
                .post("/estornos")
                .then()
                .statusCode(400)
                .extract()
                .response()
                .as(ApiError.class);
    }
}
