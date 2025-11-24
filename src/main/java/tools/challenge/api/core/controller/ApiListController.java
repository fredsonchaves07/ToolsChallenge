package tools.challenge.api.core.controller;

import jakarta.ws.rs.core.Response;

public abstract class ApiListController extends ApiController {


    public abstract Response execute();
}
