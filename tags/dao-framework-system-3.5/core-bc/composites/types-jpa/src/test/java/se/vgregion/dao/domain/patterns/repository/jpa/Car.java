package se.vgregion.dao.domain.patterns.repository.jpa;

import java.util.UUID;

import se.vgregion.dao.domain.patterns.entity.AbstractEntity;

public class Car extends AbstractEntity<UUID> {

    private UUID id;

    public UUID getId() {
        return id;
    }
}