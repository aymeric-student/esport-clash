package fr.ancyracademy.esportsclash.core.infrastructure.persistence;

import fr.ancyracademy.esportsclash.core.domain.model.BaseEntity;

import java.util.Optional;

public interface BaseRepository<T extends BaseEntity> {
    Optional<T> findById(String id);

    void save(T entity);

    void delete(T entity);

    void clear();

}
