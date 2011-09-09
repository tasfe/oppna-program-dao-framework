package se.vgregion.dao.domain.patterns.repository.jpa;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.IllegalTransactionStateException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:JpaMockEntityRepositoryTest-context.xml")
public class NoTransactionalTest {

    @Autowired
    private MockEntityRepository testRepository;

    @Test(expected = IllegalTransactionStateException.class)
    public void writeWithoutTransaction() {
        testRepository.store((new MockEntity("foo")));
    }

}
