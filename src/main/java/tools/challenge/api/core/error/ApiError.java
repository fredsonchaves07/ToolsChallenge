package tools.challenge.api.core.error;

import jakarta.json.bind.annotation.JsonbPropertyOrder;
import tools.challenge.core.error.Error;

@JsonbPropertyOrder({"statusCode", "path", "type", "message", "detail", "timestamp"})
public record ApiError(
        String message,
        int statusCode,
        String path,
        String type,
        String detail,
        String timestamp
) {
    public static ApiError error(final Error error, final String urlError, final String path, int statusCode) {
        return new ApiError(
                error.getMessage(), statusCode, path, error.getTypeError(), urlError, error.timestampFormatted());
    }
}
