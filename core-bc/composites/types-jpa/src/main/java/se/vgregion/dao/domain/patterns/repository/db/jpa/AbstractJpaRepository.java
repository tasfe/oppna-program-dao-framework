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
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import se.vgregion.dao.domain.patterns.entity.Entity;
import se.vgregion.dao.domain.patterns.repository.db.DatabaseRepository;

/**
 * An abstract default implementation of a {@link DatabaseRepository}. This is an JPA implementation used with any
 * JPA implementation such as Hibernate.
 * 
 * @param <T>
 *            The Entity Type
 * @param <ID>
 *            The ID of the Entity
 * @param <PK>
 *            The type of the primary key
 * 
 * @author Anders Asplund - <a href="http://www.callistaenterprise.se">Callista Enterprise</a>
 * 
 */
public abstract class AbstractJpaRepository<T extends Entity<ID>, ID extends Serializable, PK extends Serializable>
        implements JpaRepository<T, ID, PK> {

    /**
     * Entity manager ref.
     */
    @PersistenceContext
    protected EntityManager entityManager;

    /**
     * Entity class type.
     */
    protected Class<T> type;

    public void setType(Class<T> type) {
        this.type = type;
    }

    /**
     * Default constructor.
     */
    @SuppressWarnings("unchecked")
    public AbstractJpaRepository() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /**
     * Parameterized constructor.
     * 
     * @param type
     *            Entity class type
     */
    public AbstractJpaRepository(Class<T> type) {
        this.type = type;
    }

    /**
     * Does entity manager contain entity?
     * 
     * @param entity
     *            Entity to check for
     * @return true if found
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public boolean contains(T entity) {
        return entityManager.contains(entity);
    }

    /**
     * {@inheritDoc}
     * 
     */
    @SuppressWarnings("unchecked")
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<T> findAll() {
        Query query = entityManager.createQuery("select o from " + type.getSimpleName() + " o");
        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<T> findByNamedQuery(String queryName, Map<String, ? extends Object> args) {
        Query namedQuery = entityManager.createNamedQuery(queryName);
        for (Map.Entry<String, ? extends Object> parameter : args.entrySet()) {
            namedQuery.setParameter(parameter.getKey(), parameter.getValue());
        }
        return namedQuery.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<T> findByNamedQuery(String queryName, Object[] args) {
        Query namedQuery = entityManager.createNamedQuery(queryName);
        if (args != null) {
            for (int i = 0; i < args.length; i++) {
                namedQuery.setParameter(i + 1, args[i]);
            }
        }
        return namedQuery.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public T findInstanceByNamedQuery(String queryName, Object[] args) {
        Query namedQuery = entityManager.createNamedQuery(queryName);
        if (args != null) {
            int position = 1;
            for (Object value : args) {
                // will throw IllegalArgumentException if position is incorrect
                // or value is incorrect type
                namedQuery.setParameter(position++, value);
            }
        }

        try {
            @SuppressWarnings("unchecked")
            T result = (T) namedQuery.getSingleResult();
            return result;
        } catch (NoResultException nre) {
            return null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public T findInstanceByNamedQuery(String queryName, Map<String, ? extends Object> args) {
        Query namedQuery = entityManager.createNamedQuery(queryName);
        if (args != null) {
            for (Map.Entry<String, ? extends Object> entry : args.entrySet()) {
                // will throw IllegalArgumentException if parameter name not
                // found or value is incorrect type
                namedQuery.setParameter(entry.getKey(), entry.getValue());
            }
        }
        try {
            @SuppressWarnings("unchecked")
            T result = (T) namedQuery.getSingleResult();
            return result;
        } catch (NoResultException nre) {
            return null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public T findByPrimaryKey(PK pk) {
        return entityManager.find(type, pk);
    }

    /**
     * {@inheritDoc}
     * 
     * @throws {@link IllegalTransactionStateException} if the method is invoked without an existing transaction.
     */
    @Transactional(propagation = Propagation.MANDATORY)
    public void flush() {
        entityManager.flush();
    }

    /**
     * {@inheritDoc}
     * 
     * @throws {@link IllegalTransactionStateException} if the method is invoked without an existing transaction.
     */
    @Transactional(propagation = Propagation.MANDATORY)
    public T persist(T entity) {
        entityManager.persist(entity);
        return entity;
    }

    /**
     * {@inheritDoc}
     * 
     * @throws {@link IllegalTransactionStateException} if the method is invoked without an existing transaction.
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public void clear() {
        entityManager.clear();
    }

    /**
     * {@inheritDoc}
     * 
     * @throws {@link IllegalTransactionStateException} if the method is invoked without an existing transaction.
     */
    @Transactional(propagation = Propagation.MANDATORY)
    public void remove(T entity) {
        entityManager.remove(entity);
    }

    /**
     * {@inheritDoc}
     * 
     * @throws {@link IllegalTransactionStateException} if the method is invoked without an existing transaction.
     */
    @Transactional(propagation = Propagation.MANDATORY)
    public void remove(ID id) {
        T entity = find(id);
        entityManager.remove(entity);
    }

    /**
     * {@inheritDoc}
     * 
     * @throws {@link IllegalTransactionStateException} if the method is invoked without an existing transaction.
     */
    @Transactional(propagation = Propagation.MANDATORY)
    public void removeByPrimaryKey(PK pk) {
        T entity = findByPrimaryKey(pk);
        entityManager.remove(entity);
    }

    /**
     * {@inheritDoc}
     * 
     * @throws {@link IllegalTransactionStateException} if the method is invoked without an existing transaction.
     */
    @Transactional(propagation = Propagation.MANDATORY)
    public T merge(T entity) {
        return entityManager.merge(entity);
    }

    /**
     * {@inheritDoc}
     * 
     * @throws {@link IllegalTransactionStateException} if the method is invoked without an existing transaction.
     */
    @Transactional(propagation = Propagation.MANDATORY)
    public T store(T entity) {
        if (entity.getId() == null || entityManager.find(type, entity.getId()) == null) {
            entityManager.persist(entity);
            return entity;
        } else {
            return entityManager.merge(entity);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public void refresh(T entity) {
        entityManager.refresh(entity);
    }
}
