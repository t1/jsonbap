package ee.jakarta.tck.json.bind.defaultmapping.collections;

import ee.jakarta.tck.json.bind.defaultmapping.collections.model.PriorityQueueContainer;

import java.util.PriorityQueue;

public class CollectionsMappingTest$39 extends PriorityQueueContainer {
    {
        PriorityQueue<String> instance = new PriorityQueue<>() {{
            add("Test 1");
            add("Test 2");
        }};
        setInstance(instance);
    }
}
