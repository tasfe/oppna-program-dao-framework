/*
 * This source is based on code from the dddsample project (http://dddsample.sourceforge.net/)
 * under the MIT license
 */
package se.vgregion.core.domain.patterns.entity;


/**
 * <strong>From wikipedia</strong>: An object that is not defined by its attributes, but rather by a thread of
 * continuity and its identity.
 * <p/>
 * <strong>Example</strong>: Most airlines distinguish each seat uniquely on every flight. Each seat is an entity
 * in this context. However, Southwest Airlines (or EasyJet/RyanAir for you Europeans...) does not distinguish
 * between every seat; all seats are the same. In this context, a seat is actually a
 * {@link se.vgregion.core.domain.patterns.valueobject.ValueObject value object}.
 * 
 * @param <T>
 *            The Entity Type
 * 
 * @param <ID>
 *            The ID of the Entity
 * 
 * @author Anders Asplund - <a href="http://www.callistaenterprise.se">Callista Enterprise</a>
 */
public interface Entity<T, ID> {
    /**
     * Entities have an identity.
     * 
     * @return The identity of this entity.
     */
    ID getId();

    /**
     * Entities compare by identity, not by attributes.
     * 
     * @param other
     *            The other entity.
     * @return true if the identities are the same, regardless of other attributes.
     */
    boolean sameAs(T other);

}
