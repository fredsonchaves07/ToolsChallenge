package tools.challenge.core.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public final class InternalError extends Error {

    private static final String ERROR_MESSAGE_DEFAULT = "Ocorreu um erro ao realizar operação. Consulte o suporte.";

    private static final Logger LOGGER = LoggerFactory.getLogger(InternalError.class);

    private static final int STACK_TRACE_LIMIT = 3;

    private final List<StackTraceElement> stackTrace;

    private InternalError(final String message, final Throwable cause) {
        super(message);
        if (cause != null) initCause(cause);
        stackTrace = stackTraceError();
        logger();
    }

    private void logger() {
        LOGGER.error(this.getMessage());
        if (hasCause()) causeStackTrace();
        stackTrace.stream()
                .skip(1)
                .limit(STACK_TRACE_LIMIT)
                .forEach(trace -> LOGGER.error("  at {}", trace));
    }

    public static InternalError error(final String message) {
        return new InternalError(message, null);
    }

    public static InternalError error(final Throwable cause) {
        return new InternalError(ERROR_MESSAGE_DEFAULT, cause);
    }

    private void causeStackTrace() {
        final StackTraceElement stackTraceElement = stackTrace.stream().findFirst().orElseThrow();
        LOGGER.error("Caused by: {} at {}.{} ({}:{})",
                this.cause(),
                stackTraceElement.getClassName(),
                stackTraceElement.getMethodName(),
                stackTraceElement.getFileName(),
                stackTraceElement.getLineNumber());
    }

    public static String typeError() {
        return typeErrorClass(InternalError.class);
    }
}
