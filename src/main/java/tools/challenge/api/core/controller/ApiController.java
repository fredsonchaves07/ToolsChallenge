package tools.challenge.api.core.controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import tools.challenge.api.core.error.ApiErrorFactory;
import tools.challenge.core.either.Either;
import tools.challenge.core.error.Error;
import tools.challenge.core.error.InternalError;
import tools.challenge.core.usecases.ValueOutput;

import static tools.challenge.api.core.controller.ApiStatusCode.badRequestError;
import static tools.challenge.api.core.controller.ApiStatusCode.internalServerError;

public abstract class ApiController {

    protected static final String MENSAGEM_SUCESSO = "Operação realizada com sucesso.";

    protected static final String MENSAGEM_SERVIDOR = "Erro interno no servidor.";

    protected static final String MENSAGEM_CLIENTE = "Erro de validação ou requisição malformada.";

    @Inject
    ApiContext context;

    @Inject
    ApiErrorFactory errorFactory;

    public Response error(final Error error) {
        if (error.getTypeError().equals(InternalError.typeError()))
            return Response
                    .status(internalServerError())
                    .entity(errorFactory.create(error, internalServerError(), context.path()))
                    .build();
        return Response
                .status(badRequestError())
                .entity(errorFactory.create(error, badRequestError(), context.path()))
                .build();
    }

    public Response success(final ValueOutput valueOutput) {
        return Response
                .ok()
                .entity(valueOutput)
                .build();
    }

    public Response response(final Either<Error, ? extends ValueOutput> output) {
        if (output.isError()) return error(output.getError());
        return success(output.getSuccess());
    }
}
