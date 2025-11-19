package tools.challenge.api.core;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class ApiHealthTest {

    private Response responseHealthCheck() {
        return given()
                .when()
                .get("/")
                .then()
                .extract()
                .response();
    }

    @Test
    public void deveSerPossivelConsultarHealthCheckDaApi() {
        Response response = responseHealthCheck();
        assertEquals(200, response.statusCode());
        assertEquals("Running", responseHealthCheck().jsonPath().getString("status"));
        assertEquals("test", responseHealthCheck().jsonPath().getString("version"));
        assertEquals(LocalDate.now().toString(), responseHealthCheck().jsonPath().getString("buildDate"));
    }
}
