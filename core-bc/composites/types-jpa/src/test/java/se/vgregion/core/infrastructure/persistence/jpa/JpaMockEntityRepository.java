package se.vgregion.core.infrastructure.persistence.jpa;

import se.vgregion.core.domain.patterns.entity.MockEntity;
import se.vgregion.core.domain.patterns.entity.MockEntityRepository;

/**
 * This action do that and that, if it has something special it is.
 * 
 * @author <a href="mailto:david.rosell@redpill-linpro.com">David Rosell</a>
 */
public class JpaMockEntityRepository extends DefaultJpaRepository<MockEntity> implements MockEntityRepository {

}
