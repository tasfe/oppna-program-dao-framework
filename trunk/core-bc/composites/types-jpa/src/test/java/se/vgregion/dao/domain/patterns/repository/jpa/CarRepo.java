package se.vgregion.dao.domain.patterns.repository.jpa;

import java.util.UUID;

import se.vgregion.dao.domain.patterns.repository.db.jpa.AbstractJpaRepository;

public class CarRepo extends AbstractJpaRepository<Car, UUID, UUID> {
    public Car find(UUID id) {
        return null;
    }

    public class RaceCarRepo extends CarRepo {
    }
}
