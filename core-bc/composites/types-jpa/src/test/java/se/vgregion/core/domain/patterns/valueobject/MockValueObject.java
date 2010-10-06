package se.vgregion.core.domain.patterns.valueobject;

import java.io.Serializable;

/**
 * This action do that and that, if it has something special it is.
 * 
 * @author Anders Asplund - <a href="http://www.callistaenterprise.se">Callista Enterprise</a>
 */

public class MockValueObject extends AbstractValueObject<MockValueObject> implements Serializable {

    private static final long serialVersionUID = 1L;

    protected MockValueObject() {
        // Used by JPA
    }

    public MockValueObject(String name) {
        this.name = name;
    }

    private String name;

    public String getName() {
        return name;
    }
}
