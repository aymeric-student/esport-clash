package fr.ancyracademy.esportsclash.core.domain.model;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseEntity<T> {

    @Id
    protected String id;

    public BaseEntity(String id) {
        this.id = id;
    }

    public BaseEntity() {
    }


    public String getId() {
        return id;
    }

    public abstract T deepClone();
}
