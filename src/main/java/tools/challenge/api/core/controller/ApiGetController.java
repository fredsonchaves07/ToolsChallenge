package tools.challenge.api.core.controller;

import jakarta.ws.rs.core.Response;

public abstract class ApiGetController extends ApiController {

    public abstract Response execute(final String id);
}
