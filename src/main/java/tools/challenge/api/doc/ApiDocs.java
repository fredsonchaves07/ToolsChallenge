package tools.challenge.api.doc;

import io.vertx.core.cli.annotations.Hidden;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.openapi.annotations.Operation;

import java.net.URI;

@Path("/docs")
@Hidden
public final class ApiDocs {

    @ConfigProperty(name = "quarkus.profile", defaultValue = "prod")
    String profile;

    @GET
    @Operation(hidden = true)
    public Response swagger() {
        if (profile.equals("dev"))
            return Response.seeOther(URI.create("/q/swagger-ui/")).build();
        return Response.status(Response.Status.FORBIDDEN).entity("Endpoint not available").build();
    }
}
