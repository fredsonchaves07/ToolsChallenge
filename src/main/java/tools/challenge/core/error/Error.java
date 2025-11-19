package tools.challenge.core.error;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public abstract class Error extends RuntimeException {

    private final String typeError;

    private final LocalDateTime timestamp;

    protected Error(final String message) {
        super(message);
        this.timestamp = LocalDateTime.now();
        this.typeError = formatTypeError(getClass());
    }

    private static String formatTypeError(Class<?> clazz) {
        return clazz.getSimpleName()
                .replaceAll("([a-z])([A-Z])", "$1_$2")
                .toUpperCase()
                .replaceAll("_?ERROR$", "_ERROR");
    }

    public String getTypeError() {
        return typeError;
    }

    public LocalDateTime timestamp() {
        return timestamp;
    }

    public String timestampFormatted() {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm'Z'")
                .withZone(ZoneOffset.UTC)
                .format(timestamp.atOffset(ZoneOffset.UTC));
    }

    @Override
    public String toString() {
        return typeError + ": " + getMessage();
    }

    protected static String typeErrorClass(Class<? extends Error> errorClass) {
        return formatTypeError(errorClass);
    }

    protected boolean hasCause() {
        return this.getCause() != null;
    }

    protected String cause() {
        if (!hasCause()) return "No cause";
        return this.getCause().toString();
    }

    protected List<StackTraceElement> stackTraceError() {
        final List<StackTraceElement> stackTraceCause = stackTraceCause();
        if (!stackTraceCause.isEmpty()) return stackTraceCause;
        return stackTraceWithoutCause();
    }

    private List<StackTraceElement> stackTraceWithoutCause() {
        return Arrays.stream(getStackTrace())
                .filter(trace -> ignoredPackages()
                        .stream()
                        .noneMatch(packageName -> trace.getClassName().startsWith(packageName)))
                .toList();
    }

    private List<StackTraceElement> stackTraceCause() {
        return Arrays.stream(getCause().getStackTrace())
                .filter(trace -> ignoredPackages()
                        .stream()
                        .noneMatch(packageName -> trace.getClassName().startsWith(packageName)))
                .filter(trace -> toolsChallengePackages()
                        .stream()
                        .anyMatch(packageName -> trace.getClassName().startsWith(packageName)))
                .filter(trace -> trace.getLineNumber() > 0)
                .toList();
    }

    private List<String> ignoredPackages() {
        return List.of("java.", "tools.challenge.core.either", "tools.challenge.core.errors");
    }

    private List<String> toolsChallengePackages() {
        return List.of("tools.challenge");
    }
}
