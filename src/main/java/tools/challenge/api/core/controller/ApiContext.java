package tools.challenge.api.core.controller;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.UriInfo;

@RequestScoped
public final class ApiContext {

    @Inject
    UriInfo uriInfo;

    public String path() {
        return uriInfo.getPath();
    }

    public String client() {
        return uriInfo.getPathSegments().getFirst().getPath();
    }
}
