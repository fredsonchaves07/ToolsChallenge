package tools.challenge.core.entity;

import java.util.Objects;
import java.util.UUID;

public abstract class Identifier implements Comparable<Identifier> {

    private final String value;

    protected Identifier() {
        this.value = UUID.randomUUID().toString();
    }

    protected Identifier(final String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Identifier that = (Identifier) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    @Override
    public int compareTo(Identifier other) {
        return this.value.compareTo(other.value);
    }
}
