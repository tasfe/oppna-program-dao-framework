package se.vgregion.core.domain.patterns.repository.inmemory;

import org.junit.Assert;
import org.junit.Test;

public class InMemoryRepositoryTest {

    private MockInMemoryRepository repository = new MockInMemoryRepository();

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
