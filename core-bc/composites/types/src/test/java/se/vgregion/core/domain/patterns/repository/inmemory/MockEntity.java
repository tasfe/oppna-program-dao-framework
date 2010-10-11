package se.vgregion.core.domain.patterns.repository.inmemory;

import se.vgregion.core.domain.patterns.entity.AbstractEntity;

public class MockEntity extends AbstractEntity<MockEntity, Integer> {

    private Integer id;
    
    public Integer getId() {
        return id;
    }

    public MockEntity(Integer id) {
        this.id = id;
    }
}
