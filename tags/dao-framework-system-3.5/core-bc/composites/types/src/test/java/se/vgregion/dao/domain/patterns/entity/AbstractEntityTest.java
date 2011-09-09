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
package se.vgregion.dao.domain.patterns.entity;

import static org.junit.Assert.*;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

/**
 * @author <a href="mailto:david.rosell@redpill-linpro.com">David Rosell</a>
 */
public class AbstractEntityTest {
    AbstractEntity<Long> entity1;
    AbstractEntity<Long> entity2;
    AbstractEntity<Long> entitySame1;
    AbstractEntity<Long> entityNull1;
    AbstractEntity<Long> entityNull2;

    @Before
    public void setUp() {
        entity1 = new TestEntity(1L);
        entity2 = new TestEntity(2L);
        entitySame1 = new TestEntity(1L);
        entityNull1 = new TestEntity(null);
        entityNull2 = new TestEntity(null);
    }

    @Test
    public void testHashCode() throws Exception {
        assertEquals(1, entity1.hashCode());
        assertEquals(entity1.hashCode(), entitySame1.hashCode());
        assertNotSame(entity1.hashCode(), entity2.hashCode());
        assertNotSame(entityNull1.hashCode(), entityNull2.hashCode());
    }

    @Test
    public void testEquals() throws Exception {
        assertTrue(entity1.equals(entitySame1));
        assertFalse(entity1.equals(entity2));
        assertFalse(entityNull1.equals(entityNull2));
        assertTrue(entityNull1.equals(entityNull1));
        assertFalse(entity1.equals(entityNull1));
        assertFalse(entity1.equals(new Object()));
        assertFalse(entity1.equals(null));
    }

    @Test
    public void testSameAs() throws Exception {
        UUID id = UUID.randomUUID();
        Car car = new Car(id);
        Car car2 = new Car(id);
        Car car3 = new Car(UUID.randomUUID());

        assertTrue(car.sameAs(car));
        assertTrue(car.sameAs(car2));
        assertTrue(car2.sameAs(car));
        assertFalse(car3.sameAs(car));
        assertFalse(car.sameAs(car3));
    }
    
    @Test
    public void testSameAsWithInheritance() throws Exception {
        UUID id = UUID.randomUUID();
        Car car = new Car(id);
        RaceCar raceCar = new RaceCar(id);
        Person person = new Person(id);
        
        assertTrue(car.sameAs(car));
        assertTrue(raceCar.sameAs(car));
        assertTrue(car.sameAs(raceCar));
        assertFalse(car.sameAs(person));
        assertFalse(person.sameAs(car));
        assertFalse(car.sameAs(null));
    }

    
    @Test
    public void testToString() throws Exception {
        assertTrue(entity1.toString().contains("id=1"));
    }

    private class TestEntity extends AbstractEntity<Long> {

        private static final long serialVersionUID = 1L;
        private Long id;

        public Long getId() {
            return id;
        }

        public TestEntity(Long id) {
            this.id = id;
        }
    }
}
