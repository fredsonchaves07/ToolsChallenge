package tools.challenge.api.core;

import java.time.LocalDate;

public record HealthStatus(String status, String version, LocalDate buildDate) {

    public static HealthStatus create(String status, String version) {
        return new HealthStatus(status, version, LocalDate.now());
    }
}
