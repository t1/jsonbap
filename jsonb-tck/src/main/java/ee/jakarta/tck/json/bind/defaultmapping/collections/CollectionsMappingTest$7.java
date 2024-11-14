package ee.jakarta.tck.json.bind.defaultmapping.collections;

import com.github.t1.jsonbap.api.Bindable;
import ee.jakarta.tck.json.bind.defaultmapping.collections.model.HashSetContainer;

import java.util.HashSet;

@Bindable(deserializable = false)
public class CollectionsMappingTest$7 extends HashSetContainer {
    public CollectionsMappingTest$7(CollectionsMappingTest ignoreUnused, HashSet<String> instance) {
        setInstance(instance);
    }
}
