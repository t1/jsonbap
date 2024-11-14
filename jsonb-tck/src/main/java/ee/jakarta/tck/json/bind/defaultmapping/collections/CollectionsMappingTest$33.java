package ee.jakarta.tck.json.bind.defaultmapping.collections;

import com.github.t1.jsonbap.api.Bindable;
import ee.jakarta.tck.json.bind.defaultmapping.collections.model.DequeContainer;

import java.util.Deque;

@Bindable(deserializable = false)
public class CollectionsMappingTest$33 extends DequeContainer {
    public CollectionsMappingTest$33(CollectionsMappingTest ignoreUnused, Deque<String> instance) {
        setInstance(instance);
    }
}
