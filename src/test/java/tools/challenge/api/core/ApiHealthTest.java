package tools.challenge.api.core;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.Response;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static tools.challenge.api.core.health.ApiHealth.HEALTH_VERSION_UNKNOWN;

@QuarkusTest
public class ApiHealthTest {

    @ConfigProperty(name = "quarkus.application.version", defaultValue = HEALTH_VERSION_UNKNOWN)
    private String version;

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
        assertEquals(version, responseHealthCheck().jsonPath().getString("version"));
        assertEquals(LocalDate.now().toString(), responseHealthCheck().jsonPath().getString("buildDate"));
    }
}
