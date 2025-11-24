package tools.challenge.api.core.error;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotAllowedException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.NotSupportedException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import tools.challenge.api.core.controller.ApiContext;
import tools.challenge.core.error.Error;
import tools.challenge.core.error.InternalError;

import static tools.challenge.api.core.controller.ApiStatusCode.*;


@Provider
@ApplicationScoped
public class ApiErrorHandler implements ExceptionMapper<Throwable> {

    @Inject
    ApiErrorFactory errorFactory;

    @Inject
    ApiContext context;

    @Override
    public Response toResponse(final Throwable exception) {
        final Error domainError = mapToDomainError(exception);
        int status = statusCode(exception);
        final ApiError apiError = errorFactory.create(domainError, status, context.path());
        return Response.status(status)
                .type(MediaType.APPLICATION_JSON)
                .entity(apiError)
                .build();
    }

    private Error mapToDomainError(final Throwable throwable) {
        if (throwable instanceof Error error) return error;
        if (throwable instanceof NotSupportedException)
            return NotSupportedContentApiError.error();
        if (throwable instanceof NotFoundException)
            return NotFoundApiError.error();
        if (throwable instanceof NotAllowedException)
            return MethodNotAllowedApiError.error();
        return InternalError.error(throwable);
    }

    private int statusCode(final Throwable exception) {
        if (exception instanceof NotSupportedException) return unsuportedMediaTypeError();
        if (exception instanceof NotFoundException) return notFoundError();
        if (exception instanceof NotAllowedException) return methodNotAllowedError();
        return internalServerError();
    }
}
