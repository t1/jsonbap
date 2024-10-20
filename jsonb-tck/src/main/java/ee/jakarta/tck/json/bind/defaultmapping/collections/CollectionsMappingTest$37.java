package ee.jakarta.tck.json.bind.defaultmapping.collections;

import com.github.t1.jsonbap.api.Bindable;
import ee.jakarta.tck.json.bind.defaultmapping.collections.model.QueueContainer;

import java.util.Queue;

@Bindable
public class CollectionsMappingTest$37 extends QueueContainer {
    public CollectionsMappingTest$37(CollectionsMappingTest ignoreUnused, Queue<String> instance) {
        setInstance(instance);
    }
}
