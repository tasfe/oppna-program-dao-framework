/*
 * This source is based on code from the dddsample project (http://dddsample.sourceforge.net/)
 * under the MIT license
 */
package se.vgregion.dao.domain.patterns.entity;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Abstract default implementation common to all concrete implementation of Entity.
 * 
 * @param <T>
 *            The Entity Type
 * @param <ID>
 *            The ID of the Entity
 * 
 * @author Anders Asplund - <a href="http://www.callistaenterprise.se">Callista Enterprise</a>
 * 
 */
public abstract class AbstractEntity<ID> implements Entity<ID> {

    private static final long serialVersionUID = 1L;

    /**
     * {@inheritDoc}
     */
    public final boolean sameAs(final Entity<ID> other) {
        return other != null
                && (other.getClass().isAssignableFrom(this.getClass()) || this.getClass().isAssignableFrom(
                        other.getClass())) && this.getId().equals(other.getId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int hashCode() {
        if (getId() == null) {
            return super.hashCode();
        }
        return getId().hashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean equals(final Object other) {
        if (other == null) {
            return false;
        }
        if (other == this) {
            return true;
        }
        if (getClass() != other.getClass()) {
            return false;
        }

        Entity<?> otherType = (Entity<?>) other;

        if (getId() == null || otherType.getId() == null) {
            return false;
        }

        return new EqualsBuilder().append(otherType.getId(), getId()).isEquals();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
