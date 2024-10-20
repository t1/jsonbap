package ee.jakarta.tck.json.bind.defaultmapping.collections;

import com.github.t1.jsonbap.api.Bindable;
import ee.jakarta.tck.json.bind.defaultmapping.collections.model.TreeSetContainer;

import java.util.TreeSet;

@Bindable
public class CollectionsMappingTest$13 extends TreeSetContainer {
    public CollectionsMappingTest$13(CollectionsMappingTest ignoreUnused, TreeSet<String> instance) {
        setInstance(instance);
    }
}
