package ee.jakarta.tck.json.bind.defaultmapping.collections;

import com.github.t1.jsonbap.api.Bindable;
import ee.jakarta.tck.json.bind.defaultmapping.collections.model.SetContainer;

import java.util.HashSet;

@Bindable(deserializable = false)
public class CollectionsMappingTest$5 extends SetContainer {
    public CollectionsMappingTest$5(CollectionsMappingTest ignoreUnused, HashSet<String> instance) {
        setInstance(instance);
    }
}
