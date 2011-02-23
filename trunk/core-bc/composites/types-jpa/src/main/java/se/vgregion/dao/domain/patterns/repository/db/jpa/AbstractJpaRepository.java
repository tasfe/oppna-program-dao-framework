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
import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
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

    private Class<? extends T> type;

    public void setType(Class<? extends T> type) {
        this.type = type;
    }

    /**
     * Entity class type.
     */
    public Class<? extends T> getType() {
        return type;
    }

    /**
     * Default constructor.
     */
    public AbstractJpaRepository() {
        this.type = getTypeArguments(getClass()).get(0);
    }

    /**
     * Get the underlying class for a type, or null if the type is a variable type.
     * 
     * @param type
     *            the type
     * @return the underlying class
     */
    @SuppressWarnings("unchecked")
    private Class<? extends T> getClass(Type type) {
        if (type instanceof Class) {
            return (Class<? extends T>) type;
        } else if (type instanceof ParameterizedType) {
            return getClass(((ParameterizedType) type).getRawType());
        } else if (type instanceof GenericArrayType) {
            Type componentType = ((GenericArrayType) type).getGenericComponentType();
            Class<? extends T> componentClass = getClass(componentType);
            if (componentClass != null) {
                return (Class<? extends T>) Array.newInstance(componentClass, 0).getClass();
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * Get the actual type arguments a child class has used to extend a generic base class.
     * 
     * @param childClass
     *            the child class
     * @return a list of the raw classes for the actual type arguments.
     * 
     * @see http://www.artima.com/weblogs/viewpost.jsp?thread=208860
     */
    private List<Class<? extends T>> getTypeArguments(
            @SuppressWarnings("rawtypes") Class<? extends AbstractJpaRepository> childClass) {
        Map<Type, Type> resolvedTypes = new HashMap<Type, Type>();
        Type type = childClass;
        // start walking up the inheritance hierarchy until we hit this class
        while (!getClass(type).equals(AbstractJpaRepository.class)) {
            if (type instanceof Class) {
                // there is no useful information for us in raw types, so just keep going.
                type = ((Class<?>) type).getGenericSuperclass();
            } else {
                ParameterizedType parameterizedType = (ParameterizedType) type;
                Class<?> rawType = (Class<?>) parameterizedType.getRawType();

                Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                TypeVariable<?>[] typeParameters = rawType.getTypeParameters();
                for (int i = 0; i < actualTypeArguments.length; i++) {
                    resolvedTypes.put(typeParameters[i], actualTypeArguments[i]);
                }

                if (!rawType.equals(AbstractJpaRepository.class)) {
                    type = rawType.getGenericSuperclass();
                }
            }
        }

        // finally, for each actual type argument provided to baseClass, determine (if possible)
        // the raw class for that type argument.
        Type[] actualTypeArguments;
        if (type instanceof Class) {
            actualTypeArguments = ((Class<?>) type).getTypeParameters();
        } else {
            actualTypeArguments = ((ParameterizedType) type).getActualTypeArguments();
        }
        List<Class<? extends T>> typeArgumentsAsClasses = new ArrayList<Class<? extends T>>();
        // resolve types by chasing down type variables.
        for (Type baseType : actualTypeArguments) {
            while (resolvedTypes.containsKey(baseType)) {
                baseType = resolvedTypes.get(baseType);
            }
            typeArgumentsAsClasses.add(getClass(baseType));
        }
        return typeArgumentsAsClasses;
    }

    /**
     * Parameterized constructor.
     * 
     * @param type
     *            Entity class type
     */
    public AbstractJpaRepository(Class<? extends T> type) {
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
    public Collection<T> findByQuery(String qlString) {
        Query query = entityManager.createQuery(qlString);
        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Collection<T> findByQuery(String qlString, Map<String, ? extends Object> args) {
        Query query = entityManager.createQuery(qlString);
        for (Map.Entry<String, ? extends Object> parameter : args.entrySet()) {
            query.setParameter(parameter.getKey(), parameter.getValue());
        }
        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<T> findByQuery(String qlString, Object[] args) {
        Query query = entityManager.createQuery(qlString);
        if (args != null) {
            for (int i = 0; i < args.length; i++) {
                query.setParameter(i + 1, args[i]);
            }
        }
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
    @SuppressWarnings("unchecked")
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public T findByAttribute(String attributeName, Object value) {
        try {
            return (T) entityManager
                    .createQuery(
                            "select e from " + type.getSimpleName() + " e where e." + attributeName + " = :attr")
                    .setParameter("attr", value).getSingleResult();
        } catch (NoResultException e) {
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
        if (entity.getId() == null || find(entity.getId()) == null) {
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
