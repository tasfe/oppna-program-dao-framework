package se.vgregion.dao.domain.patterns.entity;

/**
 * By convention, implementations should have two constructors:
 * <ol>
 *      <li>Default constructor for creating an immutable {@link Entity} from scratch</li>
 *      <li>Copy constructor which creates an immutable {@link Entity} by copying the prototype</li>
 * </ol>
 * 
 * For more information about the builder pattern can be found <a href="http://en.wikipedia.org/wiki/Builder_pattern">here</a>.
 * 
 */
public interface EntityBuilder<B extends EntityBuilder<?, ?>, E extends Entity<?, ?>> {
    
    E build();
}
