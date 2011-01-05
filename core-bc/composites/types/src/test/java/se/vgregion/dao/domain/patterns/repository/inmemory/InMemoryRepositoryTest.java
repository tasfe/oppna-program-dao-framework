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
package se.vgregion.dao.domain.patterns.repository.inmemory;

import org.junit.Assert;
import org.junit.Test;

import se.vgregion.dao.domain.patterns.repository.Repository;

public class InMemoryRepositoryTest {

    private Repository<MockEntity, Integer> repository = new InMemoryRepository<MockEntity, Integer>();

    @Test
    public void store() {
        repository.store(new MockEntity(123));

        Assert.assertEquals(1, repository.findAll().size());
    }

    @Test
    public void persist() {
        repository.persist(new MockEntity(123));

        Assert.assertEquals(1, repository.findAll().size());
    }

    @Test
    public void merge() {
        repository.merge(new MockEntity(123));

        Assert.assertEquals(1, repository.findAll().size());
    }

    @Test
    public void contains() {
        repository.store(new MockEntity(123));

        Assert.assertTrue(repository.contains(new MockEntity(123)));
    }

    @Test
    public void find() {
        repository.store(new MockEntity(123));

        Assert.assertEquals(new Integer(123), repository.find(123).getId());
    }

    @Test
    public void findAll() {
        repository.store(new MockEntity(123));
        repository.store(new MockEntity(456));

        Assert.assertEquals(2, repository.findAll().size());
    }

    @Test
    public void removeById() {
        repository.store(new MockEntity(123));

        Assert.assertEquals(1, repository.findAll().size());

        repository.remove(123);

        Assert.assertEquals(0, repository.findAll().size());
    }

    @Test
    public void removeByEntity() {
        repository.store(new MockEntity(123));

        Assert.assertEquals(1, repository.findAll().size());

        repository.remove(new MockEntity(123));

        Assert.assertEquals(0, repository.findAll().size());
    }

}
