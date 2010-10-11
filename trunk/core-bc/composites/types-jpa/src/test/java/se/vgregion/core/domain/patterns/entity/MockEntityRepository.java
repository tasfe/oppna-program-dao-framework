package se.vgregion.core.domain.patterns.entity;

import se.vgregion.core.infrastructure.persistence.jpa.JpaRepository;

/**
 * This action do that and that, if it has something special it is.
 * 
 * @author <a href="mailto:david.rosell@redpill-linpro.com">David Rosell</a>
 */
public interface MockEntityRepository extends JpaRepository<MockEntity, Long, Long> {

}
