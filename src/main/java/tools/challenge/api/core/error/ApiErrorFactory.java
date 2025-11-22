package tools.challenge.api.core.error;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import tools.challenge.core.error.Error;

@ApplicationScoped
public final class ApiErrorFactory {

    @Inject
    @ConfigProperty(name = "toolschallenge.url.suporte.errors")
    String urlError;

    public ApiError create(final Error error, int statusCode, String path) {
        return ApiError.error(error, formatUrlError(error), path, statusCode);
    }

    private String formatUrlError(Error error) {
        return urlError + "/" + error.getTypeError();
    }
}
