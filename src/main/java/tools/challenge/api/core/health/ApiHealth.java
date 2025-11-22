package tools.challenge.api.core.health;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.openapi.annotations.Operation;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public final class ApiHealth {

    public final static String HEALTH_VERSION_UNKNOWN = "Unknown";

    @ConfigProperty(name = "quarkus.application.version", defaultValue = HEALTH_VERSION_UNKNOWN)
    private String version;

    @GET
    @Operation(hidden = true)
    public Response healthCheck() {
        final String status = "Running";
        return Response.ok(HealthStatus.create(status, version)).build();
    }
}
