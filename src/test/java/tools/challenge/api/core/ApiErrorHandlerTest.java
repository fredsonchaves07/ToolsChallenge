package tools.challenge.api.core;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import tools.challenge.api.core.error.ApiError;
import tools.challenge.api.core.error.MethodNotAllowedApiError;
import tools.challenge.api.core.error.NotFoundApiError;
import tools.challenge.api.core.error.NotSupportedContentApiError;

import static io.netty.handler.codec.http.HttpResponseStatus.*;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
public class ApiErrorHandlerTest {

    @Test
    void deveRetornarErro405QuandoNaoPermitirRequisicaoDeUmaRota() {
        final String mensagemErro = "Método de requisição não aceita.";
        final String typeError = MethodNotAllowedApiError.typeError();
        final String detalheErro = "https://tools.challenge/suporte/errors/" + typeError;
        final String path = "/pagamentos";
        final ApiError error = given()
                .contentType(ContentType.JSON)
                .when()
                .delete(path)
                .then()
                .statusCode(405)
                .extract()
                .response()
                .as(ApiError.class);
        assertNotNull(error);
        assertNotNull(error.timestamp());
        assertEquals(METHOD_NOT_ALLOWED.code(), error.statusCode());
        assertEquals(path, error.path());
        assertEquals(typeError, error.type());
        assertEquals(mensagemErro, error.message());
        assertEquals(detalheErro, error.detail());
    }

    @Test
    void deveRetornarErro404QuandoNaoExistirRotaOuEndpoint() {
        final String mensagemErro = "Não foi encontrado a rota ou endpoint da requisição solicitada.";
        final String typeError = NotFoundApiError.typeError();
        final String detalheErro = "https://tools.challenge/suporte/errors/" + typeError;
        final String path = "/ressarcimento";
        final ApiError error = given()
                .contentType(ContentType.JSON)
                .when()
                .put(path)
                .then()
                .statusCode(404)
                .extract()
                .response()
                .as(ApiError.class);
        assertNotNull(error);
        assertNotNull(error.timestamp());
        assertEquals(NOT_FOUND.code(), error.statusCode());
        assertEquals(typeError, error.type());
        assertEquals(path, error.path());
        assertEquals(mensagemErro, error.message());
        assertEquals(detalheErro, error.detail());
    }

    @Test
    void deveRetornarErro415QuandoMediaTypeNaoSuportado() {
        final String mensagemErro = "O tipo de conteúdo não é suportado.";
        final String typeError = NotSupportedContentApiError.typeError();
        final String detalheErro = "https://tools.challenge/suporte/errors/" + typeError;
        final String path = "/pagamentos";
        final ApiError error = given()
                .contentType(ContentType.TEXT)
                .when()
                .post(path)
                .then()
                .statusCode(415)
                .extract()
                .response()
                .as(ApiError.class);
        assertNotNull(error);
        assertNotNull(error.timestamp());
        assertEquals(UNSUPPORTED_MEDIA_TYPE.code(), error.statusCode());
        assertEquals(typeError, error.type());
        assertEquals(path, error.path());
        assertEquals(mensagemErro, error.message());
        assertEquals(detalheErro, error.detail());
    }
}
