/**
 * Copyright 2010 Västra Götalandsregionen
 *
 *   This library is free software; you can redistribute it and/or modify
 *   it under the terms of version 2.1 of the GNU Lesser General Public
 *   License as published by the Free Software Foundation.
 *
 *   This library is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public
 *   License along with this library; if not, write to the
 *   Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 *   Boston, MA 02111-1307  USA
 *
 */
package se.vgregion.dao.domain.patterns.repository;

import java.util.Collection;

import se.vgregion.dao.domain.patterns.entity.Entity;

/**
 * Common methods for retrieving domain object.
 * 
 * @param <T>
 *            The Entity type
 * @param <ID>
 *            The Id type
 * 
 * @author Anders Asplund - <a href="http://www.callistaenterprise.se">Callista Enterprise</a>
 */
public interface Repository<T extends Entity<T, ID>, ID> {
    /**
     * Store <code>object</code> in the storage.
     * 
     * @param object
     *            the instance to save in the storage
     * @return the object stored in the storage
     */
    T persist(T object);

    /**
     * Taken from the EntityManager documentation: Synchronize the persistence context to the underlying storage.
     */
    void flush();

    /**
     * Remove <code>object</code> from the storage.
     * 
     * @param object
     *            the object to be removed from the storage
     */
    void remove(T object);


    /**
     * Delete by entity ID.
     * 
     * @param id
     *            The id of the entity
     */
    void remove(ID id);

    /**
     * Find all instances of <code>T</code> in the storage.
     * 
     * @return a list <code>T</code> objects
     */
    Collection<T> findAll();

    /**
     * Finds the instance of <code>T</code> identified by it's <code>ID</code>.
     * 
     * @param id
     *            The id of the entity
     * 
     * @return an object of <code>T</code>
     */
    T find(ID id);

    /**
     * Update existing <code>object</code>.
     * 
     * @param object
     *            the object to update in the database
     * @return an object of <code>T</code>
     */
    T merge(T object);

    /**
     * Refresh the state of the instance from the storage, overwriting
     * changes made to the entity, if any.
     * 
     * @param object
     *            the object to refresh
     */
    void refresh(T object);

    /**
     * Check if the entity is available in the storage.
     * 
     * @param entity
     *            the entity object
     * @return true if present
     */
    boolean contains(T entity);

    /**
     * Convenience method that lets you persist or merge an entity transparently depending on its state.
     * 
     * @param entity
     *            the entity
     * @return the stored entity
     */
    T store(T entity);
}
