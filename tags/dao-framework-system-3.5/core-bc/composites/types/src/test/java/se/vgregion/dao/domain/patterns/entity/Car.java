package se.vgregion.dao.domain.patterns.entity;

import java.util.UUID;

public class Car extends AbstractEntity<UUID> {

    private UUID id;

    public Car(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }
    


}
