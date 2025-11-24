package tools.challenge.core.repository;

import tools.challenge.core.entity.Entity;
import tools.challenge.core.entity.Identifier;

import java.util.List;
import java.util.Optional;

public interface Repository<I extends Identifier, E extends Entity<I>> {

    void save(final E entity);

    Optional<E> findById(final I id);

    List<E> findAll();

    void delete(E entity);

    void deleteAll();

    default int count() {
        return findAll().size();
    }
}
