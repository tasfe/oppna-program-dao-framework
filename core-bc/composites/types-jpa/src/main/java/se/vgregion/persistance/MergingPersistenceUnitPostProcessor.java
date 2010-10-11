package se.vgregion.persistance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.jpa.persistenceunit.MutablePersistenceUnitInfo;
import org.springframework.orm.jpa.persistenceunit.PersistenceUnitPostProcessor;

/**
 * This merges all JPA entities from multiple jars. To use it, all entities must be listed in their respective
 * persistence.xml files using the <class> tag.
 * 
 * @see <a
 *      href="http://forum.springsource.org/showthread.php?t=61763">http://forum.springsource.org/showthread.php?t=61763</a>
 * 
 * @author Anders Asplund - <a href="http://www.callistaenterprise.se">Callista Enterprise</a>
 * @author David Rosell - Redpill-Linpro
 * 
 */
public class MergingPersistenceUnitPostProcessor implements PersistenceUnitPostProcessor {
    private Map<String, List<String>> puiClasses = new HashMap<String, List<String>>();

    /**
     * Post-process the given PersistenceUnitInfo in order to put all entities in a single persistence unit.
     * 
     * @param pui
     *            the chosen PersistenceUnitInfo, as read from <code>persistence.xml</code>. Passed in as
     *            MutablePersistenceUnitInfo.
     */
    public void postProcessPersistenceUnitInfo(MutablePersistenceUnitInfo pui) {
        List<String> classes = puiClasses.get(pui.getPersistenceUnitName());
        if (classes == null) {
            classes = new ArrayList<String>();
            puiClasses.put(pui.getPersistenceUnitName(), classes);
        }

        pui.getManagedClassNames().addAll(classes);
        classes.addAll(pui.getManagedClassNames());
    }
}
