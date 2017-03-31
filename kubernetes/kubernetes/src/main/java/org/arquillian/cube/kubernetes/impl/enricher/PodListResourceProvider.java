package org.arquillian.cube.kubernetes.impl.enricher;

import io.fabric8.kubernetes.api.model.PodList;
import java.lang.annotation.Annotation;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.arquillian.test.spi.enricher.resource.ResourceProvider;

/**
 * A {@link ResourceProvider} for {@link PodList}.
 * It refers to pods that have been created during the current session.
 */
public class PodListResourceProvider extends AbstractKubernetesResourceProvider {

    @Override
    public boolean canProvide(Class<?> type) {
        return PodList.class.isAssignableFrom(type);
    }

    @Override
    public Object lookup(ArquillianResource resource, Annotation... qualifiers) {
        return getClient().pods().inNamespace(getSession().getNamespace()).list();
    }
}
