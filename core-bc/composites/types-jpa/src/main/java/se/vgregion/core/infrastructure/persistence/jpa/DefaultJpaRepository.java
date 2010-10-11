package se.vgregion.core.infrastructure.persistence.jpa;

import se.vgregion.core.domain.patterns.entity.Entity;

/**
 * A convenient implementation of JpaRepository where Entity ID and database primary key(PK) are equal and of type
 * Long. Use this when PK=ID=Long to get find(ID) and remove(ID) implemented.
 * 
 * @param T
 *            the Entity Type
 * 
 * @author Anders Asplund - <a href="http://www.callistaenterprise.se">Callista Enterprise</a>
 */
public abstract class DefaultJpaRepository<T extends Entity<T, Long>> extends AbstractJpaRepository<T, Long, Long> {

    /**
     * {@inheritDoc}
     */
    @Override
    public T find(Long id) {
        return findByPrimaryKey(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void remove(Long id) {
        removeByPrimaryKey(id);
    }
}
