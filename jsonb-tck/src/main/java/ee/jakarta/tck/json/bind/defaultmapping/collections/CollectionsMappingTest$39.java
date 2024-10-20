package ee.jakarta.tck.json.bind.defaultmapping.collections;

import com.github.t1.jsonbap.api.Bindable;
import ee.jakarta.tck.json.bind.defaultmapping.collections.model.PriorityQueueContainer;

import java.util.PriorityQueue;

@Bindable
public class CollectionsMappingTest$39 extends PriorityQueueContainer {
    public CollectionsMappingTest$39(CollectionsMappingTest ignoreUnused, PriorityQueue<String> instance) {
        setInstance(instance);
    }
}
