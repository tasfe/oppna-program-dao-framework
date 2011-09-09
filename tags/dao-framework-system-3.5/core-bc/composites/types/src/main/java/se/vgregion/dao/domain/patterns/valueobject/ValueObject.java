/*
 * This source is based on code from the dddsample project (http://dddsample.sourceforge.net/)
 * under the MIT license
 */package se.vgregion.dao.domain.patterns.valueobject;

/**
 * <strong>From Wikipedia</strong>: A Value Object is an object that contains attributes but has no conceptual
 * identity. They should be treated as immutable.
 * <p/>
 * <strong>Example</strong>: When people exchange dollar bills, they generally do not distinguish between each
 * unique bill; they only are concerned about the face value of the dollar bill. In this context, dollar bills are
 * value objects. However, the Federal Reserve may be concerned about each unique bill; in this context each bill
 * would be an {@link se.vgregion.dao.domain.patterns.entity.Entity entity}.
 * 
 * @param <T>
 *            the Type of the Value Object
 * 
 * @author Anders Asplund - <a href="http://www.callistaenterprise.se">Callista Enterprise</a>
 */
public interface ValueObject {

    /**
     * Value objects compare by the values of their attributes, they don't have an identity.
     * 
     * @param other
     *            The other value object.
     * @return <code>true</code> if the given value object's and this value object's attributes are the same.
     */
    boolean sameValueAs(ValueObject other);

}