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
package se.vgregion.dao.domain.patterns.repository.db.jpa;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import se.vgregion.dao.domain.patterns.entity.Entity;
import se.vgregion.dao.domain.patterns.repository.db.DatabaseRepository;

/**
 * An extended repository interface specific for JPA
 * 
 * @param <T>
 *            The Entity Type
 * @param <ID>
 *            The ID of the Entity
 * @param <PK>
 *            The type of the primary key
 * 
 * @author Anders Asplund - <a href="http://www.callistaenterprise.se">Callista Enterprise</a>
 */
public interface JpaRepository<T extends Entity<ID>, ID extends Serializable, PK extends Serializable> extends
DatabaseRepository<T, ID, PK> {

    /**
     * Clear the persistence context, causing all managed entities to become detached. Changes made to entities
     * that have not been flushed to the storage will not be persisted.
     */
    void clear();

    Collection<T> findByQuery(String qlString);

    Collection<T> findByQuery(String qlString, Object[] args);

    Collection<T> findByQuery(String qlString, Map<String, ? extends Object> args);

    public Collection<T> findByAttribute(String attributeName, Object value);

    /**
     * Find instances of <code>T</code> that match the criteria defined by query <code>queryName</code>.
     * <code>args</code> provide the values for any named parameters in the query identified by
     * <code>queryName</code>.
     * 
     * @param queryName
     *            the named query to execute
     * @param args
     *            the values used by the query
     * @return a list of <code>T</code> objects
     */
    List<T> findByNamedQuery(String queryName, Map<String, ? extends Object> args);

    /**
     * Find instances of <code>T</code> that match the criteria defined by query <code>queryName</code>.
     * <code>args</code> provide values for positional arguments in the query identified by <code>queryName</code>.
     * 
     * @param queryName
     *            the named query to execute
     * @param args
     *            the positional values used in the query
     * @return a list of <code>T</code> objects
     */
    List<T> findByNamedQuery(String queryName, Object[] args);

    /**
     * Find a single instance of <code>T</code> using the query named <code>queryName</code> and the arguments
     * identified by <code>args</code>.
     * 
     * @param queryName
     *            the name of the query to use
     * @param args
     *            the arguments for the named query
     * @return T or null if no objects match the criteria if more than one instance is returned.
     */
    T findInstanceByNamedQuery(String queryName, Object[] args);

    /**
     * Find a single instance of <code>T</code> using the query named <code>queryName</code> and the arguments
     * identified by <code>args</code>.
     * 
     * @param queryName
     *            the name of the query to use
     * @param args
     *            a Map holding the named parameters of the query
     * @return T or null if no objects match the criteria if more than one instance is returned.
     */
    T findInstanceByNamedQuery(String queryName, Map<String, ? extends Object> args);

}
