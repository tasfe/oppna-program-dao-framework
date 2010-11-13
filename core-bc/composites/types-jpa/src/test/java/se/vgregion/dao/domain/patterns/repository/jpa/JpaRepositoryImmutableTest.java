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
package se.vgregion.dao.domain.patterns.repository.jpa;

import static org.junit.Assert.assertEquals;

import java.util.Collection;

import javax.annotation.Resource;
import javax.persistence.PersistenceException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.transaction.annotation.Transactional;

import se.vgregion.dao.domain.patterns.repository.jpa.ImmutableMockEntity.MockBuilder;

@ContextConfiguration("classpath:JpaMockEntityRepositoryTest-context.xml")
public class JpaRepositoryImmutableTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Resource
    private ImmutableMockEntityRepository testRepository;

    @Before
    @Transactional
    public void setUp() throws Exception {
        testRepository.store(new ImmutableMockEntity("foo"));
    }

    @Test
    @Transactional
    public void store() {
        ImmutableMockEntity entity = testRepository.findAll().iterator().next();

        ImmutableMockEntity updatedEntity = new MockBuilder(entity).setName("bar").build();

        testRepository.store(updatedEntity);

        Collection<ImmutableMockEntity> entityList = testRepository.findAll();
        assertEquals(1, entityList.size());
        
        ImmutableMockEntity actual = testRepository.findAll().iterator().next();
        Assert.assertEquals("bar", actual.getName());
    }

    @Test
    @Transactional
    public void merge() {
        ImmutableMockEntity entity = testRepository.findAll().iterator().next();

        ImmutableMockEntity updatedEntity = new MockBuilder(entity).setName("bar").build();

        testRepository.merge(updatedEntity);

        Collection<ImmutableMockEntity> entityList = testRepository.findAll();
        assertEquals(1, entityList.size());
        
        ImmutableMockEntity actual = testRepository.findAll().iterator().next();
        Assert.assertEquals("bar", actual.getName());
    }

    
    @Test(expected=PersistenceException.class)
    @Transactional
    public void persist() {
        ImmutableMockEntity entity = testRepository.findAll().iterator().next();

        ImmutableMockEntity updatedEntity = new MockBuilder(entity).setName("bar").build();

        // must fail since it's already persisted
        testRepository.persist(updatedEntity);
    }

}
