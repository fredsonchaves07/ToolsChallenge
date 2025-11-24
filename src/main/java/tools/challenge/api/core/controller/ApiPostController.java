package tools.challenge.api.core.controller;

import jakarta.ws.rs.core.Response;
import tools.challenge.core.usecases.ValueInput;

public abstract class ApiPostController<I extends ValueInput> extends ApiController {

    public abstract Response execute(final I input);
}
