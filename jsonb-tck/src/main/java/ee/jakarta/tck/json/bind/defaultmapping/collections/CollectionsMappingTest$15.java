package ee.jakarta.tck.json.bind.defaultmapping.collections;

import com.github.t1.jsonbap.api.Bindable;
import ee.jakarta.tck.json.bind.defaultmapping.collections.model.LinkedHashSetContainer;

import java.util.LinkedHashSet;

@Bindable
public class CollectionsMappingTest$15 extends LinkedHashSetContainer {
    public CollectionsMappingTest$15(CollectionsMappingTest ignoreUnused, LinkedHashSet<String> instance) {
        setInstance(instance);
    }
}
