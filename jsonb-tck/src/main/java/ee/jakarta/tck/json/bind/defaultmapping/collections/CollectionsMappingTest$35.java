package ee.jakarta.tck.json.bind.defaultmapping.collections;

import com.github.t1.jsonbap.api.Bindable;
import ee.jakarta.tck.json.bind.defaultmapping.collections.model.ArrayDequeContainer;

import java.util.ArrayDeque;

@Bindable(deserializable = false)
public class CollectionsMappingTest$35 extends ArrayDequeContainer {
    public CollectionsMappingTest$35(CollectionsMappingTest ignoreUnused, ArrayDeque<String> instance) {
        setInstance(instance);
    }
}
