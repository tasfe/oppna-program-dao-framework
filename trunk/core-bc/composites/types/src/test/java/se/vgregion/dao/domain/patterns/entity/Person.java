package se.vgregion.dao.domain.patterns.entity;

import java.util.UUID;

public class Person extends AbstractEntity<UUID> {

    private UUID id;

    public Person(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }
    


}
