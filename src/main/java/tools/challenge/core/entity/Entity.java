package tools.challenge.core.entity;


import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public abstract class Entity<ID extends Identifier> {

    private final ID id;

    private final LocalDateTime createdAt;

    private final LocalDateTime updatedAt;

    protected Entity(final ID id, final LocalDateTime createdAt, final LocalDateTime updatedAt) {
        this.id = id;
        this.createdAt = createdAt(createdAt);
        this.updatedAt = updatedAt(updatedAt);
    }

    protected Entity(final ID id) {
        this.id = id;
        this.createdAt = LocalDateTime.now().truncatedTo(ChronoUnit.MICROS);
        this.updatedAt = LocalDateTime.now().truncatedTo(ChronoUnit.MICROS);
    }

    private LocalDateTime createdAt(final LocalDateTime createdAt) {
        if (createdAt == null) return LocalDateTime.now().truncatedTo(ChronoUnit.MICROS);
        return createdAt;
    }

    private LocalDateTime updatedAt(final LocalDateTime updatedAt) {
        if (updatedAt == null) return LocalDateTime.now().truncatedTo(ChronoUnit.MICROS);
        return updatedAt;
    }

    public ID id() {
        return id;
    }

    public LocalDateTime createdAt() {
        return createdAt;
    }

    public LocalDateTime updatedAt() {
        return updatedAt;
    }

    protected abstract Entity<ID> validate() throws Error;

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entity<?> entity = (Entity<?>) o;
        return Objects.equals(id, entity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Entity - ID: " + id();
    }
}
