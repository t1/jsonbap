package ee.jakarta.tck.json.bind.defaultmapping.collections;

import com.github.t1.jsonbap.api.Bindable;
import ee.jakarta.tck.json.bind.defaultmapping.collections.model.NavigableSetContainer;

import java.util.NavigableSet;

@Bindable
public class CollectionsMappingTest$9 extends NavigableSetContainer {
    public CollectionsMappingTest$9(CollectionsMappingTest ignoreUnused, NavigableSet<String> instance) {
        setInstance(instance);
    }
}
