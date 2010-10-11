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
package se.vgregion.core.domain.patterns.repository.inmemory;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import se.vgregion.core.domain.patterns.entity.Entity;
import se.vgregion.core.domain.patterns.repository.Repository;

/**
 * Abstract in-memory repository. Stores entities in a {@link ConcurrentHashMap}
 */
public abstract class InMemoryRepository<T extends Entity<T, ID>, ID> implements Repository<T, ID> {

    private Map<ID, T> entities = new ConcurrentHashMap<ID, T>();

    /**
     * {@inheritDoc}
     * 
     * This is a no-op operation with the in-memory repository
     */
    public void clear() {
    }

    /**
     * {@inheritDoc}
     */
    public boolean contains(T entity) {
        return entities.containsValue(entity);
    }

    /**
     * {@inheritDoc}
     */
    public T find(ID id) {
        return entities.get(id);
    }

    /**
     * {@inheritDoc}
     */
    public Collection<T> findAll() {
        return entities.values();
    }

    /**
     * {@inheritDoc}
     * 
     * This is a no-op operation with the in-memory repository
     */
    public void flush() {
        
    }

    /**
     * {@inheritDoc}
     */
    public T merge(T object) {
        return entities.put(object.getId(), object);
    }

    /**
     * {@inheritDoc}
     */
    public T persist(T object) {
        return entities.put(object.getId(), object);
    }

    /**
     * {@inheritDoc}
     * 
     * Not supported by the in-memory repository
     */
    public void refresh(T object) {
        
    }

    /**
     * {@inheritDoc}
     */
    public void remove(T object) {
        entities.remove(object.getId());
    }

    /**
     * {@inheritDoc}
     */
    public void remove(ID id) {
        entities.remove(id);
    }

    /**
     * {@inheritDoc}
     */
    public T store(T entity) {
        return entities.put(entity.getId(), entity);
    }

}
